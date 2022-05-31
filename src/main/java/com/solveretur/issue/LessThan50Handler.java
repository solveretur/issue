package com.solveretur.issue;


final class LessThan50Handler extends AbstractHandler {

    public LessThan50Handler() {
        super(HandlerType.LESS_THAN_50);
    }

    @Override
    Results handle(final ProcessingType processingType, final InputData inputData) {
        if (inputData.getData() == 25) {
            return dropData(processingType, inputData);
        }
        final String res = String.format("HANDLED BY LessThan50Handler with type: %s", processingType.name());
        return new Results(inputData.getData(), res);
    }

    //    here is the method you need to implement
    //    it should put the input data you currently processing back to input stream to be processed with another group
    //    e.g
    //    >> currently you are processing 25 as ODD_AND_SMALLER_THAN_50
    //    >> it should go back to the stream and be processed as ODD_NUMBERS
    //    of course you can delete all handlers and structure the code differently but the contract needs to apply
    //    for input 25 when processing as LESS_THAN_50 it need to go back to the stream
    private Results dropData(final ProcessingType processingType, final InputData inputData) {
        return new Results(inputData.getData(), "TO BE IMPLEMENTED");
    }
}
