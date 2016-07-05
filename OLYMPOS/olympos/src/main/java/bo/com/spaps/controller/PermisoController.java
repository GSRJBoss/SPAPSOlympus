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

import bo.com.spaps.dao.PermisoDao;
import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.model.MenuAccion;
import bo.com.spaps.model.Permiso;
import bo.com.spaps.model.Rol;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.util.FacesUtil;

@Named("permisoController")
@ConversationScoped
/**
 * @author Cinthia Zabala
 *
 */
public class PermisoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2984915032089703666L;

	/******* DAO **********/
	private @Inject PermisoDao permisoDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Permiso permiso;
	private Permiso permisoSelected;
	private Sucursal sucursal;
	private Rol rol;
	private MenuAccion menuAccion;

	/******* LIST **********/
	private List<Permiso> listaPermiso;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public PermisoController() {
	}

	public Permiso getPermiso() {
		return permiso;
	}

	public void setPermiso(Permiso permiso) {
		this.permiso = permiso;
	}

	public Permiso getPermisoSelected() {
		return permisoSelected;
	}

	public void setPermisoSelected(Permiso permisoSelected) {
		this.permisoSelected = permisoSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public List<Permiso> getListaPermiso() {
		return listaPermiso;
	}

	public void setListaPermiso(List<Permiso> listaPermiso) {
		this.listaPermiso = listaPermiso;
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

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public MenuAccion getMenuAccion() {
		return menuAccion;
	}

	public void setMenuAccion(MenuAccion menuAccion) {
		this.menuAccion = menuAccion;
	}

	@PostConstruct
	public void initNew() {
		permiso = new Permiso();
		permisoSelected = new Permiso();
		sucursal = sessionMain.getSucursalLogin();
		rol = new Rol();
		menuAccion = new MenuAccion();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (permiso.getDescripcion().trim().isEmpty()
					|| permiso.getEstado().trim().isEmpty() || getRol() == null
					|| getMenuAccion() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				permiso.setRol(getRol());
				permiso.setMenuAccion(getMenuAccion());
				permiso.setFechaRegistro(new Date());
				permiso.setFechaModificacion(permiso.getFechaRegistro());
				permiso.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				Permiso r = permisoDao.registrar(permiso);
				if (r != null) {
					FacesUtil.infoMessage("Permiso registrado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de permiso: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (permiso.getDescripcion().trim().isEmpty()
					|| permiso.getEstado().trim().isEmpty() || getRol() == null
					|| getMenuAccion() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				permiso.setRol(getRol());
				permiso.setMenuAccion(getMenuAccion());
				permiso.setFechaModificacion(new Date());
				permiso.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				Permiso r = permisoDao.modificar(permiso);
				if (r != null) {
					FacesUtil.infoMessage("Permiso actualizado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de permiso: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (permisoDao.eliminar(permiso)) {
				FacesUtil.infoMessage("Permiso Eliminado", permiso.toString());
				initNew();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de permiso: "
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
