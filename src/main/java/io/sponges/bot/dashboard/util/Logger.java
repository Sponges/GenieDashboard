package io.sponges.bot.dashboard.util;

import spark.Request;

public final class Logger {

    public static void log(Request request) {
        System.out.println(request.requestMethod() + " " + request.pathInfo() + " " + request.ip() + " " + request.headers());
    }

}
