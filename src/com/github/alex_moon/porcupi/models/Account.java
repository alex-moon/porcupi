package com.github.alex_moon.porcupi.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName="accounts")
public class Account implements Model {
    @DatabaseField(generatedId=true)
    private Integer id;

    @DatabaseField(useGetSet=true, columnName="account_number")
    private String accountNumber;

    public String doAThing() {
        return "My account number is: " + accountNumber;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
}