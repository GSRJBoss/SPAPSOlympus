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

import bo.com.spaps.dao.CargoDao;
import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.model.Cargo;
import bo.com.spaps.model.Empresa;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.util.FacesUtil;

@Named("cargoController")
@ConversationScoped
/**
 * @author ANITA
 *
 */
public class CargoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3033165080058392390L;

	/******* DAO **********/
	private @Inject CargoDao cargoDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Cargo cargo;
	private Cargo cargoSelected;
	private Sucursal sucursal;
	private Empresa empresa;

	/******* LIST **********/
	private List<Cargo> listaCargo;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public CargoController() {
	}

	public Cargo getCargo() {
		return cargo;
	}

	public Cargo getCargoSelected() {
		return cargoSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Cargo> getListaCargo() {
		return listaCargo;
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

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public void setCargoSelected(Cargo cargoSelected) {
		this.cargoSelected = cargoSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaCargo(List<Cargo> listaCargo) {
		this.listaCargo = listaCargo;
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

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@PostConstruct
	public void initNew() {
		cargo = new Cargo();
		cargoSelected = new Cargo();
		sucursal = sessionMain.getSucursalLogin();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (cargo.getDescripcion().trim().isEmpty()
					|| cargo.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				cargo.setCompania(getSucursal().getCompania());
				cargo.setSucursal(getSucursal());
				cargo.setFechaRegistro(new Date());
				cargo.setFechaModificacion(cargo.getFechaRegistro());
				cargo.setUsuarioRegistro(sessionMain.getUsuarioLogin().getId());
				Cargo r = cargoDao.registrar(cargo);
				if (r != null) {
					FacesUtil.infoMessage("Cargo registrado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de cargo: " + e.getMessage());
		}

	}

	public void actualizar() {
		try {
			if (cargo.getDescripcion().trim().isEmpty()
					|| cargo.getEstado().trim().isEmpty()
					|| getSucursal() == null
					|| getSucursal().getCompania() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				cargo.setCompania(getSucursal().getCompania());
				cargo.setSucursal(getSucursal());
				cargo.setFechaModificacion(new Date());
				cargo.setUsuarioRegistro(sessionMain.getUsuarioLogin().getId());
				Cargo r = cargoDao.modificar(cargo);
				if (r != null) {
					FacesUtil.infoMessage("Cargo actualizado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de cargo: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (cargoDao.eliminar(cargo)) {
				FacesUtil.infoMessage("Cargo Eliminado", cargo.toString());
				initNew();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de cargo: "
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
