package ru.jru.modul4.karaganov.entity;

public enum Rating {
    G("G"),
    PG("PG"),
    PG13("PG-13"),
    R("R"),
    NC17("NC-17");

    private final String value;


    public String getValue() {
        return value;
    }

    Rating(String value) {
        this.value = value;
    }
}
