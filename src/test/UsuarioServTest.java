package test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import entidades.Usuario;
import generics.ServResultadoOUT;
import services.UsuarioServ;
import utils.Constantes;

public class UsuarioServTest {
	
	UsuarioServ uS = new UsuarioServ();
	Usuario usuarioInsert;
	Usuario usuarioUpdateOK;
	Usuario usuarioUpdateKO;
	ServResultadoOUT resultadoOK;
	ServResultadoOUT resultadoKO;
	
	@Before
    public void setUpTest() {
		this.usuarioInsert = new Usuario(10, "passTest1", "nombreTest1", "apellidoTest1", "apellidoTest1",
										 "emailTest1", 626390886, "dniTest1","20201303",null);
		this.usuarioUpdateOK = new Usuario(10, "passTest2", "nombreTest2", "apellidoTest2", "apellidoTest2",
				 "emailTest2", 626390886, "dniTest2","20201303",null);
		this.usuarioUpdateKO = new Usuario(1000, "passTest2", "nombreTest2", "apellidoTest2", "apellidoTest2",
				 "emailTest2", 626390886, "dniTest2","20201303",null);
		
		this.resultadoOK = new ServResultadoOUT();
		resultadoOK.setResultado(Constantes.CONS_RESULTADO_OK);
		this.resultadoKO = new ServResultadoOUT();
		resultadoKO.setResultado(Constantes.CONS_RESULTADO_KO);
    }
	
	@Test
    public void testUsuarioById() {
		assertEquals(resultadoOK.getResultado(), uS.getUsuarioById(1).getResultado());
		assertEquals(resultadoKO.getResultado(), uS.getUsuarioById(100).getResultado());
	}
	
	@Test
    public void testGetUsuarioByNifOrEmail() {
		assertEquals(resultadoOK.getResultado(), uS.getUsuarioByNifOrEmail("50550543Q", null).getResultado());
		assertEquals(resultadoOK.getResultado(), uS.getUsuarioByNifOrEmail(null, "angel@hotmail.com").getResultado());
		assertEquals(resultadoOK.getResultado(), uS.getUsuarioByNifOrEmail("1", null).getResultado());
		assertEquals(resultadoOK.getResultado(), uS.getUsuarioByNifOrEmail(null, "a").getResultado());
	}
	
	@Test
    public void testInsertUsuario() {
		//Se inserta usuario
		assertEquals(resultadoOK.getResultado(), uS.insertUsuario(usuarioInsert).getResultado());
		assertEquals(resultadoKO.getResultado(), uS.insertUsuario(null).getResultado());
	}
	
	@Test
    public void testUpdateUsuario() {
		//Se edita el usuario insertado en el test previo
		assertEquals(resultadoOK.getResultado(), uS.updateUsuario(usuarioUpdateOK).getResultado());
		//Se edita un usuario con ID inexistente
		assertEquals(resultadoKO.getResultado(), uS.updateUsuario(usuarioUpdateKO).getResultado());
	}
	
	@Test
    public void testUpdatePassUsuario() {
		assertEquals(resultadoOK.getResultado(), uS.updatePassUsuario(3, "test1").getResultado());
		assertEquals(resultadoOK.getResultado(), uS.updatePassUsuario(100, "test1").getResultado());
		assertEquals(resultadoOK.getResultado(), uS.updatePassUsuario(3, null).getResultado());
	}
	
	@Test
    public void testRemoveUsuario() {
		assertEquals(resultadoOK.getResultado(), uS.removeUsuario(100).getResultado());
		assertEquals(resultadoOK.getResultado(), uS.removeUsuario(10).getResultado());
	}
}