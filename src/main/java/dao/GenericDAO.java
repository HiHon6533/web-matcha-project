package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public abstract class GenericDAO<T, ID> {

    protected static EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("matchaPU");

    protected EntityManager em = emf.createEntityManager();

    private Class<T> entityClass;

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public T findById(ID id) {
        return em.find(entityClass, id);
    }

    public List<T> findAll() {
        return em.createQuery("FROM " + entityClass.getSimpleName(), entityClass)
                 .getResultList();
    }

    public void create(T entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    public T update(T entity) {
        em.getTransaction().begin();
        T merged = em.merge(entity);
        em.getTransaction().commit();
        return merged;
    }

    public void delete(T entity) {
        em.getTransaction().begin();
        em.remove(em.contains(entity) ? entity : em.merge(entity));
        em.getTransaction().commit();
    }
}
