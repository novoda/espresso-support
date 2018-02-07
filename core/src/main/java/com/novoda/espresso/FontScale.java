package com.novoda.espresso;

enum FontScale {

    SMALL(0.85f),
    NORMAL(1f),
    LARGE(1.15f),
    HUGE(1.3f);

    private final float value;

    FontScale(float value) {
        this.value = value;
    }

    static FontScale from(float scale) {
        for (FontScale fontScale : values()) {
            if (fontScale.value() == scale) {
                return fontScale;
            }
        }
        throw new IllegalArgumentException("Unknown scale: " + scale);
    }

    float value() {
        return value;
    }
}
