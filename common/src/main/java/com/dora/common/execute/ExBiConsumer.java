package com.dora.common.execute;

import java.util.Objects;

public interface ExBiConsumer<T> {
    void accept(T t);

    default ExBiConsumer<T> andThen(ExBiConsumer<? super T> after) {
        Objects.requireNonNull(after);
        return (l) -> {
            this.accept(l);
            after.accept(l);
        };
    }
}
