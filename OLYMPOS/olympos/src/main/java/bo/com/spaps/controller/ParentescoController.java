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

import bo.com.spaps.dao.ParentescoDao;
import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.model.Parentesco;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.util.FacesUtil;

@Named("parentescoController")
@ConversationScoped
/**
 * @author ANITA
 *
 */
public class ParentescoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 178292646123052040L;

	/******* DAO **********/
	private @Inject ParentescoDao parentescoDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Parentesco parentesco;
	private Parentesco parentescoSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<Parentesco> listaParentesco;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public ParentescoController() {
	}

	public Parentesco getParentesco() {
		return parentesco;
	}

	public Parentesco getParentescoSelected() {
		return parentescoSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Parentesco> getListaParentesco() {
		return listaParentesco;
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

	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
	}

	public void setParentescoSelected(Parentesco parentescoSelected) {
		this.parentescoSelected = parentescoSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaParentesco(List<Parentesco> listaParentesco) {
		this.listaParentesco = listaParentesco;
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
		parentesco = new Parentesco();
		parentescoSelected = new Parentesco();
		sucursal = sessionMain.getSucursalLogin();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (parentesco.getDescripcion().trim().isEmpty()
					|| parentesco.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				parentesco.setCompania(getSucursal().getCompania());
				parentesco.setSucursal(getSucursal());
				parentesco.setFechaRegistro(new Date());
				parentesco.setFechaModificacion(parentesco.getFechaRegistro());
				parentesco.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				Parentesco r = parentescoDao.registrar(parentesco);
				if (r != null) {
					FacesUtil
							.infoMessage("Parentesco registrado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de parentesco: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (parentesco.getDescripcion().trim().isEmpty()
					|| parentesco.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				parentesco.setCompania(getSucursal().getCompania());
				parentesco.setSucursal(getSucursal());
				parentesco.setFechaModificacion(new Date());
				parentesco.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				Parentesco r = parentescoDao.modificar(parentesco);
				if (r != null) {
					FacesUtil.infoMessage("Parentesco actualizado",
							r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de parentesco: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (parentescoDao.eliminar(parentesco)) {
				FacesUtil.infoMessage("Parentesco Eliminado",
						parentesco.toString());
				initNew();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de parentesco: "
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
