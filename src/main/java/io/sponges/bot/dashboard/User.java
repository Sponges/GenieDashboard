package io.sponges.bot.dashboard;

import spark.Session;

public class User {

    private static final String TOKEN_KEY = "token";

    // TODO oauth2 shit

    private final Session session;

    public User(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public String getSessionId() {
        return session.id();
    }

    public String getToken() {
        return session.attribute(TOKEN_KEY);
    }

    public void setToken(String token) {
        session.attribute(TOKEN_KEY, token);
    }
}
