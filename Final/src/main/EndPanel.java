package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class EndPanel extends JFrame{

    EndPanel(){
        super("Game Over");
        JPanel endPanel = new JPanel();


        //종료화면 생성
        this.setBackground(Color.black);
        this.setLayout(null);
        this.setSize(1280, 720);
        add(endPanel);
        

         //게임오버 문구
        JLabel Title = new JLabel();
			 Title.setText("GAME OVER");
             Title.setForeground(Color.RED);
             Title.setFont(new Font("고딕체", Font.BOLD, 72));
             Title.setSize(1280,100);
             Title.setLocation(400,0 );
             Title.setVisible(true);
             this.add(Title);

            

        //점수 표시 문구
             JLabel YourScore = new JLabel();
			 YourScore.setText("Your Score Is : " + 1234); //점수를 받아올 것
             YourScore.setForeground(Color.RED);
             YourScore.setFont(new Font("고딕체", Font.BOLD, 40));
             YourScore.setSize(1280,100);
             YourScore.setLocation(430,70);
             YourScore.setVisible(true);
             this.add(YourScore);


        //점수 기록 버튼
        JButton Record = new JButton("Record Your Score");
        Record.setSize(150, 70);
        Record.setLocation(580,480);
        this.add(Record);
        Record.addActionListener(new ActionListener(){ //버튼을 누르면 점수기록 화면
            @Override
            public void actionPerformed(ActionEvent e){
                new RecordPanel();
            }
        });

        //역대 점수 표
        // String s= new String();//랭킹표 생성
        // String csvFilePath = "C:\\Users\\user\\Desktop\\tetris_EndingWindow\\src\\main\\ranking.csv";
        // try{
        //     BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
        //     String line;
        //     while ((line = br.readLine()) != null) {
        //         String[] data = line.split(",");
        //         String RankerName[] = data[0];
        //         String  RankerScore[] = data[1];}
        // } catch (IOException e) {
        //     e.printStackTrace();}


        //     for(int i=0; i<10 ;i ++){
        //         System.out.print(i+":"+ RankerName[i] +  );
                
        //     }


        // JLabel printRanking = new JLabel();
        // printRanking.setText(s);
        // printRanking.setForeground(Color.RED);
        // printRanking.setFont(new Font("고딕체", Font.BOLD, 10));
        // printRanking.setSize(1280,100);
        // printRanking.setLocation(430,70);
        // printRanking.setVisible(true);
        // this.add(printRanking);
        

        //그만두기 버튼
       JButton QuitButton = new JButton("Quit");
       QuitButton.setSize(150, 70);
       QuitButton.setLocation(280,580 );
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
       this.add(MainButton);
       MainButton.addActionListener(new ActionListener(){ //버튼을 누르면 메인화면
        @Override
        public void actionPerformed(ActionEvent e){
            new GamePanel();
            setVisible(false);
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
