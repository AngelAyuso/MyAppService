package utils;

import entidades.Usuario;

public class Matcher {
	
	
	/**
	 * Metodo que realiza el match del usuario recibido del formulario con el devuelto por la BBDD
	 * @param usuarioMatchForm
	 * @param usuarioMatchBBDD
	 * @return
	 */
	public static Usuario matchUsuarioMatch(Usuario usuarioForm, Usuario usuarioBBDD) {
		
		Usuario usuarioMatch = new Usuario();
		usuarioMatch.setIdUsuario(usuarioForm.getIdUsuario());
		usuarioMatch.setPassword(Utils.compare(usuarioForm.getPassword(), usuarioBBDD.getPassword()));
		usuarioMatch.setDni(Utils.compare(usuarioForm.getDni(), usuarioBBDD.getDni()));
		usuarioMatch.setEmail(Utils.compare(usuarioForm.getEmail(), usuarioBBDD.getEmail()));
		usuarioMatch.setNombre(Utils.compare(usuarioForm.getNombre(), usuarioBBDD.getNombre()));
		usuarioMatch.setPrimerApellido(Utils.compare(usuarioForm.getPrimerApellido(), usuarioBBDD.getPrimerApellido()));
		usuarioMatch.setSegundoApellido(Utils.compare(usuarioForm.getSegundoApellido(), usuarioBBDD.getSegundoApellido()));
		usuarioMatch.setTelefono(Utils.compare(usuarioForm.getTelefono(), usuarioBBDD.getTelefono()).intValue());
		usuarioMatch.setFechaAlta(Utils.compare(usuarioForm.getFechaAlta(), usuarioBBDD.getFechaAlta()));
		usuarioMatch.setFechaModificacion(Utils.compare(usuarioForm.getFechaModificacion(), usuarioBBDD.getFechaModificacion()));
				
		return usuarioMatch;
	}
	
	
	
}
