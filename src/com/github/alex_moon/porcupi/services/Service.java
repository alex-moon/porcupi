package com.github.alex_moon.porcupi.services;

import java.sql.SQLException;
import java.util.List;

import com.github.alex_moon.porcupi.Config;
import com.github.alex_moon.porcupi.models.Model;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

public class Service {
    public static Dao<? extends Model, ?> getDao(Class<? extends Model> klass) throws SQLException {
        return DaoManager.createDao(Config.getDb(), klass);
    }

    public static List<? extends Model> query(Class<? extends Model> klass, String fieldname, Object value) throws SQLException {
        return getDao(klass).queryForEq(fieldname, value);
    }

    public static int update(Class<? extends Model> klass, Model object) {
        Dao<Model, String> dao;
        try {
            dao = (Dao<Model, String>) getDao(klass);
            return dao.update(klass.cast(object));
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static int create(Class<? extends Model> klass, Model object) {
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
