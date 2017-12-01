package flappyBird;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Kamenskaya on 20.05.2016.
 */
public class Renderer extends JPanel{

    private static final long serialVersionUID = 1L;

    @Override
    protected void paintComponent(Graphics g){

        super.paintComponent(g);
        FlappyBird.flappyBird.repaint(g);
    }
}
