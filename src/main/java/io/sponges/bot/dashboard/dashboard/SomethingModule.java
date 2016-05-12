package io.sponges.bot.dashboard.dashboard;

import io.sponges.bot.dashboard.Main;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Map;

public class SomethingModule extends DashboardModule {

    protected SomethingModule(Main main) {
        super(main, "something");
    }

    @Override
    public ModelAndView render(Request request, Response response) {
        Map<String, Object> model = getDefaultModel(request, request.queryParams("network"));
        return new ModelAndView(model, "dashboard_something.mustache");
    }
}
