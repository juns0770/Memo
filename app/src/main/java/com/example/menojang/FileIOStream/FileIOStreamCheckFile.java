package com.example.menojang.FileIOStream;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileIOStreamCheckFile {
    AppCompatActivity aFileIOStreamCheckFile;

    // 생성자에서 Activity를 인자값으로 받아와 Activity변수에 저장
    public FileIOStreamCheckFile(AppCompatActivity fragmentActivity) { // 파일 OS 스트림 체크 파일
        aFileIOStreamCheckFile = fragmentActivity;
    }

    public void checkFile(String fileName) {
        String sFilePath = aFileIOStreamCheckFile.getFilesDir().getAbsolutePath() + "/memo/";   // 파일 경로 확인
        File fFile = new File(sFilePath + fileName);    // 파일  객체생성하고 파일경로 + 파일이름
        System.out.println("path : " + fFile.toString());        // 잘 되었는지 확인

        if(!fFile.exists()) {   // 파일이 존재하지 않으면
            try{
                FileOutputStream fosDataFile = new FileOutputStream(fFile, false); // 파일 생성
                fosDataFile.close();
                System.out.println("텍스트파일 생성완료");   // 파일이 생성됬는지 출력

            }catch(IOException e) {
                e.printStackTrace();

            }
        }

        if(fFile.exists()){
            System.out.println("텍스트파일 있음");     // 파일이 존재하면 파일 있다고 출력
        }else {
            System.out.println("텍스트파일 없음");     // 파일이 존재하지 않으면 파일이 없다고 출력
        }
    }
}
