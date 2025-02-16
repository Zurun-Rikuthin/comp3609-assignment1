package com.rikuthin.screen_panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.rikuthin.GameFrame;
import static com.rikuthin.GameFrame.GAMEPLAY_PANEL_NAME;
import static com.rikuthin.utility.ButtonUtil.createButton;

public class MainMenuScreenPanel extends ScreenPanel {

    private final JLabel titleLabel;
    private final JPanel buttonPanel;
    private final JPanel centreWrapper;
    private final JPanel titlePanel;
    // private final JPanel muteButtonPanel;
    // private final JToggleButton muteMusicButton;

    public MainMenuScreenPanel(GameFrame gameFrame) {
        super(gameFrame);
        setBackground(new Color(87, 73, 100));
        setLayout(new BorderLayout());

        // Title Label (centered at the top)
        titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);

        titleLabel = new JLabel("Untitled Bubble Shooter", SwingConstants.CENTER);
        titleLabel.setFont(new Font(GameFrame.BODY_TYPEFACE, Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);

        titlePanel.add(Box.createVerticalStrut(100));  // Add space above the title
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createVerticalStrut(100));  // Add space below the title

        add(titlePanel, BorderLayout.NORTH);

        // ----- Centred Button Panel -----
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false);

        final Font buttonFont = new Font(GameFrame.BODY_TYPEFACE, Font.PLAIN, 16);
        buttonPanel.add(Box.createVerticalStrut(100));

        final String[] labels = {"START GAME", "HOW TO PLAY", "SETTINGS", "HIGHSCORES", "QUIT GAME"};
        final ActionListener[] actions = {this::onStartGame, this::onHowToPlay, this::onSettings, this::onHighscores, this::onQuitGame};

        for (int i = 0; i < labels.length; i++) {
            JButton button = createButton(labels[i], buttonFont, 180, 40, i != 1 && i != 2 && i != 3, actions[i]);
            buttonPanel.add(button);
            buttonPanel.add(Box.createVerticalStrut(10));
        }

        centreWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));  // Centering the buttons more easily
        centreWrapper.setOpaque(false);
        centreWrapper.add(buttonPanel);

        add(centreWrapper, BorderLayout.CENTER);

        // TODO
        // // ----- Mute Button (Top) -----
        // muteButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // muteButtonPanel.setOpaque(false);
        // muteMusicButton = createToggleButton("MUTE", 90, 40, false, this::onMuteMusic);
        // muteButtonPanel.add(muteMusicButton);
        // add(muteButtonPanel, BorderLayout.NORTH);  // Add to the west (left side)
    }

    // TODO
    private void onMuteMusic(ActionEvent e) {
        System.out.println("Mute Button not implemented");
    }

    // TODO
    private void onStartGame(ActionEvent e) {
        gameFrame.switchToPanel(GAMEPLAY_PANEL_NAME);
        gameFrame.getGameManager().startGame();
    }

    // TODO
    private void onHowToPlay(ActionEvent e) {
        System.out.println("How to Play Screen not implemented");
    }

    // TODO
    private void onSettings(ActionEvent e) {
        System.out.println("Settings Menu not implemented");
    }

    // TODO
    private void onHighscores(ActionEvent e) {
        System.out.println("High Scores screen not implemented");
    }

    private void onQuitGame(ActionEvent e) {
        System.exit(0);
    }
}
