/**
 * 
 */
package bo.com.spaps.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import bo.com.spaps.dao.IndividuoDao;
import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.model.Barrio;
import bo.com.spaps.model.Cargo;
import bo.com.spaps.model.EstadoCivil;
import bo.com.spaps.model.GrupoFamiliar;
import bo.com.spaps.model.GrupoSanguineo;
import bo.com.spaps.model.Individuo;
import bo.com.spaps.model.NivelAcademico;
import bo.com.spaps.model.Parentesco;
import bo.com.spaps.model.PlanesSeguro;
import bo.com.spaps.model.Provincia;
import bo.com.spaps.model.Raza;
import bo.com.spaps.model.Sucursal;
import bo.com.spaps.model.UnidadVecinal;
import bo.com.spaps.util.FacesUtil;

@Named("individuoController")
@ConversationScoped
/**
 * @author ANITA
 *
 */
public class IndividuoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7893881213833888106L;

	/******* DAO **********/
	private @Inject IndividuoDao individuoDao;
	private @Inject SessionMain sessionMain;

	/******* OBJECT **********/
	private Individuo individuo;
	private Individuo individuoSelected;
	private Sucursal sucursal;
	private EstadoCivil estadoCivil;
	private Raza raza;
	private GrupoSanguineo grupoSanguineo;
	private GrupoFamiliar grupoFamiliar;
	private UnidadVecinal unidadVecinal;
	private Cargo cargo;
	private Barrio barrio;
	private Parentesco parentesco;
	private NivelAcademico nivelAcademico;
	private Provincia provincia;

	/******* LIST **********/
	private List<Individuo> listaIndividuo;
	private List<PlanesSeguro> planesSeguro;

	/******* ESTADOS **********/
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;

	@Inject
	Conversation conversation;

	/**
	 * 
	 */
	public IndividuoController() {
	}

	public Individuo getIndividuo() {
		return individuo;
	}

	public Individuo getIndividuoSelected() {
		return individuoSelected;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public List<Individuo> getListaIndividuo() {
		return listaIndividuo;
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

	public void setIndividuo(Individuo individuo) {
		this.individuo = individuo;
	}

	public void setIndividuoSelected(Individuo individuoSelected) {
		this.individuoSelected = individuoSelected;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public void setListaIndividuo(List<Individuo> listaIndividuo) {
		this.listaIndividuo = listaIndividuo;
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

	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}

	public Raza getRaza() {
		return raza;
	}

	public GrupoSanguineo getGrupoSanguineo() {
		return grupoSanguineo;
	}

	public GrupoFamiliar getGrupoFamiliar() {
		return grupoFamiliar;
	}

	public UnidadVecinal getUnidadVecinal() {
		return unidadVecinal;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public Barrio getBarrio() {
		return barrio;
	}

	public Parentesco getParentesco() {
		return parentesco;
	}

	public NivelAcademico getNivelAcademico() {
		return nivelAcademico;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public void setRaza(Raza raza) {
		this.raza = raza;
	}

	public void setGrupoSanguineo(GrupoSanguineo grupoSanguineo) {
		this.grupoSanguineo = grupoSanguineo;
	}

	public void setGrupoFamiliar(GrupoFamiliar grupoFamiliar) {
		this.grupoFamiliar = grupoFamiliar;
	}

	public void setUnidadVecinal(UnidadVecinal unidadVecinal) {
		this.unidadVecinal = unidadVecinal;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public void setBarrio(Barrio barrio) {
		this.barrio = barrio;
	}

	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
	}

	public void setNivelAcademico(NivelAcademico nivelAcademico) {
		this.nivelAcademico = nivelAcademico;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public List<PlanesSeguro> getPlanesSeguro() {
		return planesSeguro;
	}

	public void setPlanesSeguro(List<PlanesSeguro> planesSeguro) {
		this.planesSeguro = planesSeguro;
	}

	@PostConstruct
	public void initNew() {
		individuo = new Individuo();
		individuoSelected = new Individuo();
		sucursal = sessionMain.getSucursalLogin();
		planesSeguro = new ArrayList<PlanesSeguro>();
		setCrear(true);
		setModificar(false);
		setRegistrar(false);
	}

	public void registrar() {
		try {
			if (individuo.getCodigo().trim().isEmpty()
					|| individuo.getNombre().trim().isEmpty()
					|| individuo.getApellidoPaterno().trim().isEmpty()
					|| individuo.getApellidoMaterno().trim().isEmpty()
					|| individuo.getSexo().trim().isEmpty()
					|| individuo.getEstado().trim().isEmpty()
					|| individuo.getImagen() == null || getParentesco() == null
					|| getGrupoFamiliar() == null || getProvincia() == null
					|| getNivelAcademico() == null) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				individuo.setFechaRegistro(new Date());
				individuo.setFechaModificacion(individuo.getFechaRegistro());
				individuo.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				Individuo r = individuoDao.registrar(individuo,
						getPlanesSeguro());
				if (r != null) {
					FacesUtil.infoMessage("Individuo registrado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al registrar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en registro de individuo: "
					+ e.getMessage());
		}
	}

	public void actualizar() {
		try {
			if (individuo.getCodigo().trim().isEmpty()
					|| individuo.getNombre().trim().isEmpty()
					|| individuo.getApellidoPaterno().trim().isEmpty()
					|| individuo.getApellidoMaterno().trim().isEmpty()
					|| individuo.getSexo().trim().isEmpty()
					|| individuo.getEstado().trim().isEmpty()) {
				FacesUtil.infoMessage("VALIDACION",
						"No puede haber campos vacíos");
				return;
			} else {
				individuo.setFechaModificacion(new Date());
				individuo.setUsuarioRegistro(sessionMain.getUsuarioLogin()
						.getId());
				Individuo r = individuoDao.modificar(individuo);
				if (r != null) {
					FacesUtil
							.infoMessage("Individuo actualizado", r.toString());
					initNew();
				} else {
					FacesUtil.errorMessage("Error al actualizar");
					initNew();
				}
			}
		} catch (Exception e) {
			System.out.println("Error en modificacion de individuo: "
					+ e.getMessage());
		}

	}

	public void eliminar() {
		try {
			if (individuoDao.eliminar(individuo)) {
				FacesUtil.infoMessage("Individuo Eliminado",
						individuo.toString());
				initNew();
			} else {
				FacesUtil.errorMessage("Error al eliminar");
				initNew();
			}
		} catch (Exception e) {
			System.out.println("Error en eliminacion de individuo: "
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
