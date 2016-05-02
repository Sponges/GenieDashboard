package io.sponges.bot.dashboard.dashboard;

import io.sponges.bot.dashboard.Main;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public abstract class DashboardFeature {

    private final String id;

    protected DashboardFeature(String id) {
        this.id = id;
    }

    public abstract ModelAndView render(Request request, Response response);


    protected Map<String, Object> getDefaultModel() {
        Map<String, Object> model = new HashMap<>();
        model.put(Main.LAYOUT_TITLE_KEY, "Dashboard");
        model.put(Main.LAYOUT_HEADER_TITLE_KEY, "Manage the Genie");
        model.put(Main.LAYOUT_HEADER_SUBTITLE_KEY, "My Network Name - " + id);
        model.put(Dashboard.NETWORK_ID_KEY, id);
        return model;
    }

    public String getId() {
        return id;
    }
}
