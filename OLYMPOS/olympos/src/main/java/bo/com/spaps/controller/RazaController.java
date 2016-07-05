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

import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;

import bo.com.spaps.dao.RazaDao;
import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.model.Raza;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.model.Usuario;
import bo.com.spaps.util.FacesUtil;

@Named("razaController")
@ConversationScoped
/**
 * @author Cinthia Zabala
 *
 */
public class RazaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5490497529873515997L;

	/******* DAO **********/
	private @Inject RazaDao razaDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Raza raza;
	private Raza razaSelected;
	private Sucursal sucursalLogin;
	private Usuario usuarioLogin;

	/******* LIST **********/
	private List<Raza> listaRaza;
	private String[] listaEstado = { "ACTIVO", "INACTIVO", "ELIMINADO" };

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private String estado = "AC";

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public RazaController() {
	}

	public Raza getRaza() {
		return raza;
	}

	public void setRaza(Raza raza) {
		this.raza = raza;
	}

	public Raza getRazaSelected() {
		return razaSelected;
	}

	public void setRazaSelected(Raza razaSelected) {
		this.razaSelected = razaSelected;
	}

	public Sucursal getSucursal() {
		return sucursalLogin;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursalLogin = sucursal;
	}

	public List<Raza> getListaRaza() {
		return listaRaza;
	}

	public void setListaRaza(List<Raza> listaRaza) {
		this.listaRaza = listaRaza;
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

	public String[] getListaEstado() {
		return listaEstado;
	}

	public String getEstado() {
		return estado;
	}

	public void setListaEstado(String[] listaEstado) {
		this.listaEstado = listaEstado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public void cambiarAspecto() {
		crear = false;
		registrar = true;
		modificar = false;
	}

	public void onRowSelect(SelectEvent event) {
		crear = false;
		registrar = false;
		modificar = true;
		raza = razaSelected;
		estado = raza.getEstado();
		if (estado.equals("AC")) {
			setEstado("ACTIVO");
		} else {
			if (estado.equals("IN")) {
				setEstado("INACTIVO");
			} else {
				setEstado("ELIMINADO");
			}
		}
		FacesContext.getCurrentInstance().renderResponse();
		resetearFitrosTabla("formTableRaza:dataTableRaza");
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	@PostConstruct
	public void initNew() {
		raza = new Raza();
		razaSelected = new Raza();
		usuarioLogin = sessionMain.getUsuarioLogin();
		sucursalLogin = sessionMain.PruebaSucursal();
		listaRaza = razaDao.obtenerRazaOrdenAscPorId();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (raza.getCodigo().trim().isEmpty()
					|| raza.getNombre().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				raza.setUsuarioRegistro(getUsuarioLogin().getId());
				raza.setSucursal(getSucursal());
				raza.setCompania(getSucursal().getCompania());
				raza.setEstado("AC");
				raza.setFechaRegistro(new Date());
				raza.setFechaModificacion(raza.getFechaRegistro());
				razaDao.registrar(raza);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de raza: " + e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (raza.getCodigo().trim().isEmpty()
					|| raza.getNombre().trim().isEmpty()
					|| raza.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				raza.setFechaModificacion(new Date());
				raza.setUsuarioRegistro(getUsuarioLogin().getId());
				if (getEstado().equals("ACTIVO") || getEstado().equals("AC")) {
					raza.setEstado("AC");
				} else {
					if (getEstado().equals("INACTIVO")
							|| getEstado().equals("IN")) {
						raza.setEstado("IN");
					} else {
						if (getEstado().equals("ELIMINADO")
								|| getEstado().equals("RM")) {
						}
						raza.setEstado("RM");
					}
				}
			}
			razaDao.modificar(raza);
			initNew();
		} catch (Exception e) {
			System.out.println("Error en modificacion de raza: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			razaDao.eliminar(raza);
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de raza: "
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

	public void endConversation() {
		if (!conversation.isTransient()) {
			conversation.end();
			System.out.println(">>>>>>>>>> CONVERSACION TERMINADA...");
		}
	}

}
