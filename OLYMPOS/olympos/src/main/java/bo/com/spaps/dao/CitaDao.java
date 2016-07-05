package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Cita;
import bo.com.spaps.model.Compania;
import bo.com.spaps.model.Empresa;
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
public class CitaDao extends
		DataAccessObjectJpa<Cita, E, R, S, O, P, Q, U, V, W> {

	public CitaDao() {
		super(Cita.class);
	}

	public Cita registrar(Cita cita) {
		try {
			beginTransaction();
			cita = create(cita);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Cita " + cita.toString());
			return cita;
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

	public Cita modificar(Cita cita) {
		try {
			beginTransaction();
			cita = update(cita);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Cita " + cita.toString());
			return cita;
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

	public boolean eliminar(Cita cita) {
		try {
			beginTransaction();
			Cita bar = modificar(cita);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Cita " + cita.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public Cita obtenerCita(Integer id) {
		return findById(id);
	}

	public List<Cita> obtenerCitaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<Cita> obtenerCitaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<Cita> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("id_compania", compania);
	}

	public List<Cita> obtenerPorEmpresa(Empresa empresa) {
		return findAllActivosByParameter("id_empresa", empresa);
	}

	public List<Cita> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("id_sucursal", sucursal);
	}

}
