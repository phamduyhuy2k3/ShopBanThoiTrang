package com.ddk.asmsof306.validation.constraint;


import com.ddk.asmsof306.repository.AccountRepository;
import com.ddk.asmsof306.validation.annotations.UsernameUnique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserNameUniqueValidation implements ConstraintValidator<UsernameUnique, String> {
    @Autowired
    AccountRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userRepository.existsByUsername(email);
    }
}
