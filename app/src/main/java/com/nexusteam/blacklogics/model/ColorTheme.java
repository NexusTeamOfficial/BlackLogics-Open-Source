package com.nexusteam.blacklogics.model;

public class ColorTheme {

    private String name;
    private int primary;
    private int primaryDark;
    private int accent;
    private int highlight;
    private int normal;

    public ColorTheme(String name, int primary, int primaryDark, int accent, int highlight, int normal) {
        this.name = name;
        this.primary = primary;
        this.primaryDark = primaryDark;
        this.accent = accent;
        this.highlight = highlight;
        this.normal = normal;
    }

    public String getName() { return name; }
    public int getPrimary() { return primary; }
    public int getPrimaryDark() { return primaryDark; }
    public int getAccent() { return accent; }
    public int getHighlight() { return highlight; }
    public int getNormal() { return normal; }
}
