package com.ddk.asmsof306.restController;

import com.ddk.asmsof306.model.Orders;
import com.ddk.asmsof306.service.OrdersService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/api/orders")
public class OrdersRestController {
    private final OrdersService ordersService;
    @PostMapping("/purchase")
    ResponseEntity<Orders> purchase(@RequestBody JsonNode orderData) {
        Orders orders= ordersService.create(orderData);
        if (orders == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(orders);
    }
}
