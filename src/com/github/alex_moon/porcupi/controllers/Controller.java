package com.github.alex_moon.porcupi.controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.alex_moon.porcupi.Config;
import com.github.alex_moon.porcupi.manager.Pokeable;
import com.github.alex_moon.porcupi.models.Model;
import com.github.alex_moon.porcupi.responses.Response;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

public class Controller implements Pokeable {
    protected List<Handler> handlers = new ArrayList<Handler>();


    // pub-sub stuff
    public void registerHandler(Handler handler) {
        handlers.add(handler);
    }
    
    public List<String> handle(String key, Object data) {
        List<String> results = new ArrayList<String>();
        for (Handler handler : handlers) {
            if (handler.canHandle(key)) {
                results.add(handler.handle(key, data));
            }
        }
        return results;
    }


    // data-layer stuff - @todo put elsewhere
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
    
    public Map<String, Object> poke() {
        Map<String, Object> results = new HashMap<String, Object>();
        results.put("className", "Controller");
        return results;
    }
}
