package bo.com.spaps.util;

import java.io.IOException;
import java.util.ArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;

public class FacesUtil {

	private static final String ORIGINAL = "ÁáÉéÍíÓóÚúÑñÜü";
	private static final String REPLACEMENT = "AaEeIiOoUuNnUu";

	public static void infoMessage(String msg,String detalle) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, detalle));
	}

	public static void warnMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, msg, "ADVERTENCIA"));
	}

	public static void errorMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, "ERROR"));
	}

	public static void fatalMessage(String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, "ERROR FATAL"));
	}

	public static String getUserSession(){
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		return request.getUserPrincipal() == null ? "null" : request.getUserPrincipal().getName();
	}

	public static Object getSessionAttribute(String attribute) {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) context.getSession(false);
		Object o = null;
		if (session != null) {
			o = session.getAttribute(attribute);
		}
		return o;
	}

	public static void setSessionAttribute(String attribute, Object value) {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) context.getSession(false);
		session.setAttribute(attribute, value);
	}

	public static void removeSessionAttribute(String attribute) {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpSession session = (HttpSession) context.getSession(false);
		session.removeAttribute(attribute);
	}

	public static void setParameter(String key, Object o) {
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam(key, o);
	}

	public static Object getParam(String key) {
		FacesContext context = FacesContext.getCurrentInstance();
		return context.getExternalContext().getRequestParameterMap().get(key);
	}
	
	/**
	 * Show Modal Html
	 * @param widgetVarDialog
	 */
	public static void showModal(String widgetVarDialog) {
		String ejecutar = String.format("openModal('%s');", widgetVarDialog);
		RequestContext.getCurrentInstance().execute(ejecutar);
	}

	/**
	 * Hide Modal Html
	 * @param widgetVarDialog
	 */
	public static void hideModal(String widgetVarDialog) {
		String ejecutar = String.format("hideModal('%s');", widgetVarDialog);
		RequestContext.getCurrentInstance().execute(ejecutar);
	}

	/**
	 * Show Dialog Primefaces
	 * @param widgetVarDialog
	 */
	public static void showDialog(String widgetVarDialog) {
		String ejecutar = String.format("PF('%s').show()", widgetVarDialog);
		RequestContext.getCurrentInstance().execute(ejecutar);
	}

	/**
	 * Hide Dialog Primefaces
	 * @param widgetVarDialog
	 */
	public static void hideDialog(String widgetVarDialog) {
		String ejecutar = String.format("PF('%s').hide()", widgetVarDialog);
		RequestContext.getCurrentInstance().execute(ejecutar);
	}

	public static void updateComponent(String component) {
		RequestContext.getCurrentInstance().update(component);
	}

	public static void updateComponets(ArrayList<String> components){
		RequestContext.getCurrentInstance().update(components);
	}

	public static void resetComponent(String component) {
		RequestContext.getCurrentInstance().reset(component);
	}

	public static String remoteAddressIp() {
		String remoteIp = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
		return remoteIp;
	}

	public static String getRealPath(String path) {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		return servletContext.getRealPath(path);
	}

	public static void resetDataTable(String id) {
		DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
		table.setSelection(null);
		table.reset();
	}
	/**
	 * Redirecciona a una pagina contenida en la misma carpeta donde se encuentra actualmente
	 * @param url de la misma carpeta donde se encuentra actualmente
	 * @throws IOException excepcion de error
	 */
	public static void redirect(String url) throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(url);
	}

	public static String getUrl(){
		HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String url = req.getRequestURL().toString();
		return url.substring(0, url.length() - req.getRequestURI().length()) + req.getContextPath() + "/";
	}

	public static String getRequestContextPath() {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
	}

	/**
	 * Quitar Acentos
	 * @param str
	 * @return
	 */
	public static String stripAccents(Object str) {
		if (str == null) {
			return null;
		}
		char[] array = str.toString().toCharArray();
		for (int index = 0; index < array.length; index++) {
			int pos = ORIGINAL.indexOf(array[index]);
			if (pos > -1) {
				array[index] = REPLACEMENT.charAt(pos);
			}
		}
		return new String(array);
	}

}
