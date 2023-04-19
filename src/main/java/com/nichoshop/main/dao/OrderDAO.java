package com.nichoshop.main.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nichoshop.main.model.Order;

@Transactional
@Repository
public class OrderDAO implements IOrderDAO {
	@PersistenceContext
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getOrders(String hql) {

		return (List<Order>) entityManager.createQuery(hql).getResultList();
	}

}
