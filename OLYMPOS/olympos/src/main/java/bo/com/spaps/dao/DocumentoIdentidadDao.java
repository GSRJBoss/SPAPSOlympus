package bo.com.spaps.dao;

import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Compania;
import bo.com.spaps.model.DocumentoIdentidad;
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
public class DocumentoIdentidadDao extends
		DataAccessObjectJpa<DocumentoIdentidad, E, R, S, O, P, Q, U, V, W> {

	private boolean isDelete = false;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public DocumentoIdentidadDao() {
		super(DocumentoIdentidad.class);
	}

	public DocumentoIdentidad registrar(DocumentoIdentidad DocumentoIdentidad) {
		try {
			beginTransaction();
			DocumentoIdentidad = create(DocumentoIdentidad);
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto", "DocumentoIdentidad "
					+ DocumentoIdentidad.getNombre());
			return DocumentoIdentidad;
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

	public DocumentoIdentidad modificar(DocumentoIdentidad DocumentoIdentidad) {
		try {
			beginTransaction();
			DocumentoIdentidad = update(DocumentoIdentidad);
			commitTransaction();
			if (!isDelete())
				FacesUtil.infoMessage("Modificación Correcta",
						"DocumentoIdentidad " + DocumentoIdentidad.getNombre());
			return DocumentoIdentidad;
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

	public boolean eliminar(DocumentoIdentidad DocumentoIdentidad) {
		try {
			setDelete(true);
			DocumentoIdentidad.setEstado("RM");
			DocumentoIdentidad bar = modificar(DocumentoIdentidad);
			FacesUtil.infoMessage("Eliminación Correcta", "Grupo Sanguineo "
					+ DocumentoIdentidad.toString());
			setDelete(false);
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			return false;
		}
	}

	public DocumentoIdentidad obtenerDocumentoIdentidad(Integer id) {
		return findById(id);
	}

	public List<DocumentoIdentidad> obtenerDocumentoIdentidadOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<DocumentoIdentidad> obtenerDocumentoIdentidadOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

	public List<DocumentoIdentidad> obtenerPorCompania(Compania compania) {
		return findAllActivosByParameter("compania", compania.getId());
	}

	public List<DocumentoIdentidad> obtenerPorSucursal(Sucursal sucursal) {
		return findAllActivosByParameter("sucursal", sucursal.getId());
	}

	public List<DocumentoIdentidad> obtenerAllActivos() {
		return findAllByEstadoOrderByAsc("AC");
	}

}
