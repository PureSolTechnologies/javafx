package com.puresoltechnologies.javafx.reactive;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;

@BenchmarkMode(Mode.Throughput)
public class MicroBenchmark {

    @Benchmark
    @Fork(3)
    public static double benchmarkPerformanceCriticalComponent() {
	return Math.random();
    }
}
