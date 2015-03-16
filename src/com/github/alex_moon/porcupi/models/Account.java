package com.github.alex_moon.porcupi.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName="accounts")
public class Account implements Model {
    @DatabaseField(generatedId=true)
    private Integer id;

    @DatabaseField(columnName="account_number")
    private String accountNumber;

    @DatabaseField(canBeNull=true)
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}