package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Compania;
import bo.com.spaps.model.Provincia;
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
public class ProvinciaDao extends
		DataAccessObjectJpa<Provincia, E, R, S, O, P, Q, U, V, W> {

	public ProvinciaDao() {
		super(Provincia.class);
	}

	public Provincia registrar(Provincia provincia) {
		try {
			beginTransaction();
			provincia = create(provincia);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Provincia " + provincia.getNombre());
			return provincia;
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

	public Provincia modificar(Provincia provincia) {
		try {
			beginTransaction();
			provincia = update(provincia);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Provincia "
					+ provincia.getNombre());
			return provincia;
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

	public boolean eliminar(Provincia provincia) {
		try {
			beginTransaction();
			Provincia bar = modificar(provincia);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Provincia "
					+ provincia.getNombre());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public Provincia obtenerProvincia(Integer id) {
		return findById(id);
	}

	public List<Provincia> obtenerProvinciaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<Provincia> obtenerProvinciaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<Provincia> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("id_compania", compania);
	}

}
