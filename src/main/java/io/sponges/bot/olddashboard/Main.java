package io.sponges.bot.olddashboard;

import io.sponges.bot.olddashboard.dashboard.Dashboard;
import spark.ModelAndView;
import spark.Request;
import spark.Spark;
import spark.TemplateEngine;
import spark.template.mustache.MustacheTemplateEngine;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Main {

    public static final String LAYOUT_TITLE_KEY = "page_title";
    public static final String LAYOUT_HEADER_TITLE_KEY = "header_title";
    public static final String LAYOUT_HEADER_SUBTITLE_KEY = "header_subtitle";
    public static final String NAV_LOGIN_LINK_KEY = "login_link";
    public static final String NAV_LOGIN_TEXT_KEY = "login_text";

    private final TemplateEngine engine = new MustacheTemplateEngine("old/templates");
    private final Map<String, Dashboard> dashboards = new HashMap<>();
    private final Map<String, String> authHashes = new HashMap<>();

    @SuppressWarnings("deprecation")
    public Main() {
        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put(LAYOUT_TITLE_KEY, "Home");
            model.put(LAYOUT_HEADER_TITLE_KEY, "I'm the Genie");
            model.put(LAYOUT_HEADER_SUBTITLE_KEY, "Your wish can be satisfied by my commands (all 74 of them)");
            if (isLoggedIn(request)) {
                model.put(NAV_LOGIN_LINK_KEY, "/dashboard");
                model.put(NAV_LOGIN_TEXT_KEY, "Dashboard");
            } else {
                model.put(NAV_LOGIN_LINK_KEY, "/login");
                model.put(NAV_LOGIN_TEXT_KEY, "Login");
            }
            return new ModelAndView(model, "index.mustache");
        }, engine);

        get("/about", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put(LAYOUT_TITLE_KEY, "About");
            model.put(LAYOUT_HEADER_TITLE_KEY, "About the Genie");
            model.put(LAYOUT_HEADER_SUBTITLE_KEY, "Why do I give you all these wishes?");
            if (isLoggedIn(request)) {
                model.put(NAV_LOGIN_LINK_KEY, "/dashboard");
                model.put(NAV_LOGIN_TEXT_KEY, "Dashboard");
            } else {
                model.put(NAV_LOGIN_LINK_KEY, "/login");
                model.put(NAV_LOGIN_TEXT_KEY, "Login");
            }
            return new ModelAndView(model, "about.mustache");
        }, engine);

        get("/donate", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put(LAYOUT_TITLE_KEY, "Donate");
            model.put(LAYOUT_HEADER_TITLE_KEY, "Donate to the Genie");
            model.put(LAYOUT_HEADER_SUBTITLE_KEY, "Put some wishes back in my lamp.");
            if (isLoggedIn(request)) {
                model.put(NAV_LOGIN_LINK_KEY, "/dashboard");
                model.put(NAV_LOGIN_TEXT_KEY, "Dashboard");
            } else {
                model.put(NAV_LOGIN_LINK_KEY, "/login");
                model.put(NAV_LOGIN_TEXT_KEY, "Login");
            }
            return new ModelAndView(model, "donate.mustache");
        }, engine);

        get("/documentation", (request, response) -> "SoonTM");

        get("/login", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put(LAYOUT_TITLE_KEY, "Login");
            model.put(LAYOUT_HEADER_TITLE_KEY, "Login to the Genie");
            model.put(LAYOUT_HEADER_SUBTITLE_KEY, "Only 2 wishes; your email address and password.");
            if (isLoggedIn(request)) {
                model.put(NAV_LOGIN_LINK_KEY, "/dashboard");
                model.put(NAV_LOGIN_TEXT_KEY, "Dashboard");
            } else {
                model.put(NAV_LOGIN_LINK_KEY, "/login");
                model.put(NAV_LOGIN_TEXT_KEY, "Login");
            }
            return new ModelAndView(model, "login.mustache");
        }, engine);

        get("/register", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put(LAYOUT_TITLE_KEY, "Register");
            model.put(LAYOUT_HEADER_TITLE_KEY, "Register a new Genie account");
            model.put(LAYOUT_HEADER_SUBTITLE_KEY, "3 wishes; your email address, password and your password again.");
            if (isLoggedIn(request)) {
                model.put(NAV_LOGIN_LINK_KEY, "/dashboard");
                model.put(NAV_LOGIN_TEXT_KEY, "Dashboard");
            } else {
                model.put(NAV_LOGIN_LINK_KEY, "/login");
                model.put(NAV_LOGIN_TEXT_KEY, "Login");
            }
            return new ModelAndView(model, "register.mustache");
        }, engine);

        post("/confirm_login", (request, response) -> {
            // TODO actually check credentials
            String body = request.body();
            if (body == null || body.length() == 0) {
                halt(401, "Email and password are required.");
                return "Email and password required.";
            }
            String[] lines = body.split("&");
            String email = URLDecoder.decode(lines[0].substring(lines[0].indexOf("=") + 1));
            String pass = URLDecoder.decode(lines[1].substring(lines[1].indexOf("=") + 1));
            if (email.length() < 3 || pass.length() < 3) {
                halt(401, "Something is too short lol");
                return "ded";
            }
            if (!email.equals("nice@memes.com") || !pass.equals("pass")) {
                halt(401, "Email or pass is wrong.");
                return "rip";
            }
            String emailHash = hash(email);
            String passHash = hash(pass);
            String authHash = hash(emailHash + passHash);
            authHashes.put(emailHash, authHash);
            request.session(true).attribute("auth", authHash);
            request.session().attribute("email", emailHash);
            response.redirect("/dashboard");
            return "Logged in! Redirecting...";
        });

        get("/logout", (request, response) -> {
            if (!request.session().attributes().contains("auth")) {
                return "Not logged in.";
            }
            request.session().removeAttribute("email");
            request.session().removeAttribute("auth");
            response.redirect("/login");
            return "Logged out! Redirecting...";
        });

        get("/dashboard", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            if (request.queryParams().contains("network")) {
                String networkId = request.queryParams("network");
                Dashboard dashboard;
                if (dashboards.containsKey(networkId)) {
                    dashboard = dashboards.get(networkId);
                } else {
                    dashboard = new Dashboard(this, networkId);
                    dashboards.put(networkId, dashboard);
                }
                return dashboard.render(request, response);
            }
            model.put(LAYOUT_TITLE_KEY, "Select A Chat");
            model.put(LAYOUT_HEADER_TITLE_KEY, "Select a Genie");
            model.put(LAYOUT_HEADER_SUBTITLE_KEY, "Which chat should I give my wishes to?");
            if (isLoggedIn(request)) {
                model.put(NAV_LOGIN_LINK_KEY, "/dashboard");
                model.put(NAV_LOGIN_TEXT_KEY, "Dashboard");
            } else {
                model.put(NAV_LOGIN_LINK_KEY, "/login");
                model.put(NAV_LOGIN_TEXT_KEY, "Login");
            }
            model.put("clients_list", "{\"clients\":[{\"prefix\":\"-\",\"id\":\"skype\",\"networks\":0},{\"prefix\":\"-\",\"id\":\"restfulapi\",\"networks\":0}]}");
            model.put("skype_networks", "{\"networks\":[{\"id\":\"19:7a2725ecbea140638d4eb17f0ea85819@thread.skype\"},{\"id\":\"19:0ef269c95a36428f873798978d49db7c@thread.skype\"},{\"id\":\"19:5be248ddbc24422db47d72cf55818054@thread.skype\"},{\"id\":\"19:4c734a16f3ac4d048f3166c420542dc0@thread.skype\"}]}");
            return new ModelAndView(model, "select.mustache");
        }, engine);

        Spark.before("/dashboard", (request, response) -> {
            if (!request.session().attributes().contains("auth") || !request.session().attributes().contains("email")) {
                System.out.println("User is not logged in!");
                response.redirect("/login");
                return;
            }
            // TODO check auth hash against user and pass combination
            String email = request.session().attribute("email");
            String auth = request.session().attribute("auth");
            if (!authHashes.containsKey(email) || !authHashes.get(email).equals(auth)) {
                halt(401, "Invalid auth.");
                return;
            }
            System.out.println("User is logged in!");
        });

        get("/invite", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            model.put(LAYOUT_TITLE_KEY, "Invite the Genie");
            model.put(LAYOUT_HEADER_TITLE_KEY, "Invite the Genie");
            if (isLoggedIn(request)) {
                model.put(NAV_LOGIN_LINK_KEY, "/dashboard");
                model.put(NAV_LOGIN_TEXT_KEY, "Dashboard");
            } else {
                model.put(NAV_LOGIN_LINK_KEY, "/login");
                model.put(NAV_LOGIN_TEXT_KEY, "Login");
            }
            if (!request.queryParams().contains("platform")) {
                model.put(LAYOUT_HEADER_SUBTITLE_KEY, "Add the Genie to your chat.");
                return new ModelAndView(model, "invite.mustache");
            }
            String platform = request.queryParams("platform");
            model.put(LAYOUT_HEADER_SUBTITLE_KEY, "Add the Genie to your " + platform + " chat.");
            if (isLoggedIn(request)) {
                model.put(NAV_LOGIN_LINK_KEY, "/dashboard");
                model.put(NAV_LOGIN_TEXT_KEY, "Dashboard");
            } else {
                model.put(NAV_LOGIN_LINK_KEY, "/login");
                model.put(NAV_LOGIN_TEXT_KEY, "Login");
            }
            return new ModelAndView(model, "invite_to.mustache");
        }, engine);
    }

    public static void main(String[] args) throws IOException {
        staticFileLocation("/old/static");
        new Main();
    }

    private String hash(String input) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        try {
            md.update(input.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] digest = md.digest();
        return new String(digest);
    }

    public boolean isLoggedIn(Request request) {
        if (!request.session().attributes().contains("auth") || !request.session().attributes().contains("email")) {
            return false;
        }
        String email = request.session().attribute("email");
        String auth = request.session().attribute("auth");
        if (!authHashes.containsKey(email)) {
            return false;
        }
        String hash = authHashes.get(email);
        return hash.equals(auth);
    }

}
