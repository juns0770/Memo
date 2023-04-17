package com.example.menojang.FileIOStream;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileIOStreamRead {
    AppCompatActivity aFileIOStreamRead;

    // 생성자에서 Activity를 인자값으로 받아와 Activity변수에 저장
    public FileIOStreamRead(AppCompatActivity fragmentActivity) {   // 파일 IO 스트림읽기
        aFileIOStreamRead = fragmentActivity;

    }

    /**
     * 파일에서 텍스트 읽기
     */
    public String readData(String sFileName) {
        String sDataFilePath = aFileIOStreamRead.getFilesDir().getAbsolutePath() + "/memo/";   // 파일 경로 확인
        File fData = new File(sDataFilePath + sFileName);                             // 파일 데이터 객체 생성
        String sReadData = "";                                                                 // 데이터를 읽을려고 문자열 선언, 파일 전체 내용
        String sReadDataTemp = "";                                                             // 데이터 단위를 1줄단위로 읽기 위해 문자열 선언, 파일 1줄내용

        try {
            FileInputStream fisData = new FileInputStream(fData);
            BufferedReader bisData = new BufferedReader(new InputStreamReader(fisData));

            while ((sReadDataTemp = bisData.readLine()) != null) {   // 파일내용이 1줄이 비어있지 않으면
                sReadData += sReadDataTemp;                          // 파일 전체 내용에 파일 1줄내용을 +해준다
            }

            System.out.println("sReadData : " + sReadData); // 읽어들인 문자열을 출력한다
            return sReadData;                               // 파일 전체내용을 리턴

        }catch(Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}