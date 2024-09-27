package com.ddk.asmsof306.component;

import com.ddk.asmsof306.security.auth.RegisterRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;

@Component
public class ApplicationQueu {
    @Bean("REGISTER_QUEUE")
    ArrayDeque<RegisterRequest> registerRequestsQueue(){
        return new ArrayDeque<>();
    }
}
