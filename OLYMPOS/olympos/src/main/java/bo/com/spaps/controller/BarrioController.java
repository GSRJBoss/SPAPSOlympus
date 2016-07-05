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

import bo.com.spaps.dao.BarrioDao;
import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.model.Barrio;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.util.FacesUtil;

@Named("barrioController")
@ConversationScoped
/**
 * @author ANITA
 *
 */
public class BarrioController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -464680502319771570L;

	/******* DAO **********/
	private @Inject BarrioDao barrioDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Barrio barrio;
	private Barrio barrioSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<Barrio> listaBarrio;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public BarrioController() {
	}

	public Barrio getBarrio() {
		return barrio;
	}

	public Barrio getBarrioSelected() {
		return barrioSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Barrio> getListaBarrio() {
		return listaBarrio;
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

	public void setBarrio(Barrio barrio) {
		this.barrio = barrio;
	}

	public void setBarrioSelected(Barrio barrioSelected) {
		this.barrioSelected = barrioSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaBarrio(List<Barrio> listaBarrio) {
		this.listaBarrio = listaBarrio;
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
		barrio = new Barrio();
		barrioSelected = new Barrio();
		sucursal = sessionMain.getSucursalLogin();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (barrio.getNombre().trim().isEmpty()
					|| barrio.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				barrio.setCompania(getSucursal().getCompania());
				barrio.setSucursal(getSucursal());
				barrio.setFechaRegistro(new Date());
				barrio.setFechaModificacion(barrio.getFechaRegistro());
				barrio.setUsuarioRegistro(sessionMain.getUsuarioLogin().getId());
				Barrio r = barrioDao.registrar(barrio);
				if (r != null) {
					FacesUtil.infoMessage("Barrio registrado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out
					.println("Error en registro de barrio: " + e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (barrio.getNombre().trim().isEmpty()
					|| barrio.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				barrio.setCompania(getSucursal().getCompania());
				barrio.setSucursal(getSucursal());
				barrio.setFechaModificacion(new Date());
				barrio.setUsuarioRegistro(sessionMain.getUsuarioLogin().getId());
				Barrio r = barrioDao.modificar(barrio);
				if (r != null) {
					FacesUtil.infoMessage("Barrio actualizado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de barrio: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (barrioDao.eliminar(barrio)) {
				FacesUtil.infoMessage("Barrio Eliminado", barrio.toString());
				initNew();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de barrio: "
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
