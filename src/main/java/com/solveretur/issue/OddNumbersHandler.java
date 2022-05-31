package com.solveretur.issue;


final class OddNumbersHandler extends AbstractHandler {

    public OddNumbersHandler() {
        super(HandlerType.ODD_NUMBERS);
    }

    @Override
    Results handle(final ProcessingType processingType, final InputData inputData) {
        final String res = String.format("HANDLED BY OddNumbersHandler with type: %s", processingType.name());
        return new Results(inputData.getData(), res);
    }
}
