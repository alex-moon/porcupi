package com.github.alex_moon.porcupi.controllers;

import java.sql.SQLException;

import spark.Spark;

import com.github.alex_moon.porcupi.models.Account;
import com.github.alex_moon.porcupi.responses.Response;

public class Accounts extends Controller {
    public Accounts() {
        Spark.get("/", (request, response) -> {
            return success("Welcome to Porcupi!");
        });

        Spark.get("/account/:accountNumber/", (request, response) -> {
            String accountNumber = request.params(":accountNumber");
            Account account = getByAccountNumber(accountNumber);
            if (account == null) {
                return error(String.format(
                    "Could not find account number \"%s\"",
                    accountNumber
                ));
            }
            return success(account);
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