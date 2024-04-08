package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener{

    private static final long serialVersionUID = 1L;

    private static int delay = 300;

    public static int STATE_GAME_PLAY = 0;
    public static int STATE_GAME_PAUSE = 1;
    public static int STATE_GAME_OVER = 2;

    private int state = STATE_GAME_PLAY;

    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;
    public static final int BLOCK_SIZE = 30;

    private Timer looper;
    private Color[][] board = new Color[BOARD_HEIGHT][BOARD_WIDTH];

    private int deletedLine;

    private Random random= new Random();

    Color SKYBLUE = new Color(86, 180, 232);
    Color VERMILION = new Color(213, 94, 0);
    Color BLUISHGREEN = new Color(0, 159, 115);
    Color REDDISHPURPLE = new Color(204, 121, 167);
    Color ORANGE = new Color(229, 159, 0);
    Color YELLOW = new Color(240, 228, 66);
    Color BLUE = new Color(0, 114, 187);

    private Color[][] colors = {{Color.CYAN, Color.MAGENTA, Color.ORANGE,Color.BLUE,
            Color.GREEN,Color.RED,Color.YELLOW}, {BLUISHGREEN, REDDISHPURPLE, ORANGE, SKYBLUE,
                BLUE, VERMILION, YELLOW}};

    private Shape[] shapes = new Shape[7];

    private Shape currentShape, nextShape;

    private int score = 0;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    public final int WIDTH = 303;
    public final int HEIGHT = 606;

    public static int colorblind = 1;

    public Board()
    {
        this.setPreferredSize(new Dimension(800, 720));
        this.setBackground(Color.black);
        this.setLayout(null);

        looper = new Timer(delay, new GameLooper());
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);

        shapes[0] = new Shape(new int[][]{
                {1, 1, 1, 1} // I shape;
        }, this, colors[colorblind][0]);

        shapes[1] = new Shape(new int[][]{
                {1, 1, 1},
                {0, 1, 0}, // T shape;
        }, this, colors[colorblind][1]);

        shapes[2] = new Shape(new int[][]{
                {1, 1, 1},
                {1, 0, 0}, // L shape;
        }, this, colors[colorblind][2]);

        shapes[3] = new Shape(new int[][]{
                {1, 1, 1},
                {0, 0, 1}, // J shape;
        }, this, colors[colorblind][3]);

        shapes[4] = new Shape(new int[][]{
                {0, 1, 1},
                {1, 1, 0}, // S shape;
        }, this, colors[colorblind][4]);

        shapes[5] = new Shape(new int[][]{
                {1, 1, 0},
                {0, 1, 1}, // Z shape;
        }, this, colors[colorblind][5]);

        shapes[6] = new Shape(new int[][]{
                {1, 1},
                {1, 1}, // O shape;
        }, this, colors[colorblind][6]);

        left_x = (WindowGame.WIDTH/2)-(WIDTH/2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;
        startGame();
    }

    private void update()
    {
        if(state == STATE_GAME_PLAY) {
            currentShape.update();
        }
        return;
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Play area Frame
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x-4,top_y-2,WIDTH,HEIGHT);


        // Mino frame
        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x, y, 200, 200);

        // Font rendering
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawString("NEXT", x + 60, y + 60);

        //쌓여있는모습
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] != null) {
                    String text = "O";
                    Font font = new Font("Arial", Font.BOLD, BLOCK_SIZE*7/5);
                    g.setFont(font);
                    g.setColor(board[row][col]);
                    g.drawString(text,col*BLOCK_SIZE+66, row*BLOCK_SIZE+80);
                }

            }
        }
        if (nextShape != null) {
            g.setColor(nextShape.getColor());
            for (int row = 0; row < nextShape.getCoords().length; row++) {
                for (int col = 0; col < nextShape.getCoords()[0].length; col++) {
                    if (nextShape.getCoords()[row][col] != 0) {
                        String text = "O";
                        Font font = new Font("Arial", Font.BOLD, BLOCK_SIZE*7/5);
                        g.setFont(font);
                        g.setColor(board[row][col]);
                        g.drawString(text,col * 30 + 530, row * 30 + 580);
                    }
                }
            }
        }
        if (currentShape != null) {
            currentShape.render(g);
        }


        // draw the board

        g.setColor(Color.white);


        //score frame
        g2.drawRect(x,top_y,250,300);
        x+=40;
        y=top_y+90;
        Font font = new Font("Arial", Font.BOLD, BLOCK_SIZE);
        g.setFont(font);
        g2.drawString("SCORE: "+ score,x,y);



        if(state == STATE_GAME_OVER) {
            g.setColor(Color.white);
            g.drawString("GAME OVER", 300, 300);
            setVisible(false);
            new EndPanel(score);
        }
        if(state == STATE_GAME_PAUSE) {
            g.setColor(Color.white);
            g.drawString("GAME PAUSE", 350, 200);
        }
    }
    public Color[][] getBoard(){
        return board;
    }

    public void setNextShape() {
        int index = random.nextInt(shapes.length);
        nextShape = new Shape(shapes[index].getCoords(), this, colors[colorblind][index]);
    }
    public void setCurrentShape() {
        currentShape = nextShape;
        setNextShape();
        for (int row = 0; row < currentShape.getCoords().length; row++) {
            for (int col = 0; col < currentShape.getCoords()[0].length; col++) {
                if (currentShape.getCoords()[row][col] != 0) {
                    if (board[currentShape.getY() + row][currentShape.getX() + col] != null) {
                        state = STATE_GAME_OVER;
                    }
                }
            }
        }
    }


    // 키 버튼 이벤트
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // 오른쪽,왼쪽,아래,회전,떨어뜨리기, 일시중지, 게임종료
        if(state== STATE_GAME_PLAY) {
            if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
                currentShape.setDeltaX(1);
            }
            else if(e.getKeyCode()==KeyEvent.VK_LEFT) {
                currentShape.setDeltaX(-1);
            }
            else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                currentShape.moveDown(); // VK_DOWN 키를 눌렀을 때 블록을 한 칸 아래로 이동
            }
            else if(e.getKeyCode()==KeyEvent.VK_UP) {
                currentShape.rotateShape();
            }
            else if(e.getKeyCode()==KeyEvent.VK_SPACE) {
                currentShape.dropDown();
            }
            else if(e.getKeyCode() == KeyEvent.VK_P) {
                state = STATE_GAME_PAUSE;
            }
            else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                // 게임 종료
                System.exit(0);
            }
        }
        // 게임 리플레이
        else if(state == STATE_GAME_OVER) {
            if(e.getKeyCode() == KeyEvent.VK_P) {
                for(int row =0; row<board.length;row++) {
                    for(int col=0; col<board[row].length; col++) {
                        board[row][col]=null;
                    }
                }
            }
            setCurrentShape();
            state = STATE_GAME_PLAY;
        }
        else if(state == STATE_GAME_PAUSE) {
            if(e.getKeyCode() == KeyEvent.VK_P) {
                state = STATE_GAME_PLAY;}
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    public void startGame() {
        stopGame();
        setNextShape();
        setCurrentShape();
        state = STATE_GAME_PLAY;
        looper.start();

    }
    public void stopGame() {
        score = 0;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                board[row][col] = null;
            }
        }
        looper.stop();
    }
    // 줄 삭제 알고리즘
    public void checkLine() {
        int deletedLines = 0; // 삭제된 줄 수를 저장할 변수
        int bottomLine = board.length - 1;
        for (int topLine = board.length - 1; topLine > 0; topLine--) {
            int count = 0;
            for (int col = 0; col < board[0].length; col++) {
                if (board[topLine][col] != null) {
                    count++;
                }
                board[bottomLine][col] = board[topLine][col];
            }
            if (count < board[0].length) {
                bottomLine--;
            } else {
                addScore();
                deletedLines++; // 삭제된 줄이 있으면 deletedLines를 증가시킴
            }
        }
        // 삭제된 줄의 수를 누적
        deletedLine += deletedLines;
    }



    class GameLooper implements ActionListener {
        private long lastSpeedIncreaseTime = System.currentTimeMillis();

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!(state ==STATE_GAME_PAUSE)) {
                update();
                repaint();

                // 시간이 1분 경과했을 때 속도 증가
                long currentTime = System.currentTimeMillis();

                if (currentTime - lastSpeedIncreaseTime >= 60000) {
                    addBonusScore();
                    delay -= 20; // 예를 들어, 50ms 간격으로 속도를 빠르게 설정
                    if (delay <=100) { // 100ms 이하로 감소하지 않도록 보정
                        delay = 100;
                    }
                    lastSpeedIncreaseTime = currentTime;
                }
                System.out.println("속도가 빨라지기까지의 시간 : "+(60 - ((currentTime-lastSpeedIncreaseTime)/1000) ) +"초");

                looper.setDelay(delay);
                System.out.println("딜레이 : "+delay);
                System.out.println("삭제된 줄의 수 : "+deletedLine);

                if(deletedLine>=5) {
                    delay -= 20;
                    addBonusScore();
                    if (delay <=100) { // 100ms 이하로 감소하지 않도록 보정
                        delay = 100;
                    }
                    deletedLine %=5;
                }
            }

        }
    }

    public void addScore() {
        score +=2 ;

    }
    public void addBonusScore() {
        score += 200;
    }

}
