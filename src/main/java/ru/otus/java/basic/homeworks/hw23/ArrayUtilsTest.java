package ru.otus.java.basic.homeworks.hw23;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ArrayUtilsTest {

    @Test
    public void testGetElementsAfterLastOne_NormalCase() {
        int[] input = {1, 2, 3, 1, 4, 5};
        int[] expected = {4, 5};
        assertArrayEquals(expected, ArrayUtils.getElementsAfterLastOne(input));
    }

    @Test
    public void testGetElementsAfterLastOne_OneAtEnd() {
        int[] input = {2, 3, 4, 1};
        int[] expected = {};
        assertArrayEquals(expected, ArrayUtils.getElementsAfterLastOne(input));
    }

    @Test
    public void testGetElementsAfterLastOne_OneAtStart() {
        int[] input = {1, 2, 3, 4};
        int[] expected = {2, 3, 4};
        assertArrayEquals(expected, ArrayUtils.getElementsAfterLastOne(input));
    }

    @Test
    public void testGetElementsAfterLastOne_NoOnes() {
        int[] input = {2, 3, 4, 5};
        assertThrows(RuntimeException.class, () -> ArrayUtils.getElementsAfterLastOne(input));
    }

    @Test
    public void testGetElementsAfterLastOne_EmptyArray() {
        int[] input = {};
        assertThrows(RuntimeException.class, () -> ArrayUtils.getElementsAfterLastOne(input));
    }

    @Test
    public void testGetElementsAfterLastOne_NullArray() {
        assertThrows(RuntimeException.class, () -> ArrayUtils.getElementsAfterLastOne(null));
    }

    @Test
    public void testIsArrayValid_ValidCase() {
        int[] input = {1, 2, 1, 2};
        assertTrue(ArrayUtils.isArrayValid(input));
    }

    @Test
    public void testIsArrayValid_OnlyOnes() {
        int[] input = {1, 1, 1};
        assertFalse(ArrayUtils.isArrayValid(input));
    }

    @Test
    public void testIsArrayValid_OnlyTwos() {
        int[] input = {2, 2, 2};
        assertFalse(ArrayUtils.isArrayValid(input));
    }

    @Test
    public void testIsArrayValid_ContainsOtherNumbers() {
        int[] input = {1, 2, 3};
        assertFalse(ArrayUtils.isArrayValid(input));
    }

    @Test
    public void testIsArrayValid_EmptyArray() {
        int[] input = {};
        assertFalse(ArrayUtils.isArrayValid(input));
    }

    @Test
    public void testIsArrayValid_NullArray() {
        assertFalse(ArrayUtils.isArrayValid(null));
    }

    @Test
    public void testIsArrayValid_SingleElement() {
        int[] input1 = {1};
        int[] input2 = {2};
        assertFalse(ArrayUtils.isArrayValid(input1));
        assertFalse(ArrayUtils.isArrayValid(input2));
    }

    @Test
    public void testIsArrayValid_ValidWithMultipleElements() {
        int[] input = {1, 2, 2, 1, 2, 1, 1, 2};
        assertTrue(ArrayUtils.isArrayValid(input));
    }
}