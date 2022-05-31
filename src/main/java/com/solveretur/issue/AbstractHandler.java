package com.solveretur.issue;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
abstract class AbstractHandler {
    private final HandlerType handlerType;

    Stream<Results> handle(final HandlerType handlerType, final ProcessingType processingType, final List<InputData> inputData) {
        Preconditions.checkArgument(handlerType == this.handlerType, String.format("I can process only: %s you gave me: %s%n", this.handlerType, handlerType));
        return inputData.stream().map(it -> handle(processingType, it));
    }

    abstract Results handle(final ProcessingType processingType, final InputData inputData);
}
