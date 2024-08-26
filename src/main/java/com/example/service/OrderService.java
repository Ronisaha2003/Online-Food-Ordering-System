package com.example.service;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.razorpay.RazorpayClient;
import com.example.model.Order;
import com.example.repository.OrderRepo;

@Service
public class OrderService {
	@Autowired
  private OrderRepo orderRepo;
	@Value("${razorpay.key.id}")
	private String razorPayKey;
	@Value("${razorpay.secret.key}")
	private String razorPaySecret;

     private RazorpayClient client;
	
	public Order createOrder(Order order) throws Exception {
		JSONObject orderReq=new JSONObject();
		orderReq.put("amount", order.getAmount()*100);//amount in paisa
		orderReq.put("currency", "INR");
		orderReq.put("receipt",order.getEmail());
		this.client=new RazorpayClient(razorPayKey,razorPaySecret);
	com.razorpay.Order razorPayOrder = client.orders.create(orderReq);
	
	System.out.println(razorPayOrder);
	
	order.setRazorpayOrderId(razorPayOrder.get("id"));
	order.setOrderStatus(razorPayOrder.get("status"));
	orderRepo.save(order);
	return order;
	}
	
	
	public Order updateorder(Map<String,String> responsePayLoad) {
	String razorPayOrderId =	responsePayLoad.get("razorpay_order_id");
	Order orde =orderRepo.findByRazorpayOrderId(razorPayOrderId);
	orde.setOrderStatus("PAYMENT_COMPLETED");
	Order updatedOrder =orderRepo.save(orde);
	return  updatedOrder;
	}
}
