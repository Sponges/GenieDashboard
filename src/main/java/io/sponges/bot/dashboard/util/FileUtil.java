package io.sponges.bot.dashboard.util;

import java.io.*;

public final class FileUtil {

    public static String read(File file, String defaults) throws IOException {
        if (!file.exists()) {
            write(file, defaults);
            return defaults;
        }
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String input;
            while ((input = reader.readLine()) != null) {
                content.append(input).append("\r\n");
            }
        }
        return content.toString();
    }

    public static void write(File file, String content) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
            writer.flush();
        }
    }

}
