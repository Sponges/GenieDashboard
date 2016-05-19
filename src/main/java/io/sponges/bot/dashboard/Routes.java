package io.sponges.bot.dashboard;

import io.sponges.bot.dashboard.module.ManageModule;
import io.sponges.bot.dashboard.module.Module;
import io.sponges.bot.dashboard.module.MusicBotModule;
import org.json.JSONObject;
import spark.ModelAndView;
import spark.Spark;
import spark.TemplateEngine;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Routes {

    private final Map<String, String> sessions = new ConcurrentHashMap<>();
    private final Map<String, Module> modules = new HashMap<>();

    private final Configuration configuration;
    private final TemplateEngine engine;

    protected Routes(Configuration configuration) {
        this.configuration = configuration;
        this.engine = new MustacheTemplateEngine(configuration.getTemplateFileLocation());
        Spark.staticFileLocation(configuration.getStaticFileLocation());
        Spark.port(this.configuration.getPort());

        Spark.get("/", (request, response) -> {
            return new ModelAndView(new Model("dashboard", "Dashboard", "Dashboard", "Control panel", "dashboard_item").toMap(), "dashboard.mustache");
        }, engine);

        Spark.get("/documentation", (request, response) -> "Documentation coming soon!");

        Spark.get("/platforms", (request, response) -> {
            Model model = new Model("platforms", "Manage Platforms", "Manage Platforms", "Where should the Genie be?", "manage_platforms_item");
            return new ModelAndView(model.toMap(), "platforms.mustache");
        }, engine);

        Spark.get("/overview", (request, response) -> {
            Set<String> params = request.queryParams();
            if (params.size() == 0) {
                response.redirect("/");
            }
            String network = request.queryParams("network");
            Model model = new Model("overview", "Overview", "Overview", "Network ID: " + network, "myguild1_item", "myguild1_overview_item");
            model.addModelProperty("network_id", network);
            return new ModelAndView(model.toMap(), "overview.mustache");
        }, engine);

        Spark.get("/chat", (request, response) -> "Chat is coming soon!");

        Spark.get("/roles", (request, response) -> {
            Set<String> params = request.queryParams();
            if (params.size() == 0) {
                response.redirect("/");
            }
            String network = request.queryParams("network");
            Model model;
            if (params.size() == 1) {
                model = new Model("roles", "Manage Roles", "Manage Roles", "Network ID: " + network,
                        "myguild1_item", "myguild1_roles_item");
            } else {
                String selectedRole = request.queryParams("role");
                String tab = selectedRole.toLowerCase() + "_role_tab";
                model = new Model("roles", "Manage Roles", "Manage Roles", "Network ID: " + network,
                        "myguild1_item", "myguild1_roles_item", tab);
                if (selectedRole.equals("admin")) model.addJsonProperty("role_info", new JSONObject("{\"role\":{\"permissions\":[\"this.is.a.test\",\"hi.another.test.dude\",\"wow.dude.*\"],\"id\":\"admin\"}}"));
                else if (selectedRole.equals("moderator")) model.addJsonProperty("role_info", new JSONObject("{\"role\":{\"permissions\":[\"aaaaaaa.lol\",\"hi.holy.fuck\",\"wow.*\"],\"id\":\"moderator\"}}"));
                else if (selectedRole.equals("trusted")) model.addJsonProperty("role_info", new JSONObject("{\"role\":{\"permissions\":[\"thdhrhrddrhhr.a.test\",\"hi.oooooe\",\"wowdrh.rdhrdhdude.*\"],\"id\":\"trusted\"}}"));
            }
            model.addModelProperty("network_id", network);
            model.addJsonProperty("network_id", network);
            model.addJsonProperty("roles_list", new JSONObject("{\"roles\":[\"trusted\",\"moderator\",\"admin\"]}"));
            return new ModelAndView(model.toMap(), "roles.mustache");

        }, engine);

        // modules shit
        register(
                new ManageModule(),
                new MusicBotModule()
        );

        Spark.get("/modules", (request, response) -> {
            Set<String> params = request.queryParams();
            if (params.size() == 0) {
                // ok wtf why is there no network specified
                response.redirect("/");
                return new ModelAndView(new Model("error", "Error", "Error", "Error", "dashboard_item").toMap(), "error.mustache");
            }
            String network = request.queryParams("network");
            if (params.size() == 1) {
                // redirect to the module manager
                response.redirect("/modules?network=" + network + "&module=manage");
                return new ModelAndView(new Model("error", "Error", "Error", "Error", "myguild1_item").toMap(), "error.mustache");
            }
            String module = request.queryParams("module");
            return modules.get(module).execute(request, response);
        }, engine);
    }

    private void register(Module... modules) {
        for (Module module : modules) {
            this.modules.put(module.getName(), module);
        }
    }

}
