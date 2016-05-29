package io.sponges.bot.dashboard;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class Model {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy");

    private final Map<String, Object> modelProperties = new HashMap<>();
    private final Map<String, Object> jsonProperties = new HashMap<>();

    private final User user;
    private final String pageName;
    private final String pageTitle;
    private final String contentTitle;
    private final String contentSubtitle;
    private final String[] activeTabs;

    public Model(User user, String pageName, String pageTitle, String contentTitle, String contentSubtitle, String... activeTabs) {
        this.user = user;
        this.pageName = pageName;
        this.pageTitle = pageTitle;
        this.contentTitle = contentTitle;
        this.contentSubtitle = contentSubtitle;
        this.activeTabs = activeTabs;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<String, Object>() {{
            put("page_title", pageTitle);
            put("content_title", contentTitle);
            put("content_subtitle", contentSubtitle);
            put("copyright_year", getCopyrightYear());
            put("genie_json", getJson());
        }};
        for (Map.Entry<String, Object> entry : modelProperties.entrySet()) {
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }

    private String getCopyrightYear() {
        return DATE_FORMAT.format(new Date());
    }

    private String getJson() {
        JSONObject json = new JSONObject();
        json.put("page_name", pageName);
        json.put("active_tabs", activeTabs);
        if (pageName.equals("donate")) {
            json.put("show_ads", false);
        } else {
            json.put("show_ads", !user.isDonator());
        }
        for (Map.Entry<String, Object> entry : jsonProperties.entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }
        json.put("networks", new JSONObject("{\"networks\":[{\"id\":\"network1836334242\"},{\"id\":\"117861824124network\"}]}"));
        return json.toString();
    }

    public void addModelProperty(String key, Object value) {
        modelProperties.put(key, value);
    }

    public void addJsonProperty(String key, Object value) {
        jsonProperties.put(key, value);
    }

}
