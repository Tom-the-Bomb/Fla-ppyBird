package org.joshtommy;

import javax.swing.*;

class FlappyBird {
    private static void initWindow() {
        // create a window frame and set the title in the toolbar
        JFrame window = new JFrame("FlappyBird");
        // when we close the window, stop the app
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create the JPanel to draw on.
        // this also initializes the game loop
        Frame frame = new Frame();
        // add the JPanel to the window
        window.add(frame);
        // pass keyboard inputs to the JPanel
        window.addKeyListener(frame);

        // don't allow the user to resize the window
        window.setResizable(false);
        // fit the window size around the components (just our JPanel).
        // pack() should be called after setResizable() to avoid issues on some platforms
        window.pack();
        // open window in the center of the screen
        window.setLocationRelativeTo(null);
        // display the window
        window.setVisible(true);
    }

    public static void main(String[] args) {
        // invokeLater() is used here to prevent our graphics processing from
        // blocking the GUI. https://stackoverflow.com/a/22534931/4655368
        // this is a lot of boilerplate code that you shouldn't be too concerned about.
        // just know that when main runs it will call initWindow() once.
        SwingUtilities.invokeLater(FlappyBird::initWindow);
    }
}
