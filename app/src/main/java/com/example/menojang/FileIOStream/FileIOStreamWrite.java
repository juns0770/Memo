package com.example.menojang.FileIOStream;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileIOStreamWrite {
    AppCompatActivity aFileIOStreamWrite;
    FileIOStreamRead cFileIOStreamRead;

    // 생성자에서 Activity를 인자값으로 받아와 Activity변수에 저장
    public FileIOStreamWrite(AppCompatActivity fragmentActivity) {  // 파일 IO 스트림 쓰기
        aFileIOStreamWrite = fragmentActivity;
        cFileIOStreamRead = new FileIOStreamRead(aFileIOStreamWrite);   // 파일쓰기 객체생성

    }

    /**
     * 파일에 텍스트 쓰기
     */
    public void writeData(String fileName, String writeData) {
        String sDataFilePath = aFileIOStreamWrite.getFilesDir().getAbsolutePath() + "/memo/";   // 파일 경로 확인
        File fDataFile = new File(sDataFilePath + fileName);                           // 파일 데이터 객체생성
        System.out.println("fileName : " + fileName);                                           // 파일 이름출력
        System.out.println("path : " + fDataFile.toString());                                   // 파일 경로 출력
        System.out.println("WriteData : " + writeData);                                         // 파일 쓰기 출력

        if(fDataFile.exists()) {    // 파일 데이터가 존재하면
            try{
                FileOutputStream fosDataFile = new FileOutputStream(fDataFile, false);   // 파일 추가
                fosDataFile.write(writeData.getBytes());                                        // 파일 내용 가져오기
                fosDataFile.close();
                System.out.println("파일 쓰기 성공");                                             // 파일 쓰기 성공 출력
                cFileIOStreamRead.readData(fileName);
            }catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
