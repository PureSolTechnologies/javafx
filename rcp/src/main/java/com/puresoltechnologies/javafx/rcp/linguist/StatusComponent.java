/****************************************************************************
 *
 *   StatusComponent.java
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

package com.puresoltechnologies.javafx.rcp.linguist;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

class StatusComponent extends HBox {

    public StatusComponent(String text, boolean selected, boolean focus, Status status) {
	super();

	setOpacity(0.0);

	Label label = new Label(text);

	if (focus) {
	    setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, BorderWidths.FULL)));
	}
	switch (status) {
	case EMPTY:
	    label.setTextFill(Color.DARKRED);
	    if (selected) {
		setOpacity(1.0);
		setBackground(new Background(new BackgroundFill(Color.ORANGERED, null, null)));
	    }
	    break;
	case ONGOING:
	    label.setTextFill(Color.ORANGE);
	    if (selected) {
		setOpacity(1.0);
		setBackground(new Background(new BackgroundFill(Color.YELLOW, null, null)));
	    }
	    break;
	case FINISHED:
	    label.setTextFill(Color.DARKGREEN);
	    if (selected) {
		setOpacity(1.0);
		setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));
	    }
	    break;
	}
	getChildren().add(label);
    }

}
