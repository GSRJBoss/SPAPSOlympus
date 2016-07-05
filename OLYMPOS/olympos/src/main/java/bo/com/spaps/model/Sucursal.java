package bo.com.spaps.model;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Sucursal
 *
 */
@Entity
@Table(name = "sucursal", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Sucursal implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(max = 255)
	@Column(name = "descripcion", nullable = false)
	private String descripcion;
	@Size(max = 255)
	@Column(name = "direccion", nullable = true)
	private String direccion;
	@Size(max = 255)
	@Column(name = "telefono", nullable = true)
	private String telefono;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_compania", nullable = false)
	private Compania compania;
	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// AC , IN , RM
	private String estado;
	@Column(name = "fecha_modificacion", nullable = false)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private Integer usuarioRegistro;
	private static final long serialVersionUID = 1L;

	public Sucursal() {
		super();
		this.id = 0;
		this.descripcion = "";
		this.direccion = "";
		this.telefono = "";
		this.estado = "AC";
		this.compania = new Compania();
		this.usuarioRegistro = 0;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Compania getCompania() {
		return compania;
	}

	public String getEstado() {
		return estado;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public Integer getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setCompania(Compania compania) {
		this.compania = compania;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public void setUsuarioRegistro(Integer usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	@Override
	public String toString() {
		return descripcion;
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
			if (!(obj instanceof Sucursal)) {
				return false;
			} else {
				if (((Sucursal) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
