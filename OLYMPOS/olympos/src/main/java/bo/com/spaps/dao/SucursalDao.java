package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Compania;
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
public class SucursalDao extends
		DataAccessObjectJpa<Sucursal, E, R, S, O, P, Q, U, V, W> {

	public SucursalDao() {
		super(Sucursal.class);
	}

	public Sucursal registrar(Sucursal sucursal) {
		try {
			beginTransaction();
			sucursal = create(sucursal);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Sucursal " + sucursal.getDescripcion());
			return sucursal;
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

	public Sucursal modificar(Sucursal sucursal) {
		try {
			beginTransaction();
			sucursal = update(sucursal);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Sucursal "
					+ sucursal.getDescripcion());
			return sucursal;
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

	public boolean eliminar(Sucursal sucursal) {
		try {
			beginTransaction();
			Sucursal bar = modificar(sucursal);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Sucursal "
					+ sucursal.getDescripcion());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public Sucursal obtenerSucursal(Integer id) {
		return findById(id);
	}

	public Sucursal obtenerSucursal(Integer id, Compania compania) {
		return findByParameterObjectTwo("id", "compania", id, compania.getId());
	}

	public Sucursal obtenerSucursal(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	public Sucursal obtenerSucursal(String descripcion, Compania compania) {
		return findByParameterObjectTwo("descripcion", "compania", descripcion,
				compania.getId());
	}

	public List<Sucursal> obtenerSucursalOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<Sucursal> obtenerSucursalOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<Sucursal> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("id_compania", compania);
	}

	public List<Sucursal> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	public List<Sucursal> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

}
