package blocks;

import java.awt.*;

public class OBlock extends Mino {
    public OBlock(){
        create(Color.YELLOW);
    }

    public void setXY(int x, int y) {
        b[0].x=x;
        b[0].y=y;
        b[1].x= b[0].x + Block.SIZE;
        b[1].y= b[0].y;
        b[2].x= b[0].x;
        b[2].y= b[0].y + Block.SIZE;
        b[3].x= b[0].x + Block.SIZE;
        b[3].y= b[0].y + Block.SIZE;;

    }
}
