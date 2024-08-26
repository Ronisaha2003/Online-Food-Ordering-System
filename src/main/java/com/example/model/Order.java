package com.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="orders")
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
   private Integer orderId;
   private String name;
  
   private String email;
   private String phno;
   private Integer amount;
   private String orderStatus;
   private String razorpayOrderId;
public Integer getOrderId() {
	return orderId;
}
public void setOrderId(Integer orderId) {
	this.orderId = orderId;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}


public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}

public String getPhno() {
	return phno;
}
public void setPhno(String phno) {
	this.phno = phno;
}
public Integer getAmount() {
	return amount;
}
public void setAmount(Integer amount) {
	this.amount = amount;
}
public String getOrderStatus() {
	return orderStatus;
}
public void setOrderStatus(String orderStatus) {
	this.orderStatus = orderStatus;
}
public String getRazorpayOrderId() {
	return razorpayOrderId;
}
public void setRazorpayOrderId(String razorpayOrderId) {
	this.razorpayOrderId = razorpayOrderId;
}
@Override
public String toString() {
	return "Order [orderId=" + orderId + ", name=" + name + ", email=" + email + ", phno=" + phno
			+ ", amount=" + amount + ", orderStatus=" + orderStatus + ", razorpayOrderId=" + razorpayOrderId + "]";
}

   
}