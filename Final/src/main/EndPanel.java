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


public class EndPanel extends JFrame{

    private int totalScore =0;

    EndPanel(int score){
        super("Game Over");
        JPanel endPanel = new JPanel();
        
        totalScore = score;

        //종료화면 생성
        getContentPane().setBackground(Color.BLACK);
        this.setLayout(null);
        this.setSize(1280, 720);
        add(endPanel);
        

         //게임오버 문구
        JLabel Title = new JLabel();
		Title.setText("GAME OVER");
        Title.setForeground(Color.white);
        Title.setFont(new Font("고딕체", Font.BOLD, 72));
        Title.setSize(1280,100);
        Title.setLocation(400,0 );
        Title.setVisible(true);
        this.add(Title);

            
        //점수 표시 문구
        JLabel YourScore = new JLabel();
		YourScore.setText("Your Score Is : " + totalScore); //점수를 받아올 것
        YourScore.setForeground(Color.white);
        YourScore.setFont(new Font("고딕체", Font.BOLD, 40));
        YourScore.setSize(1280,100);
        YourScore.setLocation(430,70);
        YourScore.setVisible(true);
        this.add(YourScore);


        //점수 기록 버튼
        try {
        ReadCsv csvReader = new ReadCsv();
        List<List<String>> now10thScore = csvReader.readCSV();
        if (totalScore >= Integer.parseInt((now10thScore.get(10)).get(1)))
        {
            JLabel URRanker = new JLabel();
            URRanker.setText("You are in the Top10");
            URRanker.setForeground(Color.white);
            URRanker.setFont(new Font("고딕체", Font.BOLD, 40));
            URRanker.setSize(1280, 100);
            URRanker.setLocation(430, 140);
            URRanker.setVisible(true);
            this.add(URRanker);

            JButton Record = new JButton("Record Your Score");
            Record.setSize(150, 70);
            Record.setLocation(540,480);
            Record.setForeground(Color.WHITE);
            Record.setBackground(Color.DARK_GRAY);
            this.add(Record);
            Record.addActionListener(new ActionListener(){ //버튼을 누르면 점수기록 화면
                @Override
                public void actionPerformed(ActionEvent e){
                    new RecordPanel(totalScore);
                }
            });
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
            System.exit(0);
        }
         });


        //시작메뉴로 돌아가기 버튼
       JButton MainButton = new JButton("Main");
       MainButton.setSize(150, 70);
       MainButton.setLocation(800,580 );
       MainButton.setForeground(Color.WHITE);
       MainButton.setBackground(Color.DARK_GRAY);
       this.add(MainButton);

       MainButton.addActionListener(new ActionListener(){ //버튼을 누르면 메인화면
        @Override
        public void actionPerformed(ActionEvent e){
            dispose();
            new MenuFrame();
        }
    });


        JButton ScoreBoardButton = new JButton("ScoreBoard");
        ScoreBoardButton.setSize(150, 70);
        ScoreBoardButton.setLocation(540,410);
        ScoreBoardButton.setForeground(Color.WHITE);
        ScoreBoardButton.setBackground(Color.DARK_GRAY);
        this.add(ScoreBoardButton);
        ScoreBoardButton.setVisible(true);

        ScoreBoardButton.addActionListener(new ActionListener(){ //버튼을 누르면 종료
            @Override
            public void actionPerformed(ActionEvent e){
                new ScoreBoard();
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