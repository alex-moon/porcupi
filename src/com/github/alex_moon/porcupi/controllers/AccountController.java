package com.github.alex_moon.porcupi.controllers;

import java.sql.SQLException;

import com.github.alex_moon.porcupi.models.Account;
import com.github.alex_moon.porcupi.services.AccountService;
import com.github.alex_moon.porcupi.views.AccountView;

public class AccountController extends Controller {
    private Boolean exampleFlag = false;

    public AccountController() {
        super();
        new AccountView(this);
        registerHandler(new ExampleHandler());
    }

    public Boolean getExampleFlag() {
        return exampleFlag;
    }

    public void getOrCreate(Account account) {
        if (account.getId() == null) {
            AccountService.create(Account.class, account);
        } else {
            AccountService.update(Account.class, account);
        }
    }

    private class ExampleHandler implements Handler {
        public String handle(String key, Object data) {
            if (key.equals("example")) {
                exampleFlag = !exampleFlag;
                return "success: exampleFlag is now " + exampleFlag;
            }
            return null;
        }
        
        public Boolean canHandle(String key) {
            return key.equals("example");
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