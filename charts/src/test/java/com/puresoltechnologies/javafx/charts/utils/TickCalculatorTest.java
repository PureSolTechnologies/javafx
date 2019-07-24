package com.puresoltechnologies.javafx.charts.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TickCalculatorTest {

    @Test
    public void testCalculateStepExponent() {
	assertEquals(-1, TickCalculator.calculateStepExponent(0.0, 1.0));
	assertEquals(2, TickCalculator.calculateStepExponent(0.0, 1000.0));
	assertEquals(2, TickCalculator.calculateStepExponent(-1000.0, 1000.0));
	assertEquals(3, TickCalculator.calculateStepExponent(-2500.0, 2500.0));
	assertEquals(2, TickCalculator.calculateStepExponent(400, 2000));
    }

    @Test
    public void testCalculateChartMin() {
	assertEquals(-400.0, TickCalculator.calculateChartMin(-350.0, 2));
	assertEquals(-400.0, TickCalculator.calculateChartMin(-400.0, 2));
	assertEquals(400.0, TickCalculator.calculateChartMin(400.0, 2));
	assertEquals(300.0, TickCalculator.calculateChartMin(350.0, 2));
    }

    @Test
    public void testCalculateChartMax() {
	assertEquals(-300.0, TickCalculator.calculateChartMax(-350.0, 2));
	assertEquals(-400.0, TickCalculator.calculateChartMax(-400.0, 2));
	assertEquals(400.0, TickCalculator.calculateChartMax(400.0, 2));
	assertEquals(400.0, TickCalculator.calculateChartMax(350.0, 2));
    }

}
