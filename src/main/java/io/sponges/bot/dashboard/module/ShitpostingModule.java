package io.sponges.bot.dashboard.module;

import io.sponges.bot.dashboard.Model;
import io.sponges.bot.dashboard.Routes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ShitpostingModule extends Module {

    public ShitpostingModule() {
        super("shitposting");
    }

    @Override
    public ModelAndView execute(Request request, Response response) {
        Model model = new Model(Routes.getUser(request.session()), "shitposting_module", "Shitposting", "Shitposting", "Manage the shitposting module",
                "myguild1_item", "myguild1_modules_item", "myguild1_shitposting_module_item");
        return new ModelAndView(model.toMap(), "shitposting_module.mustache");
    }
}
