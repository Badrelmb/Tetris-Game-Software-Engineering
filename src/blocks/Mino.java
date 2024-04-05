package blocks;
import java.awt.*;
public class Mino{
    public Block b[] = new Block[4];
    public void create(Color c){
        b[0] = new Block(c);
        b[1] = new Block(c);
        b[2] = new Block(c);
        b[3] = new Block(c);
    }

    public void setXY(int x, int y){}
    public void draw(Graphics2D g2){
        String text = "O";
        Font font = new Font("Arial", Font.BOLD, Block.SIZE*7/5);
        g2.setFont(font);
        g2.setColor(b[0].c);

        g2.drawString(text,b[0].x,b[0].y);
        g2.drawString(text,b[1].x,b[1].y);
        g2.drawString(text,b[2].x,b[2].y);
        g2.drawString(text,b[3].x,b[3].y);


    }
}
