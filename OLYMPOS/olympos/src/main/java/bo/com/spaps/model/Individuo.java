package bo.com.spaps.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Entity implementation class for Entity: Individuo
 *
 */
@Entity
@Table(name = "individuo", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Individuo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(max = 20)
	@Column(name = "codigo", nullable = false)
	private String codigo;
	@Size(max = 255)
	@Column(name = "nombre", nullable = false)
	private String nombre;
	@Size(max = 100)
	@Column(name = "apellido_paterno", nullable = false)
	private String apellidoPaterno;
	@Size(max = 100)
	@Column(name = "apellido_materno", nullable = false)
	private String apellidoMaterno;
	@Size(max = 255)
	@Column(name = "direccion", nullable = true)
	private String direccion;
	@Column(name = "edad", nullable = true)
	private Integer edad;
	@Column(name = "fecha_ingreso", nullable = false)
	private Date fechaIngreso;
	@Column(name = "fecha_nacimiento", nullable = true)
	private Date fechaNacimiento;
	@Column(name = "imagen", nullable = false)
	private byte[] imagen;
	@Size(max = 1)
	@Column(name = "sexo", nullable = false)
	private String sexo;
	@Size(max = 255)
	@Column(name = "telefono", nullable = true)
	private String telefono;
	@Size(max = 255)
	@Column(name = "directorio", nullable = true)
	private String directorio;
	@Size(max = 30)
	@Column(name = "nit", nullable = true)
	private String nit;
	@Size(max = 150)
	@Column(name = "descripcion_identidad", nullable = true)
	private String descripcionIdentidad;
	@Size(max = 255)
	@Column(name = "rango_edad", nullable = true)
	private String rangoEdad;
	@Size(max = 2)
	@Column(name = "estado", nullable = false)
	// AC , IN , RM
	private String estado;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_parentesco", nullable = false)
	private Parentesco parentesco;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_grupo_sanguineo", nullable = true)
	private GrupoSanguineo grupoSanguineo;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_documento_identidad", nullable = true)
	private DocumentoIdentidad documentoIdentidad;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_barrio", nullable = true)
	private Barrio barrio;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_cargo", nullable = true)
	private Cargo cargo;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_grupo_familiar", nullable = false)
	private GrupoFamiliar grupoFamiliar;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_raza", nullable = true)
	private Raza raza;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_unidad_vecinal", nullable = true)
	private UnidadVecinal unidadVecinal;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_provincia", nullable = true)
	private Provincia provincia;
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "id_nivel_academico", nullable = true)
	private NivelAcademico nivelAcademico;
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

	public Individuo() {
		super();
		this.id = 0;
		this.codigo = "";
		this.nombre = "";
		this.apellidoPaterno = "";
		this.apellidoMaterno = "";
		this.direccion = "";
		this.edad = 0;
		this.sexo = "";
		this.telefono = "";
		this.directorio = "";
		this.nit = "";
		this.descripcionIdentidad = "";
		this.rangoEdad = "";
		this.estado = "AC";
		this.parentesco = new Parentesco();
		this.grupoSanguineo = new GrupoSanguineo();
		this.documentoIdentidad = new DocumentoIdentidad();
		this.barrio = new Barrio();
		this.cargo = new Cargo();
		this.grupoFamiliar = new GrupoFamiliar();
		this.raza = new Raza();
		this.unidadVecinal = new UnidadVecinal();
		this.provincia = new Provincia();
		this.nivelAcademico = new NivelAcademico();
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

	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDirectorio() {
		return directorio;
	}

	public void setDirectorio(String directorio) {
		this.directorio = directorio;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getDescripcionIdentidad() {
		return descripcionIdentidad;
	}

	public void setDescripcionIdentidad(String descripcionIdentidad) {
		this.descripcionIdentidad = descripcionIdentidad;
	}

	public String getRangoEdad() {
		return rangoEdad;
	}

	public void setRangoEdad(String rangoEdad) {
		this.rangoEdad = rangoEdad;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Parentesco getParentesco() {
		return parentesco;
	}

	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
	}

	public GrupoSanguineo getGrupoSanguineo() {
		return grupoSanguineo;
	}

	public void setGrupoSanguineo(GrupoSanguineo grupoSanguineo) {
		this.grupoSanguineo = grupoSanguineo;
	}

	public DocumentoIdentidad getDocumentoIdentidad() {
		return documentoIdentidad;
	}

	public void setDocumentoIdentidad(DocumentoIdentidad documentoIdentidad) {
		this.documentoIdentidad = documentoIdentidad;
	}

	public Barrio getBarrio() {
		return barrio;
	}

	public void setBarrio(Barrio barrio) {
		this.barrio = barrio;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public GrupoFamiliar getGrupoFamiliar() {
		return grupoFamiliar;
	}

	public void setGrupoFamiliar(GrupoFamiliar grupoFamiliar) {
		this.grupoFamiliar = grupoFamiliar;
	}

	public Raza getRaza() {
		return raza;
	}

	public void setRaza(Raza raza) {
		this.raza = raza;
	}

	public UnidadVecinal getUnidadVecinal() {
		return unidadVecinal;
	}

	public void setUnidadVecinal(UnidadVecinal unidadVecinal) {
		this.unidadVecinal = unidadVecinal;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}

	public NivelAcademico getNivelAcademico() {
		return nivelAcademico;
	}

	public void setNivelAcademico(NivelAcademico nivelAcademico) {
		this.nivelAcademico = nivelAcademico;
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
			if (!(obj instanceof Individuo)) {
				return false;
			} else {
				if (((Individuo) obj).id == this.id) {
					return true;
				} else {
					return false;
				}
			}
		}
	}

}
