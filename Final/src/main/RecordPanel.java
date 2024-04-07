package Final.src.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.io.*;




public class RecordPanel extends JFrame {


    RecordPanel(){
    super("Record");
    JPanel RecordPanel = new JPanel();

    RecordPanel.setBackground(Color.black);
    setSize(300, 200);    
    add(RecordPanel);

//이름 입력받는 문구
JLabel insertName = new JLabel();
			 insertName.setText("Insert Name");
             insertName.setForeground(Color.RED);
             insertName.setFont(new Font("고딕체", Font.BOLD, 20));
             insertName.setSize(120,20);
             insertName.setLocation(90,0 );
             insertName.setVisible(true);
             this.add(insertName);


//이름을 입력
     JTextField recordScore = new JTextField(); 
     recordScore.setBounds(90, 70, 120, 20);
     add(recordScore);


//Ok버튼을 누르면 점수를 받음
    JButton OKbtn = new JButton("OK"); 
    OKbtn.setBounds(70,110,60,20);
    this.add(OKbtn);  
		OKbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                String Name = recordScore.getText();
                // new RecordPanel(); 점수를 저장받음
            
                String csvFilePath = "/Users/kim__yun_h/Downloads/tetris_EndingWindow/src/main/ranking.csv";
                try{
                    BufferedWriter bw = new BufferedWriter(new FileWriter(csvFilePath, true));
                    bw.newLine();
                    bw.write(Name + "," + 1234);
                    bw.close(); 
                   }
                  catch (IOException a) {a.printStackTrace();}        

                setVisible(false);
            
     } });		
	
//취소버튼
	JButton NObtn = new JButton("Cancel");
    NObtn.setBounds(160,110,60,20);
    this.add(NObtn);
		NObtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                new EndPanel();
                setVisible(false);
            }
        });

        setLayout(null);


        
// @Override
// public void actionPerformed(ActionEvent e) {
//     if(e.getSource() == OKbtn) {
//         try {
          
//         }

        Dimension frameSize = getSize();
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();

        setLocation((windowSize.width - frameSize.width) / 2,
                (windowSize.height - frameSize.height) / 2); //화면 중앙에 띄우기
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);}}










			
