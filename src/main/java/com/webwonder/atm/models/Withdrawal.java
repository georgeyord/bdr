package com.webwonder.atm.models;

import com.webwonder.atm.enums.NoteType;

import java.util.HashMap;
import java.util.Map;

public class Withdrawal {
    private Map<NoteType, Integer> breakdown = new HashMap<>();

    public Withdrawal add(NoteType type, int added) {
        ensureTypeExists(type);

        Integer value = breakdown.get(type);
        value += added;
        breakdown.put(type, value);

        return this;
    }

    private void ensureTypeExists(NoteType type) {
        if (!breakdown.containsKey(type)) {
            breakdown.put(type, 0);
        }
    }

    public Map<NoteType, Integer> getBreakdown() {
        return breakdown;
    }

    public Integer getCount(NoteType type) {
        return breakdown.get(type);
    }
}
