package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Compania;
import bo.com.spaps.model.GrupoFamiliar;
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
public class GrupoFamiliarDao extends
		DataAccessObjectJpa<GrupoFamiliar, E, R, S, O, P, Q, U, V, W> {

	private boolean isDelete = false;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public GrupoFamiliarDao() {
		super(GrupoFamiliar.class);
	}

	public GrupoFamiliar registrar(GrupoFamiliar grupoFamiliar) {
		try {
			beginTransaction();
			grupoFamiliar = create(grupoFamiliar);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "GrupoFamiliar "
					+ grupoFamiliar.getNombre());
			return grupoFamiliar;
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

	public GrupoFamiliar modificar(GrupoFamiliar grupoFamiliar) {
		try {
			beginTransaction();
			grupoFamiliar = update(grupoFamiliar);
			commitTransaction();
			if (!isDelete())
				FacesUtil.infoMessage("Modificación Correcta", "GrupoFamiliar "
						+ grupoFamiliar.getNombre());
			return grupoFamiliar;
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

	public boolean eliminar(GrupoFamiliar grupoFamiliar) {
		try {
			setDelete(true);
			grupoFamiliar.setEstado("RM");
			GrupoFamiliar bar = modificar(grupoFamiliar);
			setDelete(false);
			FacesUtil.infoMessage("Eliminación Correcta", "GrupoFamiliar "
					+ grupoFamiliar.getNombre());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	public GrupoFamiliar obtenerGrupoFamiliar(Integer id) {
		return findById(id);
	}

	public GrupoFamiliar obtenerGrupoFamiliarPorCodigo(String codigo) {
		return findByParameter("codigo", codigo);
	}

	public GrupoFamiliar obtenerGrupoFamiliarPorNombre(String nombre) {
		return findByParameter("nombre", nombre);
	}

	public List<GrupoFamiliar> obtenerGrupoFamiliarOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<GrupoFamiliar> obtenerGrupoFamiliarOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<GrupoFamiliar> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	public List<GrupoFamiliar> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

}
