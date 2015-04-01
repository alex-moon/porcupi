package com.github.alex_moon.porcupi;

import java.util.ArrayList;
import java.util.List;

import com.github.alex_moon.porcupi.controllers.AccountController;
import com.github.alex_moon.porcupi.controllers.Controller;
import com.github.alex_moon.porcupi.manager.ManagerServer;
import com.github.alex_moon.porcupi.manager.ManagerClient;;

class Porcupi {
    private static List<Controller> modules = new ArrayList<Controller>();

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("shell")) {
            new ManagerClient();
        } else {
            new ManagerServer();
            modules.add(new AccountController());
        }
    }
}
