package io.sponges.bot.olddashboard.dashboard;

import io.sponges.bot.olddashboard.Main;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class Dashboard {

    protected static final String NETWORK_ID_KEY = "network_id";

    private final Map<String, DashboardModule> modules = new HashMap<>();

    private final Main main;
    private final String id;

    public Dashboard(Main main, String id) {
        this.main = main;
        this.id = id;
        addModules(
                new HomeModule(main),
                new SomethingModule(main)
        );
    }

    private void addModules(DashboardModule... modules) {
        for (DashboardModule module : modules) {
            this.modules.put(module.getId().toLowerCase(), module);
        }
    }

    public ModelAndView render(Request request, Response response) {
        if (!request.queryParams().contains("module")) {
            return modules.get("home").render(request, response);
        }
        String module = request.queryParams("module");
        if (modules.containsKey(module.toLowerCase())) {
            return modules.get(module).render(request, response);
        }
        Map<String, Object> model = new HashMap<>();
        model.put(Main.LAYOUT_TITLE_KEY, "Dashboard");
        model.put(Main.LAYOUT_HEADER_TITLE_KEY, "Manage the Genie");
        model.put(Main.LAYOUT_HEADER_SUBTITLE_KEY, "My Network Name - " + id);
        model.put(Dashboard.NETWORK_ID_KEY, id);
        if (main.isLoggedIn(request)) {
            model.put(Main.NAV_LOGIN_LINK_KEY, "/dashboard");
            model.put(Main.NAV_LOGIN_TEXT_KEY, "Dashboard");
        } else {
            model.put(Main.NAV_LOGIN_LINK_KEY, "/login");
            model.put(Main.NAV_LOGIN_TEXT_KEY, "Login");
        }
        return new ModelAndView(model, "dashboard.jade");
    }

    public String getId() {
        return id;
    }
}
