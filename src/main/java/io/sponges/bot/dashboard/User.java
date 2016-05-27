package io.sponges.bot.dashboard;

import spark.Session;

public class User {

    private final Session session;

    private String email = null;
    private String passwordHash = null;

    private boolean donator = false;

    public User(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isLoggedIn() {
        // TODO auth logic
        return true;
    }

    // TODO updating password
    public void setPassword(char[] newPassword) {
        throw new UnsupportedOperationException();
    }

    public String get(String key) {
        return session.attribute(key);
    }

    public void set(String key, String value) {
        session.attribute(key, value);
    }

    public boolean isDonator() {
        return donator;
    }

    public void setIsDonator(boolean donator) {
        this.donator = donator;
    }
}
