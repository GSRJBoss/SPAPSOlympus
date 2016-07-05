package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

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
public class CompaniaDao extends
		DataAccessObjectJpa<Compania, E, R, S, O, P, Q, U, V, W> {

	public CompaniaDao() {
		super(Compania.class);
	}

	public Compania registrar(Compania compania) {
		try {
			beginTransaction();
			compania = create(compania);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Compania " + compania.getDescripcion());
			return compania;
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

	public Compania modificar(Compania compania) {
		try {
			beginTransaction();
			compania = update(compania);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Compania "
					+ compania.getDescripcion());
			return compania;
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

	public boolean eliminar(Compania compania) {
		try {
			beginTransaction();
			Compania bar = modificar(compania);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Compania "
					+ compania.getDescripcion());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public Compania obtenerCompania(Integer id) {
		return findById(id);
	}

	public Compania obtenerCompania(Integer id, Compania compania) {
		return findByParameterObjectTwo("id", "compania", id, compania.getId());
	}

	public Compania obtenerCompania(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	public List<Compania> obtenerCompaniaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<Compania> obtenerCompaniaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<Compania> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

}
