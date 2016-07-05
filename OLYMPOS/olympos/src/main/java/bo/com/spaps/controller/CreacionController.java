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
import javax.servlet.http.HttpServletRequest;

import org.primefaces.component.api.UIData;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.dao.SucursalDao;
import bo.com.spaps.model.Compania;
import bo.com.spaps.model.Usuario;
import bo.com.spaps.model.UsuarioSucursal;
import bo.com.spaps.util.FacesUtil;
//import org.richfaces.cdi.push.Push;

@Named(value = "creacionController")
@ConversationScoped
public class CreacionController implements Serializable {

	private static final long serialVersionUID = 310306444101578622L;

	public static final String PUSH_CDI_TOPIC = "pushCdi";

	@Inject
	private FacesContext facesContext;

	@Inject
	Conversation conversation;
//
//	@Inject
//	@Push(topic = PUSH_CDI_TOPIC)
//	Event<String> pushEventSucursal;

	// dao
	private @Inject SucursalDao sucursalDao;

	// ESTADOS
	private boolean modificar = false;
	private boolean registrar = false;
	private boolean crear = true;
	private boolean seleccionada1 = false;
	private boolean seleccionadaFormCompania = true;
	private boolean seleccionadaFormGestion = false;
	private boolean seleccionadaFormAgregarCompania = true;
	private boolean seleccionarForm1 = true;
	private boolean buttonCancelar = true;
	private boolean buttonAnterior;
	private boolean buttonSiguiente;

	// VAR
	private String tituloPanel = "Registrar Compania";
	private String nombreCompania = "";
	private String nombreEstado = "ACTIVO";

	private int tamanio = 1;
	private String codigo;
	private int numeroTab;

	// SESION
	private @Inject SessionMain sessionMain; // variable del login
	private Usuario usuarioLogin;
	private Compania companiaLogin;

	// OBJECT
	private Compania newCompania;
	private Compania selectedCompania;
	private boolean seleccionadaFormEmpresa = true;

	// LIST
	private List<Usuario> listUsuario = new ArrayList<Usuario>();
	private List<Compania> listaCompania;
	private List<Compania> listaCompaniaActivas = new ArrayList<Compania>();
	private List<UsuarioSucursal> listUsuarioSucursals = new ArrayList<UsuarioSucursal>();
	private List<Compania> listFilterCompania;
	private String[] listEstado = { "ACTIVO", "INACTIVO" };

	// Component Primefaces
	private UIData usersDataTable;

	@PostConstruct
	public void initNewCompania() {

		System.out.println(" init new initNewCompania");
		beginConversation();
		companiaLogin = sessionMain.getSucursalLogin().getCompania();
		usuarioLogin = sessionMain.getUsuarioLogin();

		loadValuesDefault();
	}

	// ----------------- default ----------------
	private void loadValuesDefault() {
		numeroTab = 1;
		buttonAnterior = false;
		buttonSiguiente = true;

		seleccionadaFormAgregarCompania = true;
		modificar = false;
	}

	public void resetearFitrosTabla(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance()
				.getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}

	// ----------------------busqueda de objetos localmente -----------------

	// ------------------------- conversation -------------------------

	public void beginConversation() {
		if (conversation.isTransient()) {
			System.out.println("beginning conversation : " + this.conversation);
			conversation.begin();
			System.out.println("---> Init Conversation");
		}
	}

	public void endConversation() {
		if (!conversation.isTransient()) {
			conversation.end();
		}
	}

	// ------------- registros, modificaciones --------------------

	public void registrarCompania2() {
		try {
			// validacion compania
			if (newCompania.getDescripcion().trim().isEmpty()
					|| newCompania.getNit().trim().isEmpty()
					|| newCompania.getDireccion().trim().isEmpty()
					|| newCompania.getTelefono().trim().isEmpty()) {
				FacesUtil.infoMessage("VALIDACION", "Datos de la Compania");
				buttonAnterior = false;
				buttonSiguiente = true;
				numeroTab = 1;
				return;
			}

			Date fechaActual = new Date();
			// EMPRESA
			newCompania.setDescripcion(newCompania.getDescripcion()
					.toUpperCase());
			newCompania.setEstado("AC");
			newCompania.setUsuarioRegistro(usuarioLogin.getId());
			newCompania.setFechaRegistro(fechaActual);

		} catch (Exception e) {
			FacesUtil.errorMessage("Error al registrar.");
		}
	}

