package com.ddk.asmsof306.security.auth;


import com.ddk.asmsof306.service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService service;
    private final EmailService emailService;

    //    @GetMapping({"/register","/register.html"})
//    String getRegister(Model model, @ModelAttribute RegisterRequest registerRequest) {
//        model.addAttribute("disableHeader",true);
//
//        return "register";
//    }
//    @PostMapping("/register")
//    String register(@Valid @ModelAttribute RegisterRequest registerRequest,Errors result, Model model ) {
//        if(result.hasErrors()) {
//            model.addAttribute("disableHeader",true);
//            return "register";
//        }
//
//        List<Authority> authorities=new ArrayList<>();
//        Authority authority=new Authority();
//        authority.setRole(Role.USER);
//        authorities.add(authority);
//        registerRequest.setAuthorities(authorities);
//        Account user = service.register(registerRequest);
//
//        return "redirect:/login?email="+user.getEmail()+"&registerSuccess=true";
//    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Validated RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }
    //    @PostMapping("/api/register")
//    @ResponseBody
//    void registerOnCheckOut(@Valid @Validated(RegisterRequest.ValidationStepOne.class) @RequestBody RegisterRequest registerRequest, Errors result, HttpServletResponse response){
//        if(result.hasErrors()) {
//            throw new RuntimeException("Invalid data");
//        }
//        List<Authority> authorities=new ArrayList<>();
//        Authority authority=new Authority();
//        authority.setRole(Role.USER);
//        authorities.add(authority);
//        registerRequest.setAuthorities(authorities);
//        Account user = service.register(registerRequest);
//        if(user==null){
//            throw new RuntimeException("Invalid data");
//        }else {
//            registerRequest.setToken(UUID.randomUUID().toString());
//            Cookie cookie = new Cookie("registerToken", registerRequest.getToken());
//            cookie.setMaxAge(3600);
//            cookie.setPath("/api/register/activate");
//            response.addCookie(cookie);
//            emailService.placeRegisterRequest(registerRequest);
//        }
//
//    }
//    @GetMapping("/api/register/activate")
//    String registerStep2(@RequestParam("token") String token, HttpServletRequest request, HttpServletResponse response) {
//        Cookie cookie =
//                Arrays.stream(
//                        request.getCookies())
//                        .filter(
//                                c -> c.getName().equals("registerToken"))
//                        .findFirst()
//                        .orElse(null);
//        if (cookie != null) {
//            String tokenRequest = cookie.getValue();
//            System.out.println(cookie.getMaxAge());
//            System.out.println(System.currentTimeMillis());
//            if(Objects.equals(tokenRequest,token) && System.currentTimeMillis() < cookie.getMaxAge()){
//                cookie.setMaxAge(0);
//                cookie.setPath("/api/register/activate");
//                response.addCookie(cookie);
//            }
//        }else {
//            throw new RuntimeException("Invalid token");
//        }
//        return "registerStep2";
//    }
//    @PostMapping("/api/register/activate")
//    String activeAccount(@Valid @Validated(RegisterRequest.ValidationStepTwo.class) @RequestBody RegisterRequest registerRequest, Errors result, HttpServletResponse response){
//        return "redirect:/login?registerSuccess=true";
//    }
    @GetMapping({"/login", "/login.html"})
    String getLogin(Model model) {
        model.addAttribute("disableHeader", true);
        return "login";
    }

}
