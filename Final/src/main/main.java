package Final.src.main;

import javax.swing.JFrame;

public class main{

    public static void main(String[] args){
        JFrame window = new JFrame("Simple Tetris"); //윈도우 띄우기
        window.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE)); 
        window.setResizable(false); //윈도우 크기를 이용자가 조절하지 못함


        //게임 판넬을 윈도우에 추가함
        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack(); //판넬의 크기가 윈도우 사이즈와 같다
     
        window.setLocationRelativeTo(null);
        window.setVisible(true); //윈도우가 화면에 보이도록 함

        
    }


}