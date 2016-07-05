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

import bo.com.spaps.dao.RolDao;
import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.model.Rol;
import bo.com.spaps.util.FacesUtil;

@Named("rolController")
@ConversationScoped
/**
 * @author Cinthia Zabala
 *
 */
public class RolController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7072215585036377920L;

	/******* DAO **********/
	private @Inject RolDao rolDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Rol rol;
	private Rol rolSelected;
	private Rol rolPadre;

	/******* LIST **********/
	private List<Rol> listaRol;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public RolController() {
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Rol getRolSelected() {
		return rolSelected;
	}

	public void setRolSelected(Rol rolSelected) {
		this.rolSelected = rolSelected;
	}

	public List<Rol> getListaRol() {
		return listaRol;
	}

	public void setListaRol(List<Rol> listaRol) {
		this.listaRol = listaRol;
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

	public Rol getRolPadre() {
		return rolPadre;
	}

	public void setRolPadre(Rol rolPadre) {
		this.rolPadre = rolPadre;
	}

	@PostConstruct
	public void initNew() {
		rol = new Rol();
		rolSelected = new Rol();
		setRolPadre(new Rol());
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (rol.getDescripcion().trim().isEmpty()
					|| rol.getNombre().trim().isEmpty()
					|| rol.getEstado().trim().isEmpty()) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				rol.setRol(getRolPadre());
				rol.setFechaRegistro(new Date());
				rol.setFechaModificacion(rol.getFechaRegistro());
				rol.setUsuarioRegistro(sessionMain.getUsuarioLogin().getId());
				Rol r = rolDao.registrar(rol);
				if (r != null) {
					FacesUtil.infoMessage("Rol registrado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de rol: " + e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (rol.getDescripcion().trim().isEmpty()
					|| rol.getNombre().trim().isEmpty()
					|| rol.getEstado().trim().isEmpty()) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				rol.setRol(getRolPadre());
				rol.setFechaModificacion(new Date());
				rol.setUsuarioRegistro(sessionMain.getUsuarioLogin().getId());
				Rol r = rolDao.modificar(rol);
				if (r != null) {
					FacesUtil.infoMessage("Rol actualizado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de rol: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (rolDao.eliminar(rol)) {
				FacesUtil.infoMessage("Rol Eliminado", rol.toString());
				initNew();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				initNew();
			}
		} catch (Exception e) {
			System.out
					.println("Error en eliminacion de rol: " + e.getMessage());
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
