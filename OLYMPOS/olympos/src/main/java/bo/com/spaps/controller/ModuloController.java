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

import bo.com.spaps.dao.ModuloDao;
import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.model.Modulo;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.util.FacesUtil;

@Named("moduloController")
@ConversationScoped
/**
 * @author Cesar Rojas
 *
 */
public class ModuloController implements Serializable {

	/******* DAO **********/
	private @Inject ModuloDao moduloDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Modulo modulo;
	private Modulo moduloSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<Modulo> listaModulo;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3239366648666864146L;

	/**
	 * 
	 */
	public ModuloController() {
	}

	public Modulo getModulo() {
		return modulo;
	}

	public Modulo getModuloSelected() {
		return moduloSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Modulo> getListaModulo() {
		return listaModulo;
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

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public void setModuloSelected(Modulo moduloSelected) {
		this.moduloSelected = moduloSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaModulo(List<Modulo> listaModulo) {
		this.listaModulo = listaModulo;
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
		modulo = new Modulo();
		moduloSelected = new Modulo();
		sucursal = sessionMain.getSucursalLogin();
		listaModulo = moduloDao.obtenerPorSucursal(sucursal);
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (modulo.getNombre().trim().isEmpty()
					|| modulo.getTipo().trim().isEmpty()
					|| modulo.getEstado().trim().isEmpty()) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				modulo.setFechaRegistro(new Date());
				modulo.setFechaModificacion(modulo.getFechaRegistro());
				modulo.setUsuarioRegistro(sessionMain.getUsuarioLogin().getId());
				Modulo r = moduloDao.registrar(modulo);
				if (r != null) {
					FacesUtil.infoMessage("Modulo registrado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out
					.println("Error en registro de modulo: " + e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (modulo.getNombre().trim().isEmpty()
					|| modulo.getTipo().trim().isEmpty()
					|| modulo.getEstado().trim().isEmpty()) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				modulo.setFechaModificacion(new Date());
				modulo.setUsuarioRegistro(sessionMain.getUsuarioLogin().getId());
				Modulo r = moduloDao.modificar(modulo);
				if (r != null) {
					FacesUtil.infoMessage("Modulo actualizado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de modulo: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (moduloDao.eliminar(modulo)) {
				FacesUtil.infoMessage("Modulo Eliminado", modulo.toString());
				initNew();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de modulo: "
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
