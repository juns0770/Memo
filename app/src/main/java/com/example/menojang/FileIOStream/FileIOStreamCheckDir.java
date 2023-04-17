package com.example.menojang.FileIOStream;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class FileIOStreamCheckDir {
    AppCompatActivity aFileIOStreamCheckDir;

    // 생성자에서 Activity를 인자값으로 받아와 Activity변수에 저장
    // 파일 OS 스트림 체크디렉토리
    public FileIOStreamCheckDir(AppCompatActivity fragmentActivity) {
        aFileIOStreamCheckDir = fragmentActivity;
    }

    /**
     *  파일입출력을 위한 기본 폴더 여부 확인 기본 폴더가 없을 경우 생성한다.
     */
    public void checkDir() {
        // 파일을 생성하는 경로
        String sDirPath = aFileIOStreamCheckDir.getFilesDir().getAbsolutePath() + "/memo/";
        File fFile = new File(sDirPath);        // 파일 객체생성

        // 파일이 존재하지 않으면 파일을 만든다
        if(!fFile.exists()) {
            fFile.mkdirs();
            // 파일이 존재하는지 체크
            System.out.println("기본폴더생성");
        }

        // 파일이 존재하면 파일이 있다고 출력
        if(fFile.exists()) {
            System.out.println("폴더있음");

        }

    }

}
