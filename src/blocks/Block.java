package blocks;
import java.awt.*;

public class Block extends Rectangle{
    public int x,y;
    public Color c;
    public static final int SIZE = 30;

    public Block(Color c){
        this.c=c;
    }

}