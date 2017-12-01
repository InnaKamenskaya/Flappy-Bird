package flappyBird;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;


/**
 * Created by Kamenskaya on 20.05.2016.
 */
public class FlappyBird implements ActionListener, MouseListener, KeyListener {
    public static FlappyBird flappyBird;
    public final int HEIGHT = 400;
    public final int WIDTH = 400;
    public Renderer renderer;
    public Rectangle bird;
    public ArrayList<Rectangle> columns;
    public Random rand;
    public int ticks, yMotion, score;
    public boolean gameOver, started;


    public FlappyBird() {

        JFrame jFrame = new JFrame();
        Timer timer = new Timer(20, this);
        renderer = new Renderer();
        rand = new Random();
        columns = new ArrayList<>();

        jFrame.add(renderer);
        jFrame.setTitle("Flappy Bird");
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.setSize(WIDTH, HEIGHT);
        jFrame.addMouseListener(this);
        jFrame.addKeyListener(this);
        jFrame.setResizable(false);// неизменный размер окна
        jFrame.setVisible(true);


        bird = new Rectangle(WIDTH / 2 - 5, HEIGHT / 2 - 5, 10, 10);
        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);


        timer.start();
    }

    public void jump() {

        if (gameOver) {
            bird = new Rectangle(WIDTH / 2 - 5, HEIGHT / 2 - 5, 10, 10);
            columns.clear();
            yMotion = 0;
            score = 0;

            addColumn(true);
            addColumn(true);
            addColumn(true);
            addColumn(true);

            gameOver = false;
        }

        if (!started) {
            started = true;
        } else if (!gameOver) {
            if (yMotion > 0) {
                yMotion = 0;
            }

            yMotion -= 10;

        }
    }


    public void addColumn(boolean start) {

        int space = 150;
        int width = 50;
        int height = 25 + rand.nextInt(150);
        if (start) {
            columns.add(new Rectangle(WIDTH + width + columns.size() * 150, HEIGHT - height - 60, width, height));
            columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 150, 0, width, HEIGHT - height - space));
        } else {
            columns.add(new Rectangle(columns.get(columns.size() - 1).x + 300, HEIGHT - height - 60, width, height));
            columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width, HEIGHT - height - space));
        }
    }

    public void paintColumn(Graphics g, Rectangle column) {

        g.setColor(Color.green.darker());
        g.fillRect(column.x, column.y, column.width, column.height);
    }

    public void repaint(Graphics g) {

        g.setColor(Color.cyan);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.orange);
        g.fillRect(0, HEIGHT - 60, WIDTH, 60);

        g.setColor(Color.GREEN);
        g.fillRect(0, HEIGHT - 60, WIDTH, 10);

        g.setColor(Color.red);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);

        for (Rectangle column : columns) {
            paintColumn(g, column);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 50));

        if (!gameOver && started) {
            g.drawString(String.valueOf(score), WIDTH / 2 - 13, 50);
        }

        if (!started) {
            g.drawString("Click on start!", 35, HEIGHT / 2 - 25);
        }

        if (gameOver) {
            g.drawString("Game Over!", 50, HEIGHT / 2 - 25);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jump();
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        jump();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        int speed = 5;

        ticks++;

        if (started) {
            for (int i = 0; i < columns.size(); i++) {
                Rectangle column = columns.get(i);
                column.x -= speed;
            }

            if (ticks % 2 == 0 && yMotion < 15) {
                yMotion += 2;
            }

            for (int i = 0; i < columns.size(); i++) {
                Rectangle column = columns.get(i);

                if (column.x + column.width < 0) {
                    columns.remove(column);
                    if (column.y == 0) {
                        addColumn(false);
                    }
                }
            }

            bird.y += yMotion;

            for (Rectangle column : columns) {

                if (column.y == 0 && bird.x + bird.width / 2 > column.x + column.width / 2 - 3 && bird.x + bird.width / 2 < column.x + column.width / 2 + 3) {
                    score++;
                }

                if (column.intersects(bird)) {

                    gameOver = true;

                    if (bird.x <= column.x) {
                        bird.x = column.x - bird.width;
                    }
                    else {
                        if (column.y != 0) {
                            bird.y  = column.y - bird.height;
                        }
                        else if (bird.y < column.height){
                            bird.y  = column.height;
                        }
                    }
                }

                if (bird.y > HEIGHT || bird.y < 0) {
                    gameOver = true;
                }

                if (bird.y + yMotion >= HEIGHT - 60) {
                    bird.y = HEIGHT - 60 - bird.height;
                }
            }
        }

        renderer.repaint();
    }


    public static void main(String[] args) {
        flappyBird = new FlappyBird();

    }

}
