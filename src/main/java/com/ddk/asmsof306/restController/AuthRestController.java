package com.ddk.asmsof306.restController;


import com.ddk.asmsof306.model.Account;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.ddk.asmsof306.util.Mapper.mapObject;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    @ResponseBody
    @RequestMapping("/authentication")
    public ResponseEntity<Object> getAuthentication(HttpSession session, @AuthenticationPrincipal Account user) {
        if(user==null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Map<String, Object> authen = (Map<String, Object>) session.getAttribute("authentication");
        user.setPassword(null);
        authen.put("user", user);
        return ResponseEntity.ok(authen);
    }
}
