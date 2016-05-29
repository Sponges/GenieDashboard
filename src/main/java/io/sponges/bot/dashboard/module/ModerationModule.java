package io.sponges.bot.dashboard.module;

import io.sponges.bot.dashboard.Model;
import io.sponges.bot.dashboard.Routes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.Set;

public class ModerationModule extends Module {

    public ModerationModule() {
        super("moderation");
    }

    @Override
    public ModelAndView execute(Request request, Response response) {
        Set<String> params = request.queryParams();
        String network = request.queryParams("network");
        if (network == null) {
            response.redirect("/");
            return new ModelAndView(new Model(Routes.getUser(request.session()), "error", "Error", "Error", "Error", "dashboard_item").toMap(), "error.mustache");
        }
        Model model = new Model(Routes.getUser(request.session()), "moderation_module", "Moderation", "Moderation", "Manage the moderation module",
                network + "_item", network + "_modules_item", network + "_moderation_module_item");
        return new ModelAndView(model.toMap(), "moderation_module.mustache");
    }
}
