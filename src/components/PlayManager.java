package components;
import blocks.*;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class PlayManager extends JPanel {
    public final int WIDTH = 360;
    public final int HEIGHT = 600;

    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    public static int score;


    private Mino pickMino(){
        Mino mino = null;
        int i = new Random().nextInt(7);
        switch (i){
            case 0 : mino = new IBlock();break;
            case 1 : mino = new JBlock();break;
            case 2 : mino = new OBlock();break;
            case 3 : mino = new SBlock();break;
            case 4 : mino = new TBlock();break;
            case 5 : mino = new LBlock();break;
            case 6 : mino = new ZBlock();break;
        }
        return mino;
    }



    public PlayManager(){
        left_x = (gamePannel.WIDTH/2)-(WIDTH/2);
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;

        Block_start_x = left_x+(WIDTH/2)-Block.SIZE;
        Block_start_y=top_y+Block.SIZE*2;

        next_mino_x= right_x+175;
        next_mino_y=top_y+530;

        currentBlock = pickMino();
        currentBlock.setXY(Block_start_x,Block_start_y);

        nextBlock = pickMino();
        nextBlock.setXY(next_mino_x,next_mino_y);


    }
    Mino currentBlock;
    Mino nextBlock;
    final int Block_start_x;
    final int Block_start_y;
    final int next_mino_x;
    final int next_mino_y;




    public void draw(Graphics2D g2){

        // Play area Frame
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x-4,top_y-4,WIDTH+8,HEIGHT+8);


        // Mino frame
        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x, y, 200, 200);

        // Font rendering
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawString("NEXT", x + 60, y + 60);

        //score frame
        g2.drawRect(x,top_y,250,300);
        x+=40;
        y=top_y+90;
        g2.drawString("SCORE: "+ score,x,y);

        if(currentBlock!=null){
            currentBlock.draw(g2);
        }
        if(nextBlock!=null){
            nextBlock.draw(g2);
        }
    }
}


