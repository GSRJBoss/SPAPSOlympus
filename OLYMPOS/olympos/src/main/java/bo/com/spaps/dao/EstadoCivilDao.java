package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Compania;
import bo.com.spaps.model.EstadoCivil;
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
public class EstadoCivilDao extends
		DataAccessObjectJpa<EstadoCivil, E, R, S, O, P, Q, U, V, W> {

	private boolean isDelete = false;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public EstadoCivilDao() {
		super(EstadoCivil.class);
	}

	public EstadoCivil registrar(EstadoCivil EstadoCivil) {
		try {
			beginTransaction();
			EstadoCivil = create(EstadoCivil);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "EstadoCivil "
					+ EstadoCivil.getDescripcion());
			return EstadoCivil;
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

	public EstadoCivil modificar(EstadoCivil EstadoCivil) {
		try {
			beginTransaction();
			EstadoCivil = update(EstadoCivil);
			commitTransaction();
			if (!isDelete())
				FacesUtil.infoMessage("Modificación Correcta", "EstadoCivil "
						+ EstadoCivil.getDescripcion());
			return EstadoCivil;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				if (!isDelete())
					FacesUtil.errorMessage("Error al modificar");
			}
			rollbackTransaction();
			return null;
		}
	}

	public boolean eliminar(EstadoCivil EstadoCivil) {
		try {
			setDelete(true);
			EstadoCivil.setEstado("RM");
			EstadoCivil bar = modificar(EstadoCivil);
			FacesUtil.infoMessage("Eliminación Correcta", "Grupo Sanguineo "
					+ EstadoCivil.toString());
			setDelete(false);
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	public EstadoCivil obtenerEstadoCivil(Integer id) {
		return findById(id);
	}

	public EstadoCivil obtenerEstadoCivil(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	public List<EstadoCivil> obtenerEstadoCivilOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<EstadoCivil> obtenerEstadoCivilOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<EstadoCivil> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	public List<EstadoCivil> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	public List<EstadoCivil> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

}
