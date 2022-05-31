package com.solveretur.issue;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

public class IssueServiceTest {

    @Test
    void test() {
        //   remember that in the real case we are using spring and autowiring
        //   you must not create a dependency cycle - unless you agree to use ApplicationContext.getBean
        final LessThan50Handler lessThan50Handler = new LessThan50Handler();
        final OddNumbersHandler oddNumbersHandler = new OddNumbersHandler();
        final IssueService issueService = new IssueService(lessThan50Handler, oddNumbersHandler);
        // given
        final List<InputData> input = List.of(
                new InputData(5),
                new InputData(10),
                new InputData(130),
                new InputData(25),
                new InputData(555)
        );
        // when
        final List<Results> res = issueService.process(input.stream()).collect(Collectors.toList());
        //
        Assertions
                .assertThat(res)
                .extracting(Results::getData, Results::getResult)
                .containsExactlyInAnyOrder(
                        Tuple.tuple(5, "HANDLED BY LessThan50Handler with type: ODD_AND_SMALLER_THAN_50"),
                        Tuple.tuple(10, "HANDLED BY LessThan50Handler with type: EVEN_AND_SMALLER_THAN_50"),
                        Tuple.tuple(130, "NONE"),
                        Tuple.tuple(555, "HANDLED BY OddNumbersHandler with type: ODD"),
//                      make it work for 25 even though it matches ODD_AND_SMALLER_THAN_50 put it back in the stream and it needs to be processed as another ProcessingType
                        Tuple.tuple(25, "HANDLED BY OddNumbersHandler with type: ODD")
                );
    }
}
