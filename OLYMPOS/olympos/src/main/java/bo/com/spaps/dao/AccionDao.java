/**
 * 
 */
package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Accion;
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
public class AccionDao extends
		DataAccessObjectJpa<Accion, E, R, S, O, P, Q, U, V, W> {

	/**
	 * 
	 */
	public AccionDao() {
		super(Accion.class);
	}

	public Accion registrar(Accion accion) {
		try {
			beginTransaction();
			accion = create(accion);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Accion " + accion.toString());
			return accion;
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

	public Accion modificar(Accion accion) {
		try {
			beginTransaction();
			accion = update(accion);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Accion " + accion.toString());
			return accion;
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

	public boolean eliminar(Accion accion) {
		try {
			beginTransaction();
			Accion bar = modificar(accion);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Accion " + accion.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public Accion obtenerAccion(Integer id) {
		return findById(id);
	}

	public Accion obtenerAccionPorNombre(String nombre) {
		return findByParameter("nombre", nombre);
	}

	public List<Accion> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	public List<Accion> obtenerAccionOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<Accion> obtenerAccionOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

}
