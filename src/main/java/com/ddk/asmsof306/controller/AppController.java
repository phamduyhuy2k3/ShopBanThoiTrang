package com.ddk.asmsof306.controller;

import com.ddk.asmsof306.component.Breadcrumb;
import com.ddk.asmsof306.model.Account;
import com.ddk.asmsof306.enums.PAYMENTMETHOD;
import com.ddk.asmsof306.security.auth.AuthenticationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class AppController {
    private final HttpSession session;
    private final AuthenticationService authenticationService;
    @GetMapping({"/","/index","/home","index.html"})
    String homePage(Model model){
//        List<Authority> authorities=new ArrayList<>();
//        authorities.add(Authority.builder().role(Role.ADMIN).build());
//        RegisterRequest registerRequest= RegisterRequest
//                .builder()
//                .authorities(authorities)
//                .email("huyeptrai821@gmail.com")
//                .password("123456789")
//                .username("phamduyhuy")
//                .build();
//        authenticationService.register(registerRequest);

        List<Breadcrumb> breadcrumbs=new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("Home","/"));
        session.setAttribute("breadcrumb",breadcrumbs);
        model.addAttribute("disableHeader",true);
        model.addAttribute("isHomePage",true);
        return "index";
    }


    @GetMapping({"/cart.html"})
    String shoppingCart(Model model){
        List<Breadcrumb> breadcrumbs=new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("Home","/"));
        breadcrumbs.add(new Breadcrumb("Shopping Cart","/cart.html"));
        session.setAttribute("breadcrumb",breadcrumbs);
        model.addAttribute("title","Shopping Cart");
        return "cart";
    }
    @GetMapping({"/contact.html","/contact"})
    String contactPage(Model model){
        List<Breadcrumb> breadcrumbs=new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("Home","/"));
        breadcrumbs.add(new Breadcrumb("Contact","/contact.html"));
        session.setAttribute("breadcrumb",breadcrumbs);
        model.addAttribute("title","Contact");
        return "contact";
    }


    @GetMapping({"/checkout.html","/checkout"})
    String checkOutPage(Model model, @AuthenticationPrincipal Account user){
        if (user!=null){
            model.addAttribute("user",user);
        }else {
            return "redirect:/login?unauthorized";
        }
        List<Breadcrumb> breadcrumbs=new ArrayList<>();
        breadcrumbs.add(new Breadcrumb("Home","/"));
        breadcrumbs.add(new Breadcrumb("Checkout","/checkout.html"));
        session.setAttribute("breadcrumb",breadcrumbs);
        model.addAttribute("title","Checkout");
        List<PAYMENTMETHOD> paymentmethods= Arrays.stream(PAYMENTMETHOD.values()).toList();
        model.addAttribute("paymentmethods",paymentmethods);

        return "checkout";
    }
//    @GetMapping({"/login.html","/login"})
//    String loginPage(Model model){
//        model.addAttribute("disableHeader",true);
//        return "login";
//    }


}
