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

package com.puresoltechnologies.javafx.i18n.linguist;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

class StatusComponent extends JPanel {

	private static final long serialVersionUID = 9057595345044306838L;

	public StatusComponent(String text, boolean selected, boolean focus,
			Status status) {
		super();

		setOpaque(false);
		BoxLayout layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		setLayout(layout);

		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(JLabel.LEFT);
		label.setVerticalAlignment(JLabel.VERTICAL);

		if (focus) {
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		switch (status) {
		case EMPTY:
			label.setForeground(new Color(127, 0, 0));
			if (selected) {
				setOpaque(true);
				setBackground(new Color(255, 192, 192));
			}
			break;
		case ONGOING:
			label.setForeground(new Color(127, 127, 0));
			if (selected) {
				setOpaque(true);
				setBackground(new Color(255, 255, 192));
			}
			break;
		case FINISHED:
			label.setForeground(new Color(0, 127, 0));
			if (selected) {
				setOpaque(true);
				setBackground(new Color(192, 255, 192));
			}
			break;
		}
		add(label, BorderLayout.CENTER);
	}

}
