package AI.Collector;

import AI.Collector.FailureContextAggregator;

public class FailureContextAggregatorTest {

    public static void main(String[] args) throws Exception {

        FailureContextAggregator aggregator =
                new FailureContextAggregator();

        aggregator.aggregateFailures();
    }
}
