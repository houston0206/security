package edu.ttap.rainbowtable;

import java.util.function.Function;

import java.util.List;
import java.util.Optional;

/**
 * A rainbow table is a collection of chains of passwords for the purpose of
 * reversing hashes.
 */
public class RainbowTable {

    /**
     * Constructs a new rainbow table from an already-computed list of endpoints.
     * @param chains a list of password chains, pairs of starting and ending passwords.
     * @param hasher a function that maps a password to its hash
     * @param reducer a function that maps a hash to its password
     */
    public RainbowTable(
            List<Pair<Password, Password>> chains,
            Function<Password, Hash> hasher,
            Function<Hash, Password> reducer) {
        // TODO: implement me!
    }

    /**
     * Attempts to reverse the given hash according to the rainbow table algorithm.
     * 
     * @param h the hash to invert
     * @param maxSteps the maximum number of steps (hash-reduce cycles) to attempt
     * @return an Optional containing the password if found, or empty if not
     */
    public Optional<Password> invert(Hash h, int maxSteps) {
        // TODO: implement me!
        return Optional.empty();
    }
}
