package com.dora.common.db.sequence;

public class SequenceUtils {
    private static ISequence sequence;

    public SequenceUtils() {
    }

    public static Long nextID() {
        return sequence != null ? sequence.nextID() : null;
    }

    public static void setSequence(ISequence sequence) {
        SequenceUtils.sequence = sequence;
    }
}
