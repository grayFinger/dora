package com.dora.common.db.sequence.impl;


import com.dora.common.db.sequence.ISequence;

public class NullSequence implements ISequence {
    public NullSequence() {
    }

    public Long nextID() {
        return null;
    }
}
