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

import bo.com.spaps.dao.PaisDao;
import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.model.Pais;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.util.FacesUtil;

@Named("paisController")
@ConversationScoped
/**
 * @author ANITA
 *
 */
public class PaisController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1859210202043949704L;

	/******* DAO **********/
	private @Inject PaisDao paisDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Pais pais;
	private Pais paisSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<Pais> listaPais;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public PaisController() {
	}

	public Pais getPais() {
		return pais;
	}

	public Pais getPaisSelected() {
		return paisSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Pais> getListaPais() {
		return listaPais;
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

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public void setPaisSelected(Pais paisSelected) {
		this.paisSelected = paisSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaPais(List<Pais> listaPais) {
		this.listaPais = listaPais;
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
		pais = new Pais();
		paisSelected = new Pais();
		sucursal = sessionMain.getSucursalLogin();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (pais.getNombre().trim().isEmpty()
					|| pais.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				pais.setCompania(getSucursal().getCompania());
				pais.setSucursal(getSucursal());
				pais.setFechaRegistro(new Date());
				pais.setFechaModificacion(pais.getFechaRegistro());
				pais.setUsuarioRegistro(sessionMain.getUsuarioLogin().getId());
				Pais r = paisDao.registrar(pais);
				if (r != null) {
					FacesUtil.infoMessage("Pais registrado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de pais: " + e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (pais.getNombre().trim().isEmpty()
					|| pais.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				pais.setCompania(getSucursal().getCompania());
				pais.setSucursal(getSucursal());
				pais.setFechaModificacion(new Date());
				pais.setUsuarioRegistro(sessionMain.getUsuarioLogin().getId());
				Pais r = paisDao.modificar(pais);
				if (r != null) {
					FacesUtil.infoMessage("Pais actualizado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de pais: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (paisDao.eliminar(pais)) {
				FacesUtil.infoMessage("Pais Eliminado", pais.toString());
				initNew();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de pais: "
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
