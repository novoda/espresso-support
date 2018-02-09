package com.novoda.espresso;

enum FontScale {

    SMALL(String.valueOf(0.85f)),
    NORMAL(String.valueOf(1f)),
    LARGE(String.valueOf(1.15f)),
    HUGE(String.valueOf(1.3f));

    private final String value;

    FontScale(String value) {
        this.value = value;
    }

    static FontScale from(float scale) {
        for (FontScale fontScale : values()) {
            if (fontScale.value().equals(String.valueOf(scale))) {
                return fontScale;
            }
        }
        throw new IllegalArgumentException("Unknown scale: " + scale);
    }

    String value() {
        return value;
    }
}
