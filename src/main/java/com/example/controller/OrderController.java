package com.example.controller;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.Order;
import com.example.service.OrderService;



@Controller
public class OrderController {
 @Autowired
 private OrderService Serv;
 @GetMapping("/checkout")
 public String init() {
	 return "Checkout";
 }
 @PostMapping(value="/create-order", produces="application/json")
 @ResponseBody
 public ResponseEntity<Order>createOrder(@RequestBody Order order)throws Exception{
	Order createdOrder= Serv.createOrder(order);
	 return new ResponseEntity<>(createdOrder,HttpStatus.CREATED);
 }
 @PostMapping("handle-payment-callback")
 public String handlePaymentCallback(@RequestParam Map<String,String> respPayLoad) {
	 System.out.println(respPayLoad);
	 Serv.updateorder(respPayLoad);
	return "success";
 }
}