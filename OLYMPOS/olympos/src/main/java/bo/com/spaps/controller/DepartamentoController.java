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

import bo.com.spaps.dao.DepartamentoDao;
import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.model.Departamento;
import bo.com.spaps.model.Pais;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.util.FacesUtil;

@Named("departamentoController")
@ConversationScoped
/**
 * @author ANITA
 *
 */
public class DepartamentoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6352243762752386771L;

	/******* DAO **********/
	private @Inject DepartamentoDao departamentoDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Departamento departamento;
	private Departamento departamentoSelected;
	private Sucursal sucursal;
	private Pais pais;

	/******* LIST **********/
	private List<Departamento> listaDepartamento;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public DepartamentoController() {
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public Departamento getDepartamentoSelected() {
		return departamentoSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Departamento> getListaDepartamento() {
		return listaDepartamento;
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

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public void setDepartamentoSelected(Departamento departamentoSelected) {
		this.departamentoSelected = departamentoSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaDepartamento(List<Departamento> listaDepartamento) {
		this.listaDepartamento = listaDepartamento;
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

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	@PostConstruct
	public void initNew() {
		departamento = new Departamento();
		departamentoSelected = new Departamento();
		sucursal = sessionMain.getSucursalLogin();
		pais = new Pais();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (departamento.getNombre().trim().isEmpty()
					|| departamento.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null || getPais() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				departamento.setCompania(getSucursal().getCompania());
				departamento.setSucursal(getSucursal());
				departamento.setPais(getPais());
				departamento.setFechaRegistro(new Date());
				departamento.setFechaModificacion(departamento
						.getFechaRegistro());
				departamento.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				Departamento r = departamentoDao.registrar(departamento);
				if (r != null) {
					FacesUtil.infoMessage("Departamento registrado",
							r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de departamento: "
					+ e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (departamento.getNombre().trim().isEmpty()
					|| departamento.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null || getPais() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				departamento.setCompania(getSucursal().getCompania());
				departamento.setSucursal(getSucursal());
				departamento.setPais(getPais());
				departamento.setFechaModificacion(new Date());
				departamento.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				Departamento r = departamentoDao.modificar(departamento);
				if (r != null) {
					FacesUtil.infoMessage("Departamento actualizado",
							r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de departamento: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (departamentoDao.eliminar(departamento)) {
				FacesUtil.infoMessage("Departamento Eliminado",
						departamento.toString());
				initNew();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de departamento: "
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
