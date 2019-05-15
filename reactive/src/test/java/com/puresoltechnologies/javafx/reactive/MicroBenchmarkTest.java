package com.puresoltechnologies.javafx.reactive;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.text.DecimalFormat;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class MicroBenchmarkTest {

    private static DecimalFormat df = new DecimalFormat("0.000");
    private static final double REFERENCE_SCORE = 10.0;

    @Test
    public void launchReactiveBechmark() throws RunnerException {
	Options opt = new OptionsBuilder().include(MicroBenchmark.class.getSimpleName()).build();
	Collection<RunResult> runResults = new Runner(opt).run();
	assertFalse(runResults.isEmpty());
	for (RunResult runResult : runResults) {
	    assertDeviationWithin(runResult, REFERENCE_SCORE, 0.05);
	}
    }

    private static void assertDeviationWithin(RunResult result, double referenceScore, double maxDeviation) {
	double score = result.getPrimaryResult().getScore();
	double deviation = Math.abs((score / referenceScore) - 1);
	String deviationString = df.format(deviation * 100) + "%";
	String maxDeviationString = df.format(maxDeviation * 100) + "%";
	String errorMessage = "Deviation " + deviationString + " exceeds maximum allowed deviation "
		+ maxDeviationString;
	assertTrue(deviation < maxDeviation, errorMessage);
    }
}
