package bo.com.spaps.model;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Compania
 *
 */
@Entity
@Table(name = "compania", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Compania implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(max = 255)
	@Column(name = "descripcion", nullable = true)
	private String descripcion;
	@Size(max = 255)
	@Column(name = "direccion", nullable = true)
	private String direccion;
	@Size(max = 30)
	@Column(name = "nit", nullable = true)
	private String nit;
	@Size(max = 255)
	@Column(name = "telefono", nullable = true)
	private String telefono;
	
	@Column(name = "foto_perfil", nullable = true)
	private byte[] fotoPerfil;
	
	@Column(name = "peso_foto", nullable = true)
	private int pesoFoto;
	
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

	public Compania() {
		super();
		this.id = 0;
		this.descripcion = "";
		this.direccion = "";
		this.nit = "";
		this.telefono = "";
		this.estado = "AC";
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

	public String getNit() {
		return this.nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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
			if (!(obj instanceof Compania)) {
				return false;
			} else {
				if (((Compania) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	public int getPesoFoto() {
		return pesoFoto;
	}

	public void setPesoFoto(int pesoFoto) {
		this.pesoFoto = pesoFoto;
	}

	public byte[] getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(byte[] fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

}
