package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Compania;
import bo.com.spaps.model.GrupoSanguineo;
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
public class GrupoSanguineoDao extends
		DataAccessObjectJpa<GrupoSanguineo, E, R, S, O, P, Q, U, V, W> {

	private boolean isDelete = false;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public GrupoSanguineoDao() {
		super(GrupoSanguineo.class);
	}

	public GrupoSanguineo registrar(GrupoSanguineo GrupoSanguineo) {
		try {
			beginTransaction();
			GrupoSanguineo = create(GrupoSanguineo);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Grupo Sanguineo "
					+ GrupoSanguineo.getDescripcion());
			return GrupoSanguineo;
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

	public GrupoSanguineo modificar(GrupoSanguineo GrupoSanguineo) {
		try {
			beginTransaction();
			GrupoSanguineo = update(GrupoSanguineo);
			commitTransaction();
			if (!isDelete())
				FacesUtil.infoMessage("Modificación Correcta",
						"Grupo Sanguineo " + GrupoSanguineo.getDescripcion());
			return GrupoSanguineo;
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

	public boolean eliminar(GrupoSanguineo GrupoSanguineo) {
		try {
			setDelete(true);
			GrupoSanguineo.setEstado("RM");
			GrupoSanguineo bar = modificar(GrupoSanguineo);
			setDelete(false);
			FacesUtil.infoMessage("Eliminación Correcta", "Grupo Sanguineo "
					+ GrupoSanguineo.getDescripcion());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	public GrupoSanguineo obtenerGrupoSanguineo(Integer id) {
		return findById(id);
	}

	public GrupoSanguineo obtenerPorDescripcion(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	public List<GrupoSanguineo> obtenerGrupoSanguineoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<GrupoSanguineo> obtenerGrupoSanguineoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<GrupoSanguineo> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	public List<GrupoSanguineo> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

	public List<GrupoSanguineo> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

}
