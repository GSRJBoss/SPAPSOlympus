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

import bo.com.spaps.dao.NivelAcademicoDao;
import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.model.NivelAcademico;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.util.FacesUtil;

@Named("nivelAcademicoController")
@ConversationScoped
/**
 * @author ANITA
 *
 */
public class NivelAcademicoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1510151773858646733L;

	/******* DAO **********/
	private @Inject NivelAcademicoDao nivelAcademicoDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private NivelAcademico nivelAcademico;
	private NivelAcademico nivelAcademicoSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<NivelAcademico> listaNivelAcademico;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public NivelAcademicoController() {
	}

	public NivelAcademico getNivelAcademico() {
		return nivelAcademico;
	}

	public NivelAcademico getNivelAcademicoSelected() {
		return nivelAcademicoSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<NivelAcademico> getListaNivelAcademico() {
		return listaNivelAcademico;
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

	public void setNivelAcademico(NivelAcademico nivelAcademico) {
		this.nivelAcademico = nivelAcademico;
	}

	public void setNivelAcademicoSelected(NivelAcademico nivelAcademicoSelected) {
		this.nivelAcademicoSelected = nivelAcademicoSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaNivelAcademico(List<NivelAcademico> listaNivelAcademico) {
		this.listaNivelAcademico = listaNivelAcademico;
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
		nivelAcademico = new NivelAcademico();
		nivelAcademicoSelected = new NivelAcademico();
		sucursal = sessionMain.getSucursalLogin();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (nivelAcademico.getDescripcion().trim().isEmpty()
					|| nivelAcademico.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				nivelAcademico.setCompania(getSucursal().getCompania());
				nivelAcademico.setSucursal(getSucursal());
				nivelAcademico.setFechaRegistro(new Date());
				nivelAcademico.setFechaModificacion(nivelAcademico
						.getFechaRegistro());
				nivelAcademico.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				NivelAcademico r = nivelAcademicoDao.registrar(nivelAcademico);
				if (r != null) {
					FacesUtil.infoMessage("NivelAcademico registrado",
							r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de nivelAcademico: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (nivelAcademico.getDescripcion().trim().isEmpty()
					|| nivelAcademico.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				nivelAcademico.setCompania(getSucursal().getCompania());
				nivelAcademico.setSucursal(getSucursal());
				nivelAcademico.setFechaModificacion(new Date());
				nivelAcademico.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				NivelAcademico r = nivelAcademicoDao.modificar(nivelAcademico);
				if (r != null) {
					FacesUtil.infoMessage("NivelAcademico actualizado",
							r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de nivelAcademico: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (nivelAcademicoDao.eliminar(nivelAcademico)) {
				FacesUtil.infoMessage("NivelAcademico Eliminado",
						nivelAcademico.toString());
				initNew();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de nivelAcademico: "
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
