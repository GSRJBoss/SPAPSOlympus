package bo.com.spaps.dao;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.model.UploadedFile;

import bo.com.spaps.model.Sucursal;
import bo.com.spaps.model.Usuario;

/**
 * Class SessionMain, datos persistente durante la session del usuario
 * 
 * @author mauricio.bejarano.rivera
 *
 */

// sessionMain.usuarioLogin
@Named
@SessionScoped
public class SessionMain implements Serializable {

	private static final long serialVersionUID = -1848071985457563391L;
	private @Inject FacesContext facesContext;
	// private Logger log = Logger.getLogger(this.getClass());

	private @Inject UsuarioDao usuarioDao;
	private @Inject SucursalDao sucursalDao;

	// Object
	private Usuario usuarioLogin;
	private Sucursal sucursalLogin;
	private UploadedFile file;

	private String pathFisico;
	private String urlPath;

	private boolean modificar = false;
	private boolean isSuperAdmin = false;

	@PostConstruct
	public void initSessionMain() {
		System.out.println("----- initSessionMain() --------");
		usuarioLogin = new Usuario();
		sucursalLogin = new Sucursal();

		pathFisico = "";
		urlPath = "";
	}

	public Usuario validarUsuario_(String login, String password) {
		return usuarioDao.login(login, password);

	}

	/**
	 * Verifica si la pagina tiene permiso de acceso
	 * 
	 * @param pagina
	 * @return boolean
	 */
	public boolean tienePermisoPagina(String pagina) {
		if (pagina.equals("profile.xhtml") || pagina.equals("dashboard.xhtml")
				|| pagina.equals("manual_usuario.xhtml")
				|| pagina.equals("orden_servicio_index.xhtml")
				|| pagina.equals("documento.xhtml")
				|| pagina.equals("cliente.xhtml")
				|| pagina.equals("orden_servicio.xhtml")
				|| pagina.equals("facturacion_index.xhtml")
				|| pagina.equals("factura.xhtml")
				|| pagina.equals("factura_list.xhtml")
				|| pagina.equals("factura_orden_servicio.xhtml")
				|| pagina.equals("certificacion.xhtml")
				|| pagina.equals("tipo_hoja.xhtml")
				|| pagina.equals("directorio.xhtml")) {
			return true;
		}
		// for(Permiso p: listPermiso){
		// if(p.getDetallePagina().getPagina().getPath().equals(pagina)){
		// return true;
		// }
		// }
		return false;
	}

	/**
	 * Verifica si la pagina tiene permiso de acceso
	 * 
	 * @param pagina
	 * @return boolean
	 */
	public boolean tienePermisoPaginaAccion(String pagina, String accion) {
		// for(Permiso p: listPermiso){
		// Accion accionAux = p.getDetallePagina().getAccion();
		// Pagina paginaAux = p.getDetallePagina().getPagina();
		// if(paginaAux.getNombre().equals(pagina) &&
		// accionAux.getNombre().equals(accion)){
		// return true;
		// }
		// }
		return false;
	}

	public String getParameterRequest(String name) {
		HttpServletRequest request = (HttpServletRequest) facesContext
				.getExternalContext().getRequest();
		return request.getParameter(name);
	}

	public void setAttributeSession(String key, String value) {
		try {
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);
			session.setAttribute(key, value);
		} catch (Exception e) {
			// log.error("setAttributeSession() ERROR: " + e.getMessage());
		}
	}

	public String getAttributeSession(String key) {
		try {
			HttpSession request = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);
			return request.getAttribute(key) != null ? (String) request
					.getAttribute(key) : null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean removeAttributeSession(String key) {
		try {
			HttpSession request = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);
			request.removeAttribute(key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// ----------------------------------------

	public Usuario getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(Usuario usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public void actualizarUsuario() {
		usuarioDao.update(usuarioLogin);
	}

	public Sucursal getSucursalLogin() {
		if (sucursalLogin == null) {
			try {
				sucursalLogin = sucursalDao.obtenerSucursal(1);
			} catch (Exception e) {
				sucursalLogin = null;
				System.out.println("getSucursalLogin() ERROR: " + e.getMessage());
			}
		}
		return sucursalLogin;
	}

	public Usuario PruebaUsuario() {
		return usuarioDao.obtenerUsuario(2);
	}

	public Sucursal PruebaSucursal() {
		return sucursalDao.obtenerSucursal(1);
	}

	public UploadedFile getFile() {
		System.out.println("getFile " + file);
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;

		System.out.println("setFile " + file);
	}

	public boolean isModificar() {
		return modificar;
	}

	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	/*
	 * public ConfiguracionContabilidad getConfiguracionContabilidad() {
	 * if(configuracionContabilidad==null){ configuracionContabilidad =
	 * configuracionContabilidadDao
	 * .obtenerConfiguracionContabilidadPorEmpresa(empresaLogin); } return
	 * configuracionContabilidad; }
	 * 
	 * public void setConfiguracionContabilidad(ConfiguracionContabilidad
	 * configuracionContabilidad) { this.configuracionContabilidad =
	 * configuracionContabilidad; }
	 */

	public String getPathFisico() {
		if (pathFisico.trim().isEmpty()) {
			ServletContext servletContext = (ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext();
			pathFisico = (String) servletContext
					.getRealPath("/resources/temp/");
			return pathFisico;
		}
		return pathFisico;
	}

	public void setPathFisico(String pathFisico) {
		this.pathFisico = pathFisico;
	}

	public String getUrlPath() {
		if (urlPath.trim().isEmpty()) {
			HttpServletRequest request = (HttpServletRequest) facesContext
					.getExternalContext().getRequest();
			String urlPath2 = request.getRequestURL().toString();
			urlPath = urlPath2.substring(0, urlPath2.length()
					- request.getRequestURI().length())
					+ request.getContextPath() + "/";
			return urlPath;
		}
		return urlPath;
	}

	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}

	public boolean isSuperAdmin() {
		return isSuperAdmin;
	}

	public void setSuperAdmin(boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public void setSucursalLogin(Sucursal sucursalLogin) {
		this.sucursalLogin = sucursalLogin;
	}

}
