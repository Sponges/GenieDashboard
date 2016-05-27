package io.sponges.bot.dashboard.module;

import io.sponges.bot.dashboard.Model;
import io.sponges.bot.dashboard.Routes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class SearchersModule extends Module {

    public SearchersModule() {
        super("searchers");
    }

    @Override
    public ModelAndView execute(Request request, Response response) {
        Model model = new Model(Routes.getUser(request.session()), "searchers_module", "Searchers", "Searchers", "Manage the searchers module",
                "myguild1_item", "myguild1_modules_item", "myguild1_searchers_module_item");
        return new ModelAndView(model.toMap(), "searchers_module.mustache");
    }
}
