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

import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.dao.SucursalDao;
import bo.com.spaps.model.Compania;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.util.FacesUtil;

@Named("sucursalController")
@ConversationScoped
/**
 * @author Cinthia Zabala
 *
 */
public class SucursalController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 440622384287445276L;

	/******* DAO **********/
	private @Inject SucursalDao sucursalDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Sucursal sucursal;
	private Sucursal sucursalSelected;
	private Compania compania;

	/******* LIST **********/
	private List<Sucursal> listaSucursal;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public SucursalController() {
	}

	public Compania getCompania() {
		return compania;
	}

	public void setCompania(Compania compania) {
		this.compania = compania;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Sucursal getSucursalSelected() {
		return sucursalSelected;
	}

	public void setSucursalSelected(Sucursal sucursalSelected) {
		this.sucursalSelected = sucursalSelected;
	}

	public List<Sucursal> getListaSucursal() {
		return listaSucursal;
	}

	public void setListaSucursal(List<Sucursal> listaSucursal) {
		this.listaSucursal = listaSucursal;
	}

	public boolean isModificar() {
		return modificar;
	}

	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	public boolean isRegistrar() {
		return registrar;
	}

	public void setRegistrar(boolean registrar) {
		this.registrar = registrar;
	}

	public boolean isCrear() {
		return crear;
	}

	public void setCrear(boolean crear) {
		this.crear = crear;
	}

	@PostConstruct
	public void initNew() {
		sucursal = new Sucursal();
		sucursalSelected = new Sucursal();
		sucursal = sessionMain.getSucursalLogin();
		compania = new Compania();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (sucursal.getDescripcion().trim().isEmpty()
					|| sucursal.getDireccion().trim().isEmpty()
					|| sucursal.getEstado().trim().isEmpty()
					|| getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				sucursal.setCompania(getCompania());
				sucursal.setFechaRegistro(new Date());
				sucursal.setFechaModificacion(sucursal.getFechaRegistro());
				sucursal.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				Sucursal r = sucursalDao.registrar(sucursal);
				if (r != null) {
					FacesUtil.infoMessage("Sucursal registrado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de sucursal: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (sucursal.getDescripcion().trim().isEmpty()
					|| sucursal.getDireccion().trim().isEmpty()
					|| sucursal.getEstado().trim().isEmpty()
					|| getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				sucursal.setCompania(getCompania());
				sucursal.setFechaModificacion(new Date());
				sucursal.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				Sucursal r = sucursalDao.modificar(sucursal);
				if (r != null) {
					FacesUtil.infoMessage("Sucursal actualizado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de sucursal: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (sucursalDao.eliminar(sucursal)) {
				FacesUtil
						.infoMessage("Sucursal Eliminado", sucursal.toString());
				initNew();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de sucursal: "
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
