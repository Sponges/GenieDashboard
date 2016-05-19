package io.sponges.bot.dashboard;

import io.sponges.bot.dashboard.util.FileUtil;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public final class Configuration {

    private final int port;
    private final String templateFileLocation;
    private final String staticFileLocation;

    protected Configuration(File file) {
        String content = null;
        try {
            content = FileUtil.read(file, new JSONObject()
                    .put("port", 4567)
                    .put("template-file-location", "templates")
                    .put("static-file-location", "static")
                    .toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (content == null) {
            this.port = 4567;
            this.templateFileLocation = "templates";
            this.staticFileLocation = "static";
            return;
        }
        JSONObject parsed = new JSONObject(content);
        this.port = parsed.getInt("port");
        this.templateFileLocation = parsed.getString("template-file-location");
        this.staticFileLocation = parsed.getString("static-file-location");
    }

    public int getPort() {
        return port;
    }

    public String getTemplateFileLocation() {
        return templateFileLocation;
    }

    public String getStaticFileLocation() {
        return staticFileLocation;
    }
}
