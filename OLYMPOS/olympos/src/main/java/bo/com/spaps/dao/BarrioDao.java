package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Barrio;
import bo.com.spaps.model.Compania;
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
public class BarrioDao extends
		DataAccessObjectJpa<Barrio, E, R, S, O, P, Q, U, V, W> {

	public BarrioDao() {
		super(Barrio.class);
	}

	public Barrio registrar(Barrio barrio) {
		try {
			beginTransaction();
			barrio = create(barrio);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Barrio " + barrio.getNombre());
			return barrio;
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

	public Barrio modificar(Barrio barrio) {
		try {
			beginTransaction();
			barrio = update(barrio);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Barrio " + barrio.getNombre());
			return barrio;
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

	public boolean eliminar(Barrio barrio) {
		try {
			beginTransaction();
			Barrio bar = modificar(barrio);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Barrio " + barrio.getNombre());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public Barrio obtenerBarrio(Integer id) {
		return findById(id);
	}

	public List<Barrio> obtenerBarrioOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<Barrio> obtenerBarrioOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<Barrio> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("id_compania", compania);
	}

}
