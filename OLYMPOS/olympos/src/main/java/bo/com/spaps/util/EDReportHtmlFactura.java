package bo.com.spaps.util;

import java.util.List;

public class EDReportHtmlFactura {
	
	private String urlPathImgEmrpesal;
	private String razonSocialEmpresa;
	private String representanteLegal;
	private String direccion;
	private String telefono;
	private String ciudad;
	
	private List<EDReportHtmlDetalleFactura> listEDReportHtmlDetalleFactura;
	
	private String nitEmpresa;
	private String nroFactura;
	private String numeroAutorizacion;
	
	private String lugarFecha;
	private String nitCliente;
	private String razonSocialCliente;
	
	private String montoLiteral;
	private String montoNumero;
	
	private String codigoControl;
	private String fechaLimiteEmision;
	
	private String urlPathImgQr;
	private String leyenda1;
	private String leyenda2;
	public EDReportHtmlFactura(String urlPathImgEmrpesal,
			String razonSocialEmpresa, String representanteLegal,
			String direccion, String telefono, String ciudad,
			List<EDReportHtmlDetalleFactura> listEDReportHtmlDetalleFactura,
			String nitEmpresa, String nroFactura, String numeroAutorizacion,
			String lugarFecha, String nitCliente, String razonSocialCliente,
			String montoLiteral, String montoNumero, String codigoControl,
			String fechaLimiteEmision, String urlPathImgQr, String leyenda1,
			String leyenda2) {
		super();
		this.urlPathImgEmrpesal = urlPathImgEmrpesal;
		this.razonSocialEmpresa = razonSocialEmpresa;
		this.representanteLegal = representanteLegal;
		this.direccion = direccion;
		this.telefono = telefono;
		this.ciudad = ciudad;
		this.listEDReportHtmlDetalleFactura = listEDReportHtmlDetalleFactura;
		this.nitEmpresa = nitEmpresa;
		this.nroFactura = nroFactura;
		this.numeroAutorizacion = numeroAutorizacion;
		this.lugarFecha = lugarFecha;
		this.nitCliente = nitCliente;
		this.razonSocialCliente = razonSocialCliente;
		this.montoLiteral = montoLiteral;
		this.montoNumero = montoNumero;
		this.codigoControl = codigoControl;
		this.fechaLimiteEmision = fechaLimiteEmision;
		this.urlPathImgQr = urlPathImgQr;
		this.leyenda1 = leyenda1;
		this.leyenda2 = leyenda2;
	}
	public String getUrlPathImgEmrpesal() {
		return urlPathImgEmrpesal;
	}
	public void setUrlPathImgEmrpesal(String urlPathImgEmrpesal) {
		this.urlPathImgEmrpesal = urlPathImgEmrpesal;
	}
	public String getRazonSocialEmpresa() {
		return razonSocialEmpresa;
	}
	public void setRazonSocialEmpresa(String razonSocialEmpresa) {
		this.razonSocialEmpresa = razonSocialEmpresa;
	}
	public String getRepresentanteLegal() {
		return representanteLegal;
	}
	public void setRepresentanteLegal(String representanteLegal) {
		this.representanteLegal = representanteLegal;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public List<EDReportHtmlDetalleFactura> getListEDReportHtmlDetalleFactura() {
		return listEDReportHtmlDetalleFactura;
	}
	public void setListEDReportHtmlDetalleFactura(
			List<EDReportHtmlDetalleFactura> listEDReportHtmlDetalleFactura) {
		this.listEDReportHtmlDetalleFactura = listEDReportHtmlDetalleFactura;
	}
	public String getNitEmpresa() {
		return nitEmpresa;
	}
	public void setNitEmpresa(String nitEmpresa) {
		this.nitEmpresa = nitEmpresa;
	}
	public String getNroFactura() {
		return nroFactura;
	}
	public void setNroFactura(String nroFactura) {
		this.nroFactura = nroFactura;
	}
	public String getNumeroAutorizacion() {
		return numeroAutorizacion;
	}
	public void setNumeroAutorizacion(String numeroAutorizacion) {
		this.numeroAutorizacion = numeroAutorizacion;
	}
	public String getLugarFecha() {
		return lugarFecha;
	}
	public void setLugarFecha(String lugarFecha) {
		this.lugarFecha = lugarFecha;
	}
	public String getNitCliente() {
		return nitCliente;
	}
	public void setNitCliente(String nitCliente) {
		this.nitCliente = nitCliente;
	}
	public String getRazonSocialCliente() {
		return razonSocialCliente;
	}
	public void setRazonSocialCliente(String razonSocialCliente) {
		this.razonSocialCliente = razonSocialCliente;
	}
	public String getMontoLiteral() {
		return montoLiteral;
	}
	public void setMontoLiteral(String montoLiteral) {
		this.montoLiteral = montoLiteral;
	}
	public String getMontoNumero() {
		return montoNumero;
	}
	public void setMontoNumero(String montoNumero) {
		this.montoNumero = montoNumero;
	}
	public String getCodigoControl() {
		return codigoControl;
	}
	public void setCodigoControl(String codigoControl) {
		this.codigoControl = codigoControl;
	}
	public String getFechaLimiteEmision() {
		return fechaLimiteEmision;
	}
	public void setFechaLimiteEmision(String fechaLimiteEmision) {
		this.fechaLimiteEmision = fechaLimiteEmision;
	}
	public String getUrlPathImgQr() {
		return urlPathImgQr;
	}
	public void setUrlPathImgQr(String urlPathImgQr) {
		this.urlPathImgQr = urlPathImgQr;
	}
	public String getLeyenda1() {
		return leyenda1;
	}
	public void setLeyenda1(String leyenda1) {
		this.leyenda1 = leyenda1;
	}
	public String getLeyenda2() {
		return leyenda2;
	}
	public void setLeyenda2(String leyenda2) {
		this.leyenda2 = leyenda2;
	}
	

	
}
