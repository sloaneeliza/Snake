/*
    Sloane Wright
    September 24, 2023
 */

import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int frameWidth = 700;
        int frameHeight = 700;

        JFrame frame = new JFrame("Snake");
        frame.setVisible(true);
        frame.setSize(frameWidth, frameHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame sg = new SnakeGame(frameWidth, frameHeight);
        frame.add(sg);
        frame.pack();
        sg.requestFocus();
    }
}