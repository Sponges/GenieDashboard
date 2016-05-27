package io.sponges.bot.dashboard.module;

import io.sponges.bot.dashboard.Model;
import io.sponges.bot.dashboard.Routes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ModerationModule extends Module {

    public ModerationModule() {
        super("moderation");
    }

    @Override
    public ModelAndView execute(Request request, Response response) {
        Model model = new Model(Routes.getUser(request.session()), "moderation_module", "Moderation", "Moderation", "Manage the moderation module",
                "myguild1_item", "myguild1_modules_item", "myguild1_moderation_module_item");
        return new ModelAndView(model.toMap(), "moderation_module.mustache");
    }
}
