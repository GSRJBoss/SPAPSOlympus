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

import bo.com.spaps.dao.AccionDao;
import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.model.Accion;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.util.FacesUtil;

@Named("accionController")
@ConversationScoped
/**
 * @author ANITA
 *
 */
public class AccionController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3535243222597988887L;

	/******* DAO **********/
	private @Inject AccionDao accionDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Accion accion;
	private Accion accionSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<Accion> listaAccion;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public AccionController() {
	}

	public Accion getAccion() {
		return accion;
	}

	public Accion getAccionSelected() {
		return accionSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Accion> getListaAccion() {
		return listaAccion;
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

	public void setAccion(Accion accion) {
		this.accion = accion;
	}

	public void setAccionSelected(Accion accionSelected) {
		this.accionSelected = accionSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaAccion(List<Accion> listaAccion) {
		this.listaAccion = listaAccion;
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

	@PostConstruct
	public void initNew() {
		accion = new Accion();
		accionSelected = new Accion();
		sucursal = sessionMain.getSucursalLogin();
		listaAccion = accionDao.obtenerPorSucursal(sucursal);
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (accion.getNombre().trim().isEmpty()
					|| accion.getTipo().trim().isEmpty()
					|| accion.getEstado().trim().isEmpty()) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				accion.setFechaRegistro(new Date());
				accion.setFechaModificacion(accion.getFechaRegistro());
				accion.setUsuarioRegistro(sessionMain.getUsuarioLogin().getId());
				Accion r = accionDao.registrar(accion);
				if (r != null) {
					FacesUtil.infoMessage("Accion registrado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out
					.println("Error en registro de accion: " + e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (accion.getNombre().trim().isEmpty()
					|| accion.getTipo().trim().isEmpty()
					|| accion.getEstado().trim().isEmpty()) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				accion.setFechaModificacion(new Date());
				accion.setUsuarioRegistro(sessionMain.getUsuarioLogin().getId());
				Accion r = accionDao.modificar(accion);
				if (r != null) {
					FacesUtil.infoMessage("Accion actualizado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de accion: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (accionDao.eliminar(accion)) {
				FacesUtil.infoMessage("Accion Eliminado", accion.toString());
				initNew();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de accion: "
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
