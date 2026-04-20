package edu.ttap.rainbowtable;

import java.util.function.Function;

import java.util.List;
import java.util.Optional;

/**
 * A rainbow table is a collection of chains of passwords for the purpose of
 * reversing hashes.
 */
public class RainbowTable {
    private List<Pair<Password, Password>> chains;
    private Function<Password, Hash> hasher;
    private Function<Hash, Password> reducer;
    
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
        this.chains = chains;
        this.hasher = hasher;
        this.reducer = reducer;
    }

    /**
     * Retrieves the starting password of a chain corresponding to endpoint p
     * 
     * @param p the password to search for
     * @return the password if p is an endpoint, null otherwise
     */
    public Password getStart(Password p) {
        for (Pair<Password, Password> chain : chains) {
            if (chain.second().equals(p)) {
                return chain.first();
            }
        }
        return null;
    }

    /**
     * Attempts to reverse the given hash according to the rainbow table algorithm.
     * 
     * @param h the hash to invert
     * @param maxSteps the maximum number of steps (hash-reduce cycles) to attempt
     * @return an Optional containing the password if found, or empty if not
     */
    public Optional<Password> invert(Hash h, int maxSteps) {
        // Step 1: use reduction function on hash, turn into a password
        // Step 2: check to see if the password is an endpoint in a chain, if yes, skip to step 5
        // Step 3: use hash function on the password
        // Step 3.5: increment a counter, if maxSteps has been reached, return empty optional
        // Step 4: go back to step 1 and repeat until endpoint is found
        // Step 5: go to the start of the chain, and repeat the hashing-reducing process until you find the precursor to h
        Password p = reducer.apply(h);
        Hash curHash = h;
        boolean hasAnswer = false;
        for (int i = 0; i < maxSteps; i++) {
            p = reducer.apply(curHash);
            if(getStart(p) != null) {
                p = getStart(p);
                hasAnswer = true;
                break;
            } 
            curHash = hasher.apply(p);
        }
        curHash = hasher.apply(p);
        if(!hasAnswer) {
            return Optional.empty();
        }
        while(!curHash.equals(h)) {
            p = reducer.apply(curHash);
            curHash = hasher.apply(p);
        }
        return Optional.of(p);
    }
}