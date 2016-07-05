package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Compania;
import bo.com.spaps.model.Especialidad;
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
public class EspecialidadDao extends
		DataAccessObjectJpa<Especialidad, E, R, S, O, P, Q, U, V, W> {

	public EspecialidadDao() {
		super(Especialidad.class);
	}

	public Especialidad registrar(Especialidad especialidad) {
		try {
			beginTransaction();
			especialidad = create(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Especialidad "
					+ especialidad.toString());
			return especialidad;
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

	public Especialidad modificar(Especialidad especialidad) {
		try {
			beginTransaction();
			especialidad = update(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Especialidad "
					+ especialidad.toString());
			return especialidad;
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

	public boolean eliminar(Especialidad especialidad) {
		try {
			beginTransaction();
			Especialidad bar = modificar(especialidad);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Especialidad "
					+ especialidad.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public Especialidad obtenerEspecialidad(Integer id) {
		return findById(id);
	}

	public List<Especialidad> obtenerEspecialidadOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<Especialidad> obtenerEspecialidadOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<Especialidad> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("id_compania", compania);
	}

}
