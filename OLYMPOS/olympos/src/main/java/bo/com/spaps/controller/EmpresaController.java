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

import bo.com.spaps.dao.EmpresaDao;
import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.model.Empresa;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.util.FacesUtil;

@Named("empresaController")
@ConversationScoped
/**
 * @author ANITA
 *
 */
public class EmpresaController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8889148483605117052L;

	/******* DAO **********/
	private @Inject EmpresaDao empresaDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Empresa empresa;
	private Empresa empresaSelected;
	private Sucursal sucursal;

	/******* LIST **********/
	private List<Empresa> listaEmpresa;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public EmpresaController() {
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public Empresa getEmpresaSelected() {
		return empresaSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Empresa> getListaEmpresa() {
		return listaEmpresa;
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

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public void setEmpresaSelected(Empresa empresaSelected) {
		this.empresaSelected = empresaSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaEmpresa(List<Empresa> listaEmpresa) {
		this.listaEmpresa = listaEmpresa;
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
		empresa = new Empresa();
		empresaSelected = new Empresa();
		sucursal = sessionMain.getSucursalLogin();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (empresa.getDescripcion().trim().isEmpty()
					|| empresa.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				empresa.setCompania(getSucursal().getCompania());
				empresa.setSucursal(getSucursal());
				empresa.setFechaRegistro(new Date());
				empresa.setFechaModificacion(empresa.getFechaRegistro());
				empresa.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				Empresa r = empresaDao.registrar(empresa);
				if (r != null) {
					FacesUtil.infoMessage("Empresa registrado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de empresa: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (empresa.getDescripcion().trim().isEmpty()
					|| empresa.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				empresa.setCompania(getSucursal().getCompania());
				empresa.setSucursal(getSucursal());
				empresa.setFechaModificacion(new Date());
				empresa.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				Empresa r = empresaDao.modificar(empresa);
				if (r != null) {
					FacesUtil.infoMessage("Empresa actualizado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de empresa: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (empresaDao.eliminar(empresa)) {
				FacesUtil.infoMessage("Empresa Eliminado", empresa.toString());
				initNew();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de empresa: "
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
