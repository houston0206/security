package edu.ttap.rainbowtable;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.Test;

/**
 * Tests for the RainbowTable class.
 */
public class RainbowTableTest {

    /**
     * A hard-coded hash function taken from the reading.
     * @param p the password to hash
     * @return the hash of the given password
     */
    private static Hash exampleHasher(Password p) {
        return switch (p.value()) {
            case "balloon" -> new Hash("0x03f0ad");
            case "hotdog" -> new Hash("0x11afde");
            case "cattail" -> new Hash("0xab312c");
            case "tailspin" -> new Hash("0x7c9e2f");
            case "spincycle" -> new Hash("0x5b8a1d");
            case "cyclotron" -> new Hash("0x2c4b8e");
            default -> throw new IllegalArgumentException(
                "Example hash function undefined for password: " + p.value());
        };
    }

    /**
     * A hard-coded reduction function taken from the reading.
     * @param h the hash to reduce
     * @return the resulting password from reducting the hash
     */
    private static Password exampleReducer(Hash h) {
        return switch (h.value()) {
            case "0x03f0ad" -> new Password("hotdog");
            case "0x11afde" -> new Password("cattail");
            case "0xab312c" -> new Password("tailspin");
            case "0x7c9e2f" -> new Password("spincycle");
            case "0x5b8a1d" -> new Password("cyclotron");
            case "0x2c4b8e" -> new Password("guitar");
            default -> throw new IllegalArgumentException(
                "Example reducer undefined for hash: " + h.value());
        };
    }

    /** A test drawn from the reading example. */
    @Test
    public void readingTest() {
        RainbowTable table = new RainbowTable(
            Collections.singletonList(new Pair<>(new Password("balloon"), new Password("guitar"))),
            RainbowTableTest::exampleHasher,
            RainbowTableTest::exampleReducer);
        assertEquals(Optional.of(new Password("hotdog")), table.invert(new Hash("0x11afde"), 10));
    }
}