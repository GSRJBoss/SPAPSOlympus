package bo.com.spaps.service;

import java.util.List;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import bo.com.spaps.model.Usuario;

/**
 * Implementation of the generic Data Access Service
 * All CRUD (create, read, update, delete) basic data access operations for any 
 * persistent object are performed in this class.
 */

public abstract class DataAccessService<T> {

	@Inject
	private EntityManager em ;


	@Inject
	private Event<T> tEventSrc;

	public DataAccessService() {
	}

	private Class<T> type;

	/**
	 * Default constructor
	 * 
	 * @param type entity class
	 */
	public DataAccessService(Class<T> type) {
		this.type = type;
	}

	/**
	 * Stores an instance of the entity class in the database
	 * @param T Object
	 * @return 
	 */

	public T create(T t) throws Exception {

		System.out.println("Registering "+ t.toString());
		synchronized (em) {
			EntityTransaction tx = em.getTransaction();
			try {
				tx.begin();
				System.out.println("tx.begin()");
				this.em.persist(t);
				this.em.flush();
				this.em.refresh(t);
				tEventSrc.fire(t);
				tx.commit();
				System.out.println("register complet:  "+t);
				return t;
			} catch (Exception e) {
				System.out.println("Registering Error " + e);
				tx.rollback();
				return null;
			}
		}
	}

	/**
	 * Removes the number of entries from a table
	 * @param <T>
	 * @param items
	 * @return 
	 */
	public boolean createItems(List<T> items) {
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			for (T t : items) {
				this.em.persist(t);
				this.em.flush();
				this.em.refresh(t);
				tEventSrc.fire(t);
			}
			tx.commit();
			return true;
		} catch (Exception e) {
			System.out.println("Registering Error " + e);
			tx.rollback();
			return false;
		}
	}
	/**
	 * Retrieves an entity instance that was previously persisted to the database 
	 * @param T Object
	 * @param id
	 * @return 
	 */
	public T find(Object id) {
		return this.em.find(this.type, id);
	}

	/**
	 * Removes the record that is associated with the entity instance
	 * @param type
	 * @param id 
	 */
	public void delete(Object id) {
		Object ref = this.em.getReference(this.type, id);
		this.em.remove(ref);
	}

	/**
	 * Removes the number of entries from a table
	 * @param <T>
	 * @param items
	 * @return 
	 */
	public boolean deleteItems(T[] items) {
		for (T item : items) {
			if( item instanceof Usuario){
				Usuario user = (Usuario)item;
				if(user.getId() == 1){
					continue;
				}
			}
			em.remove(em.merge(item));
		}
		return true;
	}

	/**
	 * Updates the entity instance
	 * @param <T>
	 * @param t
	 * @return the object that is updated
	 */
	public T update(T item) {
		try {
			T t= (T) this.em.merge(item);
			System.out.println("update complet:  "+item);
			return t;
		} catch (Exception e) {
			System.out.println("Error:  "+e.toString());
			return null;	
		}

	}

}