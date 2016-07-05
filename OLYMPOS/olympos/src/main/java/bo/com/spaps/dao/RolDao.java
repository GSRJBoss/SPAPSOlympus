/**
 * 
 */
package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Rol;
import bo.com.spaps.model.Usuario;
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
public class RolDao extends DataAccessObjectJpa<Rol, E, R, S, O, P, Q, U, V, W> {

	/**
	 * 
	 */
	public RolDao() {
		super(Rol.class);
	}

	public Rol registrar(Rol rol) {
		try {
			beginTransaction();
			rol = create(rol);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Rol " + rol.toString());
			return rol;
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

	public Rol modificar(Rol rol) {
		try {
			beginTransaction();
			rol = update(rol);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Rol " + rol.toString());
			return rol;
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

	public boolean eliminar(Rol rol) {
		try {
			beginTransaction();
			Rol bar = modificar(rol);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Rol " + rol.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public Rol obtenerRol(Integer id) {
		return findById(id);
	}

	public Rol obtenerRolPorNombre(String nombre) {
		return findByParameter("nombre", nombre);
	}

	public List<Rol> obtenerRolChilds(Integer id) {
		return findAllActivosByParameter("rol", id);
	}

	public List<Rol> obtenerRolChilds(String nombre) {
		return findAllActivosByParameter("nombre", nombre);
	}

	public List<Rol> obtenerRolOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<Rol> obtenerRolOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}
	
	

}
