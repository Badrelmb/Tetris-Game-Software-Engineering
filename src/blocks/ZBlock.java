package blocks;

import java.awt.*;

public class ZBlock extends Mino {
    public ZBlock(){
        create(Color.RED);
    }

    public void setXY(int x, int y) {
        b[0].x=x;
        b[0].y=y;
        b[1].x= b[0].x + Block.SIZE;
        b[1].y= b[0].y;
        b[2].x= b[0].x + Block.SIZE;
        b[2].y= b[0].y - Block.SIZE;
        b[3].x= b[0].x;
        b[3].y= b[0].y + Block.SIZE;

    }
}
