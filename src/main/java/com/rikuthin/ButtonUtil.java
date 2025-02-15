package com.rikuthin;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToggleButton;

public class ButtonUtil {

    private ButtonUtil() {
    }

    public static JButton createButton(final String text, final Font font, final int buttonWidth, final int buttonHeight, boolean enabled, final ActionListener actionListener) {
        final Dimension buttonSize = new Dimension(buttonWidth, buttonHeight);
        final JButton button = new JButton(text);

        button.setFont(font);
        button.setPreferredSize(buttonSize);
        button.setMinimumSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setEnabled(enabled);
        button.addActionListener(actionListener);

        return button;
    }

    public static JToggleButton createToggleButton(final String text, final Font font, final int buttonWidth, final int buttonHeight, boolean enabled, final ActionListener actionListener) {
        final Dimension buttonSize = new Dimension(buttonWidth, buttonHeight);
        final JToggleButton button = new JToggleButton(text);

        button.setFont(font);
        button.setPreferredSize(buttonSize);
        button.setMinimumSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setEnabled(enabled);
        button.addActionListener(actionListener);

        return button;
    }
}
