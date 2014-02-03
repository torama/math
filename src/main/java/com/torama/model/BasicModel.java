package com.torama.model;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.torama.model.i.IBasicModel;

@Repository
public class BasicModel<T> implements IBasicModel<T> {
	
	@Autowired(required=true)
	protected SessionFactory sessionFactory;

	/**
	 * {@inheritDoc}
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T persistOrMerge(T object) throws Exception {
		T result = (T) sessionFactory.getCurrentSession().merge(object);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T load(Class<T> model, long id) {
		return (T) sessionFactory.getCurrentSession().load(model, id);
	}
	
	/**
	 * Löscht dieses Objekt.
	 */
	@SuppressWarnings("unchecked")
	public void remove() {
		this.remove((T) this);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(T object) {
		sessionFactory.getCurrentSession().delete(object);
	}
	
	/**
	 * @param criterions
	 * @return
	 */
	private Criteria prepareCriteria(Class<T> model, Criterion[] criterions) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(model);
		if (criterions == null) {
			return criteria;
		}
		for (Criterion actual: criterions) {
			criteria.add(actual);
		}
		return criteria;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T findUniqueByCriterion(Class<T> model, Criterion... criterions) {
		Criteria criteria = prepareCriteria(model, criterions);
		return (T) criteria.uniqueResult();
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCriterion(Class<T> model, Criterion... criterions) {
		Criteria criteria = prepareCriteria(model, criterions);
		return (List<T>) criteria.list();
	}
	
	
}
