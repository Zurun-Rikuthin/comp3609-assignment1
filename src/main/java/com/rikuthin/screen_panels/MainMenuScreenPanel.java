package com.rikuthin.screen_panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import static com.rikuthin.ButtonUtil.createButton;
import com.rikuthin.GameFrame;
import static com.rikuthin.GameFrame.GAMEPLAY_PANEL_NAME;

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

        JButton startGameButton = createButton("START GAME", 180, 40, true, this::onStartGame);
        JButton howToPlayButton = createButton("HOW TO PLAY", 180, 40, false, this::onHowToPlay);
        JButton settingsMenuButton = createButton("SETTINGS", 180, 40, false, this::onSettings);
        JButton highscoresListButton = createButton("HIGHSCORES", 180, 40, false, this::onHighscores);
        JButton quitGameButton = createButton("QUIT GAME", 180, 40, true, this::onQuitGame);

        buttonPanel.add(Box.createVerticalStrut(100));
        buttonPanel.add(startGameButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(howToPlayButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(settingsMenuButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(highscoresListButton);
        buttonPanel.add(Box.createVerticalStrut(80));
        buttonPanel.add(quitGameButton);

        centreWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));  // Centering the buttons more easily
        centreWrapper.setOpaque(false);
        centreWrapper.add(buttonPanel);

        add(centreWrapper, BorderLayout.CENTER);

        // // ----- Mute Button (Top) -----
        // muteButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // muteButtonPanel.setOpaque(false);
        // muteMusicButton = createToggleButton("MUTE", 90, 40, false, this::onMuteMusic);
        // muteButtonPanel.add(muteMusicButton);
        // add(muteButtonPanel, BorderLayout.NORTH);  // Add to the west (left side)
    }

    @SuppressWarnings(value = {"unused"})
    private void onMuteMusic(ActionEvent e) {
        System.out.println("Mute Button not implemented");
    }

    @SuppressWarnings(value = {"unused"})
    private void onStartGame(ActionEvent e) {
        gameFrame.switchToPanel(GAMEPLAY_PANEL_NAME);
    }

    @SuppressWarnings(value = {"unused"})
    private void onHowToPlay(ActionEvent e) {
        System.out.println("How to Play Screen not implemented");
    }

    @SuppressWarnings(value = {"unused"})
    private void onSettings(ActionEvent e) {
        System.out.println("Settings Menu not implemented");
    }

    @SuppressWarnings(value = {"unused"})
    private void onHighscores(ActionEvent e) {
        System.out.println("High Scores screen not implemented");
    }

    @SuppressWarnings(value = {"unused"})
    private void onQuitGame(ActionEvent e) {
        System.exit(0);
    }
}
