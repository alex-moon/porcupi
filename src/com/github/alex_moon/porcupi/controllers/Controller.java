package com.github.alex_moon.porcupi.controllers;

import java.sql.SQLException;
import java.util.List;

import com.github.alex_moon.porcupi.Config;
import com.github.alex_moon.porcupi.models.Model;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

public class Controller {
    protected static Dao<Model, String> getDao(Class klass) throws SQLException {
        return DaoManager.createDao(Config.getDb(), klass);
    }
    
    protected static List query(Class klass, String fieldname, Object value) throws SQLException {
        return getDao(klass).queryForEq(fieldname, value);
    }
}
