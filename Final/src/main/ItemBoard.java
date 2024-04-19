package Final.src.main;

import java.awt.*;
import java.util.Random;

public class ItemBoard extends Board{

    public static final int BOARD_WIDTH = 10;
    public static final int BOARD_HEIGHT = 20;
    public static final int BLOCK_SIZE = 30;

    private Random random= new Random();
    private Color[] colors = {Color.CYAN, Color.MAGENTA, Color.ORANGE,Color.BLUE,
            Color.GREEN,Color.RED,Color.YELLOW};
    private int deletedLine;   
    private Shape[] shapes = new Shape[7];
    
    public ItemBoard(){
        int index = random.nextInt(4);
              
}