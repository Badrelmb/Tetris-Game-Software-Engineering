package Final.src.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.*;

public class ReadCsv {
    public static void main(String[] args) {
        ReadCsv csvReader = new ReadCsv();
        csvReader.readCSV();
    }

    public List<List<String>> readCSV() {
        List<List<String>> csvList = new ArrayList<List<String>>();
        File csv = new File("src/main/ranking.csv");
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null) { //한 줄의 데이터를 읽기
                List<String> aLine = new ArrayList<String>();
                String[] lineArr = line.split(","); //  ,로 나누어 배열에 저장
                aLine = Arrays.asList(lineArr);
                csvList.add(aLine);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) { 
                    br.close(); 
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return csvList;
    }
}

