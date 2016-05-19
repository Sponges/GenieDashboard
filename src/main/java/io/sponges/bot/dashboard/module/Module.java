package io.sponges.bot.dashboard.module;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public abstract class Module {

    private final String name;

    public Module(String name) {
        this.name = name;
    }

    public abstract ModelAndView execute(Request request, Response response);

    public String getName() {
        return name;
    }
}
