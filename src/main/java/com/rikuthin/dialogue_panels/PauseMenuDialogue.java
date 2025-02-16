package com.rikuthin.dialogue_panels;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import com.rikuthin.GameFrame;
import static com.rikuthin.utility.ButtonUtil.createButton;

public class PauseMenuDialogue extends Dialog {

    private final JLabel menuLabel;

    private final JButton resumeButton;
    private final JButton settingsButton;
    private final JButton mainMenuButton;
    private final JButton quitGameButton;

    public PauseMenuDialogue(GameFrame gameFrame, Runnable onResumeCallback) {
        super(gameFrame, "Pause Menu", Dialog.ModalityType.APPLICATION_MODAL);
        setSize(200, 260); // Example size of dialog
        setLocationRelativeTo(gameFrame);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        Font menuFont = new Font(GameFrame.BODY_TYPEFACE, Font.PLAIN, 8);

        menuLabel = new JLabel("PAUSE");
        menuLabel.setFont(menuFont);
        menuLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        resumeButton = createButton("RESUME", menuFont, 100, 30, true, e -> onResume(onResumeCallback));
        resumeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        settingsButton = createButton("SETTINGS", menuFont, 100, 30, false, this::onSettings);
        settingsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainMenuButton = createButton("MAIN MENU", menuFont, 100, 30, false, this::onMainMenu);
        mainMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        quitGameButton = createButton("QUIT GAME", menuFont, 100, 30, true, this::onQuitGame);
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

    private void onResume(Runnable onResumeCallback) {
        onResumeCallback.run();
        setVisible(false);
    }

    private void onSettings(ActionEvent e) {
        System.out.println("Settings button clicked.");
    }

    private void onMainMenu(ActionEvent e) {
        System.out.println("Main Menu button clicked.");
    }

    @SuppressWarnings(value = "unused")
    private void onQuitGame(ActionEvent e) {
        System.exit(0);
    }
}
