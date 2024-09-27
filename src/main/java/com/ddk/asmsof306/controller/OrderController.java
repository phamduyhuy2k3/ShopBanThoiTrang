package com.ddk.asmsof306.controller;

import com.ddk.asmsof306.component.Breadcrumb;
import com.ddk.asmsof306.model.Account;
import com.ddk.asmsof306.service.OrdersService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final HttpSession session;
    private final OrdersService ordersService;
    @GetMapping("/user/orders")
    public String orderHistory(Model model, @AuthenticationPrincipal Account account){
        List<Breadcrumb> breadcrumbs=new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("Home","/"));
        breadcrumbs.add(new Breadcrumb("Order history","/user/orders"));
        session.setAttribute("breadcrumb",breadcrumbs);
        model.addAttribute("orders",ordersService.findByUsername(account.getId()));
        return "ordersHistory";
    }
    @GetMapping("/user/orders/{id}")
    public String orderDetail(@PathVariable("id") Integer id, Model model){
        List<Breadcrumb> breadcrumbs=new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("Home","/"));
        breadcrumbs.add(new Breadcrumb("Order history","/user/orders"));
        breadcrumbs.add(new Breadcrumb("Order detail","/user/orders/"+id));
        session.setAttribute("breadcrumb",breadcrumbs);
        model.addAttribute("order",ordersService.findById(id));
        return "ordersDetail";
    }
}
