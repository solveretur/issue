package com.solveretur.issue;

import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
final class IssueService {
    private final LessThan50Handler lessThan50Handler;
    private final OddNumbersHandler oddNumbersHandler;


    //    this is the contract you cannot change signature of this method !!!
    Stream<Results> process(final Stream<InputData> inputDataStream) {
        final Map<ProcessingType, List<InputData>> groupedInput = inputDataStream.collect(Collectors.groupingBy(ProcessingType::findMatching));
        return groupedInput.entrySet()
                .stream()
                .sorted(Comparator.comparingInt(it -> it.getKey().ordinal()))
                .flatMap(entry -> process(entry.getKey(), entry.getValue()));
    }

    private Stream<Results> process(final ProcessingType processingType, final List<InputData> inputData) {
        final HandlerType handlerType = processingType.getHandlerType();
        switch (handlerType) {
            case LESS_THAN_50:
                return lessThan50Handler.handle(handlerType, processingType, inputData);
            case ODD_NUMBERS:
                return oddNumbersHandler.handle(handlerType, processingType, inputData);
            case NONE:
                return inputData.stream().map(it -> new Results(it.getData(), "NONE"));
            default:
                throw new IllegalStateException(String.format("Couldn't find handler for %s%n", handlerType));
        }
    }
}
