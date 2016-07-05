/**
 * 
 */
package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Compania;
import bo.com.spaps.model.Modulo;
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

/**
 * @author Cesar Rojas
 *
 */
@Stateless
public class ModuloDao extends
		DataAccessObjectJpa<Modulo, E, R, S, O, P, Q, U, V, W> {

	/**
	 * 
	 */
	public ModuloDao() {
		super(Modulo.class);
	}

	public Modulo registrar(Modulo modulo) {
		try {
			beginTransaction();
			modulo = create(modulo);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Modulo " + modulo.toString());
			return modulo;
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

	public Modulo modificar(Modulo modulo) {
		try {
			beginTransaction();
			modulo = update(modulo);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Modulo " + modulo.toString());
			return modulo;
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

	public boolean eliminar(Modulo modulo) {
		try {
			beginTransaction();
			modulo.setEstado("RM");
			Modulo bar = modificar(modulo);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Modulo " + modulo.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public Modulo obtenerModulo(Integer id) {
		return findById(id);
	}

	public Modulo obtenerModuloPorNombre(String nombre) {
		return findByParameter("nombre", nombre);
	}

	public List<Modulo> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	public List<Modulo> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	public List<Modulo> obtenerModuloOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<Modulo> obtenerModuloOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

}
