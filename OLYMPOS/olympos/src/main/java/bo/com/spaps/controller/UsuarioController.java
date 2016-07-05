/**
 * 
 */
package bo.com.spaps.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.dao.UsuarioDao;
import bo.com.spaps.model.Rol;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.model.Usuario;
import bo.com.spaps.model.UsuarioSucursal;
import bo.com.spaps.util.FacesUtil;

@Named("usuarioController")
@ConversationScoped
/**
 * @author Cinthia Zabala
 *
 */
public class UsuarioController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5924799378154915210L;

	/******* DAO **********/
	private @Inject UsuarioDao usuarioDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Usuario usuario;
	private Usuario usuarioSelected;
	private Sucursal sucursal;
	private Rol rol;

	/******* LIST **********/
	private List<Usuario> listaUsuario;
	private List<UsuarioSucursal> companias;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public UsuarioController() {
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioSelected() {
		return usuarioSelected;
	}

	public void setUsuarioSelected(Usuario usuarioSelected) {
		this.usuarioSelected = usuarioSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
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

	public List<UsuarioSucursal> getCompanias() {
		return companias;
	}

	public void setCompanias(List<UsuarioSucursal> companias) {
		this.companias = companias;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	@PostConstruct
	public void initNew() {
		usuario = new Usuario();
		usuarioSelected = new Usuario();
		sucursal = sessionMain.getSucursalLogin();
		listaUsuario = new ArrayList<>();
		companias = new ArrayList<>();
		rol = new Rol();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (usuario.getNombre().trim().isEmpty()
					|| usuario.getEmail().trim().isEmpty()
					|| usuario.getLogin().trim().isEmpty()
					|| usuario.getPassword().trim().isEmpty()
					|| usuario.getTipo().trim().isEmpty()
					|| usuario.getEstado().trim().isEmpty() || getRol() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				usuario.setRol(getRol());
				usuario.setFechaRegistro(new Date());
				usuario.setFechaModificacion(usuario.getFechaRegistro());
				usuario.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				Usuario r = usuarioDao.registrar(usuario, getCompanias());
				if (r != null) {
					FacesUtil.infoMessage("Usuario registrado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de usuario: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (usuario.getNombre().trim().isEmpty()
					|| usuario.getEmail().trim().isEmpty()
					|| usuario.getLogin().trim().isEmpty()
					|| usuario.getPassword().trim().isEmpty()
					|| usuario.getTipo().trim().isEmpty()
					|| usuario.getEstado().trim().isEmpty() || getRol() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				usuario.getRol();
				usuario.setFechaModificacion(new Date());
				usuario.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				Usuario r = usuarioDao.modificar(usuario);
				if (r != null) {
					FacesUtil.infoMessage("Usuario actualizado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de usuario: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (usuarioDao.eliminar(usuario)) {
				FacesUtil.infoMessage("Usuario Eliminado", usuario.toString());
				initNew();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de usuario: "
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
