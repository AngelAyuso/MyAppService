package services;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.log4j.Logger;

import entidades.Usuario;
import generics.ServResultadoOUT;
import utils.Constantes;
import utils.Data;
import utils.Matcher;

public class UsuarioServ {
	private static final Logger logger = Logger.getLogger(UsuarioServ.class);
	
	private EntityManager getEntityManager(){
		EntityManagerFactory factory=Persistence.createEntityManagerFactory(Constantes.CONS_ENTITY_MANAGER_FACTORY);
		return factory.createEntityManager();
	}
	
	/*
	 * Metodo que recupera el usuario por ID
	 */
	public ServResultadoOUT getUsuarioById(int idUsuario) {
		
		logger.info("MyAppService.UsuarioServ.getUsuarioById - INICIO");
		logger.info("  Parametros de entrada:");
		logger.info("  idUsuario - " + idUsuario);

		ServResultadoOUT resultadoBean = new ServResultadoOUT();
		
		EntityManager em = getEntityManager();
		Usuario usuario = em.find(Usuario.class, idUsuario);
		
		if(usuario != null) {
			logger.info("  find() - Usuario" );			
			resultadoBean.setResultado(Constantes.CONS_RESULTADO_OK);
			resultadoBean.setUsuario(usuario);
		} else {
			logger.info("  find() - Usuaro no encontrado");
			resultadoBean.setResultado(Constantes.CONS_RESULTADO_KO);
			resultadoBean.setCodError(Constantes.COD_ERROR_102);
		}

		logger.info("MyAppService.UsuarioServ.getUsuarioById - FIN");
		
		return resultadoBean;		
	}
	
	/**
	 * Metodo que recupera el Usuario por dni o email
	 * @param dni
	 * @param email
	 * @return
	 */
	public ServResultadoOUT getUsuarioByNifOrEmail(String dni, String email) {
		
		logger.info("MyAppService.UsuarioServ.getUsuarioByNifOrEmail - INICIO");
		logger.info("  Parametros de entrada:");
		logger.info("  dni - " + dni);
		logger.info("  email - " + email);
		
		ServResultadoOUT resultadoBean = new ServResultadoOUT();
		
		if(Data.isValid(dni) || Data.isValid(email)) {
			EntityManager em = getEntityManager();
			Usuario usuario = null;
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
				resultadoBean.setResultado(Constantes.CONS_RESULTADO_OK);
				resultadoBean.setUsuario(usuario);
				logger.info("  find() - Usuario" );
			} catch(Exception ex) {
				resultadoBean.setResultado(Constantes.CONS_RESULTADO_KO);
				resultadoBean.setCodError(Constantes.COD_ERROR_102);
				logger.info("  find() - Usuaro no encontrado");
			}
		} else {
			resultadoBean.setResultado(Constantes.CONS_RESULTADO_KO);
			resultadoBean.setCodError(Constantes.COD_ERROR_100);
		}
		logger.info("MyAppService.UsuarioServ.getUsuarioByNifOrEmail - FIN");
		
