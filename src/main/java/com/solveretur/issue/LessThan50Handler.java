package com.solveretur.issue;


import java.util.Optional;

final class LessThan50Handler extends AbstractHandler {

    public LessThan50Handler() {
        super(HandlerType.LESS_THAN_50);
    }

    @Override
    Optional<Results> handle(final ProcessingType processingType, final InputData inputData, final GroupedInputData groupedInputData) {
        if (inputData.getData() == 25) {
            return dropData(processingType, inputData, groupedInputData);
        }
        final String res = String.format("HANDLED BY LessThan50Handler with type: %s", processingType.name());
        return Optional.of(new Results(inputData.getData(), res));
    }

    //    here is the method you need to implement
    //    it should put the input data you currently processing back to input stream to be processed with another group
    //    e.g
    //    >> currently you are processing 25 as ODD_AND_SMALLER_THAN_50
    //    >> it should go back to the stream and be processed as ODD_NUMBERS
    //    of course you can delete all handlers and structure the code differently but the contract needs to apply
    //    for input 25 when processing as LESS_THAN_50 it need to go back to the stream
    private Optional<Results> dropData(final ProcessingType processingType, final InputData inputData, final GroupedInputData groupedInputData) {
        groupedInputData.feedback(ProcessingType.findMatching(inputData, processingType), inputData);
        return Optional.empty();
    }
}
