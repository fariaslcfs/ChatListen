package br.fatecsjc.controller;

/**
 * @author Luiz Carlos Farias da Silva
 * Class contains controller methods for CRUD operations of messages table
 */

import br.fatecsjc.exceptions.NonexistentEntityException;
import br.fatecsjc.model.Messages;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class MessagesJpaController implements Serializable {

	private static final long serialVersionUID = 1L;

	public MessagesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
	
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Messages messages) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(messages);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Messages messages) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            messages = em.merge(messages);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = messages.getId();
                if (findMessages(id) == null) {
                    throw new NonexistentEntityException("The mensagens with id " + id + " no longer exists.");
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
            Messages messages;
            try {
                messages = em.getReference(Messages.class, id);
                messages.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mensagens with id " + id + " no longer exists.", enfe);
            }
            em.remove(messages);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public List<Messages> findMessagesEntities() {
        return findMessagesEntities(true, -1, -1);
    }

    public List<Messages> findMessagesEntities(int maxResults, int firstResult) {
        return findMessagesEntities(false, maxResults, firstResult);
    }

    @SuppressWarnings("unchecked")
	private List<Messages> findMessagesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Messages.class));
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

    public Messages findMessages(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Messages.class, id);
        } finally {
            em.close();
        }
    }

    public int getMessagesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            Root<Messages> rt = cq.from(Messages.class);
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
	
	public void createIndex() {
		EntityManager em = getEntityManager();
		try {
			em.getTransaction().begin();
			em.createNativeQuery("alter table messages add index (id)").executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
		}
	}
    
}
