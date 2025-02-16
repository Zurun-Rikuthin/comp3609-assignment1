package com.rikuthin.screen_panels.gameplay_subpanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.rikuthin.GameFrame;
import com.rikuthin.dialogue_panels.PauseMenuDialogue;
import static com.rikuthin.utility.ButtonUtil.createButton;

/**
 * The StatusPanel displays the current score and elapsed time, and provides a
 * pause button for the game.
 * <p>
 * This panel is displayed at the top of the gameplay screen.
 * </p>
 */
public final class StatusPanel extends JPanel {

    private final JButton pauseMenuButton;
    private final JLabel scoreLabel;
    private final JLabel timerLabel;
    private final Timer gameTimer;

    private int score;
    private int elapsedSeconds;

    /**
     * Constructs the StatusPanel, initialising the score and timer displays and
     * starting the game timer.
     */
    public StatusPanel() {
        // Set background colour and panel size.
        setBackground(new Color(87, 73, 100));
        setPreferredSize(new Dimension(GameFrame.FRAME_WIDTH, 60));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        score = 0;
        elapsedSeconds = 0;

        // Create the font used for the button and labels.
        Font buttonFont = new Font(GameFrame.BODY_TYPEFACE, Font.PLAIN, 16);

        // Create the pause button.
        pauseMenuButton = createButton("PAUSE", buttonFont, 100, 40, true, this::onPause);

        // Create the score and timer labels.
        scoreLabel = createStatusLabel();
        timerLabel = createStatusLabel();

        // Arrange components with horizontal spacing.
        add(Box.createHorizontalStrut(20));
        add(pauseMenuButton);
        add(Box.createHorizontalGlue());
        add(scoreLabel);
        add(Box.createHorizontalGlue());
        add(timerLabel);
        add(Box.createHorizontalStrut(20));

        // Initialise the score display.
        updateScore(0);

        // Initialise and start the game timer (updates every second).
        gameTimer = new Timer(1000, this::onTimerTick);
        gameTimer.start();
    }

    /**
     * Returns the total elapsed time in seconds since the game started.
     *
     * @return The elapsed seconds.
     */
    public int getElapsedSeconds() {
        return elapsedSeconds;
    }

    /**
     * Returns the current score.
     *
     * @return The current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Updates the displayed score and internal score counter.
     *
     * @param score The new score.
     */
    public final void updateScore(final int score) {
        this.score = score;
        scoreLabel.setText(String.format("Score: %d", score));
    }

    /**
     * Action invoked by the game timer every second. Increments the elapsed
     * time and updates the timer display.
     *
     * @param e The action event triggered by the timer.
     */
    private void onTimerTick(ActionEvent e) {
        elapsedSeconds++;
        updateTimer(elapsedSeconds);
    }

    /**
     * Updates the timer label with a formatted elapsed time string.
     *
     * @param elapsedSeconds The elapsed time in seconds.
     */
    private void updateTimer(final int elapsedSeconds) {
        int minutes = elapsedSeconds / 60;
        int seconds = elapsedSeconds % 60;
        timerLabel.setText(String.format("Elapsed Time: %d:%02d", minutes, seconds));
    }

    /**
     * Pauses the game when the pause button is clicked. Stops the timer and
     * displays the pause menu dialogue.
     *
     * @param e The action event triggered by clicking the pause button.
     */
    private void onPause(ActionEvent e) {
        gameTimer.stop();
        showPauseMenu();
    }

    /**
     * Displays the pause menu dialogue.
     */
    private void showPauseMenu() {
        PauseMenuDialogue pauseMenuDialogue = new PauseMenuDialogue(
                (GameFrame) getTopLevelAncestor(),
                this::onResume
        );
        pauseMenuDialogue.setVisible(true);
    }

    /**
     * Resumes the game by restarting the game timer.
     */
    private void onResume() {
        gameTimer.start();
    }

    /**
     * Creates a JLabel for displaying status information (score or timer).
     *
     * @return A configured JLabel.
     */
    private JLabel createStatusLabel() {
        JLabel label = new JLabel();
        label.setFont(new Font(GameFrame.BODY_TYPEFACE, Font.BOLD, 16));
        label.setForeground(Color.WHITE);

        // Will try to fix for the next assignment
        // label.setForeground(new Color(70, 0, 50));
        // label.setBorder(new RoundedBorder(Color.BLACK, Color.WHITE, 10));
        return label;
    }
}
