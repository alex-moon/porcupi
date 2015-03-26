package com.github.alex_moon.porcupi.views;

import spark.Request;
import spark.Response;

import com.github.alex_moon.porcupi.controllers.AccountController;
import com.github.alex_moon.porcupi.models.Account;

public class AccountView extends View {
    private Account account;
    private AccountController controller;
    
    public AccountView(AccountController controller) {
        this.controller = controller;
    }
    
    public String getAccount(Request request, Response response) {
        String accountNumber = request.params(":accountNumber");
        account = AccountController.getByAccountNumber(accountNumber);
        if (account == null) {
            return error(String.format(
                "Could not find account number \"%s\"",
                accountNumber
            ));
        }
        if (controller.getExampleFlag()) { return success("Account is hidden (for example)"); }
        return success(account);
    }
    
    public String postAccount(Request request, Response response) {
        account = gson.fromJson(request.body(), Account.class);
        controller.getOrCreate(account);
        return success(account);
    }
}
