/****************************************************************************
 *
 *   AboutBox.java
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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import com.puresoltechnologies.javafx.i18n.Translator;

/**
 * This class provides an about box with information about the copyrights,
 * authors and license.
 *
 * @author Rick-Rainer Ludwig
 *
 */
class AboutBox extends JDialog implements ActionListener, WindowListener {

    private static final long serialVersionUID = 2224920916064371369L;

    private static final Translator translator = Translator.getTranslator(AboutBox.class);

    private final JButton okButton = new JButton(translator.i18n("OK"));
    private final JTabbedPane tabbedPane = new JTabbedPane();
    private final JFrame frame;

    public AboutBox(JFrame frame) {
	super(frame, translator.i18n("About"), false);
	this.frame = frame;
	this.addWindowListener(this);
	initUI();
    }

    private void initUI() {
	Container pane = getContentPane();
	pane.setLayout(new BorderLayout());

	JLabel title;
	if (frame != null) {
	    title = new JLabel(frame.getTitle());
	} else {
	    title = new JLabel("???");
	}
	title.setFont(new Font(Font.SERIF, Font.BOLD, 24));
	title.setHorizontalTextPosition(JLabel.HORIZONTAL);
	pane.add(title, BorderLayout.NORTH);

	JPanel buttonPanel = new JPanel();
	buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	buttonPanel.add(okButton);
	okButton.addActionListener(this);
	pane.add(buttonPanel, BorderLayout.SOUTH);

	pane.add(tabbedPane, BorderLayout.CENTER);

	addCopyright();

	pack();
	setSize(new Dimension(this.getSize().width, 480));
	setLocationRelativeTo(getParent());
    }

    private void addCopyright() {
	String message = "I18NLinguist\n\n" + translator.i18n("author:") + " Rick-Rainer Ludwig\n"
		+ translator.i18n("email:") + " ludwig@puresol-technologies.com\n";

	message += "\n\n";

	message += translator.i18n("Copyright 2009-2011 PureSol-Technologies                                \n"
		+ "                                                                        \n"
		+ "Licensed under the Apache License, Version 2.0 (the \"License\");       \n"
		+ "you may not use this file except in compliance with the License.        \n"
		+ "You may obtain a copy of the License at                                 \n"
		+ "                                                                        \n"
		+ "    http://www.apache.org/licenses/LICENSE-2.0                          \n"
		+ "                                                                        \n"
		+ "Unless required by applicable law or agreed to in writing, software     \n"
		+ "distributed under the License is distributed on an \"AS IS\" BASIS,     \n"
		+ "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n"
		+ "See the License for the specific language governing permissions and     \n"
		+ "limitations under the License.                                          \n");
	JTextArea textField = new JTextArea();
	textField.setEditable(false);
	textField.setText(message);
	tabbedPane.add(new JScrollPane(textField), translator.i18n("Copyright"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == okButton) {
	    dispose();
	}
    }

    @Override
    public void windowActivated(WindowEvent arg0) {
    }

    @Override
    public void windowClosed(WindowEvent arg0) {
    }

    @Override
    public void windowClosing(WindowEvent arg0) {
	dispose();
    }

    @Override
    public void windowDeactivated(WindowEvent arg0) {
    }

    @Override
    public void windowDeiconified(WindowEvent arg0) {
    }

    @Override
    public void windowIconified(WindowEvent arg0) {
    }

    @Override
    public void windowOpened(WindowEvent arg0) {
    }

    public static void main(String args[]) {
	new AboutBox(null).setVisible(true);
    }
}
