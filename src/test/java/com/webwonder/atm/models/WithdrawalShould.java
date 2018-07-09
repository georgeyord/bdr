package com.webwonder.atm.models;

import com.webwonder.atm.enums.NoteType;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

public class WithdrawalShould {
    Withdrawal withdrawal;

    @Before
    public void setUp() {
        withdrawal = new Withdrawal();
    }

    @Test
    public void beAbleToRepsondWithTheCurrentBreakdown() {
        Map<NoteType, Integer> breakdown = withdrawal.getBreakdown();

        assertThat(breakdown).doesNotContainKey(NoteType.FIFTY_NOTE);
        assertThat(breakdown).doesNotContainKey(NoteType.TWENTY_NOTE);
        assertThat(breakdown.get(NoteType.FIFTY_NOTE)).isNull();
        assertThat(breakdown.get(NoteType.TWENTY_NOTE)).isNull();
    }

    @Test
    public void beAbleToAddNotesOfASpecificType() {
        withdrawal.add(NoteType.FIFTY_NOTE, 3);
        Map<NoteType, Integer> breakdown = withdrawal.getBreakdown();

        assertThat(breakdown).contains(entry(NoteType.FIFTY_NOTE, 3));
        assertThat(breakdown).doesNotContainKey(NoteType.TWENTY_NOTE);
    }

    @Test
    public void beAbleToAddNotesOfASpecificTypeMultipleTimes() {
        withdrawal.add(NoteType.FIFTY_NOTE, 3);
        withdrawal.add(NoteType.FIFTY_NOTE, 2);
        Map<NoteType, Integer> breakdown = withdrawal.getBreakdown();

        assertThat(breakdown).contains(entry(NoteType.FIFTY_NOTE, 5));
        assertThat(breakdown).doesNotContainKey(NoteType.TWENTY_NOTE);
    }

    @Test
    public void beAbleToAddNotesOfMultipleSpecificTypes() {
        withdrawal
            .add(NoteType.FIFTY_NOTE, 3)
            .add(NoteType.TWENTY_NOTE, 2);
        Map<NoteType, Integer> breakdown = withdrawal.getBreakdown();

        assertThat(breakdown).contains(entry(NoteType.FIFTY_NOTE, 3));
        assertThat(breakdown).contains(entry(NoteType.TWENTY_NOTE, 2));
    }
}