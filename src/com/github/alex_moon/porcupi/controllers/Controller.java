package com.github.alex_moon.porcupi.controllers;

import java.sql.SQLException;
import java.util.List;

import com.github.alex_moon.porcupi.Config;
import com.github.alex_moon.porcupi.models.Account;
import com.github.alex_moon.porcupi.models.Model;
import com.github.alex_moon.porcupi.responses.Response;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

public class Controller {
    protected static Gson gson = new Gson();

    public String success(Object object) {
        Response response = Response.getSuccessResponse();
        response.setObject(object);
        return gson.toJson(response);
    }

    public String error(String message) {
        Response response = Response.getErrorResponse();
        response.setMessage(message);
        return gson.toJson(response);
    }

    protected static Dao<? extends Model, ?> getDao(Class<? extends Model> klass) throws SQLException {
        return DaoManager.createDao(Config.getDb(), klass);
    }

    protected static List<? extends Model> query(Class<? extends Model> klass, String fieldname, Object value) throws SQLException {
        return getDao(klass).queryForEq(fieldname, value);
    }

    protected static int update(Class<? extends Model> klass, Model object) {
        Dao<Model, String> dao;
        try {
            dao = (Dao<Model, String>) getDao(klass);
            return dao.update(klass.cast(object));
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    protected static int create(Class<? extends Model> klass, Model object) {
        Dao<Model, String> dao;
        try {
            dao = (Dao<Model, String>) getDao(klass);
            return dao.create(klass.cast(object));
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
