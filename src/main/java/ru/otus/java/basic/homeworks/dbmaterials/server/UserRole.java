package ru.otus.java.basic.homeworks.dbmaterials.server;

public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private final String roleName;

    UserRole(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public String toString() {
        return roleName;
    }
}