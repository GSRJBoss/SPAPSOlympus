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
 * Entity implementation class for Entity: Personal
 *
 */
@Entity
@Table(name = "personal", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Personal implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "nombre", nullable = false)
	@Size(max = 255)
	private String nombre;
	@Size(max = 100)
	@Column(name = "apellido_paterno", nullable = false)
	private String apellidoPaterno;
	@Size(max = 100)
	@Column(name = "apellido_materno", nullable = false)
	private String apellidoMaterno;
	@Column(name = "direccion", nullable = true)
	@Size(max = 255)
	private String direccion;
	@Column(name = "directorio", nullable = false)
	private Integer directorio;
	@Size(max = 20)
	@Column(name = "registro_medico", nullable = true)
	private String registroMedico;
	@Size(max = 255)
	@Column(name = "telefono", nullable = true)
	private String telefono;
	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// AC , IN , RM
	private String estado;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_cargo", nullable = false)
	private Cargo cargo;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_usuario", nullable = true)
	private Usuario usuario;
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

	public Personal() {
		super();
		this.id = 0;
		this.nombre = "";
		this.apellidoPaterno = "";
		this.apellidoMaterno = "";
		this.direccion = "";
		this.directorio = 0;
		this.registroMedico = "";
		this.telefono = "";
		this.estado = "AC";
		this.cargo = new Cargo();
		this.usuario = new Usuario();
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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Integer getDirectorio() {
		return directorio;
	}

	public void setDirectorio(Integer directorio) {
		this.directorio = directorio;
	}

	public String getRegistroMedico() {
		return registroMedico;
	}

	public void setRegistroMedico(String registroMedico) {
		this.registroMedico = registroMedico;
	}

	public String getTelefono() {
		return telefono;
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

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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
		return getNombre() + " " + getApellidoPaterno() + " "
				+ getApellidoMaterno();
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
			if (!(obj instanceof Personal)) {
				return false;
			} else {
				if (((Personal) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
