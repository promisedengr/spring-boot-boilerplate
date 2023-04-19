package com.nichoshop.main.service;

import java.util.List;
import org.joda.time.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.nichoshop.main.repository.MessageRepository;
import com.nichoshop.main.repository.OrderRepository;
import com.nichoshop.main.request.CancelMessageRequest;
import com.nichoshop.main.request.MessageBodyRequest;
import com.nichoshop.main.request.SellerOrderRequest;
import com.nichoshop.main.model.Message;
import com.nichoshop.main.model.Order;
import com.nichoshop.main.dao.IOrderDAO;

@Service
public class OrderSellerService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    IOrderDAO dao;
    @Autowired
    MessageRepository messageRepository;

    public List<?> getList(int sellerId, SellerOrderRequest sellerOrderReq) {
        String hql;
        // setting the search key first
        if (sellerOrderReq.getSearchKey() == 0) {
            hql = "SELECT p, o FROM Order o INNER JOIN Item p ON ( p.id = o.itemId ) WHERE p.nsln LIKE '%"
                    + sellerOrderReq.getSearchWord() + "%'";
            // refer select p as item
        } else if (sellerOrderReq.getSearchKey() == 1) {
            hql = "SELECT p, o FROM Order o INNER JOIN Item p ON ( p.id = o.itemId ) WHERE p.title LIKE '%"
                    + sellerOrderReq.getSearchWord() + "%'";

        } else if (sellerOrderReq.getSearchKey() == 2) {
            hql = "SELECT o FROM Order o WHERE o.orderId = " + sellerOrderReq.getSearchWord();

        } else { // sellerOrderReq.getSearchKey() == 3
            hql = "SELECT p, o FROM Order o INNER JOIN User p ON ( p.id = o.buyerId ) WHERE p.name LIKE '%"
                    + sellerOrderReq.getSearchWord() + "%'";
        }
        // set sellerId
        hql += " AND o.sellerId = " + String.valueOf(sellerId);
        // calculate the duration
        DateTime now = new DateTime();
        int days;
        if (sellerOrderReq.getDuration() == 0) {
            days = 1;
        } else if (sellerOrderReq.getDuration() == 1) {
            days = 3;
        } else if (sellerOrderReq.getDuration() == 2) {
            days = 7;
        } else if (sellerOrderReq.getDuration() == 3) {
            days = 14;
        } else if (sellerOrderReq.getDuration() == 4) {
            days = 30;
        } else { // sellerOrderReq.getDuration() == 5
            days = 60;
        }
        DateTime date = now.minusDays(days).withTimeAtStartOfDay();
        hql += " AND o.createdAt > '" + date.toString() + "'";
        // sort by sort key
        if (sellerOrderReq.getSort() == 0) {
            hql += " ORDER BY o.soldDate ASC";
        } else if (sellerOrderReq.getSort() == 1) {
            hql += " ORDER BY o.soldDate DESC";
        } else if (sellerOrderReq.getSort() == 2) {
            hql += " ORDER BY o.shipByDate ASC";
        } else { // when it is equals to 3
            hql += " ORDER BY o.shipByDate DESC";
        }
        System.out.println(hql);
        List<?> orders = dao.getOrders(hql);
        return orders;
    }

    public Order getOrderDetail(Long sellerId, Long orderId) {
        return orderRepository.findBySellerIdANDOrderId(sellerId, orderId);
    }

    public Order confirmShipment(Long sellerId, Long orderId) {
        Order order = orderRepository.findBySellerIdANDOrderId(sellerId, orderId);
        order.setStatus(2); // 2: confirmed shipment
        orderRepository.save(order);
        return order;
    }

    public void createCancelMessage(Long fromId, MessageBodyRequest cancelMessageReq) {
        Message message = new Message();
        message.setFromId(fromId);
        message.setToId(cancelMessageReq.getToId());
        message.setReason(cancelMessageReq.getReason());
        message.setContent(cancelMessageReq.getMessage());
        messageRepository.save(message);
    }

    public Message retund(Long fromId, MessageBodyRequest cancelMessageReq) {
        Message message = new Message();
        message.setFromId(fromId);
        message.setToId(cancelMessageReq.getToId());
        message.setReason(cancelMessageReq.getReason());
        message.setContent(cancelMessageReq.getMessage());
        messageRepository.save(message);
        return message;
    }

}
