package io.sponges.bot.dashboard.module;

import io.sponges.bot.dashboard.Model;
import io.sponges.bot.dashboard.Routes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Set;

public class SearchersModule extends Module {

    public SearchersModule() {
        super("searchers");
    }

    @Override
    public ModelAndView execute(Request request, Response response) {
        Set<String> params = request.queryParams();
        String network = request.queryParams("network");
        if (network == null) {
            response.redirect("/");
            return new ModelAndView(new Model(Routes.getUser(request.session()), "error", "Error", "Error", "Error", "dashboard_item").toMap(), "error.mustache");
        }
        Model model = new Model(Routes.getUser(request.session()), "searchers_module", "Searchers", "Searchers", "Manage the searchers module",
                network + "_item", network + "_modules_item", network + "_searchers_module_item");
        return new ModelAndView(model.toMap(), "searchers_module.mustache");
    }
}
