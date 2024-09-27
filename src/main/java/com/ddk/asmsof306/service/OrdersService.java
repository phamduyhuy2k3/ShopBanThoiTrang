package com.ddk.asmsof306.service;

import com.ddk.asmsof306.model.Orders;
import com.ddk.asmsof306.model.OrdersDetail;
import com.ddk.asmsof306.model.Product;
import com.ddk.asmsof306.repository.OrdersDetailRepository;
import com.ddk.asmsof306.repository.OrdersRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final OrdersDetailRepository ordersDetailRepository;
    private final ProductService productService;
    @Transactional(rollbackFor = Exception.class)
    public Orders create(JsonNode orderData) {
        ObjectMapper mapper = new ObjectMapper();
        Orders order = mapper.convertValue(orderData, Orders.class);
        List<OrdersDetail> details = mapper.convertValue(orderData.get("ordersDetails"), new TypeReference<List<OrdersDetail>>() {})
                .stream().peek(d -> d.setOrders(order)).collect(Collectors.toList());
        List<Integer> productIds = details.stream().map(detail -> detail.getProduct().getId()).collect(Collectors.toList());
        List<Product> products = productService.findAllByIds(productIds);
        Map<Integer, Product> productMap = products.stream().collect(Collectors.toMap(Product::getId, p -> p));
        for (OrdersDetail detail : details) {
            Integer productId = detail.getProduct().getId();
            int requestedQuantity = detail.getQuantity();

            Product product = productMap.get(productId);
            if (product == null) {
                throw new RuntimeException("Product not found with ID: " + productId);
            }

            int currentStock = product.getStock();
            if (currentStock < requestedQuantity) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            product.setStock(currentStock - requestedQuantity);
        }

        // Save the order and order details after stock check passes
        ordersRepository.save(order);
        ordersDetailRepository.saveAll(details);

        // Save the updated product stock in one go
        productService.saveAll(products);

        return order;
    }

    public Orders findById(Integer id) {
        return ordersRepository.findById(id).get();
    }

    public List<Orders> findByUsername(Integer userId) {
        return ordersRepository.findByAccount(userId);
    }

    public List<Orders> findAll() {
        return ordersRepository.findAll();
    }
}
