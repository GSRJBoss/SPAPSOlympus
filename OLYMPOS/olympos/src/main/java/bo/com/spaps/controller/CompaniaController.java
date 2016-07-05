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

import bo.com.spaps.dao.CompaniaDao;
import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.model.Compania;
import bo.com.spaps.util.FacesUtil;

@Named("companiaController")
@ConversationScoped
/**
 * @author ANITA
 *
 */
public class CompaniaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8415070711673824153L;

	/******* DAO **********/
	private @Inject CompaniaDao companiaDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Compania compania;
	private Compania companiaSelected;

	/******* LIST **********/
	private List<Compania> listaCompania;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public CompaniaController() {
	}

	public Compania getCompania() {
		return compania;
	}

	public Compania getCompaniaSelected() {
		return companiaSelected;
	}

	public List<Compania> getListaCompania() {
		return listaCompania;
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

	public void setCompania(Compania compania) {
		this.compania = compania;
	}

	public void setCompaniaSelected(Compania companiaSelected) {
		this.companiaSelected = companiaSelected;
	}

	public void setListaCompania(List<Compania> listaCompania) {
		this.listaCompania = listaCompania;
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
		compania = new Compania();
		companiaSelected = new Compania();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (compania.getDescripcion().trim().isEmpty()
					|| compania.getDireccion().trim().isEmpty()
					|| compania.getEstado().trim().isEmpty()) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				compania.setFechaRegistro(new Date());
				compania.setFechaModificacion(compania.getFechaRegistro());
				compania.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				Compania r = companiaDao.registrar(compania);
				if (r != null) {
					FacesUtil.infoMessage("Compania registrado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de compania: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (compania.getDescripcion().trim().isEmpty()
					|| compania.getDireccion().trim().isEmpty()
					|| compania.getEstado().trim().isEmpty()) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				compania.setFechaModificacion(new Date());
				compania.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				Compania r = companiaDao.modificar(compania);
				if (r != null) {
					FacesUtil.infoMessage("Compania actualizado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de compania: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (companiaDao.eliminar(compania)) {
				FacesUtil
						.infoMessage("Compania Eliminado", compania.toString());
				initNew();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de compania: "
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
