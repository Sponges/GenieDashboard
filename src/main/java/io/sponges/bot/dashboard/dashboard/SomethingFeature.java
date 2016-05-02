package io.sponges.bot.dashboard.dashboard;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Map;

public class SomethingFeature extends DashboardFeature {

    protected SomethingFeature() {
        super("something");
    }

    @Override
    public ModelAndView render(Request request, Response response) {
        Map<String, Object> model = getDefaultModel();
        return new ModelAndView(model, "dashboard_something.jade");
    }
}
