/****************************************************************************
 *
 *   I18NProjectConfiguratorTest.java
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

package com.puresoltechnologies.javafx.i18n.proc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

public class I18NProjectConfiguratorTest {

    @Test
    public void testReadI18NProperties() throws Exception {
	I18NProjectConfiguration config = new I18NProjectConfiguration(new File("."));
	assertEquals(new File("."), config.getProjectDirectory());
	assertEquals("src/main/java", config.getRelativeSourceDirectory());
	assertEquals("src/i18n", config.getRelativeI18nDirectory());
	assertEquals("src/main/resources", config.getRelativeDestinationDirectory());

	assertEquals(new File("./src/main/java"), config.getSourceDirectory());
	assertEquals(new File("./src/i18n"), config.getI18nDirectory());
	assertEquals(new File("./src/main/resources"), config.getDestinationDirectory());

	assertTrue(config.getProjectDirectory().exists());
	assertTrue(config.getSourceDirectory().exists());
	assertTrue(config.getI18nDirectory().exists());
	assertTrue(config.getDestinationDirectory().exists());
    }

}
