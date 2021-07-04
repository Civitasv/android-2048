package com.civitasv;

import com.civitasv.model.Tile;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testGrid() {
        String[][] tiles = new String[1][1];
        System.out.println(Arrays.deepToString(tiles));
    }
}