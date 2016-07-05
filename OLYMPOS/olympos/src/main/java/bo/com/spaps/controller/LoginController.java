package bo.com.spaps.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import bo.com.spaps.dao.CompaniaDao;
import bo.com.spaps.dao.PermisoDao;
import bo.com.spaps.dao.RolDao;
import bo.com.spaps.dao.SessionMain;
import bo.com.spaps.model.Compania;
import bo.com.spaps.model.Permiso;
import bo.com.spaps.model.Rol;
import bo.com.spaps.model.Usuario;
import bo.com.spaps.service.UsuarioRegistration;
import bo.com.spaps.util.DateUtility;
import bo.com.spaps.util.FacesUtil;

//import bo.com.erp360.model.security.UsuarioRol;

@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {

	private static final long serialVersionUID = 903796340358480031L;

	private @Inject SessionMain sessionMain; // variable del login
	private @Inject RolDao rolDao;
	private @Inject PermisoDao permisoDao;

	private String username;
	private String password;

	// temporal
	private StreamedContent fotoPerfilTemp;

	private UploadedFile file;

	private UploadedFile fileLogo;;

	private boolean modificar = false;

	@PostConstruct
	public void initNewLogin() {
		System.out.println("initNewLogin()");
		username = "";
		password = "";
	}

	public void login() {
		System.out.println(" ------- login() ----");
		if (username.isEmpty() || password.isEmpty()) {
			System.out.println("login() -> Usuario o Password sin datos.");
			FacesUtil.errorMessage("Ingresar Usuario y Contraseña.");
			return;
		}

		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		Usuario usuarioSession = sessionMain
				.validarUsuario_(username, password);
		System.out.println("logeo : " + usuarioSession.getNombre());
		if (usuarioSession != null) {
			// validacion usuario eliminado
			if (usuarioSession.getState().equals("RM")) {
				FacesUtil.infoMessage("Verificar!",
						"Usuario o contraseña incorrecta");
				return;
			}
			// validacion usuario inactivo
			if (usuarioSession.getState().equals("IN")) {
				FacesUtil.infoMessage("Usuario Inactivo", "");
				return;
			}
			try {
				if (request.getUserPrincipal() != null) {
					logout();
				}
				request.login(username, password);
				load(usuarioSession);
				/* cargarPermisosUserSession(); */
				try {
					context.getExternalContext().redirect(
							request.getContextPath() + "/pages/index.xhtml");
				} catch (IOException ex) {
					context.addMessage(null, new FacesMessage("Error!",
							"Ocurrio un Error!"));
				}
			} catch (ServletException e) {
				System.out.println("login() -> " + e.toString());
				context.addMessage(null, new FacesMessage("Verificar!",
						"Usuario o contraseña incorrecta"));
			}
		} else {
			System.out.println("login() -> No existe Usuario");
			FacesUtil.errorMessage("Revisar Usuario o Contraseña.");
		}
	}

	private void load(Usuario usuario) {
		this.usuarioSession = usuario;
		Rol rol = usuarioSession.getRol();
		sessionMain.setUsuarioLogin(usuario);
		// sessionMain.setSucursalLogin(usuario.getSucursal());
		// sessionMain.cargarPermisos(usuarioRolV1.getRoles());
		setImageUserSession();
		setImageLogo();
	}

	public void logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context
				.getExternalContext().getRequest();
		HttpSession session = request.getSession(false);
		System.out.println("User ({0}) Cerrando sesion #"
				+ DateUtility.getCurrentDateTime() + " user"
				+ request.getUserPrincipal().getName());
		if (session != null) {
			session.invalidate();
			try {
				context.getExternalContext().redirect(
						request.getContextPath() + "/login.xhtml");
			} catch (IOException e) {
				System.out.println("logout() -> " + e.toString());
			}
		}
	}

	public void verificarTipoCambio() {
		System.out.println("verificarTipoCambio()");
		RequestContext.getCurrentInstance().execute("stickyTipoCambio()");
		int test = 0;
		if (0 == test) {
			// RequestContext.getCurrentInstance().execute("stickyTipoCambio()");
		}
	}

	private static byte[] toByteArrayUsingJava(InputStream is)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int reads = is.read();
		while (reads != -1) {
			baos.write(reads);
			reads = is.read();
		}
		return baos.toByteArray();
	}

	public StreamedContent getImageUserSession() {
		String mimeType = "image/jpg";
		StreamedContent file;
		InputStream is = null;
		try {
			HttpSession request = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);
			byte[] image = (byte[]) request.getAttribute("imageUser");
			is = new ByteArrayInputStream(image);
			return new DefaultStreamedContent(new ByteArrayInputStream(
					toByteArrayUsingJava(is)), mimeType);
		} catch (Exception e) {
			System.out.println("getCompaniaSession() -> error : "
					+ e.getMessage());
			return null;
		}
	}

	public void setImageUserSession() {
		// cargar foto del usuario
		try {
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);
			byte[] image = sessionMain.getUsuarioLogin().getFotoPerfil();
			if (image == null) {
				image = toByteArrayUsingJava(getImageDefault().getStream());
			}
			session.setAttribute("imageUser", image);

		} catch (Exception e) {
			System.out.println("setImageUserSession() - Error: "
					+ e.getMessage());
		}
	}

	private StreamedContent getImageDefault() {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream stream = classLoader.getResourceAsStream("avatar.png");
		return new DefaultStreamedContent(stream, "image/png");
	}

	private StreamedContent getImageDefaultLogo() {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		InputStream stream = classLoader.getResourceAsStream("logo.png");
		return new DefaultStreamedContent(stream, "image/png");
	}

	// /perfil
	private @Inject UsuarioRegistration usuarioRegistration;
	private @Inject CompaniaDao CompaniaDao;
	private Usuario usuarioSession;

	public void upload() {
		setModificar(true);
		System.out.println("upload()  file:" + file);
		if (file != null) {
			usuarioSession.setFotoPerfil(file.getContents());
			usuarioSession.setPesoFoto(file.getContents().length);
			usuarioRegistration.update(usuarioSession);
			setImageUserSession2();
			System.out.println("upload()  OK");
		}
	}

	public void setImageUserSession2() {
		// cargar foto del usuario
		try {
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);
			byte[] image = usuarioSession.getFotoPerfil();
			session.setAttribute("imageUser", image);

		} catch (Exception e) {
			System.out.println("setImageUserSession() - Error: "
					+ e.getMessage());
		}
	}

	public void uploadLogo() {
		setModificar(true);
		System.out.println("upload()  fileLogo:" + fileLogo);
		if (fileLogo != null) {
			Compania compania = sessionMain.getSucursalLogin().getCompania();
			compania.setFotoPerfil(fileLogo.getContents());
			compania.setPesoFoto(fileLogo.getContents().length);
			compania = CompaniaDao.update(compania);
			setImageLogo2();
			System.out.println("uploadLogo()  OK");
		}
	}

	public void setImageLogo() {
		// cargar foto del usuario
		try {
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);
			byte[] image = sessionMain.getSucursalLogin().getCompania()
					.getFotoPerfil();
			if (image == null) {
				image = toByteArrayUsingJava(getImageDefaultLogo().getStream());
			}
			session.setAttribute("imageLogo", image);

		} catch (Exception e) {
			System.out.println("setImageUserSession() - Error: "
					+ e.getMessage());
		}
	}

	public void setImageLogo2() {
		// cargar foto del usuario
		try {
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);
			byte[] image = sessionMain.getSucursalLogin().getCompania()
					.getFotoPerfil();
			session.setAttribute("imageLogo", image);

		} catch (Exception e) {
			System.out.println("setImageUserSession() - Error: "
					+ e.getMessage());
		}
	}

	public StreamedContent getImageLogo() {
		String mimeType = "image/png";
		StreamedContent file;
		InputStream is = null;
		try {
			HttpSession request = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);
			byte[] image = (byte[]) request.getAttribute("imageLogo");
			is = new ByteArrayInputStream(image);
			return new DefaultStreamedContent(new ByteArrayInputStream(
					toByteArrayUsingJava(is)), mimeType);
		} catch (Exception e) {
			System.out.println("getCompaniaSession() -> error : "
					+ e.getMessage());
			return null;
		}
	}

	// PERMISOS
	private void cargarPermisosUserSession() {
		try {
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);
			Rol rol = usuarioSession.getRol();
			List<Permiso> listPermiso = permisoDao.obtenerPorRol(rol);
			for (Permiso p : listPermiso) {
				session.setAttribute(p.getMenuAccion().getMenu().getNombre(),
						"AC");
			}
		} catch (Exception e) {

		}
	}

	public void permisoValidado(String permiso) {
		try {
			System.out.println("permisoValidado(" + permiso + ")");
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request1 = (HttpServletRequest) context
					.getExternalContext().getRequest();
			HttpSession request = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);
			String permisoAux = request.getAttribute(permiso) != null ? (String) request
					.getAttribute(permiso) : "IN";
			if (!permisoAux.equals("AC")) {
				FacesContext
						.getCurrentInstance()
						.getExternalContext()
						.redirect(request1.getContextPath() + "/error403.xhtml");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ----------- Getters and Setters ------------

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public StreamedContent getFotoPerfilTemp() {
		return fotoPerfilTemp;
	}

	public void setFotoPerfilTemp(StreamedContent fotoPerfilTemp) {
		this.fotoPerfilTemp = fotoPerfilTemp;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public boolean isModificar() {
		return modificar;
	}

	public void setModificar(boolean modificar) {
		this.modificar = modificar;
	}

	public UploadedFile getFileLogo() {
		return fileLogo;
	}

	public void setFileLogo(UploadedFile fileLogo) {
		this.fileLogo = fileLogo;
	}
}
