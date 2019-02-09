package net.greenmanov.anime.rurybooru.service.helper;

import net.greenmanov.anime.rurybooru.persistance.filters.FilterOperator;
import net.greenmanov.anime.rurybooru.persistance.filters.ImageFilter;
import net.greenmanov.anime.rurybooru.persistance.filters.ImageRatioFilter;
import net.greenmanov.anime.rurybooru.persistance.filters.ImageWidthFilter;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * Class FilterDecoderTest
 *
 * @author Lukáš Kurčík <lukas.kurcik@gmail.com>
 */
public class FilterDecoderTest {

    @Test
    public void testDecodeWidthShort() {
        testDecodeWidth(FilterDecoder.decode("w:1920"), 1920, FilterOperator.EQ);
    }

    @Test
    public void testDecodeWidth() {
        testDecodeWidth(FilterDecoder.decode("width:1920"), 1920, FilterOperator.EQ);
    }

    @Test
    public void testDecodeWidthEqShort() {
        testDecodeWidth(FilterDecoder.decode("w:=6"), 6, FilterOperator.EQ);
    }

    @Test
    public void testDecodeWidthEq() {
        testDecodeWidth(FilterDecoder.decode("width:=6"), 6, FilterOperator.EQ);
    }

    @Test
    public void testDecodeWidthGtShort() {
        testDecodeWidth(FilterDecoder.decode("w:>64"), 64, FilterOperator.GT);
    }

    @Test
    public void testDecodeWidthGt() {
        testDecodeWidth(FilterDecoder.decode("width:>64"), 64, FilterOperator.GT);
    }

    @Test
    public void testDecodeWidthGoeShort() {
        testDecodeWidth(FilterDecoder.decode("w:>=64"), 64, FilterOperator.GOE);
    }

    @Test
    public void testDecodeWidthGoe() {
        testDecodeWidth(FilterDecoder.decode("width:>=64"), 64, FilterOperator.GOE);
    }

    @Test
    public void testDecodeWidthLtShort() {
        testDecodeWidth(FilterDecoder.decode("w:<64"), 64, FilterOperator.LT);
    }

    @Test
    public void testDecodeWidthLt() {
        testDecodeWidth(FilterDecoder.decode("width:<64"), 64, FilterOperator.LT);
    }

    @Test
    public void testDecodeWidthLoeShort() {
        testDecodeWidth(FilterDecoder.decode("w:<=64"), 64, FilterOperator.LOE);
    }

    @Test
    public void testDecodeWidthLoe() {
        testDecodeWidth(FilterDecoder.decode("width:<=64"), 64, FilterOperator.LOE);
    }

    @Test
    public void testDecodeRatioShort() {
        testDecodeRatio(FilterDecoder.decode("r:1920:1080"), 1920, 1080, 0);
    }

    @Test
    public void testDecodeRatio() {
        testDecodeRatio(FilterDecoder.decode("ratio:1920:1080"), 1920, 1080, 0);
    }
    @Test
    public void testDecodeRatioDeltaShort() {
        testDecodeRatio(FilterDecoder.decode("r:800:600:0.25"), 800, 600, 0.25);
    }

    @Test
    public void testDecodeRatioDelta() {
        testDecodeRatio(FilterDecoder.decode("ratio:1920:1080:3"), 1920, 1080, 3);
    }

    private void testDecodeWidth(ImageFilter filter, int width, FilterOperator operator) {
        assertNotNull(filter);
        assertTrue(filter instanceof ImageWidthFilter);
        assertEquals(((ImageWidthFilter) filter).getWidth(), width);
        assertEquals(((ImageWidthFilter) filter).getOperator(), operator);
    }

    private void testDecodeRatio(ImageFilter filter, int width, int height, double delta) {
        assertNotNull(filter);
        assertTrue(filter instanceof ImageRatioFilter);
        assertEquals(((ImageRatioFilter) filter).getWidth(), width);
        assertEquals(((ImageRatioFilter) filter).getHeight(), height);
        assertEquals(((ImageRatioFilter) filter).getDelta(), delta);
    }
}