package net.greenmanov.anime.rurybooru.service.helper;

import net.greenmanov.anime.rurybooru.persistance.filters.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Decodes filters represented as string into  ImageFilter object
 * <p>
 * Formats
 * - ImageWidthFilter
 * w:[SIZE], width:[SIZE]
 * w:[OP][SIZE], width:[OP][SIZE]
 * - ImageHeightFilter
 * h:[SIZE], height:[SIZE]
 * h:[OP][SIZE], height:[OP][SIZE]
 * - ImageRatioFilter
 * r:[WIDTH]:[HEIGHT], ratio:[WIDTH]:[HEIGHT]
 * r:[WIDTH]:[HEIGHT]:[DELTA], ratio:[WIDTH]:[HEIGHT]:[DELTA]
 * <p>
 * [OP] can be =, &lt; &gt;, &lt;=, &gt;=
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
final public class FilterDecoder {

    private final static Pattern WIDTH_PATTERN = Pattern.compile("^w(?:idth)?:(=?|<|>|<=|>=)(\\d+)");
    private final static Pattern HEIGHT_PATTERN = Pattern.compile("^h(?:eight)?:(=?|<|>|<=|>=)(\\d+)");
    private final static Pattern RATIO_PATTERN = Pattern.compile("^r(?:atio)?:(=?|<|>|<=|>=)(\\d+)(?::(\\d+))(?::(\\d+\\.?\\d*))?");

    private FilterDecoder() {
    }

    /**
     * Decode list of strings, if string can't be decoded its ignored
     *
     * @param strings List of strings
     * @return List of filters
     */
    public static List<ImageFilter> decode(List<String> strings) {
        List<ImageFilter> filters = new ArrayList<>();
        for (String s : strings) {
            ImageFilter f = decode(s);
            if (f != null) {
                filters.add(f);
            }
        }
        return filters;
    }

    /**
     * Decode string
     *
     * @param string String representation of filter
     * @return Filter or null if can't be decoded
     */
    public static ImageFilter decode(String string) {
        try {
            if (string.startsWith("w:") || string.startsWith("width:")) {
                return decodeWidth(string);
            } else if (string.startsWith("h:") || string.startsWith("height:")) {
                return decodeHeight(string);
            } else if (string.startsWith("r:") || string.startsWith("ratio:")) {
                return decodeRatio(string);
            }
        } catch (NumberFormatException e) {
            // Just invalid string instead of number in user input, not a problem
        }
        return null;
    }

    private static ImageFilter decodeRatio(String string) {
        Matcher m = RATIO_PATTERN.matcher(string);
        if (m.find()) {
            int width = Integer.valueOf(m.group(2));
            int height = 1;
            if (m.group(3) != null) {
                height = Integer.valueOf(m.group(3));
            }
            double delta = 0;
            if (m.group(4) != null) {
                delta = Double.valueOf(m.group(4));
            }
            return new ImageRatioFilter(width, height, delta, FilterOperator.fromString(m.group(1)));
        }
        return null;
    }

    private static ImageFilter decodeWidth(String string) {
        Matcher m = WIDTH_PATTERN.matcher(string);
        if (m.find()) {
            return new ImageWidthFilter(Integer.valueOf(m.group(2)), FilterOperator.fromString(m.group(1)));
        }
        return null;
    }

    private static ImageFilter decodeHeight(String string) {
        Matcher m = HEIGHT_PATTERN.matcher(string);
        if (m.find()) {
            return new ImageHeightFilter(Integer.valueOf(m.group(2)), FilterOperator.fromString(m.group(1)));
        }
        return null;
    }

}
