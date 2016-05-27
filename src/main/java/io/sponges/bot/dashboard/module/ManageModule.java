package io.sponges.bot.dashboard.module;

import io.sponges.bot.dashboard.Model;
import io.sponges.bot.dashboard.Routes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ManageModule extends Module {

    public ManageModule() {
        super("manage");
    }

    @Override
    public ModelAndView execute(Request request, Response response) {
        Model model = new Model(Routes.getUser(request.session()), "manage_module", "Manage Modules", "Manage Modules", "Enable and disable Genie modules",
                "myguild1_item", "myguild1_modules_item", "myguild1_manage_module_item");
        return new ModelAndView(model.toMap(), "manage_module.mustache");
    }
}
