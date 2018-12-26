package com.puresoltechnologies.javafx.utils.graphics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Matrix2DTest {

    @Test
    public void testIdentity() {
	Matrix2D identity = Matrix2D.identity();
	assertEquals(1.0, identity.get(0, 0), 1e-9);
	assertEquals(0.0, identity.get(0, 1), 1e-9);
	assertEquals(0.0, identity.get(0, 2), 1e-9);
	assertEquals(0.0, identity.get(1, 0), 1e-9);
	assertEquals(1.0, identity.get(1, 1), 1e-9);
	assertEquals(0.0, identity.get(1, 2), 1e-9);
	assertEquals(0.0, identity.get(2, 0), 1e-9);
	assertEquals(0.0, identity.get(2, 1), 1e-9);
	assertEquals(1.0, identity.get(2, 2), 1e-9);
    }

    @Test
    public void testMove() {
	Matrix2D identity = Matrix2D.identity();
	identity = identity.move(2.0, 3.0);
	assertEquals(1.0, identity.get(0, 0), 1e-9);
	assertEquals(0.0, identity.get(0, 1), 1e-9);
	assertEquals(2.0, identity.get(0, 2), 1e-9);
	assertEquals(0.0, identity.get(1, 0), 1e-9);
	assertEquals(1.0, identity.get(1, 1), 1e-9);
	assertEquals(3.0, identity.get(1, 2), 1e-9);
	assertEquals(0.0, identity.get(2, 0), 1e-9);
	assertEquals(0.0, identity.get(2, 1), 1e-9);
	assertEquals(1.0, identity.get(2, 2), 1e-9);
    }

}
