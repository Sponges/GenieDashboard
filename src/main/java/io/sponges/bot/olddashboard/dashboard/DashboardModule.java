package io.sponges.bot.olddashboard.dashboard;

import io.sponges.bot.olddashboard.Main;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public abstract class DashboardModule {

    private final Main main;
    private final String id;

    protected DashboardModule(Main main, String id) {
        this.main = main;
        this.id = id;
    }

    public abstract ModelAndView render(Request request, Response response);


    protected Map<String, Object> getDefaultModel(Request request, String networkId) {
        Map<String, Object> model = new HashMap<>();
        model.put(Main.LAYOUT_TITLE_KEY, "Dashboard");
        model.put(Main.LAYOUT_HEADER_TITLE_KEY, "Manage the Genie");
        model.put(Main.LAYOUT_HEADER_SUBTITLE_KEY, "My Network Name - " + id);
        model.put(Dashboard.NETWORK_ID_KEY, networkId);
        if (main.isLoggedIn(request)) {
            model.put(Main.NAV_LOGIN_LINK_KEY, "/dashboard");
            model.put(Main.NAV_LOGIN_TEXT_KEY, "Dashboard");
        } else {
            model.put(Main.NAV_LOGIN_LINK_KEY, "/login");
            model.put(Main.NAV_LOGIN_TEXT_KEY, "Login");
        }
        return model;
    }

    public String getId() {
        return id;
    }
}
