/****************************************************************************
 *
 *   SourceLocationTest.java
 *   -------------------
 *   copyright            : (c) 2009-2011 by PureSol-Technologies
 *   author               : Rick-Rainer Ludwig
 *   email                : ludwig@puresol-technologies.com
 *
 ****************************************************************************/

/****************************************************************************
 *
 * Copyright 2009-2011 PureSol-Technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ****************************************************************************/

package com.puresoltechnologies.javafx.i18n.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.jupiter.api.Test;

public class SourceLocationTest {

    @Test
    public void testConstructors() {
	SourceLocation sourceLocation = new SourceLocation("File1.java", 100);
	assertEquals("File1.java", sourceLocation.getFile());
	assertEquals(100, sourceLocation.getLine());
	assertEquals(1, sourceLocation.getLineCount());

	sourceLocation = new SourceLocation("File2.java", 200, 2);
	assertEquals("File2.java", sourceLocation.getFile());
	assertEquals(200, sourceLocation.getLine());
	assertEquals(2, sourceLocation.getLineCount());
    }

    @Test
    public void testHashCode() {
	SourceLocation sourceLocation = new SourceLocation("File", 1, 2);
	System.out.println(sourceLocation.hashCode());
	assertTrue(sourceLocation.hashCode() > 0);
    }

    @Test
    public void testEquals() {
	SourceLocation sl1 = new SourceLocation("File", 1, 2);
	assertTrue(sl1.equals(sl1));
	assertFalse(sl1.equals(null));
	assertFalse(sl1.equals("Test"));

	SourceLocation sl2 = new SourceLocation("File", 1, 2);
	assertTrue(sl1.equals(sl2));
	assertTrue(sl2.equals(sl1));
    }

    @Test
    public void testClone() {
	SourceLocation origin = new SourceLocation("File.java", 1, 2);
	SourceLocation cloned = origin.clone();
	assertTrue(origin.equals(cloned));
	assertEquals(0, origin.compareTo(cloned));
	assertEquals(0, cloned.compareTo(origin));
    }

    @Test
    public void testExceptions() throws Exception {
	assertThrows(IllegalArgumentException.class, () -> {
	    new SourceLocation(null, 1, 2);
	});
    }

    @Test
    public void testExceptions2() throws Exception {
	assertThrows(IllegalArgumentException.class, () -> {
	    new SourceLocation("", 1, 2);
	});
    }

    @Test
    public void testExceptions3() throws Exception {
	assertThrows(IllegalArgumentException.class, () -> {
	    new SourceLocation("File.java", 0, 2);
	});
    }

    @Test
    public void testExceptions4() throws Exception {
	assertThrows(IllegalArgumentException.class, () -> {
	    new SourceLocation("File.java", -2, 2);
	});
    }

    @Test
    public void testExceptions5() throws Exception {
	assertThrows(IllegalArgumentException.class, () -> {
	    new SourceLocation("File.java", 1, 0);
	});
    }

    @Test
    public void testExceptions6() throws Exception {
	assertThrows(IllegalArgumentException.class, () -> {
	    new SourceLocation("File.java", 1, -3);
	});
    }

    @Test
    public void testCompareTo() {
	SourceLocation default1 = new SourceLocation("File", 1, 2);
	SourceLocation default2 = new SourceLocation("File", 1, 2);
	assertEquals(0, default1.compareTo(default2));
	assertEquals(0, default2.compareTo(default1));
	assertTrue(default1.equals(default2));
	assertTrue(default2.equals(default1));
	assertEquals(0, default1.compareTo(default1));
	assertEquals(-1, default1.compareTo(null));
    }

    @Test
    public void testToString() {
	assertEquals("File.java:1", new SourceLocation("File.java", 1).toString());
	assertEquals("File.java:1-2", new SourceLocation("File.java", 1, 2).toString());
    }

    @Test
    public void testMarshallingAndUnmarshalling() throws Exception {
	File file = new File("SourceLocationMarshallerTest");
	SourceLocation sourceLocation = new SourceLocation("File.java", 1, 2);
	JAXBContext context = JAXBContext.newInstance(sourceLocation.getClass());
	assertNotNull(context);

	Marshaller marshaller = context.createMarshaller();
	assertNotNull(marshaller);
	marshaller.marshal(sourceLocation, file);
	assertTrue(file.exists());

	Unmarshaller unmarshaller = context.createUnmarshaller();
	SourceLocation unmarshalled = (SourceLocation) unmarshaller.unmarshal(file);
	assertNotSame(sourceLocation, unmarshalled);
	assertEquals(sourceLocation, unmarshalled);
	assertTrue(file.delete());
	assertFalse(file.exists());
    }
}
