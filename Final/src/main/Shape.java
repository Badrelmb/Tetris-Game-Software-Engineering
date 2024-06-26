package Final.src.main;

import java.awt.*;

public class Shape {

    private Board board;
    private Color color;

    private int x ,y;
    private int normal = 600;
    private long time, lastTime;

    private int delay;

    private int[][] coords;
    private int[][] reference;

    private int deltaX;


    private boolean collision = false, moveX = true;

    private int timePassedFromCollision = -1;

    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;
    public static final int BLOCK_SIZE = 30;

    public Shape(int[][] coords,Board board,Color color) { // 블록 모양, 보드선택, 색깔
        this.coords = coords;
        this.board = board;
        this.color = color;
        deltaX = 0;
        x = 4;
        y = 0;
        delay = normal;
        time = 0;
        lastTime = System.currentTimeMillis();
        reference = new int[coords.length][coords[0].length];

        System.arraycopy(coords, 0, reference, 0, coords.length);
    }

    long deltaTime;

    public void update() {
        int i = 0;
        for (int col = 0; col < coords[0].length; col++) {
            if (coords[0][col] == 4)
                i++;
        }
        if (i == 0) // Heavy Block의 경우 부딪히면 움직이지 못함
            moveX = true;
        deltaTime = System.currentTimeMillis() - lastTime;
        time += deltaTime;
        lastTime = System.currentTimeMillis();
        // 블록을 쌓으면서 줄 삭제 확인
        if(collision && timePassedFromCollision > 300) {
            // 보드에 블록 쌓기
            for (int row = 0; row < coords.length; row++) {
                for (int col = 0; col < coords[0].length; col++) {
                    if (coords[row][col] == 1) {
                        board.getBoard()[y + row][x + col] = color;
                    }
                    else if (coords[row][col] == 2) {
                        board.getBoard()[y + row][x + col] = Color.WHITE;
                    }
                    else if(coords[row][col]==3){
                        board.getBoard()[y + row][x + col] = Color.GRAY;
                    }                    
                    else if (coords[row][col] == 4) {
                        board.getBoard()[y + row][x + col] = null;
                    }
                }
            }
            // 줄 삭제 확인
            board.checkLine();

            // 새로운 모양 생성
            board.setCurrentShape();
            timePassedFromCollision = -1;
        }


        // x축 움직임
        if (!(x + deltaX + coords[0].length > 10) && !(x + deltaX < 0)) {

            for (int row = 0; row < coords.length; row++) {
                for (int col = 0; col < coords[row].length; col++) {
                    if (coords[row][col] != 0) {
                        if (board.getBoard()[y + row][x + deltaX + col] != null) {
                            moveX = false;
                        }

                    }
                }
            }

            if (moveX) {
                x += deltaX;
            }

        }
        //y축 움직임
        if (timePassedFromCollision == -1) {
            if (!(y + 1 + coords.length > 20)) {

                for (int row = 0; row < coords.length; row++) {
                    for (int col = 0; col < coords[row].length; col++) {
                        if (coords[row][col] != 0) {
                            if (coords[row][col] == 4) {
                                if (board.getBoard()[y + 1 + row][x + col] != null)
                                    moveX = false;
                                board.getBoard()[y + 1 + row][x + col] = null;
                            }
                            else {
                                if (board.getBoard()[y + 1 + row][x + col] != null) {
                                    collision();
                                }
                            }
 
                        }
                    }
                }
                if (time > delay) {
                    y++;
                    board.addScore();
                    time = 0;
                }
            } else {
                collision();
            }
        } else {
            timePassedFromCollision += deltaTime;
        }

        deltaX = 0;
    }

    private void collision() {
        collision = true;
        timePassedFromCollision = 0;
    }


    public void moveDown() {

        if (!(y + 1 + coords.length > 20)) {
            for (int row = 0; row < coords.length; row++) {
                for (int col = 0; col < coords[row].length; col++) {
                    if (coords[row][col] != 0) {
                        if (board.getBoard()[y + 1 + row][x + col] != null) {
                            collision();
                            return;
                        }
                    }
                }
            }
            y++;
        } else {
            collision();
        }

    }

    public void dropDown() {

        while (!(y + 1 + coords.length > 20)) {
            for (int row = 0; row < coords.length; row++) {
                for (int col = 0; col < coords[row].length; col++) {
                    if (coords[row][col] != 0) {
                        if (board.getBoard()[y + 1 + row][x + col] != null) {
                            collision();
                            return;
                        }
                    }
                }
            }
            y++;
        }
        collision();

    }






    public void rotateShape() {

        int[][] rotatedShape = null;

        rotatedShape = transposeMatrix(coords);

        rotatedShape = reverseRows(rotatedShape);

        if ((x + rotatedShape[0].length > 10) || (y + rotatedShape.length > 20)) {
            return;
        }

        for (int row = 0; row < rotatedShape.length; row++) {
            for (int col = 0; col < rotatedShape[row].length; col++) {
                if (rotatedShape[row][col] != 0) {
                    if (board.getBoard()[y + row][x + col] != null) {
                        return;
                    }
                }
            }
        }
        coords = rotatedShape;
    }

    private int[][] transposeMatrix(int[][] matrix) {
        int[][] temp = new int[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                temp[j][i] = matrix[i][j];
            }
        }
        return temp;
    }

    private int[][] reverseRows(int[][] matrix) {

        int middle = matrix.length / 2;

        for (int i = 0; i < middle; i++) {
            int[] temp = matrix[i];

            matrix[i] = matrix[matrix.length - i - 1];
            matrix[matrix.length - i - 1] = temp;
        }

        return matrix;

    }
    
    
    public void render(Graphics g) {
        for (int row = 0; row < coords.length; row++) {
            for (int col = 0; col < coords[0].length; col++) {
                if (coords[row][col] != 0) {
                    Color color;
                    Color itemColor_L;
                    Color itemColor_Bomb;
                    if (coords[row][col] == 2) { // L 아이템
                        itemColor_L = Color.white;
                        g.setColor(itemColor_L);
                        g.drawString("L", col * BLOCK_SIZE + x * BLOCK_SIZE + 66, row * BLOCK_SIZE + y * BLOCK_SIZE + 80);
                    } else if (coords[row][col] == 3) { // Bomb 아이템
                        itemColor_Bomb = Color.GRAY;
                        g.setColor(itemColor_Bomb);
                        g.drawString("B", col * BLOCK_SIZE + x * BLOCK_SIZE + 66, row * BLOCK_SIZE + y * BLOCK_SIZE + 80);
                    } else if (coords[row][col] == 4) { // 무게추 아이템
                        color = getColor();
                        g.setColor(color);
                        g.drawString("H", col * BLOCK_SIZE + x * BLOCK_SIZE + 66, row * BLOCK_SIZE + y * BLOCK_SIZE + 80);
                    } else {
                        color = getColor(); // Shape의 색상 사용
                        g.setColor(color);
                        g.drawString("O", col * BLOCK_SIZE + x * BLOCK_SIZE + 66, row * BLOCK_SIZE + y * BLOCK_SIZE + 80);
                    }
                    
                }
            }
        }
        
    }
    

    public void setCoords(int[][] Coords){
        this.coords = Coords;
    }
    public void setDeltaX(int deltaX) {
        this.deltaX = deltaX;
    }
    public int getY() {
        return y;
    }
    public int getX() {
        return x;
    }
    public Color getColor() {
        return color;
    }
    public int[][] getCoords(){
        return coords;
    }


}