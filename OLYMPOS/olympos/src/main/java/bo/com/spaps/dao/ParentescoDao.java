/**
 * 
 */
package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Compania;
import bo.com.spaps.model.Empresa;
import bo.com.spaps.model.Parentesco;
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

/**
 * @author ANITA
 *
 */
@Stateless
public class ParentescoDao extends
		DataAccessObjectJpa<Parentesco, E, R, S, O, P, Q, U, V, W> {

	public ParentescoDao() {
		super(Parentesco.class);
	}

	public Parentesco registrar(Parentesco parentesco) {
		try {
			beginTransaction();
			parentesco = create(parentesco);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "Parentesco "
					+ parentesco.toString());
			return parentesco;
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

	public Parentesco modificar(Parentesco parentesco) {
		try {
			beginTransaction();
			parentesco = update(parentesco);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta", "Parentesco "
					+ parentesco.toString());
			return parentesco;
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

	public boolean eliminar(Parentesco parentesco) {
		try {
			beginTransaction();
			Parentesco bar = modificar(parentesco);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta", "Parentesco "
					+ parentesco.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public Parentesco obtenerParentesco(Integer id) {
		return findById(id);
	}

	public List<Parentesco> obtenerParentescoOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<Parentesco> obtenerParentescoOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<Parentesco> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania);
	}

	public List<Parentesco> obtenerPorEmpresa(Empresa empresa) {
		return findAllActivosByParameter("empresa", empresa);
	}

	public List<Parentesco> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal);
	}
}
