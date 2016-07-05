/**
 * 
 */
package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Permiso;
import bo.com.spaps.model.Rol;
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

/**
 * @author Cesar Rojas
 *
 */
@Stateless
public class PermisoDao extends
		DataAccessObjectJpa<Permiso, E, R, S, O, P, Q, U, V, W> {

	/**
	 * 
	 */
	public PermisoDao() {
		super(Permiso.class);
	}

	public Permiso registrar(Permiso permiso) {
		try {
			beginTransaction();
			permiso = create(permiso);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Permiso " + permiso.toString());
			return permiso;
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

	public Permiso modificar(Permiso permiso) {
		try {
			beginTransaction();
			permiso = update(permiso);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Permiso " + permiso.toString());
			return permiso;
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

	public boolean eliminar(Permiso permiso) {
		try {
			beginTransaction();
			Permiso bar = modificar(permiso);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Permiso " + permiso.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public Permiso obtenerPermiso(Integer id) {
		return findById(id);
	}

	public Permiso obtenerPermisoPorNombre(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	public List<Permiso> obtenerPermisosDeRol(Integer id) {
		return findAllActivosByParameter("rol", id);
	}

	public List<Permiso> obtenerPermisosDeRol(String nombre) {
		return findAllActivosByParameter("rol", nombre);
	}

	public List<Permiso> obtenerPermisosDeMenuAccion(Integer id) {
		return findAllActivosByParameter("menuAccion", id);
	}

	public List<Permiso> obtenerPermisoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<Permiso> obtenerPermisoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}
	
	public List<Permiso> obtenerPorRol(Rol rol) {
		return findAllActiveParameter("rol", rol.getId());
	}

}
