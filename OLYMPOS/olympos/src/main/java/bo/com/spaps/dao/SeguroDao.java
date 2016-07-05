package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Compania;
import bo.com.spaps.model.Seguro;
import bo.com.spaps.model.Sucursal;
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
public class SeguroDao extends
		DataAccessObjectJpa<Seguro, E, R, S, O, P, Q, U, V, W> {

	public SeguroDao() {
		super(Seguro.class);
	}

	public Seguro registrar(Seguro seguro) {
		try {
			beginTransaction();
			seguro = create(seguro);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Seguro " + seguro.getNombre());
			return seguro;
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

	public Seguro modificar(Seguro seguro) {
		try {
			beginTransaction();
			seguro = update(seguro);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Seguro " + seguro.getNombre());
			return seguro;
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

	public boolean eliminar(Seguro seguro) {
		try {
			beginTransaction();
			Seguro bar = modificar(seguro);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Seguro " + seguro.getNombre());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public Seguro obtenerSeguro(Integer id) {
		return findById(id);
	}

	public Seguro obtenerSeguro(Integer id, Compania compania) {
		return findByParameterObjectTwo("id", "compania", id, compania.getId());
	}

	public Seguro obtenerSeguro(String codigo) {
		return findByParameter("codigo", codigo);
	}

	public Seguro obtenerSeguro(String codigo, Compania compania) {
		return findByParameterObjectTwo("codigo", "compania", codigo,
				compania.getId());
	}

	public Seguro obtenerSeguroPorDescripcion(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	public Seguro obtenerSeguroPorDescripcion(String descripcion,
			Compania compania) {
		return findByParameterObjectTwo("descripcion", "compania", descripcion,
				compania.getId());
	}

	public List<Seguro> obtenerSeguroOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<Seguro> obtenerSeguroOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<Seguro> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	public List<Seguro> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	public List<Seguro> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

}
