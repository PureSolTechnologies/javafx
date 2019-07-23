package com.puresoltechnologies.javafx.charts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AbstractRendererTest {

    public static Stream<Arguments> provideCalcPosXValues() {
	return Stream.of( //
		Arguments.of(100.0, 100.0, 0.0, 100.0, 50.0, "Simple Center Test", 150), //
		Arguments.of(100.0, 100.0, 400.0, 2000.0, 400.0, "Simple Edge Test", 100) //
	);
    }

    @DisplayName("CalcPosX testing")
    @ParameterizedTest(name = "{5}: expects {6} for x={0}, width={1} and {2} <= {4} <= {3}")
    @MethodSource("provideCalcPosXValues")
    public void testCalcPosX(double x, double width, double min, double max, double value, String comment,
	    double result) {
	assertEquals(result, AbstractRenderer.calcPosX(x, width, min, max, value), comment);
    }

    public static Stream<Arguments> provideCalcPosYValues() {
	Arguments.of(100.0, 100.0, 0.0, 100.0, 50.0, "Simple Center Test");
	return Stream.of( //
		Arguments.of(100.0, 100.0, 0.0, 100.0, 50.0, "Simple Center Test", 150), //
		Arguments.of(100.0, 100.0, 400.0, 2000.0, 400.0, "Simple Edge Test", 200) //
	);
    }

    @DisplayName("CalcPosY testing")
    @ParameterizedTest(name = "{5}: expects {6} for y={0}, height={1} and {2} <= {4} <= {3}")
    @MethodSource("provideCalcPosYValues")
    public void testCalcPosY(double y, double height, double min, double max, double value, String comment,
	    double result) {
	assertEquals(result, AbstractRenderer.calcPosY(y, height, min, max, value), comment);
    }

}
