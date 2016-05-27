package io.sponges.bot.dashboard.module;

import io.sponges.bot.dashboard.Model;
import io.sponges.bot.dashboard.Routes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class ChatBridgesModule extends Module {

    public ChatBridgesModule() {
        super("bridges");
    }

    @Override
    public ModelAndView execute(Request request, Response response) {
        Model model = new Model(Routes.getUser(request.session()), "bridges_module", "Chat Bridges", "Chat Bridges",
                "Manage the chat bridges module", "myguild1_item", "myguild1_modules_item",
                "myguild1_bridges_module_item");
        return new ModelAndView(model.toMap(), "bridges_module.mustache");
    }
}
