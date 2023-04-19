package com.nichoshop.main.dao;

import java.util.List;

import com.nichoshop.main.model.Order;

public interface IOrderDAO {
	
	List<Order> getOrders(String hql);
	// Book getBook(int bookId);
	// Book createBook(Book book);
	// Book updateBook(int bookId,Book book);
	// boolean deleteBook(int bookId);

}