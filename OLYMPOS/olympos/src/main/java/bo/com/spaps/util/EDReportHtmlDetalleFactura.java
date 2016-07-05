package bo.com.spaps.util;

public class EDReportHtmlDetalleFactura {
	
	private String concepto;
	private String cantidad;
	private String precio;
	private String subtotal;
	
	public EDReportHtmlDetalleFactura(String concepto, String cantidad,
			String precio, String subtotal) {
		super();
		this.concepto = concepto;
		this.cantidad = cantidad;
		this.precio = precio;
		this.subtotal = subtotal;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
	
	

}
