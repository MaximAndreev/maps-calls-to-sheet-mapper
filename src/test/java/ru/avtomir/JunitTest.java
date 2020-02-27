package ru.avtomir;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JunitTest {

    @Test
    public void nothing_done() {
        Assertions.assertTrue(true, "Junit 5 wired correctly");
    }
}
