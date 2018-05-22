package com.puresoltechnologies.javafx.utils.graphics;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Matrix2DTest {

    @Test
    public void testIdentity() {
	Matrix2D identity = Matrix2D.identity();
	assertEquals(1.0, identity.get(0, 0), 0.0);
	assertEquals(0.0, identity.get(0, 1), 0.0);
	assertEquals(0.0, identity.get(0, 2), 0.0);
	assertEquals(0.0, identity.get(1, 0), 0.0);
	assertEquals(1.0, identity.get(1, 1), 0.0);
	assertEquals(0.0, identity.get(1, 2), 0.0);
	assertEquals(0.0, identity.get(2, 0), 0.0);
	assertEquals(0.0, identity.get(2, 1), 0.0);
	assertEquals(1.0, identity.get(2, 2), 0.0);
    }

    @Test
    public void testMove() {
	Matrix2D identity = Matrix2D.identity();
	identity = identity.move(2.0, 3.0);
	assertEquals(1.0, identity.get(0, 0), 0.0);
	assertEquals(0.0, identity.get(0, 1), 0.0);
	assertEquals(2.0, identity.get(0, 2), 0.0);
	assertEquals(0.0, identity.get(1, 0), 0.0);
	assertEquals(1.0, identity.get(1, 1), 0.0);
	assertEquals(3.0, identity.get(1, 2), 0.0);
	assertEquals(0.0, identity.get(2, 0), 0.0);
	assertEquals(0.0, identity.get(2, 1), 0.0);
	assertEquals(1.0, identity.get(2, 2), 0.0);
    }

}
