package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Cargo;
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
public class CargoDao extends
		DataAccessObjectJpa<Cargo, E, R, S, O, P, Q, U, V, W> {

	public CargoDao() {
		super(Cargo.class);
	}

	public Cargo registrar(Cargo cargo) {
		try {
			beginTransaction();
			cargo = create(cargo);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Cargo " + cargo.getDescripcion());
			return cargo;
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

	public Cargo modificar(Cargo cargo) {
		try {
			beginTransaction();
			cargo = update(cargo);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Cargo " + cargo.getDescripcion());
			return cargo;
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

	public boolean eliminar(Cargo cargo) {
		try {
			beginTransaction();
			Cargo bar = modificar(cargo);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Cargo " + cargo.getDescripcion());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public Cargo obtenerCargo(Integer id) {
		return findById(id);
	}

	public Cargo obtenerCargo(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	public List<Cargo> obtenerCargoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<Cargo> obtenerCargoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<Cargo> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	public List<Cargo> obtenerPorEmpresa(Empresa empresa) {
		return findAllActivosByParameter("empresa", empresa.getId());
	}

	public List<Cargo> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	public List<Cargo> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

}
