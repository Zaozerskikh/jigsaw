package jigsaw.game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomColorPickerTest {

    @Test
    void getColorTest() {
        var picker = new RandomColorPicker();

        // avoiding color repetitions test.
        assertNotEquals(picker.getColor(), picker.getColor());
    }
}