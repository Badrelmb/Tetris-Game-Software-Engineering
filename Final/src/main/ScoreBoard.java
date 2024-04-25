package Final.src.main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.util.*;

import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class ScoreBoard extends JFrame{

    ScoreBoard(){
        super("ScoreBoard");

        JPanel ScorePanel = new JPanel();
        getContentPane().setBackground(Color.BLACK);
        this.setLayout(null);
        this.setSize(1280, 720);
        add(ScorePanel);
        

         //스코어보드 문구
        JLabel Title = new JLabel();
		Title.setText("SCORE BOARD");
        Title.setForeground(Color.white);
        Title.setFont(new Font("고딕체", Font.BOLD, 72));
        Title.setSize(1280,100);
        Title.setLocation(350,0 );
        Title.setVisible(true);
        this.add(Title);

        try{
        ReadCsv csvReader = new ReadCsv();
        List<List<String>> ScoreList = csvReader.readCSV();

        for(int i = 1; i <= 10; i++)
            {
                JLabel Scores = new JLabel();
                Scores.setText(ScoreList.get(i).get(0) + "  " + ScoreList.get(i).get(1));
                if(i == RecordPanel.GetRank()) // 플레이어가 Rank 등록 시 빨간색으로
                    Scores.setForeground(Color.red);
                else
                    Scores.setForeground(Color.white);
                Scores.setFont(new Font("고딕체", Font.BOLD, 30));
                Scores.setSize(250, 40);
                Scores.setLocation(540, (80+i*50));
                Scores.setVisible(true);
                this.add(Scores);
            }
        }
        catch (ArrayIndexOutOfBoundsException a) {a.printStackTrace();}   
        

        //그만두기 버튼
       JButton QuitButton = new JButton("Quit");
       QuitButton.setSize(150, 70);
       QuitButton.setLocation(280,580 );
       QuitButton.setForeground(Color.WHITE);
       QuitButton.setBackground(Color.DARK_GRAY);
       this.add(QuitButton);
       QuitButton.setVisible(true);

       QuitButton.addActionListener(new ActionListener(){ //버튼을 누르면 종료
        @Override
        public void actionPerformed(ActionEvent e){
            setVisible(false);
            dispose();
        }
         });

    
        Dimension frameSize = getSize();
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();

        setLocation((windowSize.width - frameSize.width) / 2,
                (windowSize.height - frameSize.height) / 2); //화면 중앙에 띄우기
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
