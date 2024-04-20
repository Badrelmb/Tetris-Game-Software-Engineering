package Final.src.main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener{

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
    
    
    private Random random= new Random();

    private Color[] colors = {Color.CYAN, Color.MAGENTA, Color.ORANGE,Color.BLUE,
            Color.GREEN,Color.RED,Color.YELLOW};
    private Shape[] shapes = new Shape[7];

    private Shape currentShape, nextShape;
    private int deletedLineCount = 0;
    private int score = 0;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    public final int WIDTH = 303;
    public final int HEIGHT = 606;


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
        }, this, colors[0]);

        shapes[1] = new Shape(new int[][]{
                {1, 1, 1},
                {0, 1, 0}, // T shape;
        }, this, colors[1]);

        shapes[2] = new Shape(new int[][]{
                {1, 1, 1},
                {1, 0, 0}, // L shape;
        }, this, colors[2]);

        shapes[3] = new Shape(new int[][]{
                {1, 1, 1},
                {0, 0, 1}, // J shape;
        }, this, colors[3]);

        shapes[4] = new Shape(new int[][]{
                {0, 1, 1},
                {1, 1, 0}, // S shape;
        }, this, colors[4]);

        shapes[5] = new Shape(new int[][]{
                {1, 1, 0},
                {0, 1, 1}, // Z shape;
        }, this, colors[5]);

        shapes[6] = new Shape(new int[][]{
                {1, 1},
                {1, 1}, // O shape;
        }, this, colors[6]);

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
        nextShape = new Shape(shapes[index].getCoords(), this, colors[index]);
    
            
    }
    public void setCurrentShape() {
        currentShape = nextShape;        
        setNextShape();

        for (int row = 0; row < currentShape.getCoords().length; row++) {
            for (int col = 0; col < currentShape.getCoords()[0].length; col++) {
                if (currentShape.getCoords()[row][col] != 0) { // 블럭이 존재하고
                    if (board[currentShape.getY() + row][currentShape.getX() + col] != null) { // 행을 초과하면
                        state = STATE_GAME_OVER;
                    }
                }
            }
        }
        setRandomBlockToWhite();
    }
    
    


    // 키 버튼 이벤트
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    
    public void keyPressed(KeyEvent e) {
        if (state == STATE_GAME_PLAY) {
            // 게임 플레이 중일 때의 키 입력 처리
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                currentShape.setDeltaX(1);
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                currentShape.setDeltaX(-1);
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                currentShape.moveDown();
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                currentShape.rotateShape();
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                currentShape.dropDown();
            } else if (e.getKeyCode() == KeyEvent.VK_P) {
                state = STATE_GAME_PAUSE;
            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0); // ESC 키로 게임 종료
            }
        } else if (state == STATE_GAME_OVER) {
            // 게임 오버 상태일 때의 키 입력 처리
            if (e.getKeyCode() == KeyEvent.VK_P) {
                // 게임 재시작
                startGame();
            } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                System.exit(0); // ESC 키로 게임 종료
            }
        } else if (state == STATE_GAME_PAUSE) {
            // 게임 일시 정지 상태일 때의 키 입력 처리
            if (e.getKeyCode() == KeyEvent.VK_P) {
                state = STATE_GAME_PLAY; // 다시 게임 플레이 상태로 변경
            }
        
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
        List<Integer> fullLines = new ArrayList<>();
        
        // 꽉 찬 줄을 찾아서 삭제할 줄로 표시합니다.
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            boolean isFull = true;
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (board[row][col] != null && board[row][col].equals(Color.WHITE)) {
                    // board 배열의 해당 행의 모든 열을 null로 초기화
                    for (int c = 0; c < board[row].length; c++) {
                        board[row][c] = null;
                    }
                }
                
                if (board[row][col] == null) {
                    isFull = false;
                    break;
                }
            }
            if (isFull) {
                fullLines.add(row);
            }
        }
    
        // 각 꽉 찬 줄에 대한 애니메이션을 적용하고 처리합니다.
        if (!fullLines.isEmpty()) {
            // 줄 삭제 및 deletedLineCount 증가
            animateLineDeletion(fullLines);
            deletedLineCount += fullLines.size();
        }
    }

    private void setRandomBlockToWhite() {
        if (currentShape != null && deletedLineCount >= 3) { // 현재 떨어지고 있는 블록이 있고, 삭제된 줄 수가 3 이상인 경우
            int[][] coords = currentShape.getCoords();
            Random random = new Random();
            boolean found = false;
    
            // 랜덤하게 흰색 블록을 설정합니다.
            while (!found) {
                int row = random.nextInt(coords.length);
                int col = random.nextInt(coords[row].length);
    
                if (coords[row][col] != 0) { // 블록이 존재하는 경우
                    coords[row][col] = 2; // 흰색 블록으로 설정
                    found = true;
                }
            }
    
            // 다시 렌더링하여 변경 사항을 적용합니다.
            repaint();
        }
    }
    
    
    private void animateLineDeletion(List<Integer> fullLines) {
        Timer timer = new Timer(100, new ActionListener() {
            int flashCount = 0;
            boolean isVisible = true;
    
            @Override
            public void actionPerformed(ActionEvent e) {
                if (flashCount < 3) { // 3번 깜빡이도록 설정합니다.
                    // 꽉 찬 줄을 깜빡이는 효과를 줍니다.
                    for (Integer line : fullLines) {
                        for (int col = 0; col < BOARD_WIDTH; col++) {
                            if (isVisible) {
                                board[line][col] = Color.WHITE; // 흰색으로 깜빡입니다.
                            } else {
                                board[line][col] = null; // 다시 비웁니다.
                            }
                        }
                    }
                    isVisible = !isVisible;
                    repaint(); // 화면을 갱신합니다.
                    flashCount++;
                } else {
                    // 꽉 찬 줄을 지우고, 남은 블록들을 아래로 내립니다.
                    clearAndDropLines(fullLines);
                    ((Timer) e.getSource()).stop(); // 타이머를 중지합니다.
                }
            }
        });
    
        timer.start();
    }
    
    private void clearAndDropLines(List<Integer> fullLines) {
        // 꽉 찬 줄을 삭제합니다.
        for (Integer line : fullLines) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                board[line][col] = null;
            }
        }
    
        // 삭제된 줄 위의 모든 블록을 한 번에 아래로 이동시킵니다.
        for (int row = fullLines.get(0) - 1; row >= 0; row--) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (board[row][col] != null) {
                    // 블록을 최대한 아래로 이동시킵니다.
                    int fallDistance = fullLines.size();
                    while (row + fallDistance < BOARD_HEIGHT && board[row + fallDistance][col] == null) {
                        board[row + fallDistance][col] = board[row][col];
                        board[row][col] = null;
                        fallDistance++;
                    }
                }
            }
        }
    
        // 점수 업데이트 등 게임 로직 처리를 수행합니다.
        score += fullLines.size() * 100; // 삭제된 줄 수에 따라 점수를 추가합니다.
        checkLine();
        deletedLineCount %= 3;
        repaint(); // 화면을 다시 그립니다.
    }
    
    
    


    class GameLooper implements ActionListener {
        private long lastSpeedIncreaseTime = System.currentTimeMillis();

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!(state ==STATE_GAME_PAUSE)) {
                update();
                repaint();
                 // deletedLineCount가 3 이상이면 nextShape 업데이트
                


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
                System.out.println("삭제된 줄의 수 : "+deletedLineCount);

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
