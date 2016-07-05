package bo.com.spaps.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Seguro
 *
 */
@Entity
@Table(name = "seguro", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Seguro implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(max = 2)
	@Column(name = "codigo", nullable = false)
	private String codigo;
	@Size(max = 255)
	@Column(name = "nombre", nullable = false)
	private String nombre;
	@Size(max = 255)
	@Column(name = "direccion", nullable = true)
	private String direccion;
	@Size(max = 30)
	@Column(name = "nit", nullable = true)
	private String nit;
	@Size(max = 255)
	@Column(name = "telefono", nullable = true)
	private String telefono;
	@Size(max = 255)
	@Column(name = "representante", nullable = true)
	private String representante;
	@Size(max = 255)
	@Column(name = "telefono_representante", nullable = true)
	private String telefonoRepresentante;
	@Size(max = 30)
	@Column(name = "cuenta_ingreso", nullable = true)
	private String cuentaIngreso;
	@Size(max = 30)
	@Column(name = "cuenta_egreso", nullable = true)
	private String cuentaEgreso;
	@Size(max = 30)
	@Column(name = "cuenta_traspaso", nullable = true)
	private String cuentaTraspaso;
	@Column(name = "fecha_inicio", nullable = true)
	private Date fechaInicio;
	@Column(name = "fecha_fin", nullable = true)
	private Date fechaFin;
	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// AC , IN , RM
	private String estado;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_compania", nullable = false)
	private Compania compania;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_sucursal", nullable = false)
	private Sucursal sucursal;
	@Column(name = "fecha_modificacion", nullable = false)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private Integer usuarioRegistro;
	private static final long serialVersionUID = 1L;

	public Seguro() {
		super();
		this.id = 0;
		this.codigo = "";
		this.nombre = "";
		this.direccion = "";
		this.nit = "";
		this.telefono = "";
		this.representante = "";
		this.telefonoRepresentante = "";
		this.cuentaIngreso = "";
		this.cuentaEgreso = "";
		this.cuentaTraspaso = "";
		this.estado = "AC";
		this.compania = new Compania();
		this.sucursal = new Sucursal();
		this.usuarioRegistro = 0;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getRepresentante() {
		return representante;
	}

	public void setRepresentante(String representante) {
		this.representante = representante;
	}

	public String getTelefonoRepresentante() {
		return telefonoRepresentante;
	}

	public void setTelefonoRepresentante(String telefonoRepresentante) {
		this.telefonoRepresentante = telefonoRepresentante;
	}

	public String getCuentaIngreso() {
		return cuentaIngreso;
	}

	public void setCuentaIngreso(String cuentaIngreso) {
		this.cuentaIngreso = cuentaIngreso;
	}

	public String getCuentaEgreso() {
		return cuentaEgreso;
	}

	public void setCuentaEgreso(String cuentaEgreso) {
		this.cuentaEgreso = cuentaEgreso;
	}

	public String getCuentaTraspaso() {
		return cuentaTraspaso;
	}

	public void setCuentaTraspaso(String cuentaTraspaso) {
		this.cuentaTraspaso = cuentaTraspaso;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Compania getCompania() {
		return compania;
	}

	public void setCompania(Compania compania) {
		this.compania = compania;
	}

	public Sucursal getSucursal() {
		return sucursal;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fecha_modificacion) {
		this.fechaModificacion = fecha_modificacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fecha_registro) {
		this.fechaRegistro = fecha_registro;
	}

	public Integer getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(Integer usuario_registro) {
		this.usuarioRegistro = usuario_registro;
	}

	@Override
	public String toString() {
		return getNombre();
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		} else {
			if (!(obj instanceof Seguro)) {
				return false;
			} else {
				if (((Seguro) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
