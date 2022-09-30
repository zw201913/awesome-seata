package com.example.awesomebusiness.controller;

import com.example.awesomebusiness.service.ShoppingCartService;
import io.seata.core.exception.GlobalTransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zouwei
 * @className ShoppingCartController
 * @date: 2022/9/18 13:55
 * @description:
 */
@RestController
@RequestMapping(value = "/shoppingCart")
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService shoppingCartService;

	@PostMapping("/placeOrder")
	public String placeOrder() throws GlobalTransactionException {
		return shoppingCartService.placeOrder();
	}

}
