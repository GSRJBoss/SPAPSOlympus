package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Compania;
import bo.com.spaps.model.PlanSeguro;
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
public class PlanSeguroDao extends
		DataAccessObjectJpa<PlanSeguro, E, R, S, O, P, Q, U, V, W> {

	public PlanSeguroDao() {
		super(PlanSeguro.class);
	}

	public PlanSeguro registrar(PlanSeguro planSeguro) {
		try {
			beginTransaction();
			planSeguro = create(planSeguro);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "PlanSeguro "
					+ planSeguro.getDescripcion());
			return planSeguro;
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

	public PlanSeguro modificar(PlanSeguro planSeguro) {
		try {
			beginTransaction();
			planSeguro = update(planSeguro);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "PlanSeguro "
					+ planSeguro.getDescripcion());
			return planSeguro;
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

	public boolean eliminar(PlanSeguro planSeguro) {
		try {
			beginTransaction();
			PlanSeguro bar = modificar(planSeguro);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "PlanSeguro "
					+ planSeguro.getDescripcion());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public PlanSeguro obtenerPlanSeguro(Integer id) {
		return findById(id);
	}

	public PlanSeguro obtenrPlanSeguro(Integer id, Compania compania) {
		return findByParameterObjectTwo("id", "compania", id, compania.getId());
	}

	public PlanSeguro obtenerPlanSeguro(String codigo) {
		return findByParameter("codigo", codigo);
	}

	public PlanSeguro obtenerPlanSeguro(String codigo, Compania compania) {
		return findByParameterObjectTwo("codigo", "compania", codigo,
				compania.getId());
	}

	public PlanSeguro obtenerPlanSeguroPorDescripcion(String descripcion) {
		return findByParameter("descripcion", descripcion);
	}

	public PlanSeguro obtenerPlanSeguroPorDescripcion(String descripcion,
			Compania compania) {
		return findByParameterObjectTwo("descripcion", "compania", descripcion,
				compania.getId());
	}

	public List<PlanSeguro> obtenerPlanSeguroOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<PlanSeguro> obtenerPlanSeguroOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<PlanSeguro> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	public List<PlanSeguro> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	public List<PlanSeguro> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

}
