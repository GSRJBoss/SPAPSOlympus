package bo.com.spaps.model;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Menu_Accion
 *
 */
@Entity
@Table(name = "menu_accion", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class MenuAccion implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(max = 10)
	@Column(name = "tipo", nullable = false)
	private String tipo;
	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// AC , IN , RM
	private String estado;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_menu", nullable = false)
	private Menu menu;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_accion", nullable = false)
	private Accion accion;
	@Column(name = "fecha_modificacion", nullable = false)
	private Date fechaModificacion;
	@Column(name = "fecha_registro", nullable = false)
	private Date fechaRegistro;
	@Column(name = "usuario_registro", nullable = false)
	private Integer usuarioRegistro;
	private static final long serialVersionUID = 1L;

	public MenuAccion() {
		super();
		this.id = 0;
		this.tipo = "";
		this.estado = "AC";
		this.menu = new Menu();
		this.accion = new Accion();
		this.usuarioRegistro = 0;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Accion getAccion() {
		return accion;
	}

	public void setAccion(Accion accion) {
		this.accion = accion;
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

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public void setUsuarioRegistro(Integer usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	public Integer getUsuario_registro() {
		return this.usuarioRegistro;
	}

	public void setUsuario_registro(Integer usuario_registro) {
		this.usuarioRegistro = usuario_registro;
	}

	@Override
	public String toString() {
		return id.toString();
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
			if (!(obj instanceof MenuAccion)) {
				return false;
			} else {
				if (((MenuAccion) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
