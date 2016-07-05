package bo.com.spaps.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Class Usuario Module Seguridad
 * 
 * @author Mauricio.Bejarano.Rivera
 * 
 */
@Entity
@Table(name = "usuario", schema = "public",  uniqueConstraints = @UniqueConstraint(columnNames = "login"))
public class Usuario implements Serializable {

	private static final long serialVersionUID = -8095169378568467468L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(max = 255)
	@Column(name = "nombre", nullable = false)
	private String nombre;
	@Size(max = 200)
	@Column(name = "email", nullable = false)
	private String email;
	@Size(max = 10)
	@Column(name = "login", nullable = false)
	private String login;
	@Size(max = 255)
	@Column(name = "password", nullable = false)
	private String password;
	@Size(max = 10)
	@Column(name = "tipo", nullable = false)
	private String tipo;
	@Size(max = 255)
	@Column(name = "pagina_inicio", nullable = false)
	private String paginaInicio;
	@Column(name = "peso_foto", nullable = true)
	private int pesoFoto;
	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// AC , IN , RM
	private String estado;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_rol", nullable = false)
	private Rol rol;
	@Column(name = "fecha_modificacion", nullable = false)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = true)
	private Integer usuarioRegistro;
	
	@Column(name = "foto_perfil", nullable = true)
	private byte[] fotoPerfil;

	public Usuario() {
		super();
		this.id = 0;
		this.nombre = "";
		this.email = " ";
		this.login = "";
		this.password = "";
		this.tipo = "";
		this.estado = "AC";
		this.rol = new Rol();
		this.usuarioRegistro = 0;
	}

	public String getEstado() {
		return estado;
	}

	public Rol getRol() {
		return rol;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return nombre;
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
			if (!(obj instanceof Usuario)) {
				return false;
			} else {
				if (((Usuario) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getState() {
		return estado;
	}

	public void setState(String state) {
		this.estado = state;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public byte[] getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(byte[] fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(Integer usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	public int getPesoFoto() {
		return pesoFoto;
	}

	public void setPesoFoto(int pesoFoto) {
		this.pesoFoto = pesoFoto;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getPaginaInicio() {
		return paginaInicio;
	}

	public void setPaginaInicio(String paginaInicio) {
		this.paginaInicio = paginaInicio;
	}

}
