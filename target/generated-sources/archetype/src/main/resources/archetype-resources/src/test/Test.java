#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package test;


import entidades.Usuario;
import services.UsuarioServ;

public class Test {

	public static void main(String[] args) {

		UsuarioServ uS = new UsuarioServ();
		uS.getUsuarioById(1);
//		uS.getUsuarioByNifOrEmail("50550543Q", null);
		
		Usuario usuario = new Usuario(4, "test4", "pass", "nombre", "test4", "test4", "test4", 123456, "test4");
//		uS.insertUsuario(usuario);
//		uS.updateUsuario(usuario);
//		uS.updatePassUsuario(4, "Pass");
//		uS.removeUsuario(4);
	}

}
