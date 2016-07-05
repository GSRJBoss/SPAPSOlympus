package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Compania;
import bo.com.spaps.model.Departamento;
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
public class DepartamentoDao extends
		DataAccessObjectJpa<Departamento, E, R, S, O, P, Q, U, V, W> {

	public DepartamentoDao() {
		super(Departamento.class);
	}

	public Departamento registrar(Departamento departamento) {
		try {
			beginTransaction();
			departamento = create(departamento);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Departamento "
					+ departamento.getNombre());
			return departamento;
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

	public Departamento modificar(Departamento departamento) {
		try {
			beginTransaction();
			departamento = update(departamento);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Departamento "
					+ departamento.getNombre());
			return departamento;
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

	public boolean eliminar(Departamento departamento) {
		try {
			beginTransaction();
			Departamento bar = modificar(departamento);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Departamento "
					+ departamento.getNombre());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public Departamento obtenerDepartamento(Integer id) {
		return findById(id);
	}

	public List<Departamento> obtenerDepartamentoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<Departamento> obtenerDepartamentoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<Departamento> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("id_compania", compania);
	}

}
