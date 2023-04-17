package com.example.menojang;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.menojang.FileIOStream.FileIOStreamCheckDir;
import com.example.menojang.FileIOStream.FileIOStreamCheckFile;
import com.example.menojang.FileIOStream.FileIOStreamRead;
import com.example.menojang.FileIOStream.FileIOStreamWrite;

public class MainActivity extends AppCompatActivity {

    private long backBtnTime = 0;
    ListView listview;

    FileIOStreamCheckDir cFileIOStreamCheckDir;
    FileIOStreamCheckFile cFileIOStreamCheckFile;
    FileIOStreamWrite cFileIOStreamWrite;
    FileIOStreamRead cFileIOStreamRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 액션바 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        cFileIOStreamRead = new FileIOStreamRead(this);
        cFileIOStreamCheckDir = new FileIOStreamCheckDir(this);
        cFileIOStreamCheckDir.checkDir();   // 폴더 생성

        cFileIOStreamCheckFile = new FileIOStreamCheckFile(this);
        cFileIOStreamCheckFile.checkFile("title");      // 메모들의 제목을 저장할 파일
        cFileIOStreamCheckFile.checkFile("time");       // 메모들의 작성 시간(수정 시간)을 저장할 파일
        cFileIOStreamCheckFile.checkFile("memo");       // 메모들의 내용을 저장할 파일

        System.out.println("제목 파일 : " + cFileIOStreamRead.readData("title"));

        // 싱글톤 초기화
        Define.ins().listFileTitle = cFileIOStreamRead.readData("title");
        Define.ins().listFileTime = cFileIOStreamRead.readData("time");
        Define.ins().listFileMemo = cFileIOStreamRead.readData("memo");

        // split 싱글톤 초기화
        String[] splitTitle = Define.ins().listFileTitle.split("Φ");
        String[] splitTime = Define.ins().listFileTime.split("¤");
        String[] splitMemo = Define.ins().listFileMemo.split("Ψ");

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listView);
        listview.setAdapter(Define.ins().adapter);

        // 리스트뷰 아이템 클릭 이벤트
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                OnClickHandler(view, position); // 다이얼로그 팝업 함수 호출
            }
        });

        // 중복 추가를 막기 위해 리스트 초기화
        Define.ins().adapter.listViewItemList.clear();
        if (!splitTitle[0].equals("")) {    // 제목이 있다면
            for (int i = 0; i < splitTitle.length; i++) {
                System.out.println("splitTitle : " + splitTitle[i]);
                System.out.println("splitTime[ : " + splitTime[i]);
                // 리스트에 값을 전달
                Define.ins().adapter.addItem(splitTitle[i], splitTime[i], splitMemo[i]);
                Define.ins().adapter.notifyDataSetChanged();    // 리스트 갱신
            }
        }

        Button btnAddMemo = (Button) findViewById(R.id.btnAddMemo);
        // 메모 추가 버튼 이벤트
        btnAddMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Define.ins().chek = 1;      // 추가 버튼을 눌렀다는 것을 판단하기 위해 1을 변수에 저장
                // MemoActivity 이동
                Intent intent = new Intent(MainActivity.this, MemoActivity.class);
                startActivity(intent);
                finish();   // 현재 액티비티 종료
            }
        });
    }

    public void OnClickHandler(View view, int pos) {
        cFileIOStreamWrite = new FileIOStreamWrite(MainActivity.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("삭제 / 수정 / 취소").setMessage("선택하세요.");

        // 맨 오른쪽에 위치 && 삭제 버튼을 눌렀을 때
        builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // 싱글톤을 변수에 복사
                String deleteTitle = Define.ins().listFileTitle;
                String deleteTime = Define.ins().listFileTime;
                String deleteMemo = Define.ins().listFileMemo;

                System.out.println("deleteTitle1 : " + deleteTitle);
                System.out.println("deleteTime1 : " + deleteTime);

                // 삭제할 데이터를 찾아 바꿔준다.
                deleteTitle = deleteTitle.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getTitle() + "Φ", "");
                deleteTime = deleteTime.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getTime() + "¤", "");
                deleteMemo = deleteMemo.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getMemo() + "Ψ", "");

                System.out.println("deleteTitle2 : " + deleteTitle);
                System.out.println("deleteTime2 : " + deleteTime);

                // 바꾼 데이터를 싱글톤에 다시 저장함
                Define.ins().listFileTitle = deleteTitle;
                Define.ins().listFileTime = deleteTime;
                Define.ins().listFileMemo = deleteMemo;

                // 바뀐 싱글톤을 파일에 저장한다.
                cFileIOStreamWrite.writeData("title", Define.ins().listFileTitle);
                cFileIOStreamWrite.writeData("time", Define.ins().listFileTime);
                cFileIOStreamWrite.writeData("memo", Define.ins().listFileMemo);

                // 중복 추가를 방지하기 위해 리스트 초기화
                Define.ins().adapter.listViewItemList.clear();

                // 리스트에 추가하기 위해 split 변수에 싱글톤을 split한다.
                String[] splitTitle = Define.ins().listFileTitle.split("Φ");
                String[] splitTime = Define.ins().listFileTime.split("¤");
                String[] splitMemo = Define.ins().listFileMemo.split("Ψ");

                // 제목이 있다면
                if (!splitTitle[0].equals("")) {
                    for (int i = 0; i < splitTitle.length; i++) {
                        System.out.println("아이템 추가 확인 1 : " + splitTitle[i]);
                        System.out.println("아이템 추가 확인 2 : " + splitTime[i]);
                        System.out.println("아이템 추가 확인 3 : " + splitMemo[i]);
                        // 어댑터에 값 전달
                        Define.ins().adapter.addItem(splitTitle[i], splitTime[i], splitMemo[i]);
                    }
                }
                // 리스트 갱신
                Define.ins().adapter.notifyDataSetChanged();
            }
        });

        // 맨 오른쪽에서 왼쪽 위치 && 수정 버튼을 눌렀을 때
        builder.setNegativeButton("수정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Define.ins().chek = 2;      // 수정 버튼을 눌렀다는 것을 판단하기 위해 2를 변수에 저장

                // MemoActivity 이동
                Intent intent = new Intent(MainActivity.this, MemoActivity.class);
                intent.putExtra("pos", pos);    // 이동할 액티비티에 선택한 메모의 위치 전달
                startActivity(intent);
            }
        });

        // 맨 왼쪽에 위치 && 취소 버튼을 눌렀을 때
        builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                // 아무작업을 하지 않음
            }
        });
        // 설정한 다이얼로그를 생성 후 팝업함
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    // 뒤로가기 이벤트
    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        // 1초 이내에 2번 누를 경우 해당 액티비티 종료 그렇지 않을 경우 토스트 메세지만 띄운다.
        if (0 <= gapTime && 1000 >= gapTime) {
            finish();
        } else {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}