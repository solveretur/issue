package com.solveretur.issue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

enum ProcessingType {
    ODD_AND_SMALLER_THAN_50(it -> it.getData() % 2 != 0 && it.getData() < 50, HandlerType.LESS_THAN_50),
    EVEN_AND_SMALLER_THAN_50(it -> it.getData() % 2 == 0 && it.getData() < 50, HandlerType.LESS_THAN_50),
    ODD(it -> it.getData() % 2 != 0, HandlerType.ODD_NUMBERS),
    NONE(it -> true, HandlerType.NONE);

    ProcessingType(final Predicate<InputData> predicate, final HandlerType handlerType) {
        this.predicate = predicate;
        this.handlerType = handlerType;
    }

    private final Predicate<InputData> predicate;
    private final HandlerType handlerType;

    static ProcessingType findMatching(final InputData inputData) {
        return Arrays.stream(values())
                .sorted(Comparator.comparingInt(ProcessingType::ordinal))
                .filter(it -> it.predicate.test(inputData))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Must find a match"));
    }

    static ProcessingType findMatching(final InputData inputData, final ProcessingType processingType) {
        return Arrays.stream(values())
                .sorted(Comparator.comparingInt(ProcessingType::ordinal))
                .filter(it -> it.ordinal() > processingType.ordinal())
                .filter(it -> it.predicate.test(inputData))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Must find a match"));
    }

    public HandlerType getHandlerType() {
        return handlerType;
    }
}
