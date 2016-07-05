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

import bo.com.spaps.dao.GrupoFamiliarDao;
import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.model.GrupoFamiliar;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.model.Usuario;
import bo.com.spaps.util.FacesUtil;

@Named("grupoFamiliarController")
@ConversationScoped
/**
 * @author ANITA
 *
 */
public class GrupoFamiliarController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 74072553476463104L;

	/******* DAO **********/
	private @Inject GrupoFamiliarDao grupoFamiliarDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private GrupoFamiliar grupoFamiliar;
	private GrupoFamiliar grupoFamiliarSelected;
	private Sucursal sucursalLogin;
	private Usuario usuarioLogin;

	/******* LIST **********/
	private List<GrupoFamiliar> listaGrupoFamiliar;
	private String[] listaEstado = { "ACTIVO", "INACTIVO", "ELIMINADO" };
	private String[] listaSexo = { "Femenino", "Masculino" };

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private String estado = "AC";
	private String sexo = "";

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public GrupoFamiliarController() {
	}

	public GrupoFamiliar getGrupoFamiliar() {
		return grupoFamiliar;
	}

	public GrupoFamiliar getGrupoFamiliarSelected() {
		return grupoFamiliarSelected;
	}

	public List<GrupoFamiliar> getListaGrupoFamiliar() {
		return listaGrupoFamiliar;
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

	public void setGrupoFamiliar(GrupoFamiliar grupoFamiliar) {
		this.grupoFamiliar = grupoFamiliar;
	}

	public void setGrupoFamiliarSelected(GrupoFamiliar grupoFamiliarSelected) {
		this.grupoFamiliarSelected = grupoFamiliarSelected;
	}

	public void setListaGrupoFamiliar(List<GrupoFamiliar> listaGrupoFamiliar) {
		this.listaGrupoFamiliar = listaGrupoFamiliar;
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

	public Sucursal getSucursalLogin() {
		return sucursalLogin;
	}

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public String[] getListaEstado() {
		return listaEstado;
	}

	public String[] getListaSexo() {
		return listaSexo;
	}

	public String getEstado() {
		return estado;
	}

	public void setSucursalLogin(Sucursal sucursalLogin) {
		this.sucursalLogin = sucursalLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public void setListaEstado(String[] listaEstado) {
		this.listaEstado = listaEstado;
	}

	public void setListaSexo(String[] listaSexo) {
		this.listaSexo = listaSexo;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	@PostConstruct
	public void initNew() {
		grupoFamiliar = new GrupoFamiliar();
		grupoFamiliarSelected = new GrupoFamiliar();
		sucursalLogin = sessionMain.PruebaSucursal();
		usuarioLogin = sessionMain.getUsuarioLogin();
		listaGrupoFamiliar = grupoFamiliarDao
				.obtenerGrupoFamiliarOrdenAscPorId();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
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
		grupoFamiliar = grupoFamiliarSelected;
		estado = grupoFamiliar.getEstado();
		sexo = grupoFamiliar.getSexo();
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
		resetearFitrosTabla("formTableDocumentoIdentidad:dataTableDocumentoIdentidad");
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	public void registrar() {
		try {
			if (grupoFamiliar.getCodigo().trim().isEmpty()
					|| grupoFamiliar.getSexo().trim().isEmpty()
					|| getSucursalLogin() == null
					|| getSucursalLogin().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				grupoFamiliar.setSucursal(getSucursalLogin());
				grupoFamiliar.setCompania(getSucursalLogin().getCompania());
				grupoFamiliar.setFechaRegistro(new Date());
				grupoFamiliar.setFechaModificacion(grupoFamiliar
						.getFechaRegistro());
				grupoFamiliar.setUsuarioRegistro(getUsuarioLogin().getId());
				grupoFamiliarDao.registrar(grupoFamiliar);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en registro de grupoFamiliar: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (grupoFamiliar.getCodigo().trim().isEmpty()
					|| grupoFamiliar.getSexo().trim().isEmpty()
					|| grupoFamiliar.getEstado().trim().isEmpty()
					|| getSucursalLogin() == null
					|| getSucursalLogin().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				grupoFamiliar.setFechaModificacion(new Date());
				grupoFamiliar.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				grupoFamiliarDao.modificar(grupoFamiliar);
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de grupoFamiliar: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			grupoFamiliarDao.eliminar(grupoFamiliar);
			initNew();
		} catch (Exception e) {
			System.out.println("Error en eliminacion de grupoFamiliar: "
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
