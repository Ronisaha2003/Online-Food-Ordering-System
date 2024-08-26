package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Order;

public interface OrderRepo extends JpaRepository<Order, Integer> {
	 public Order  findByRazorpayOrderId(String orderId);
	}