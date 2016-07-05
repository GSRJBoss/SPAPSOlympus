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

import bo.com.spaps.dao.PlanSeguroDao;
import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.model.PlanSeguro;
import bo.com.spaps.model.Seguro;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.util.FacesUtil;

@Named("planSeguroController")
@ConversationScoped
/**
 * @author ANITA
 *
 */
public class PlanSeguroController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6907918915369621290L;

	/******* DAO **********/
	private @Inject PlanSeguroDao planSeguroDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private PlanSeguro planSeguro;
	private PlanSeguro planSeguroSelected;
	private Sucursal sucursal;
	private Seguro seguro;

	/******* LIST **********/
	private List<PlanSeguro> listaPlanSeguro;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public PlanSeguroController() {
	}

	public PlanSeguro getPlanSeguro() {
		return planSeguro;
	}

	public PlanSeguro getPlanSeguroSelected() {
		return planSeguroSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<PlanSeguro> getListaPlanSeguro() {
		return listaPlanSeguro;
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

	public void setPlanSeguro(PlanSeguro planSeguro) {
		this.planSeguro = planSeguro;
	}

	public void setPlanSeguroSelected(PlanSeguro planSeguroSelected) {
		this.planSeguroSelected = planSeguroSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaPlanSeguro(List<PlanSeguro> listaPlanSeguro) {
		this.listaPlanSeguro = listaPlanSeguro;
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

	public Seguro getSeguro() {
		return seguro;
	}

	public void setSeguro(Seguro seguro) {
		this.seguro = seguro;
	}

	@PostConstruct
	public void initNew() {
		planSeguro = new PlanSeguro();
		planSeguroSelected = new PlanSeguro();
		sucursal = sessionMain.getSucursalLogin();
		seguro = new Seguro();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (planSeguro.getDescripcion().trim().isEmpty()
					|| planSeguro.getCodigo().trim().isEmpty()
					|| planSeguro.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null
					|| getSeguro() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				planSeguro.setCompania(getSucursal().getCompania());
				planSeguro.setSucursal(getSucursal());
				planSeguro.setSeguro(getSeguro());
				planSeguro.setFechaRegistro(new Date());
				planSeguro.setFechaModificacion(planSeguro.getFechaRegistro());
				planSeguro.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				PlanSeguro r = planSeguroDao.registrar(planSeguro);
				if (r != null) {
					FacesUtil
							.infoMessage("PlanSeguro registrado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de planSeguro: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (planSeguro.getDescripcion().trim().isEmpty()
					|| planSeguro.getCodigo().trim().isEmpty()
					|| planSeguro.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null
					|| getSeguro() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				planSeguro.setCompania(getSucursal().getCompania());
				planSeguro.setSucursal(getSucursal());
				planSeguro.setSeguro(getSeguro());
				planSeguro.setFechaModificacion(new Date());
				planSeguro.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				PlanSeguro r = planSeguroDao.modificar(planSeguro);
				if (r != null) {
					FacesUtil.infoMessage("PlanSeguro actualizado",
							r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de planSeguro: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (planSeguroDao.eliminar(planSeguro)) {
				FacesUtil.infoMessage("PlanSeguro Eliminado",
						planSeguro.toString());
				initNew();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de planSeguro: "
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