	public void onRowSelectCompania(SelectEvent event) {
		System.out.println("Ingreso a onRowSelectCompania...");
		newCompania = selectedCompania;

		modificar = true;
		crear = false;
		registrar = false;
		resetearFitrosTabla("formTableCompania:dataTableCompania");
	}

	// para pagina index.xhtml
	public void onRowSelectCompania3(SelectEvent event) {
		System.out.println(" onRowSelectCompania3 ");
		newCompania = selectedCompania;
		seleccionadaFormAgregarCompania = false;
		FacesUtil.updateComponent("formCompania");
	}

	// para pagina index.xhtml
	public void onRowSelectCompania2(SelectEvent event) {
		System.out.println(" onRowSelectCompania2 ");
		newCompania = selectedCompania;
		seleccionadaFormAgregarCompania = false;
		// resetearFitrosTabla("form1:dataTableGestion");
		try {
			// HttpSession session = (HttpSession)
			// FacesContext.getCurrentInstance().getExternalContext().getSession(false);
			// session.setAttribute("compania",
			// selectedCompania.getRazonSocial());
			// session.setAttribute("gestion", selectedGestion.getGestion());
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context
					.getExternalContext().getRequest();
			String path = request.getContextPath() + "/pages/dashboard.xhtml";
			System.out.println("PATH :  " + path);
			context.getExternalContext().redirect(
					request.getContextPath() + "/pages/dashboard.xhtml");
		} catch (Exception e) {
			System.out.println(" Error : " + e.getMessage());
		}
	}

	public void onRowUnSelect(UnselectEvent event) {
		/*
		 * FacesMessage msg = new FacesMessage("Grupo Centro Costo Selected",
		 * ((GrupoCentroCosto)event.getObject()).getNombre());
		 * FacesContext.getCurrentInstance().addMessage(null, msg);
		 */
	}

	public void crearCompania() {
		crear = false;
		modificar = false;
		registrar = true;
	}

	// ------------------- action for view --------------------------
	// button form index.xhtml
	public void formButtonAtras() {
		seleccionadaFormCompania = true;
		seleccionadaFormGestion = false;
		seleccionadaFormAgregarCompania = true;
		selectedCompania = new Compania();
	}

	// public void actionButtonSiguiente() {
	// int numeroAux = numeroTab + 1;
	// if (numeroTab == 3 && numeroAux == 4) {
	// numeroTab = 4;
	// buttonAnterior = true;
	// buttonSiguiente = false;
	// } else {
	// numeroTab++;
	// buttonAnterior = true;
	// buttonSiguiente = true;
	// }
	// loadVarTabItem(numeroTab);
	// }
	//
	// public void actionButtonAnterior() {
	// int numeroAux = numeroTab - 1;
	// if (numeroTab == 2 && numeroAux == 1) {
	// numeroTab = 1;
	// buttonAnterior = false;
	// buttonSiguiente = true;
	// } else {
	// numeroTab--;
	// buttonAnterior = true;
	// buttonSiguiente = true;
	// }
	// loadVarTabItem(numeroTab);
	// }

	// ---------------- get and set ----------------------

	public List<Compania> getListaCompania() {
		return listaCompania;
	}

	public List<Compania> getlistaCompaniaActivas() {
		return listaCompaniaActivas;
	}

	public String getTituloPanel() {
		return tituloPanel;
	}

	public void setTituloPanel(String tituloPanel) {
		this.tituloPanel = tituloPanel;
	}

	public boolean isModificar() {
		return modificar;
	}

	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	public Compania getSelectedCompania() {
		return selectedCompania;
	}

	public void setSelectedCompania(Compania selectedCompania) {
		seleccionada1 = true;
		seleccionadaFormCompania = false;
		seleccionadaFormGestion = true;
		this.selectedCompania = selectedCompania;
	}

	public List<Usuario> getListUsuario() {
		return listUsuario;
	}

	public void setListUsuario(List<Usuario> listUsuario) {
		this.listUsuario = listUsuario;
	}

	// public void setSelectedGestion(Gestion selectedGestion) {
	// this.selectedGestion = selectedGestion;
	// // cargar siguiente pagina
	// try {
	// HttpSession session = (HttpSession) FacesContext
	// .getCurrentInstance().getExternalContext()
	// .getSession(false);
	// session.setAttribute("compania", selectedCompania.getRazonSocial());
	// session.setAttribute("gestion", selectedGestion.getGestion());
	// FacesContext.getCurrentInstance().getExternalContext()
	// .redirect("/webapp/pages/dashboard.xhtml");
	// } catch (Exception e) {
	// }
	// }