		return resultadoBean;
	}
	
	/**
	 * Metodo que inserta un registro de un usuario
	 * @param usuario
	 * @return
	 */
	public ServResultadoOUT insertUsuario(Usuario usuario) {
		
		logger.info("MyAppService.UsuarioServ.insertUsuario - INICIO");
		ServResultadoOUT resultadoBean = new ServResultadoOUT();
		
		if(usuario != null) {
			EntityManager em=getEntityManager();
			EntityTransaction tx=em.getTransaction();
			tx.begin();
			
			//Se anade fecha de alta
			usuario.setFechaAlta(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
			
			try {
				em.persist(usuario);
				resultadoBean.setResultado(Constantes.CONS_RESULTADO_OK);
				tx.commit();
			} catch(Exception ex) {
				logger.info("  persist() - Error");
				resultadoBean.setResultado(Constantes.CONS_RESULTADO_KO);
				resultadoBean.setCodError(Constantes.COD_ERROR_101);
				ex.printStackTrace();
				tx.rollback();
			}
			logger.info("  Resultado - " + resultadoBean.getResultado());
			logger.info("MyAppService.UsuarioServ.insertUsuario - FIN");
		} else {
			resultadoBean.setResultado(Constantes.CONS_RESULTADO_KO);
			resultadoBean.setCodError(Constantes.COD_ERROR_100);
		}
		
		return resultadoBean;
	}
	
	/**
	 * Metodo que realiza un update de un usuario
	 * @param usuario
	 * @return
	 */
	public ServResultadoOUT updateUsuario(Usuario usuario) {
		
		logger.info("MyAppService.UsuarioServ.updateUsuario - INICIO");
		ServResultadoOUT resultadoBean = new ServResultadoOUT();
		
		if(Data.isValid(usuario)) {
			EntityManager em=getEntityManager();
			EntityTransaction tx=em.getTransaction();
			tx.begin();
			
			//Se recupera el usuario a modificar
			Usuario usuarioBBDD = em.find(Usuario.class, usuario.getIdUsuario());
			
			//Se realiza el match con los datos de la BBDD
			usuario = Matcher.matchUsuarioMatch(usuario, usuarioBBDD);
			
			//Se anade fecha de modificacion
			usuario.setFechaModificacion(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
			
			try {
				em.merge(usuario);
				resultadoBean.setResultado(Constantes.CONS_RESULTADO_OK);
				tx.commit();
			} catch(Exception ex) {
				logger.info("  persist() - Error");
				resultadoBean.setResultado(Constantes.CONS_RESULTADO_KO);
				resultadoBean.setCodError(Constantes.COD_ERROR_103);
				ex.printStackTrace();
				tx.rollback();
			}
		} else {
			resultadoBean.setResultado(Constantes.CONS_RESULTADO_KO);
			resultadoBean.setCodError(Constantes.COD_ERROR_100);
		}
		logger.info("  Resultado - " + resultadoBean.getResultado());
		logger.info("MyAppService.UsuarioServ.updateUsuario - FIN");
		
		return resultadoBean;
	}
	
	/**
	 * Metodo que actualiza la password de un Usuario
	 * @param idUsuario
	 * @param password
	 * @return
	 */
	public ServResultadoOUT updatePassUsuario(int idUsuario, String password) {
		
		logger.info("MyAppService.UsuarioServ.updatePassUsuario - INICIO");
		logger.info("  Parametros de entrada:");
		logger.info("  idUsuario - " + idUsuario);
		logger.info("  password - " + password);
		
		EntityManager em=getEntityManager();
		ServResultadoOUT resultadoBean = new ServResultadoOUT();
		
		if(Data.isValid(password)) {
			
			//Se recupera el usuario a modificar
			Usuario usuario = em.find(Usuario.class, idUsuario);
			
			if(usuario != null) {
				logger.info("  find() - usuario");
				
				EntityTransaction tx=em.getTransaction();
				tx.begin();
				
				//Se actualiza la password
				usuario.setPassword(password);
				try {
					em.merge(usuario);
					resultadoBean.setResultado(Constantes.CONS_RESULTADO_OK);
					tx.commit();
				} catch(Exception ex) {
					logger.info("  persist() - Error");
					resultadoBean.setResultado(Constantes.CONS_RESULTADO_KO);
					resultadoBean.setCodError(Constantes.COD_ERROR_103);;
					ex.printStackTrace();
					tx.rollback();
				}
				
			} else {
				logger.info("  find() - usuario no encontrado");
				resultadoBean.setResultado(Constantes.CONS_RESULTADO_KO);
				resultadoBean.setCodError(Constantes.COD_ERROR_105);
			}
		}  else {
			resultadoBean.setResultado(Constantes.CONS_RESULTADO_KO);
			resultadoBean.setCodError(Constantes.COD_ERROR_100);
		}
		
		logger.info("  Resultado - " + resultadoBean.getResultado());
		logger.info("MyAppService.UsuarioServ.updatePassUsuario - FIN");
		
		return resultadoBean;
	}
	
	
	/**
	 * Metodo que elimina a un Usuario
	 * @param idUsuario
	 * @return
	 */
	public ServResultadoOUT removeUsuario(int idUsuario) {
		 
		logger.info("MyAppService.UsuarioServ.removeUsuario - INICIO");
		logger.info("  Parametros de entrada:");
		logger.info("  idUsuario - " + idUsuario);
		
		EntityManager em=getEntityManager();
		ServResultadoOUT resultadoBean = new ServResultadoOUT();
		
		//Se recupera el usuario a modificar
		Usuario usuario = em.find(Usuario.class, idUsuario);
		
		if(usuario != null) {
			logger.info("  find() - usuario");
			
			EntityTransaction tx=em.getTransaction();
			tx.begin();
			
			try {
				em.remove(usuario);
				resultadoBean.setResultado(Constantes.CONS_RESULTADO_OK);
				tx.commit();
			} catch(Exception ex) {
				logger.info("  persist() - Error");
				resultadoBean.setResultado(Constantes.CONS_RESULTADO_KO);
				resultadoBean.setCodError(Constantes.COD_ERROR_104);
				ex.printStackTrace();
				tx.rollback();
			}
			
		} else {
			logger.info("  find() - usuario no encontrado");
			resultadoBean.setResultado(Constantes.CONS_RESULTADO_KO);
			resultadoBean.setCodError(Constantes.COD_ERROR_105);;
		}
		
		logger.info("  Resultado - " + resultadoBean.getResultado());
		logger.info("MyAppService.UsuarioServ.removeUsuario - FIN");
		
		return resultadoBean;
	}
}