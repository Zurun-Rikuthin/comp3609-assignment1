package com.rikuthin.dialogue_panels;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import static com.rikuthin.ButtonUtil.createButton;
import com.rikuthin.GameFrame;

public class PauseMenuDialogue extends Dialog {

    private final JLabel menuLabel;

    private final JButton resumeButton;
    private final JButton settingsButton;
    private final JButton mainMenuButton;
    private final JButton quitGameButton;

    public PauseMenuDialogue(GameFrame gameFrame, Runnable onResumeCallback) {
        super(gameFrame, "Pause Menu", Dialog.ModalityType.APPLICATION_MODAL);
        setSize(160, 260); // Example size of dialog
        setLocationRelativeTo(gameFrame);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        menuLabel = new JLabel("PAUSE");
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        resumeButton = createButton("RESUME", 80, 40, true, this::onResume);
        resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        settingsButton = createButton("SETTINGS", 80, 40, false, this::onSettings);
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainMenuButton = createButton("MAIN MENU", 80, 40, false, this::onMainMenu);
        mainMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        quitGameButton = createButton("QUIT GAME", 80, 40, true, this::onQuitGame);
        quitGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(20));
        add(menuLabel);
        add(Box.createVerticalStrut(20));
        add(resumeButton);
        add(Box.createVerticalStrut(10));
        add(settingsButton);
        add(Box.createVerticalStrut(10));
        add(mainMenuButton);
        add(Box.createVerticalStrut(10));
        add(quitGameButton);
        add(Box.createVerticalStrut(20));

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                onResumeCallback.run();
            }
        });
    }

    @SuppressWarnings(value = "unused")
    private void onResume(ActionEvent e) {

    }

    @SuppressWarnings(value = "unused")
    private void onSettings(ActionEvent e) {

    }

    @SuppressWarnings(value = "unused")
    private void onMainMenu(ActionEvent e) {

    }

    @SuppressWarnings(value = "unused")
    private void onQuitGame(ActionEvent e) {
        System.exit(0);
    }
}
