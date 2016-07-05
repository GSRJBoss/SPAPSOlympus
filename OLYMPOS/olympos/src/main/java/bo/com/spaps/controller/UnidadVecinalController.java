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
import bo.com.spaps.dao.UnidadVecinalDao;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.model.UnidadVecinal;
import bo.com.spaps.util.FacesUtil;

@Named("unidadVecinalController")
@ConversationScoped
/**
 * @author ANITA
 *
 */
public class UnidadVecinalController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8031953384804673612L;

	/******* DAO **********/
	private @Inject UnidadVecinalDao unidadVecinalDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private UnidadVecinal unidadVecinal;
	private UnidadVecinal unidadVecinalSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<UnidadVecinal> listaUnidadVecinal;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public UnidadVecinalController() {
	}

	public UnidadVecinal getUnidadVecinal() {
		return unidadVecinal;
	}

	public UnidadVecinal getUnidadVecinalSelected() {
		return unidadVecinalSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<UnidadVecinal> getListaUnidadVecinal() {
		return listaUnidadVecinal;
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

	public void setUnidadVecinal(UnidadVecinal unidadVecinal) {
		this.unidadVecinal = unidadVecinal;
	}

	public void setUnidadVecinalSelected(UnidadVecinal unidadVecinalSelected) {
		this.unidadVecinalSelected = unidadVecinalSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaUnidadVecinal(List<UnidadVecinal> listaUnidadVecinal) {
		this.listaUnidadVecinal = listaUnidadVecinal;
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
		unidadVecinal = new UnidadVecinal();
		unidadVecinalSelected = new UnidadVecinal();
		sucursal = sessionMain.getSucursalLogin();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (unidadVecinal.getNumero().trim().isEmpty()
					|| unidadVecinal.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				unidadVecinal.setCompania(getSucursal().getCompania());
				unidadVecinal.setSucursal(getSucursal());
				unidadVecinal.setFechaRegistro(new Date());
				unidadVecinal.setFechaModificacion(unidadVecinal
						.getFechaRegistro());
				unidadVecinal.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				UnidadVecinal r = unidadVecinalDao.registrar(unidadVecinal);
				if (r != null) {
					FacesUtil.infoMessage("UnidadVecinal registrado",
							r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de unidadVecinal: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (unidadVecinal.getNumero().trim().isEmpty()
					|| unidadVecinal.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				unidadVecinal.setCompania(getSucursal().getCompania());
				unidadVecinal.setSucursal(getSucursal());
				unidadVecinal.setFechaModificacion(new Date());
				unidadVecinal.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				UnidadVecinal r = unidadVecinalDao.modificar(unidadVecinal);
				if (r != null) {
					FacesUtil.infoMessage("UnidadVecinal actualizado",
							r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de unidadVecinal: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (unidadVecinalDao.eliminar(unidadVecinal)) {
				FacesUtil.infoMessage("UnidadVecinal Eliminado",
						unidadVecinal.toString());
				initNew();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de unidadVecinal: "
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