	public boolean isSeleccionada1() {
		return seleccionada1;
	}

	public void setSeleccionada1(boolean seleccionada1) {
		this.seleccionada1 = seleccionada1;
	}

	public String getNombreCompania() {
		return nombreCompania;
	}

	public void setNombreCompania(String nombreCompania) {
		this.nombreCompania = nombreCompania;
	}

	public List<Compania> getListFilterCompania() {
		return listFilterCompania;
	}

	public void setListFilterCompania(List<Compania> listFilterCompania) {
		this.listFilterCompania = listFilterCompania;
	}

	public String getNombreEstado() {
		return nombreEstado;
	}

	public void setNombreEstado(String nombreEstado) {
		this.nombreEstado = nombreEstado;
	}

	public String[] getListEstado() {
		return listEstado;
	}

	public void setListEstado(String[] listEstado) {
		this.listEstado = listEstado;
	}

	public boolean isSeleccionadaFormCompania() {
		return seleccionadaFormCompania;
	}

	public void setSeleccionadaFormCompania(boolean seleccionadaFormCompania) {
		this.seleccionadaFormCompania = seleccionadaFormCompania;
	}

	public boolean isSeleccionadaFormGestion() {
		return seleccionadaFormGestion;
	}

	public void setSeleccionadaFormGestion(boolean seleccionadaFormGestion) {
		this.seleccionadaFormGestion = seleccionadaFormGestion;
	}

	public boolean isSeleccionadaFormAgregarCompania() {
		return seleccionadaFormAgregarCompania;
	}

	public void setSeleccionadaFormAgregarCompania(
			boolean seleccionadaFormAgregarCompania) {
		this.seleccionadaFormAgregarCompania = seleccionadaFormAgregarCompania;
	}

	public boolean isCrear() {
		return crear;
	}

	public void setCrear(boolean crear) {
		this.crear = crear;
	}

	public boolean isRegistrar() {
		return registrar;
	}

	public void setRegistrar(boolean registrar) {
		this.registrar = registrar;
	}

	public Compania getCompaniaLogin() {
		return companiaLogin;
	}

	public void setCompaniaLogin(Compania companiaLogin) {
		this.companiaLogin = companiaLogin;
	}

	public Compania getNewCompania() {
		return newCompania;
	}

	public void setNewCompania(Compania newCompania) {
		this.newCompania = newCompania;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public boolean isSeleccionarForm1() {
		return seleccionarForm1;
	}

	public void setSeleccionarForm1(boolean seleccionarForm1) {
		this.seleccionarForm1 = seleccionarForm1;
	}

	public UIData getUsersDataTable() {
		return usersDataTable;
	}

	public void setUsersDataTable(UIData usersDataTable) {
		this.usersDataTable = usersDataTable;
	}

	public boolean isButtonCancelar() {
		return buttonCancelar;
	}

	public void setButtonCancelar(boolean buttonCancelar) {
		this.buttonCancelar = buttonCancelar;
	}

	public int getTamanio() {
		return tamanio;
	}

	public void setTamanio(int tamanio) {
		this.tamanio = tamanio;
	}

	public int getNumeroTab() {
		return numeroTab;
	}

	public void setNumeroTab(int numeroTab) {
		this.numeroTab = numeroTab;
	}

	public boolean isButtonAnterior() {
		return buttonAnterior;
	}

	public void setButtonAnterior(boolean buttonAnterior) {
		this.buttonAnterior = buttonAnterior;
	}

	public boolean isButtonSiguiente() {
		return buttonSiguiente;
	}

	public void setButtonSiguiente(boolean buttonSiguiente) {
		this.buttonSiguiente = buttonSiguiente;
	}

	public boolean isSeleccionadaFormEmpresa() {
		return seleccionadaFormEmpresa;
	}

	public void setSeleccionadaFormEmpresa(boolean seleccionadaFormEmpresa) {
		this.seleccionadaFormEmpresa = seleccionadaFormEmpresa;
	}

	public List<UsuarioSucursal> getListUsuarioSucursals() {
		return listUsuarioSucursals;
	}

	public void setListUsuarioSucursals(
			List<UsuarioSucursal> listUsuarioSucursals) {
		this.listUsuarioSucursals = listUsuarioSucursals;
	}

}
