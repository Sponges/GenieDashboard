package io.sponges.bot.dashboard.dashboard;

import io.sponges.bot.dashboard.Main;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class Dashboard {

    protected static final String NETWORK_ID_KEY = "network_id";

    private final Map<String, DashboardFeature> features = new HashMap<>();

    private final String id;

    public Dashboard(String id) {
        this.id = id;
        addFeatures(
                new HomeFeature(),
                new SomethingFeature()
        );
    }

    private void addFeatures(DashboardFeature... features) {
        for (DashboardFeature feature : features) {
            this.features.put(feature.getId().toLowerCase(), feature);
        }
    }

    public ModelAndView render(Request request, Response response) {
        if (!request.queryParams().contains("feature")) {
            return features.get("home").render(request, response);
        }
        String feature = request.queryParams("feature");
        if (features.containsKey(feature.toLowerCase())) {
            return features.get(feature).render(request, response);
        }
        Map<String, Object> model = new HashMap<>();
        model.put(Main.LAYOUT_TITLE_KEY, "Dashboard");
        model.put(Main.LAYOUT_HEADER_TITLE_KEY, "Manage the Genie");
        model.put(Main.LAYOUT_HEADER_SUBTITLE_KEY, "My Network Name - " + id);
        model.put(Dashboard.NETWORK_ID_KEY, id);
        return new ModelAndView(model, "dashboard.jade");
    }

    public String getId() {
        return id;
    }
}
