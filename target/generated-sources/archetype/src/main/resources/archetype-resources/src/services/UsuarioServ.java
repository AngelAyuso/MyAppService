#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package services;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import entidades.Usuario;
import utils.Constantes;

public class UsuarioServ {
	
	private EntityManager getEntityManager(){
		EntityManagerFactory factory=Persistence.createEntityManagerFactory(Constantes.CONS_ENTITY_MANAGER_FACTORY);
		return factory.createEntityManager();
	}
	
	/*
	 * Metodo que recupera el usuario por ID
	 */
	public Usuario getUsuarioById(int idUsuario) {
		
		System.out.println("${package}.UsuarioServ.getUsuarioById - INICIO");
		System.out.println("  Parametros de entrada:");
		System.out.println("  idUsuario - " + idUsuario);
		
		EntityManager em = getEntityManager();
		
		Usuario usuario = new Usuario();
		usuario = em.find(Usuario.class, idUsuario);
		
		if(usuario != null) {
			System.out.println("  find() - Usuario" );			
		} else {
			System.out.println("  find() - Usuaro no encontrado");
		}

		System.out.println("${package}.UsuarioServ.getUsuarioById - FIN");
		
		return usuario;		
	}
	
	/**
	 * Metodo que recupera el Usuario por dni o email
	 * @param dni
	 * @param email
	 * @return
	 */
	public Usuario getUsuarioByNifOrEmail(String dni, String email) {
		
		System.out.println("${package}.UsuarioServ.getUsuarioByNifOrEmail - INICIO");
		System.out.println("  Parametros de entrada:");
		System.out.println("  dni - " + dni);
		System.out.println("  email - " + email);
		
		EntityManager em = getEntityManager();
		Usuario usuario = new Usuario();
		Query qr;
		
		if(dni != null && !"".equals(dni)) {
			qr=em.createNamedQuery("Usuario.findByDni");
			qr.setParameter(1, dni);
		} else {
			qr=em.createNamedQuery("Usuario.findByEmail");
			qr.setParameter(1, email);
		}
		
		try {
			usuario = (Usuario) qr.getResultList().get(0);
			System.out.println("  find() - Usuario" );
		} catch(Exception ex) {
			System.out.println("  find() - Usuaro no encontrado");
			System.out.println("  Error:");
			ex.printStackTrace();
		}

		System.out.println("${package}.UsuarioServ.getUsuarioByNifOrEmail - FIN");
		
		return usuario;
	}
	
	/**
	 * Metodo que inserta un registro de un usuario
	 * @param usuario
	 * @return
	 */
	public String insertUsuario(Usuario usuario) {
		
		System.out.println("${package}.UsuarioServ.insertUsuario - INICIO");
		
		String resultado = Constantes.CONS_RESULTADO_KO;
		
		EntityManager em=getEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		
		try {
			em.persist(usuario);
			resultado = Constantes.CONS_RESULTADO_OK;
			tx.commit();
		} catch(Exception ex) {
			System.out.println("  persist() - Error");
			ex.printStackTrace();
			tx.rollback();
		}
		System.out.println("  Resultado - " + resultado);
		System.out.println("${package}.UsuarioServ.insertUsuario - FIN");
		
		return resultado;
	}
	
	/**
	 * Metodo que realiza un update de un usuario
	 * @param usuario
	 * @return
	 */
	public String updateUsuario(Usuario usuario) {
		
		System.out.println("${package}.UsuarioServ.updateUsuario - INICIO");
		
		String resultado = Constantes.CONS_RESULTADO_KO;
		
		EntityManager em=getEntityManager();
		EntityTransaction tx=em.getTransaction();
		tx.begin();
		
		try {
			em.merge(usuario);
			resultado = Constantes.CONS_RESULTADO_OK;
			tx.commit();
		} catch(Exception ex) {
			System.out.println("  persist() - Error");
			ex.printStackTrace();
			tx.rollback();
		}
		System.out.println("  Resultado - " + resultado);
		System.out.println("${package}.UsuarioServ.updateUsuario - FIN");
		
		return resultado;
	}
	
	/**
	 * Metodo que actualiza la password de un Usuario
	 * @param idUsuario
	 * @param password
	 * @return
	 */
	public String updatePassUsuario(int idUsuario, String password) {
		
		System.out.println("${package}.UsuarioServ.updatePassUsuario - INICIO");
		System.out.println("  Parametros de entrada:");
		System.out.println("  idUsuario - " + idUsuario);
		System.out.println("  password - " + password);
		
		EntityManager em=getEntityManager();
		String resultado = Constantes.CONS_RESULTADO_KO;
		
		//Se recupera el usuario a modificar
		Usuario usuario = em.find(Usuario.class, idUsuario);
		
		if(usuario != null) {
			System.out.println("  find() - usuario");
			
			EntityTransaction tx=em.getTransaction();
			tx.begin();
			
			//Se actualiza la password
			usuario.setPassword(password);
			try {
				em.merge(usuario);
				resultado = Constantes.CONS_RESULTADO_OK;
				tx.commit();
			} catch(Exception ex) {
				System.out.println("  persist() - Error");
				ex.printStackTrace();
				tx.rollback();
			}
			
		} else {
			System.out.println("  find() - usuario no encontrado");
		}
		
		System.out.println("  Resultado - " + resultado);
		System.out.println("${package}.UsuarioServ.updatePassUsuario - FIN");
		
		return resultado;
	}
	
	
	/**
	 * Metodo que elimina a un Usuario
	 * @param idUsuario
	 * @return
	 */
	public String removeUsuario(int idUsuario) {
		 
		System.out.println("${package}.UsuarioServ.removeUsuario - INICIO");
		System.out.println("  Parametros de entrada:");
		System.out.println("  idUsuario - " + idUsuario);
		
		EntityManager em=getEntityManager();
		String resultado = Constantes.CONS_RESULTADO_KO;
		
		//Se recupera el usuario a modificar
		Usuario usuario = em.find(Usuario.class, idUsuario);
		
		if(usuario != null) {
			System.out.println("  find() - usuario");
			
			EntityTransaction tx=em.getTransaction();
			tx.begin();
			
			try {
				em.remove(usuario);
				resultado = Constantes.CONS_RESULTADO_OK;
				tx.commit();
			} catch(Exception ex) {
				System.out.println("  persist() - Error");
				ex.printStackTrace();
				tx.rollback();
			}
			
		} else {
			System.out.println("  find() - usuario no encontrado");
		}
		
		System.out.println("  Resultado - " + resultado);
		System.out.println("${package}.UsuarioServ.removeUsuario - FIN");
		
		return resultado;
	}
}