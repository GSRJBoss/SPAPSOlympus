package bo.com.spaps.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Compania;
import bo.com.spaps.model.Empresa;
import bo.com.spaps.model.Individuo;
import bo.com.spaps.model.PlanesSeguro;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.util.FacesUtil;
import bo.com.spaps.util.O;
import bo.com.spaps.util.P;
import bo.com.spaps.util.Q;
import bo.com.spaps.util.R;
import bo.com.spaps.util.S;
import bo.com.spaps.util.U;
import bo.com.spaps.util.V;
import bo.com.spaps.util.W;

@Stateless
public class IndividuoDao extends
		DataAccessObjectJpa<Individuo, PlanesSeguro, R, S, O, P, Q, U, V, W> {

	public IndividuoDao() {
		super(Individuo.class, PlanesSeguro.class);
	}

	public Individuo registrar(Individuo individuo,
			List<PlanesSeguro> planesSeguros) {
		try {
			beginTransaction();
			individuo = create(individuo);
			for (PlanesSeguro planesSeguro : planesSeguros) {
				planesSeguro.setIndividuo(individuo);
				planesSeguro.setFechaRegistro(new Date());
				planesSeguro = createE(planesSeguro);
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Individuo " + individuo.toString());
			return individuo;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al registrar");
			}
			rollbackTransaction();
			return null;
		}
	}

	/*
	 * public Individuo registrar(Individuo individuo) { try {
	 * beginTransaction(); individuo = create(individuo); commitTransaction();
	 * FacesUtil.infoMessage("Registro Correcto", "Individuo " +
	 * individuo.toString()); return individuo; } catch (Exception e) { String
	 * cause = e.getMessage(); if (cause .contains(
	 * "org.hibernate.exception.ConstraintViolationException: could not execute statement"
	 * )) { FacesUtil.errorMessage("Ya existe un registro igual."); } else {
	 * FacesUtil.errorMessage("Error al registrar"); } rollbackTransaction();
	 * return null; } }
	 */

	public Individuo modificar(Individuo individuo) {
		try {
			beginTransaction();
			individuo = update(individuo);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Individuo "
					+ individuo.toString());
			return individuo;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al modificar");
			}
			rollbackTransaction();
			return null;
		}
	}

	public boolean eliminar(Individuo individuo) {
		try {
			beginTransaction();
			Individuo bar = modificar(individuo);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Individuo "
					+ individuo.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public Individuo obtenerIndividuo(Integer id) {
		return findById(id);
	}

	public Individuo obtenerIndividuoPorFamilia(Integer id) {
		return findByParameter("grupoFamiliar", id);
	}

	public Individuo obtenerIndividuoPorFamilia(String codigo) {
		return findByParameter("grupoFamiliar", codigo);
	}

	public List<Individuo> obtenerIndividuosPorNombre(String nombre,
			String apellidoPaterno, String apellidoMaterno) {
		String query = "select em.* from individuo em where nombre like '"
				+ nombre + "' or apellidoPaterno like '" + apellidoPaterno
				+ "' or apellidoMaterno like '" + apellidoMaterno
				+ "' and estado='AC'";
		return executeQueryResulList(query);
	}

	public List<Individuo> obtenerIndividuosPorCodigo(String codigo) {
		String query = "select em.* from individuo em where codigo like '"
				+ codigo + "' and estado='AC'";
		return executeQueryResulList(query);
	}

	public List<Individuo> obtenerIndividuoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<Individuo> obtenerIndividuoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<Individuo> obtenerFamiliaPorCodigo(String codigo) {
		return findAllActivosByParameter("grupoFamiliar", codigo);
	}

	public List<Individuo> obtenerFamiliaPorId(Integer id) {
		return findAllActivosByParameter("grupoFamiliar", id);
	}

	public List<Individuo> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania);
	}

	public List<Individuo> obtenerPorEmpresa(Empresa empresa) {
		return findAllActivosByParameter("empresa", empresa);
	}

	public List<Individuo> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal);
	}

	public List<PlanesSeguro> obtenerPlanesDeIndividuo(Individuo individuo) {
		return findAllActivosByParameterE("individuo", individuo.getId());
	}

	public boolean VerificarSeguro(Individuo individuo) {
		return obtenerPlanesDeIndividuo(individuo).size() == 0;
	}

}
