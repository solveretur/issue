package com.solveretur.issue;


import java.util.Optional;

final class OddNumbersHandler extends AbstractHandler {

    public OddNumbersHandler() {
        super(HandlerType.ODD_NUMBERS);
    }

    @Override
    Optional<Results> handle(final ProcessingType processingType, final InputData inputData, final GroupedInputData groupedInputData) {
        final String res = String.format("HANDLED BY OddNumbersHandler with type: %s", processingType.name());
        return Optional.of(new Results(inputData.getData(), res));
    }
}
