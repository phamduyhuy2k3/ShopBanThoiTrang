package com.ddk.asmsof306.service;

import com.ddk.asmsof306.model.Account;
import com.ddk.asmsof306.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    AccountRepository dao;

    public List<Account> findAll() {
        return dao.findAll();
    }

    public Account findById(Integer id) {
        return dao.findById(id).get();
    }
    public Optional<Account> findByUserNameOrEmail(String username) {
        return dao.findByUsernameOrEmail(username);
    }
//    public List<Account> getAdministrators() {
//        return dao.getAdministrators();
//    }
}
