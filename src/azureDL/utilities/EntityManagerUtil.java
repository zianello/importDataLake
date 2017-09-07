package azureDL.utilities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

public class EntityManagerUtil {
	
	  @PersistenceUnit(unitName="test")
	  private static final EntityManagerFactory entityManagerFactory;
	  
	  static {
		    try {
		      entityManagerFactory = Persistence.createEntityManagerFactory("test");
		    } catch (Throwable ex) {
		      System.err.println("Initial SessionFactory creation failed." + ex);
		      throw new ExceptionInInitializerError(ex);
		    }
	  }

	  public static EntityManager getEntityManager() {
	    return entityManagerFactory.createEntityManager();
	  }
	  
	  public static void closeEMFactory(){
		  entityManagerFactory.close();
	  }
	  
	}