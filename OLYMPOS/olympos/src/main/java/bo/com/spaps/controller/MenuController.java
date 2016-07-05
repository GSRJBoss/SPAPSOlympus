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

import bo.com.spaps.dao.MenuDao;
import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.model.Menu;
import bo.com.spaps.model.MenuAccion;
import bo.com.spaps.model.Modulo;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.util.FacesUtil;

@Named("menuController")
@ConversationScoped
/**
 * @author Cinthia Zabala
 *
 */
public class MenuController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2382121984515532700L;

	/******* DAO **********/
	private @Inject MenuDao menuDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Menu menu;
	private Menu menuSelected;
	private Sucursal sucursal;
	private Menu menuPadre;
	private Modulo modulo;

	/******* LIST **********/
	private List<Menu> listaMenu;
	private List<MenuAccion> accions;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public MenuController() {
		// TODO Auto-generated constructor stub
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Menu getMenuSelected() {
		return menuSelected;
	}

	public void setMenuSelected(Menu menuSelected) {
		this.menuSelected = menuSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public List<Menu> getListaMenu() {
		return listaMenu;
	}

	public void setListaMenu(List<Menu> listaMenu) {
		this.listaMenu = listaMenu;
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

	public List<MenuAccion> getAccions() {
		return accions;
	}

	public void setAccions(List<MenuAccion> accions) {
		this.accions = accions;
	}

	public Menu getMenuPadre() {
		return menuPadre;
	}

	public void setMenuPadre(Menu menuPadre) {
		this.menuPadre = menuPadre;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	@PostConstruct
	public void initNew() {
		menu = new Menu();
		menuSelected = new Menu();
		sucursal = sessionMain.getSucursalLogin();
		listaMenu = new ArrayList<>();
		accions = new ArrayList<>();
		menuPadre = new Menu();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (menu.getNombre().trim().isEmpty()
					|| menu.getRuta().trim().isEmpty()
					|| menu.getEstado().trim().isEmpty()
					|| menu.getTipo().trim().isEmpty() || getModulo() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				menu.setModulo(getModulo());
				menu.setFechaRegistro(new Date());
				menu.setFechaModificacion(menu.getFechaRegistro());
				menu.setUsuarioRegistro(sessionMain.getUsuarioLogin().getId());
				Menu r = menuDao.registrar(menu, getAccions());
				if (r != null) {
					FacesUtil.infoMessage("Menu registrado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de menu: " + e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (menu.getNombre().trim().isEmpty()
					|| menu.getRuta().trim().isEmpty()
					|| menu.getEstado().trim().isEmpty()
					|| menu.getTipo().trim().isEmpty() || getModulo() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				menu.setModulo(getModulo());
				menu.setFechaModificacion(new Date());
				menu.setUsuarioRegistro(sessionMain.getUsuarioLogin().getId());
				Menu r = menuDao.modificar(menu);
				if (r != null) {
					FacesUtil.infoMessage("Menu actualizado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de menu: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (menuDao.eliminar(menu)) {
				FacesUtil.infoMessage("Menu Eliminado", menu.toString());
				initNew();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de menu: "
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
