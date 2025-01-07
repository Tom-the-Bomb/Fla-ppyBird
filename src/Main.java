
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // create a window frame and set the title in the toolbar
        //
        // methods used referenced from: <https://docs.oracle.com/javase/8/docs/api/javax/swing/JFrame.html>
        //
        JFrame window = new JFrame("FlappiBird");
        // when we close the window, stop the app
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // create the JPanel to draw on.
        // this also initializes the game loop
        Panel frame = new Panel();
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
}
