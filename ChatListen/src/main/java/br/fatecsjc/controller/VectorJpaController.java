package br.fatecsjc.controller;

/**
 * @author Luiz Carlos Farias da Silva
 * Class contains controller methods for CRUD operations of the vector table
 */

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.fatecsjc.exceptions.NonexistentEntityException;
import br.fatecsjc.model.Vector;

public class VectorJpaController implements Serializable {
	private static final long serialVersionUID = 1L;

	private EntityManagerFactory emf = null;

	public VectorJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Vector vector) {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(vector);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Vector vector) throws NonexistentEntityException,
			Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			vector = em.merge(vector);
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = vector.getId();
				if (findVector(id) == null) {
					throw new NonexistentEntityException("The vectors with id "
							+ id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(Integer id) throws NonexistentEntityException {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Vector vector;
			try {
				vector = em.getReference(Vector.class, id);
				vector.getId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The vector with id "
						+ id + " no longer exists.", enfe);
			}
			em.remove(vector);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Vector> findVectorEntities() {
		return findVectorEntities(true, -1, -1);
	}

	public List<Vector> findMensagensEntities(int maxResults, int firstResult) {
		return findVectorEntities(false, maxResults, firstResult);
	}

	@SuppressWarnings("unchecked")
	private List<Vector> findVectorEntities(boolean all, int maxResults,
			int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Vector.class));
			Query q = em.createQuery(cq);
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	public Vector findVector(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Vector.class, id);
		} finally {
			em.close();
		}
	}

	public int getVectorCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
			Root<Vector> rt = cq.from(Vector.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}

	public void truncateTable(String tab) {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			em.createNativeQuery("truncate table " + tab).executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
		}
	}

}
