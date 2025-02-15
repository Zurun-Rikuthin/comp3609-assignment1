package com.rikuthin;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToggleButton;

public class ButtonUtil {
    private ButtonUtil() {}

    public static JButton createButton(final String text, final int width, final int height, boolean enabled, final ActionListener actionListener) {
        JButton button = new JButton(text);
        Dimension buttonSize = new Dimension(width, height);

        button.setPreferredSize(buttonSize);
        button.setMinimumSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setEnabled(enabled);
        button.addActionListener(actionListener);
        return button;
    }

    public static JToggleButton createToggleButton(final String text, final int width, final int height, boolean enabled, final ActionListener actionListener) {
        JToggleButton button = new JToggleButton(text);
        Dimension buttonSize = new Dimension(width, height);

        button.setPreferredSize(buttonSize);
        button.setMinimumSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.setEnabled(enabled);
        button.addActionListener(actionListener);
        return button;
    }
}
