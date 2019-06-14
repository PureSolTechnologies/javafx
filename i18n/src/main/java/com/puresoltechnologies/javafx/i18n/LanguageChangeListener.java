/****************************************************************************
 *
 *   LanguageChangeListener.java
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
 
package com.puresoltechnologies.javafx.i18n;

/**
 * This interface is used to implement a listener for changed translations. This
 * can be used to update GUIs.
 * 
 * @author Rick-Rainer Ludwig
 * 
 */
public interface LanguageChangeListener {

	/**
	 * This method is called in cases of changed translations
	 * 
	 * @param translator
	 *            is the translator with changed translation.
	 */
	public void translationChanged(Translator translator);

}
