/**
 * 
 */
package bo.com.spaps.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import bo.com.spaps.dao.ProvinciaDao;
import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.model.Departamento;
import bo.com.spaps.model.Provincia;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.util.FacesUtil;

@Named("provinciaController")
@ConversationScoped
/**
 * @author ANITA
 *
 */
public class ProvinciaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4655926156628536668L;

	/******* DAO **********/
	private @Inject ProvinciaDao provinciaDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Provincia provincia;
	private Provincia provinciaSelected;
	private Sucursal sucursal;
	private Departamento departamento;

	/******* LIST **********/
	private List<Provincia> listaProvincia;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public ProvinciaController() {
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public Provincia getProvinciaSelected() {
		return provinciaSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Provincia> getListaProvincia() {
		return listaProvincia;
	}

	public boolean isModificar() {
		return modificar;
	}

	public boolean isRegistrar() {
		return registrar;
	}

	public boolean isCrear() {
		return crear;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public void setProvinciaSelected(Provincia provinciaSelected) {
		this.provinciaSelected = provinciaSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaProvincia(List<Provincia> listaProvincia) {
		this.listaProvincia = listaProvincia;
	}

	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	public void setRegistrar(boolean registrar) {
		this.registrar = registrar;
	}

	public void setCrear(boolean crear) {
		this.crear = crear;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	@PostConstruct
	public void initNew() {
		provincia = new Provincia();
		provinciaSelected = new Provincia();
		sucursal = sessionMain.getSucursalLogin();
		departamento = new Departamento();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (provincia.getNombre().trim().isEmpty()
					|| provincia.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null
					|| getDepartamento() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				provincia.setCompania(getSucursal().getCompania());
				provincia.setSucursal(getSucursal());
				provincia.setDepartamento(getDepartamento());
				provincia.setFechaRegistro(new Date());
				provincia.setFechaModificacion(provincia.getFechaRegistro());
				provincia.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				Provincia r = provinciaDao.registrar(provincia);
				if (r != null) {
					FacesUtil.infoMessage("Provincia registrado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de provincia: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (provincia.getNombre().trim().isEmpty()
					|| provincia.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null
					|| getDepartamento() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				provincia.setCompania(getSucursal().getCompania());
				provincia.setSucursal(getSucursal());
				provincia.setDepartamento(getDepartamento());
				provincia.setFechaModificacion(new Date());
				provincia.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				Provincia r = provinciaDao.modificar(provincia);
				if (r != null) {
					FacesUtil
							.infoMessage("Provincia actualizado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de provincia: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (provinciaDao.eliminar(provincia)) {
				FacesUtil.infoMessage("Provincia Eliminado",
						provincia.toString());
				initNew();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de provincia: "
					+ e.getMessage());
		}
	}

	public void initConversation() {
		if (!FacesContext.getCurrentInstance().isPostback()
				&& conversation.isTransient()) {
			conversation.begin();
			System.out.println(">>>>>>>>>> CONVERSACION INICIADA...");
		}
	}

	public String endConversation() {
		if (!conversation.isTransient()) {
			conversation.end();
			System.out.println(">>>>>>>>>> CONVERSACION TERMINADA...");
		}
		return "kardex_producto.xhtml?faces-redirect=true";
	}

}
