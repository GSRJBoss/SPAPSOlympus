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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Especialidad
 *
 */
@Entity
@Table(name = "especialidad", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Especialidad implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotNull
	@Size(max = 2)
	@Column(name = "codigo")
	private String codigo;
	@Size(max = 2)
	@Column(name = "tipo_moneda", nullable = true)
	private String tipoMoneda;
	@Column(name = "tipetici", nullable = true)
	private Integer tipeTici;
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

	public Especialidad() {
		super();
		this.id = 0;
		this.codigo = "";
		this.tipoMoneda = "";
		this.tipeTici = 0;
		this.estado = "AC";
		this.compania = new Compania();
		this.sucursal = new Sucursal();
		this.usuarioRegistro = 0;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getTipoMoneda() {
		return this.tipoMoneda;
	}

	public void setTipoMoneda(String tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}

	public Integer getTipeTici() {
		return this.tipeTici;
	}

	public void setTipeTici(Integer tipeTici) {
		this.tipeTici = tipeTici;
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
		return getCodigo();
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
			if (!(obj instanceof Especialidad)) {
				return false;
			} else {
				if (((Especialidad) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
