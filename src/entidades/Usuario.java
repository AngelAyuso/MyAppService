package entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="usuarios")
@NamedQueries({
	@NamedQuery(name="Usuario.findByEmail", query="SELECT u From Usuario u where u.email=?1"),
	@NamedQuery(name="Usuario.findByDni", query="SELECT u From Usuario u where u.dni=?1")
})
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUsuario;
	private String password;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String email;
	private int telefono;
	private String dni;
	private String fechaAlta;
	private String fechaModificacion;

	public Usuario() {		
	}
	
	public Usuario(int idUsuario, String password, String nombre, String primerApellido, 
					String segundoApellido, String email, int telefono, String dni, String fechaAlta, String fechaModificacion) {
		super();
		this.idUsuario = idUsuario;
		this.password = password;
		this.nombre = nombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.email = email;
		this.telefono = telefono;
		this.dni = dni;
		this.fechaAlta = fechaAlta;
		this.fechaModificacion = fechaModificacion;
	}
	
	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrimerApellido() {
		return primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	public String getSegundoApellido() {
		return segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public String getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}	
}