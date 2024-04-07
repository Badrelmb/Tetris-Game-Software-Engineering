package Final.src.main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JPanel; //게임 판넬 크기

public class GamePanel extends JPanel implements Runnable{

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS =60;
    Thread gameThread;

    public GamePanel() {

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.black);
        this.setLayout(null);

  
        JButton btn1 = new JButton("game over");//버튼 생성
        btn1.setSize(300, 200);
        this.add(btn1);
        

        btn1.addActionListener(new ActionListener(){ //버튼을 누르면 게임오버
            @Override
            public void actionPerformed(ActionEvent e){
                new EndPanel();
                setVisible(false);
            }
        });

    }

    public void launchGame(){//게임 시작
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override//게임 시작하게 해주는 메소드
    public void run(){

        //게임 루프는 업데이트와 드로우로 구성이 됨
        double drawInterbal = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null){ //게임이 실행되는 동안 업데이트와 드로우가 반복됨
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) /drawInterbal;
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
            }

        }


        

    }
 

    private void update() { //점수정보 업데이트

        // if(KeyHandler.pausePressed == false && pm.gameOver == false){
        //     pm.update();
       // }
    }

    public void paintComponent(Graphics g){ //UI텍스트 등

        super.paintComponent(g);
    }

    
    
}
