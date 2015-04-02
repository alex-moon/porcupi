package com.github.alex_moon.porcupi.controllers;

import java.sql.SQLException;

import com.github.alex_moon.porcupi.models.Account;
import com.github.alex_moon.porcupi.services.AccountService;
import com.github.alex_moon.porcupi.views.AccountView;

public class AccountController extends Controller {
    public AccountController() {
        views.add(new AccountView(this));
    }

    public void createOrUpdate(Account account) {
        if (account.getId() == null) {
            AccountService.create(Account.class, account);
        } else {
            AccountService.update(Account.class, account);
        }
    }

    public static Account getByAccountNumber(String accountNumber) {
        try {
            return (Account) AccountService.query(Account.class, "account_number", accountNumber).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}