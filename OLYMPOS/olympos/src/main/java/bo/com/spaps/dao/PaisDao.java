package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Compania;
import bo.com.spaps.model.Pais;
import bo.com.spaps.util.E;
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
public class PaisDao extends
		DataAccessObjectJpa<Pais, E, R, S, O, P, Q, U, V, W> {

	public PaisDao() {
		super(Pais.class);
	}

	public Pais registrar(Pais pais) {
		try {
			beginTransaction();
			pais = create(pais);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Pais " + pais.getNombre());
			return pais;
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

	public Pais modificar(Pais pais) {
		try {
			beginTransaction();
			pais = update(pais);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Pais " + pais.getNombre());
			return pais;
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

	public boolean eliminar(Pais pais) {
		try {
			beginTransaction();
			Pais bar = modificar(pais);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Pais " + pais.getNombre());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public Pais obtenerPais(Integer id) {
		return findById(id);
	}

	public List<Pais> obtenerPaisOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<Pais> obtenerPaisOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<Pais> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("id_compania", compania);
	}

}
