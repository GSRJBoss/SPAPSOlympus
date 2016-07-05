package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Agenda;
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
public class AgendaDao extends
		DataAccessObjectJpa<Agenda, E, R, S, O, P, Q, U, V, W> {

	public AgendaDao() {
		super(Agenda.class);
	}

	public Agenda registrar(Agenda agenda) {
		try {
			beginTransaction();
			agenda = create(agenda);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Agenda " + agenda.toString());
			return agenda;
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

	public Agenda modificar(Agenda agenda) {
		try {
			beginTransaction();
			agenda = update(agenda);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Agenda " + agenda.toString());
			return agenda;
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

	public boolean eliminar(Agenda agenda) {
		try {
			beginTransaction();
			Agenda bar = modificar(agenda);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Agenda " + agenda.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public Agenda obtenerAgenda(Integer id) {
		return findById(id);
	}

	public List<Agenda> obtenerAgendaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<Agenda> obtenerAgendaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<Agenda> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("id_compania", compania);
	}

}
