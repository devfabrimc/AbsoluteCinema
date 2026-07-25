package com.absolutecinema.model;

public enum Format {
    TWO_D("2D"),
    THREE_D("3D"),
    IMAX("IMAX");

    private final String displayName;

    Format(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
