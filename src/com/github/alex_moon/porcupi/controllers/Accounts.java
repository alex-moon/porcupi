package com.github.alex_moon.porcupi.controllers;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import spark.Spark;

import com.github.alex_moon.porcupi.manager.Poke;
import com.github.alex_moon.porcupi.models.Account;

public class Accounts extends Controller {
    private Boolean exampleFlag = false;
    private Account account;

    public Accounts() {
        Spark.get("/", (request, response) -> {
            return success("Welcome to Porcupi!");
        });

        Spark.get("/account/:accountNumber/", (request, response) -> {
            String accountNumber = request.params(":accountNumber");
            account = getByAccountNumber(accountNumber);
            if (account == null) {
                return error(String.format(
                    "Could not find account number \"%s\"",
                    accountNumber
                ));
            }
            if (exampleFlag) { return success("Account is hidden (for example)"); }
            return success(account);
        });

        Spark.post("/account/", (request, response) -> {
            account = gson.fromJson(request.body(), Account.class);
            Poke.poke(this);
            if (account.getId() == null) {
                create(Account.class, account);
            } else {
                update(Account.class, account);
            }
            return success(account);
        });
        
        registerHandler(new ExampleHandler());
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
            if (key.equals("example")) {
                return true;
            }
            return false;
        }
    }

    public static Account getByAccountNumber(String accountNumber) {
        try {
            return (Account) query(Account.class, "account_number", accountNumber).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public Map<String, Object> poke() {
        Map<String, Object> results = new HashMap<String, Object>();
        results.put("className", "Accounts");
        results.put("exampleFlag", exampleFlag);
        results.put("account", gson.toJson(account));
        return results;
    }
}