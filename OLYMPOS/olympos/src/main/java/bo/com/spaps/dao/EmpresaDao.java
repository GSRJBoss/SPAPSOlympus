package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

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
public class EmpresaDao extends
		DataAccessObjectJpa<Empresa, E, R, S, O, P, Q, U, V, W> {

	public EmpresaDao() {
		super(Empresa.class);
	}

	public Empresa registrar(Empresa empresa) {
		try {
			beginTransaction();
			empresa = create(empresa);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Empresa " + empresa.getDescripcion());
			return empresa;
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

	public Empresa modificar(Empresa empresa) {
		try {
			beginTransaction();
			empresa = update(empresa);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Empresa " + empresa.getDescripcion());
			return empresa;
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

	public boolean eliminar(Empresa empresa) {
		try {
			beginTransaction();
			Empresa bar = modificar(empresa);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Empresa " + empresa.getDescripcion());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public Empresa obtenerEmpresa(Integer id) {
		return findById(id);
	}

	public Empresa obtenerEmpresa(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	public List<Empresa> obtenerEmpresaOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<Empresa> obtenerEmpresaOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<Empresa> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	public List<Empresa> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	public List<Empresa> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

}
