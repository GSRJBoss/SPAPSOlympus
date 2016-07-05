/**
 * 
 */
package bo.com.spaps.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

/**
 * @author ANITA
 *
 */
@Entity
@Table(name = "parentesco", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Parentesco implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7829315036882066474L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(max = 255)
	@Column(name = "descripcion", nullable = false)
	private String descripcion;
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

	public Parentesco() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public Compania getCompania() {
		return compania;
	}

	public Sucursal getSucursal() {
		return sucursal;
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

	public void setId(Integer id) {
		this.id = id;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setCompania(Compania compania) {
		this.compania = compania;
	}

	public void setSucursal(Sucursal sucursal) {
		this.sucursal = sucursal;
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
		return getDescripcion();
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
			if (!(obj instanceof Parentesco)) {
				return false;
			} else {
				if (((Parentesco) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
