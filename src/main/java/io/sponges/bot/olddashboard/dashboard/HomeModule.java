package io.sponges.bot.olddashboard.dashboard;

import io.sponges.bot.olddashboard.Main;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Map;

public class HomeModule extends DashboardModule {

    protected HomeModule(Main main) {
        super(main, "home");
    }

    @Override
    public ModelAndView render(Request request, Response response) {
        Map<String, Object> model = getDefaultModel(request, request.queryParams("network"));
        return new ModelAndView(model, "dashboard_home.mustache");
    }
}
