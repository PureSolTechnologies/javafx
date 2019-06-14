package com.puresoltechnologies.javafx.i18n.linguist;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.puresoltechnologies.javafx.i18n.LocaleChooser;
import com.puresoltechnologies.javafx.i18n.Translator;

class TranslationCopyDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 2407043456239419407L;

	private static final Translator translator = Translator
			.getTranslator(TranslationCopyDialog.class);

	private final LocaleChooser sourceTranslations = new LocaleChooser();
	private final LocaleChooser targetTranslations = new LocaleChooser();
	private final JButton okButton = new JButton(translator.i18n("OK"));
	private final JButton cancelButton = new JButton(translator.i18n("Cancel"));

	private boolean finishedByOK = false;

	public TranslationCopyDialog(JFrame parent) {
		super(parent, translator.i18n("Copy Translation"), true);
		initUI();
	}

	private void initUI() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		setContentPane(panel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.add(okButton);
		okButton.addActionListener(this);
		buttonPanel.add(cancelButton);
		cancelButton.addActionListener(this);
		panel.add(buttonPanel, BorderLayout.SOUTH);

		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));
		listPanel.add(new JLabel(translator.i18n("from:")));
		listPanel.add(sourceTranslations);
		listPanel.add(new JLabel(translator.i18n("to:")));
		listPanel.add(targetTranslations);
		panel.add(listPanel, BorderLayout.CENTER);
		pack();
	}

	private void ok() {
		finishedByOK = true;
		dispose();
	}

	private void cancel() {
		finishedByOK = false;
		dispose();
	}

	public boolean isFinishedByOK() {
		return finishedByOK;
	}

	public Locale getSource() {
		return sourceTranslations.getSelectedLocale();
	}

	public Locale getTarget() {
		return targetTranslations.getSelectedLocale();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {
			ok();
		} else if (e.getSource() == cancelButton) {
			cancel();
		}
	}
}
