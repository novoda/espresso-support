package com.novoda.espresso;

import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ColorContrast {

    private static double MINIMUM_CONTRAST_RATIO_FOR_NORMAL_SIZE_TEXT = 3f;
    private static double MINIMUM_CONTRAST_RATIO_FOR_LARGE_SIZE_TEXT = 4.5f;

    @Test
    public void whiteBlack() {
        assertThat(contrastRatio(Color.WHITE, Color.BLACK), is(21.0));
    }

    @Test
    public void blackWhiteIsSameAsWhiteBlack() {
        assertThat(contrastRatio(Color.BLACK, Color.WHITE), is(contrastRatio(Color.WHITE, Color.BLACK)));
    }

    @Test
    public void yellowWhite() {
        assertThat(contrastRatio(Color.YELLOW, Color.WHITE), is(1.1));
    }

    @Test
    public void blueWhite() {
        assertThat(contrastRatio(Color.BLUE, Color.WHITE), is(8.6));
    }

    /**
     * Calculate contrast ratio between two colors, rounded to 1 decimal place.
     */
    private double contrastRatio(@ColorInt int foreground, @ColorInt int background) {
        double rawContrastRatio = rawContrastRatio(foreground, background);
        return Math.round(10 * rawContrastRatio) / 10.0;
    }

    private double rawContrastRatio(@ColorInt int foreground, @ColorInt int background) {
        double L1 = getLuminosity(foreground);
        double L2 = getLuminosity(background);
        if (L1 > L2) {
            return (L1 + 0.05) / (L2 + 0.05);
        } else {
            return (L2 + 0.05) / (L1 + 0.05);
        }
    }

    /**
     * See <a href="https://www.w3.org/TR/2008/REC-WCAG20-20081211/#relativeluminancedef">relative luminance on w3.org</a>
     */
    private static double getLuminosity(int color) {
        return calculateColorPartForLuminance(red(color)) * 0.2126
                + calculateColorPartForLuminance(green(color)) * 0.7152
                + calculateColorPartForLuminance(blue(color)) * 0.0722;
    }

    @IntRange(from = 0, to = 255)
    private static int red(@ColorInt int color) {
        return (color >> 16) & 0xFF;
    }

    @IntRange(from = 0, to = 255)
    private static int green(@ColorInt int color) {
        return (color >> 8) & 0xFF;
    }

    @IntRange(from = 0, to = 255)
    private static int blue(@ColorInt int color) {
        return color & 0xFF;
    }

    /**
     * if RsRGB <= 0.03928 then R = RsRGB/12.92 else R = ((RsRGB+0.055)/1.055) ^ 2.4
     * if GsRGB <= 0.03928 then G = GsRGB/12.92 else G = ((GsRGB+0.055)/1.055) ^ 2.4
     * if BsRGB <= 0.03928 then B = BsRGB/12.92 else B = ((BsRGB+0.055)/1.055) ^ 2.4
     *
     * @see <a href="https://www.w3.org/TR/2008/REC-WCAG20-20081211/#relativeluminancedef">relative luminance on w3.org</a>
     */
    private static double calculateColorPartForLuminance(@IntRange(from = 0, to = 255) int colorPart) {
        double colorPartSrgb = (double) colorPart / 255;
        if (colorPartSrgb <= 0.03928) {
            return colorPartSrgb / 12.92;
        } else {
            return Math.pow(((colorPartSrgb + 0.055) / 1.055), 2.4);
        }
    }
}
