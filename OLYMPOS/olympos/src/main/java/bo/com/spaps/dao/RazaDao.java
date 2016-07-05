package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Compania;
import bo.com.spaps.model.Raza;
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
public class RazaDao extends
		DataAccessObjectJpa<Raza, E, R, S, O, P, Q, U, V, W> {

	private boolean isDelete = false;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public RazaDao() {
		super(Raza.class);
	}

	public Raza registrar(Raza Raza) {
		try {
			beginTransaction();
			Raza = create(Raza);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Raza " + Raza.getNombre());
			return Raza;
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

	public Raza modificar(Raza Raza) {
		try {
			beginTransaction();
			Raza = update(Raza);
			commitTransaction();
			if (!isDelete())
				FacesUtil.infoMessage("Modificación Correcta",
						"Raza " + Raza.getNombre());
			return Raza;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				if (!isDelete())
					FacesUtil.errorMessage("Error al modificar");
			}
			rollbackTransaction();
			return null;
		}
	}

	public boolean eliminar(Raza Raza) {
		try {
			setDelete(true);
			Raza.setEstado("RM");
			Raza bar = modificar(Raza);
			FacesUtil.infoMessage("Eliminación Correcta", "Grupo Sanguineo "
					+ Raza.toString());
			setDelete(false);
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	public Raza obtenerRaza(Integer id) {
		return findById(id);
	}

	public List<Raza> obtenerRazaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<Raza> obtenerRazaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<Raza> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	public List<Raza> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

	public List<Raza> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

}
