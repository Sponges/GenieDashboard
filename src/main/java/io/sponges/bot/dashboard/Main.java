package io.sponges.bot.dashboard;

import java.io.File;

public final class Main {

    public static void main(String[] args) {
        Configuration configuration;
        if (args.length > 0) {
            File file = new File(args[0]);
            configuration = new Configuration(file);
        } else {
            File file = new File("config.json");
            configuration = new Configuration(file);
        }
        new Routes(configuration);
    }

}
