import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class SnakeGame extends JPanel implements ActionListener, KeyListener{
    private class Block{
        int x;
        int y;
        Block (int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    int frameWidth;
    int frameHeight;
    int blockSize = 25;

    Block snakeSize;
    ArrayList<Block>snakeBody;

    Block food;
    Random r;

    int velX;
    int velY;
    Timer gameLoop;

    boolean gameOver = false;

    SnakeGame(int frameWidth, int frameHeight){
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        setPreferredSize(new Dimension(this.frameWidth, this.frameHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);
        snakeSize = new Block(5, 5);
        snakeBody = new ArrayList<Block>();

        food = new Block(10,10);
        r = new Random();
        placeFood();

        velX = 1;
        velY = 1;

        gameLoop = new Timer (100, this);
        gameLoop.start();
    }

    public void paintComponent (Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw (Graphics g){

        for(int i = 0; i < frameWidth/blockSize; i++) {
            //(x1, y1, x2, y2)
            g.drawLine(i * blockSize, 0, i * blockSize, frameHeight);
            g.drawLine(0, i * blockSize, frameWidth, i * blockSize);
    }

        g.setColor(Color.red);
        g.fill3DRect(food.x * blockSize, food.y * blockSize, blockSize, blockSize, true);

        g.setColor(Color.cyan);
        g.fill3DRect(snakeSize.x * blockSize, snakeSize.y * blockSize, blockSize, blockSize, true);

        for (int i = 0; i < snakeBody.size(); i++) {    //snake body
        Block snakePart = snakeBody.get(i);
        g.fill3DRect(snakePart.x * blockSize, snakePart.y * blockSize, blockSize, blockSize, true);
    }

        g.setFont(new Font("Arial", Font.PLAIN, 24));   //score
        if (gameOver) {
        g.setColor(Color.red);
        g.drawString("Game Over: " + String.valueOf(snakeBody.size()), blockSize - 16, blockSize);
    }
        else {
        g.drawString("Score: " + String.valueOf(snakeBody.size()), blockSize - 16, blockSize);
    }
}

    public void placeFood(){
        food.x = r.nextInt(frameWidth/blockSize);
        food.y = r.nextInt(frameHeight/blockSize);
    }

    public void move() {
        //eat food
        if (collision(snakeSize, food)) {
            snakeBody.add(new Block(food.x, food.y));
            placeFood();
        }

        //move snake body
        for (int i = snakeBody.size()-1; i >= 0; i--) {
            Block snakePart = snakeBody.get(i);
            if (i == 0) { //right before the head
                snakePart.x = snakeSize.x;
                snakePart.y = snakeSize.y;
            }
            else {
                Block prevSnakePart = snakeBody.get(i-1);
                snakePart.x = prevSnakePart.x;
                snakePart.y = prevSnakePart.y;
            }
        }
        //move snake head
        snakeSize.x += velX;
        snakeSize.y += velY;

        //game over conditions
        for (int i = 0; i < snakeBody.size(); i++) {
            Block snakePart = snakeBody.get(i);

            //collide with snake head
            if (collision(snakeSize, snakePart)) {
                gameOver = true;
            }
        }

        if (snakeSize.x * blockSize < 0 || snakeSize.x * blockSize > frameWidth || //passed left border or right border
                snakeSize.y * blockSize < 0 || snakeSize.y * blockSize > frameHeight ) { //passed top border or bottom border
            gameOver = true;
        }
    }

    public boolean collision(Block tile1, Block tile2) {
        return tile1.x == tile2.x && tile1.y == tile2.y;
    }

    @Override
    public void actionPerformed(ActionEvent e) { //called every x milliseconds by gameLoop timer
        move();
        repaint();
        if (gameOver) {
            gameLoop.stop();
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        // System.out.println("KeyEvent: " + e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_UP && velY != 1) {
            velX = 0;
            velY = -1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN && velY != -1) {
            velX = 0;
            velY = 1;
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT && velX != 1) {
            velX = -1;
            velY = 0;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velX != -1) {
            velX = 1;
            velY = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

}