package io.sponges.bot.dashboard.module;

import io.sponges.bot.dashboard.Model;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MusicBotModule extends Module {

    public MusicBotModule() {
        super("music");
    }

    @Override
    public ModelAndView execute(Request request, Response response) {
        return new ModelAndView(new Model("music_module", "Music Bot", "Music Bot", "Manage the music bot module",
                "myguild1_item", "myguild1_modules_item", "myguild1_music_module_item").toMap(), "music_module.mustache");
    }
}
