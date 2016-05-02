package io.sponges.bot.dashboard;

import io.sponges.bot.dashboard.dashboard.Dashboard;
import spark.ModelAndView;
import spark.template.jade.JadeTemplateEngine;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFileLocation;

public class Main {

    public static final String LAYOUT_TITLE_KEY = "title";
    public static final String LAYOUT_HEADER_TITLE_KEY = "header_title";
    public static final String LAYOUT_HEADER_SUBTITLE_KEY = "header_subtitle";

    private final JadeTemplateEngine engine = new JadeTemplateEngine();
    private final Map<String, Dashboard> dashboards = new HashMap<>();

    public Main() {
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put(LAYOUT_TITLE_KEY, "Home");
            model.put(LAYOUT_HEADER_TITLE_KEY, "I'm the Genie");
            model.put(LAYOUT_HEADER_SUBTITLE_KEY, "Your wish can be satisfied by my commands (all 74 of them)");
            return new ModelAndView(model, "index.jade");
        }, engine);

        get("/about", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put(LAYOUT_TITLE_KEY, "About");
            model.put(LAYOUT_HEADER_TITLE_KEY, "About the Genie");
            model.put(LAYOUT_HEADER_SUBTITLE_KEY, "Why do I give you all these wishes?");
            return new ModelAndView(model, "about.jade");
        }, engine);

        get("/donate", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put(LAYOUT_TITLE_KEY, "Donate");
            model.put(LAYOUT_HEADER_TITLE_KEY, "Donate to the Genie");
            model.put(LAYOUT_HEADER_SUBTITLE_KEY, "Put some wishes back in my lamp.");
            return new ModelAndView(model, "donate.jade");
        }, engine);

        get("/documentation", (request, response) -> "SoonTM");

        get("/login", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put(LAYOUT_TITLE_KEY, "Login");
            model.put(LAYOUT_HEADER_TITLE_KEY, "Login to the Genie");
            model.put(LAYOUT_HEADER_SUBTITLE_KEY, "Only 2 wishes; your email address and password.");
            return new ModelAndView(model, "login.jade");
        }, engine);

        get("/register", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put(LAYOUT_TITLE_KEY, "Register");
            model.put(LAYOUT_HEADER_TITLE_KEY, "Register a new Genie account");
            model.put(LAYOUT_HEADER_SUBTITLE_KEY, "3 wishes; your email address, password and your password again.");
            return new ModelAndView(model, "register.jade");
        }, engine);

        post("/confirm_login", (request, response) -> {
            // TODO add login logic here
            response.redirect("/dashboard");
            return "Logged in! Redirecting...";
        });

        get("/dashboard", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            if (request.queryParams().contains("network")) {
                String networkId = request.queryParams("network");
                Dashboard dashboard;
                if (dashboards.containsKey(networkId)) {
                    dashboard = dashboards.get(networkId);
                } else {
                    dashboard = new Dashboard(networkId);
                    dashboards.put(networkId, dashboard);
                }
                return dashboard.render(request, response);
            }
            model.put(LAYOUT_TITLE_KEY, "Select A Chat");
            model.put(LAYOUT_HEADER_TITLE_KEY, "Select a Genie");
            model.put(LAYOUT_HEADER_SUBTITLE_KEY, "Which chat should I give my wishes to?");
            return new ModelAndView(model, "select.jade");
        }, engine);
    }

    public static void main(String[] args) throws IOException {
        staticFileLocation("/static");
        new Main();
    }

}
