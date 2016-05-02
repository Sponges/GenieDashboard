package io.sponges.bot.dashboard.dashboard;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Map;

public class HomeFeature extends DashboardFeature {

    protected HomeFeature() {
        super("home");
    }

    @Override
    public ModelAndView render(Request request, Response response) {
        Map<String, Object> model = getDefaultModel();
        return new ModelAndView(model, "dashboard_home.jade");
    }
}
