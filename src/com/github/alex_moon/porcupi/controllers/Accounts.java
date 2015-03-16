package com.github.alex_moon.porcupi.controllers;

import java.sql.SQLException;

import spark.Spark;

import com.github.alex_moon.porcupi.models.Account;

public class Accounts extends Controller {
    public Accounts() {
        Spark.get("/", (request, response) -> {
            return "You doed it lol";
        });

        Spark.get("/account/:accountNumber", (request, response) -> {
            String accountNumber = request.params(":accountNumber");
            Account account = getByAccountNumber(accountNumber);
            if (account == null) {
                return String.format("Sorry, we couldn't find account number '%s'", accountNumber);
            }
            return account.doAThing();
        });
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
}