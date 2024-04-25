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
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.io.*;




public class RecordPanel extends JFrame {

    private static int playerRank = 0;

    static int GetRank()
    {
        return playerRank;
    }

    RecordPanel(int score){
    super("Record");
    JPanel RecordPanel = new JPanel();

    getContentPane().setBackground(Color.BLACK);
    setSize(300, 200);    
    add(RecordPanel);

//이름 입력받는 문구
    JLabel insertName = new JLabel();
	insertName.setText("Insert Name");
    insertName.setForeground(Color.white);
    insertName.setFont(new Font("고딕체", Font.BOLD, 20));
    insertName.setSize(150,20);
    insertName.setLocation(90,0 );
    insertName.setVisible(true);
    this.add(insertName);


//이름을 입력
    JTextField recordScore = new JTextField(); 
    recordScore.setBounds(90, 70, 120, 20);
    add(recordScore);


//Ok버튼을 누르면 점수를 받음
//추가 : 새로운 Ranker가 입력한 이름과 점수를 합쳐서 1등~10등까지 CSV 출력
    JButton OKbtn = new JButton("OK"); 
    OKbtn.setBounds(70,110,60,20);
    OKbtn.setForeground(Color.WHITE);
    OKbtn.setBackground(Color.DARK_GRAY);

    this.add(OKbtn); 
	OKbtn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
            String Name = recordScore.getText();
            ReadCsv csvReader = new ReadCsv();
            List<List<String>> ScoreList = csvReader.readCSV();
            String csvFilePath = "Final/src/main/ranking.csv";
            try{
                for(int Rank = 10; Rank > 0; Rank--)
                {
                    if(Rank == 1 || score < Integer.parseInt((ScoreList.get(Rank - 1)).get(1)))
                    {
                        playerRank = Rank;
                        int i = 0;
                        BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath, false));
                        while(i < Rank)
                        {
                            bw.write(ScoreList.get(i).get(0) + "," + ScoreList.get(i).get(1));
                            bw.newLine();
                            i++;
                        }
                        bw.write(Name + "," + score);
                        while(i < 10)
                        {
                            bw.newLine();
                            bw.write(ScoreList.get(i).get(0) + "," + ScoreList.get(i).get(1));
                            i++;
                        }
                        bw.close(); 
                        break;
                    }
                }
               }
            catch (IOException a) {a.printStackTrace();}        

            setVisible(false);
            
     } });		
	
//취소버튼
	JButton NObtn = new JButton("Cancel");
    NObtn.setBounds(160,110,60,20);
    NObtn.setForeground(Color.WHITE);
    NObtn.setBackground(Color.DARK_GRAY);

    this.add(NObtn);


		NObtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                setVisible(false);
            }
        });

        setLayout(null);


        Dimension frameSize = getSize();
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();

        setLocation((windowSize.width - frameSize.width) / 2,
                (windowSize.height - frameSize.height) / 2); //화면 중앙에 띄우기
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);}}
