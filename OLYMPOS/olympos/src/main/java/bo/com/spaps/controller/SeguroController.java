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

import bo.com.spaps.dao.SeguroDao;
import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.model.Seguro;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.util.FacesUtil;

@Named("seguroController")
@ConversationScoped
/**
 * @author ANITA
 *
 */
public class SeguroController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8330072044521452223L;

	/******* DAO **********/
	private @Inject SeguroDao seguroDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Seguro seguro;
	private Seguro seguroSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<Seguro> listaSeguro;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public SeguroController() {
	}

	public Seguro getSeguro() {
		return seguro;
	}

	public Seguro getSeguroSelected() {
		return seguroSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Seguro> getListaSeguro() {
		return listaSeguro;
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

	public void setSeguro(Seguro seguro) {
		this.seguro = seguro;
	}

	public void setSeguroSelected(Seguro seguroSelected) {
		this.seguroSelected = seguroSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaSeguro(List<Seguro> listaSeguro) {
		this.listaSeguro = listaSeguro;
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
		seguro = new Seguro();
		seguroSelected = new Seguro();
		sucursal = sessionMain.getSucursalLogin();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (seguro.getCodigo().trim().isEmpty()
					|| seguro.getNombre().trim().isEmpty()
					|| seguro.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				seguro.setCompania(getSucursal().getCompania());
				seguro.setSucursal(getSucursal());
				seguro.setFechaRegistro(new Date());
				seguro.setFechaModificacion(seguro.getFechaRegistro());
				seguro.setUsuarioRegistro(sessionMain.getUsuarioLogin().getId());
				Seguro r = seguroDao.registrar(seguro);
				if (r != null) {
					FacesUtil.infoMessage("Seguro registrado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out
					.println("Error en registro de seguro: " + e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (seguro.getCodigo().trim().isEmpty()
					|| seguro.getNombre().trim().isEmpty()
					|| seguro.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				seguro.setCompania(getSucursal().getCompania());
				seguro.setSucursal(getSucursal());
				seguro.setFechaModificacion(new Date());
				seguro.setUsuarioRegistro(sessionMain.getUsuarioLogin().getId());
				Seguro r = seguroDao.modificar(seguro);
				if (r != null) {
					FacesUtil.infoMessage("Seguro actualizado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de seguro: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (seguroDao.eliminar(seguro)) {
				FacesUtil.infoMessage("Seguro Eliminado", seguro.toString());
				initNew();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de seguro: "
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
