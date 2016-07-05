package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Compania;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.model.UnidadVecinal;
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
public class UnidadVecinalDao extends
		DataAccessObjectJpa<UnidadVecinal, E, R, S, O, P, Q, U, V, W> {

	public UnidadVecinalDao() {
		super(UnidadVecinal.class);
	}

	public UnidadVecinal registrar(UnidadVecinal UnidadVecinal) {
		try {
			beginTransaction();
			UnidadVecinal = create(UnidadVecinal);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "UnidadVecinal "
					+ UnidadVecinal.getNumero());
			return UnidadVecinal;
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

	public UnidadVecinal modificar(UnidadVecinal UnidadVecinal) {
		try {
			beginTransaction();
			UnidadVecinal = update(UnidadVecinal);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "UnidadVecinal "
					+ UnidadVecinal.getNumero());
			return UnidadVecinal;
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

	public boolean eliminar(UnidadVecinal UnidadVecinal) {
		try {
			beginTransaction();
			UnidadVecinal bar = modificar(UnidadVecinal);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "UnidadVecinal "
					+ UnidadVecinal.getNumero());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public UnidadVecinal obtenerUnidadVecinal(Integer id) {
		return findById(id);
	}

	public UnidadVecinal obtenerUnidadVecinal(String numero) {
		return findByParameter("numero", numero);
	}

	public List<UnidadVecinal> obtenerUnidadVecinalOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<UnidadVecinal> obtenerUnidadVecinalOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<UnidadVecinal> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	public List<UnidadVecinal> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

}
