package com.example.awesomebusiness.controller;

import com.example.awesomebusiness.service.ShoppingCartService;
import io.seata.core.exception.GlobalTransactionException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
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

	@PostMapping("/placeOrder/{model}")
	public String placeOrder(@PathVariable("model") String model) throws GlobalTransactionException {
		if (StringUtils.equalsIgnoreCase(model, "AT")) {
			return shoppingCartService.placeOrder4AT();
		} else if (StringUtils.equalsIgnoreCase(model, "XA")) {
			return shoppingCartService.placeOrder4XA();
		} else if (StringUtils.equalsIgnoreCase(model, "TCC")) {
			return shoppingCartService.placeOrder4TCC();
		}
		return shoppingCartService.placeOrder4AT();
	}
}
