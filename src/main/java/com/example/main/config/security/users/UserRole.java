package com.example.main.config.security.users;

public enum UserRole {
    USER, ADMIN;

    public boolean isAdmin() {
        return this == ADMIN;
    }

    public String toApplicationRole() {
        return "ROLE_" + this.name();
    }
}
