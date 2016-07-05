package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Compania;
import bo.com.spaps.model.NivelAcademico;
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
public class NivelAcademicoDao extends
		DataAccessObjectJpa<NivelAcademico, E, R, S, O, P, Q, U, V, W> {

	public NivelAcademicoDao() {
		super(NivelAcademico.class);
	}

	public NivelAcademico registrar(NivelAcademico NivelAcademico) {
		try {
			beginTransaction();
			NivelAcademico = create(NivelAcademico);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "NivelAcademico "
					+ NivelAcademico.getDescripcion());
			return NivelAcademico;
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

	public NivelAcademico modificar(NivelAcademico NivelAcademico) {
		try {
			beginTransaction();
			NivelAcademico = update(NivelAcademico);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "NivelAcademico "
					+ NivelAcademico.getDescripcion());
			return NivelAcademico;
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

	public boolean eliminar(NivelAcademico NivelAcademico) {
		try {
			beginTransaction();
			NivelAcademico bar = modificar(NivelAcademico);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "NivelAcademico "
					+ NivelAcademico.getDescripcion());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public NivelAcademico obtenerNivelAcademico(Integer id) {
		return findById(id);
	}

	public List<NivelAcademico> obtenerNivelAcademicoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<NivelAcademico> obtenerNivelAcademicoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<NivelAcademico> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("id_compania", compania);
	}

}
