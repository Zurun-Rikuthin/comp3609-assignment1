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

import static com.rikuthin.ButtonUtil.createButton;
import com.rikuthin.GameFrame;
import com.rikuthin.RoundedBorder;
import com.rikuthin.dialogue_panels.PauseMenuDialogue;

public final class StatusPanel extends JPanel {

    private final JButton pauseMenuButton;

    private final JLabel scoreLabel;
    private final JLabel timerLabel;

    private final Timer gameTimer;

    private int score;
    private int elapsedSeconds;

    public StatusPanel() {
        setBackground(new Color(87, 73, 100));
        setPreferredSize(new Dimension(GameFrame.FRAME_WIDTH, 60));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        score = 0;
        elapsedSeconds = 0;

        Font buttonFont = new Font(GameFrame.BODY_TYPEFACE, Font.PLAIN, 16);

        pauseMenuButton = createButton("PAUSE", buttonFont, 100, 40, true, this::onPause);
        scoreLabel = createStatusLabel();
        timerLabel = createStatusLabel();

        add(Box.createHorizontalStrut(20));
        add(pauseMenuButton);
        add(Box.createHorizontalGlue());
        add(scoreLabel);
        add(Box.createHorizontalGlue());
        add(timerLabel);
        add(Box.createHorizontalStrut(20));

        updateScore(0);

        gameTimer = new Timer(1000, this::onTimerTick);
        gameTimer.start();
    }

    public int getElapsedSeconds() {
        return elapsedSeconds;
    }

    public int getScore() {
        return score;
    }

    public final void updateScore(final int score) {
        scoreLabel.setText(String.format("Score: %d", score));
        this.score = score;
    }

    private void updateTimer(final int elapsedSeconds) {
        int minutes = elapsedSeconds / 60;
        int seconds = elapsedSeconds % 60;

        timerLabel.setText(String.format("Elapsed Time: %d:%02d", minutes, seconds));
        this.elapsedSeconds = elapsedSeconds;
    }

    private void onTimerTick(ActionEvent e) {
        elapsedSeconds++;
        updateTimer(elapsedSeconds);
    }

    @SuppressWarnings(value = {"unused"})
    private void onPause(ActionEvent e) {
        gameTimer.stop();

        PauseMenuDialogue pauseMenuDialogue = new PauseMenuDialogue(
                (GameFrame) getTopLevelAncestor(),
                this::onResume
        );
        pauseMenuDialogue.setVisible(true);
    }

    private void onResume() {
        gameTimer.start();
    }

    private JLabel createStatusLabel() {
        JLabel label = new JLabel();
        label.setFont(new Font(GameFrame.BODY_TYPEFACE, Font.BOLD, 16));
        label.setForeground(new Color(70, 0, 50));
        label.setBorder(new RoundedBorder(Color.BLACK, Color.WHITE, 10));

        return label;
    }
    
}
