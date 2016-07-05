package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Compania;
import bo.com.spaps.model.Personal;
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
public class PersonalDao extends
		DataAccessObjectJpa<Personal, E, R, S, O, P, Q, U, V, W> {

	public PersonalDao() {
		super(Personal.class);
	}

	public Personal registrar(Personal personal) {
		try {
			beginTransaction();
			personal = create(personal);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Personal " + personal.toString());
			return personal;
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

	public Personal modificar(Personal personal) {
		try {
			beginTransaction();
			personal = update(personal);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Personal "
					+ personal.toString());
			return personal;
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

	public boolean eliminar(Personal personal) {
		try {
			beginTransaction();
			Personal bar = modificar(personal);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Personal "
					+ personal.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public Personal obtenerPersonal(Integer id) {
		return findById(id);
	}

	public List<Personal> obtenerPersonalOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<Personal> obtenerPersonalOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<Personal> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("id_compania", compania);
	}

}
