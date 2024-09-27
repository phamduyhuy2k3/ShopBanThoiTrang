package com.ddk.asmsof306.service;

import com.ddk.asmsof306.repository.OrdersDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrdersDetailService {
    private final OrdersService ordersService;
    private final ProductService productService;
    private final OrdersDetailRepository ordersDetailRepository;


}
