/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.garimpeiro.dao;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 * Classe abstrata para acesso as entidades
 *
 * @see Garimpeiro
 * @author Tiago Michel <micheltiago@hotmail.com>
 * @Phone 51-92331335
 */
public abstract class AbstractDao {

    private static EntityManager entityManager;

    /**
     *
     * @param factory
     */
    public AbstractDao(EntityManagerFactory factory) {
        entityManager = factory.createEntityManager();
    }

    /**
     * Localiza Entidade pela chave primaria.
     *
     * @param clazz
     * @param id
     * @return
     */
    protected Object find(Class clazz, Long id) {
        try {
            return entityManager.find(clazz, id);
        } catch (Exception e) {
            //Nao e um erro :)
        }
        return null;
    }

    /**
     * Localiza Totas as Entidades.
     *
     * @param clazz
     * @return
     */
    protected List findAll(Class clazz) {

        try {
            Query q = entityManager.createNamedQuery(clazz.getSimpleName() + ".findAll");
            return q.getResultList();
        } catch (Exception e) {
            //Nao e um erro :)
        }
        return null;
    }

    /**
     * Localiza Entidade pela chave primaria usando NamedQuery.
     *
     * @param clazz
     * @param id
     * @return
     */
    protected Object findById(Class clazz, Long id) {
        try {
            Query q = entityManager.createNamedQuery(clazz.getSimpleName() + ".findById");
            q.setParameter("id", id);
            return q.getSingleResult();
        } catch (Exception e) {
            //Nao e um erro :)
        }
        return null;
    }

    /**
     * Localiza Entidade pela chave primaria usando NamedQuery.
     *
     * @param clazz
     * @param namedQuery
     * @return
     */
    protected List findByNamedQuery(Class clazz, String namedQuery) {
        try {
            Query q = entityManager.createNamedQuery(clazz.getSimpleName() + "." + namedQuery);
            return q.getResultList();
        } catch (Exception e) {
            //Nao e um erro :)
        }
        return null;
    }

    /**
     * Localizar utilizado query nativa (SQL Puro).
     *
     * @param sql
     * @param clazz
     * @return
     */
    protected List<Class> nativeFind(String sql, Class clazz) {
        try {
            Query q = entityManager.createNativeQuery(sql, clazz);
            return q.getResultList();
        } catch (Exception e) {
            //Nao e um erro :)
        }
        return null;
    }

    /**
     * Localiza de acordo com o parametro passado
     *
     * @param clazz
     * @param operation
     * @param value
     * @return Object
     */
    protected Object findByParam(Class clazz, String operation, String value) {
        try {
            Query q = entityManager.createNamedQuery(clazz.getSimpleName() + ".findBy" + operation);
            q.setParameter(operation.toLowerCase(), value);
            return q.getSingleResult();
        } catch (Exception e) {
            //Nao e um erro :)
        }
        return null;
    }

    /**
     * Localiza de um registro acordo com o objeto passado.
     *
     * @param clazz
     * @param operation
     * @param objeto
     * @return Object
     */
    protected Object findByParam(Class clazz, String operation, Object objeto) {
        try {
            Query q = entityManager.createNamedQuery(clazz.getSimpleName() + ".findBy" + operation);
            q.setParameter(operation.toLowerCase(), objeto);
            return q.getSingleResult();
        } catch (Exception e) {
            System.out.println("ERROORO " + e);
        }
        return null;
    }

    /**
     * Localiza varios registros de acordo com o parametro passado.
     *
     * @param clazz
     * @param operation
     * @param value
     * @return Object
     */
    protected List findAllByParam(Class clazz, String operation, String value) {
        try {
            Query q = entityManager.createNamedQuery(clazz.getSimpleName() + ".findBy" + operation);
            q.setParameter(operation.toLowerCase(), value);
            return q.getResultList();
        } catch (Exception e) {
            //Nao e um erro :)
        }
        return null;
    }

    /**
     * Cria Entidade no banco de dados.
     *
     * @param entity
     * @param clazz
     * @return
     */
    protected Object create(Object entity, Class clazz) {

        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            System.err.println("Erro ao criar Entidade " + e.getMessage());
        }
        return null;
    }

    /**
     * Atualiza entidade no banco de dados.
     *
     * @param entity
     * @return
     */
    protected Object update(Object entity) {

        try {
            entityManager.getTransaction().begin();
            Object o = entityManager.merge(entity);
            entityManager.getTransaction().commit();
            return o;
        } catch (Exception e) {
            System.err.println("Erro ao atualizar Entidade " + e.getMessage());
        }

        return null;
    }

    /**
     * Remove entidade do banco de dados.
     *
     * @param entity
     */
    protected void delete(Object entity) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Erro ao remover Entidade " + e.getMessage());
        }
    }

    List findByParamList(Class clazz, String operation, Map<String, Object> parameters) {
        try {
            Query q = entityManager.createNamedQuery(clazz.getSimpleName() + ".findBy" + operation);
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                q.setParameter(entry.getKey().toLowerCase(), entry.getValue());
            }
            return q.getResultList();
        } catch (Exception e) {
            System.err.println("Erro " + e);
            //Nao e um erro :)
        }
        return null;
    }
}
