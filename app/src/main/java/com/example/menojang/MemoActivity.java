package com.example.menojang;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.menojang.FileIOStream.FileIOStreamCheckDir;
import com.example.menojang.FileIOStream.FileIOStreamCheckFile;
import com.example.menojang.FileIOStream.FileIOStreamRead;
import com.example.menojang.FileIOStream.FileIOStreamWrite;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MemoActivity extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    private long backBtnTime = 0;
    int pos = 0;

    FileIOStreamWrite cFileIOStreamWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo);

        // 액션바 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // id 연결
        editText1 = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);

        // 수정 버튼을 눌러서 왔을 때
        if(Define.ins().chek == 2){
            if(!(getIntent().getIntExtra("pos",-1) == -1)){ // 위치 값이 정상적일 때
                int pos1 = getIntent().getExtras().getInt("pos");   // 변수에 받은 위치 값 저장
                pos = pos1; // 전역변수에 저장
                System.out.println("pos : " + pos);
                System.out.println("listViewItemlist1 = " + Define.ins().adapter.listViewItemList.get(pos1).getTitle());
                System.out.println("listViewItemlist2 = " + Define.ins().adapter.listViewItemList.get(pos1).getMemo());

                // 해당 메모에 저장되어 있는 메모 데이터를 불러와 setText해준다.
                editText1.setText(Define.ins().adapter.listViewItemList.get(pos1).getTitle());
                editText2.setText(Define.ins().adapter.listViewItemList.get(pos1).getMemo());
            }
        }

        // 특수문자, 영문대문자 제한(split할 때 에러 방지)
        InputFilter filterKoEnNum2 = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if(source.equals("")){ // for backspace
                    return source;
                }
                // []안의 글자만 입력 가능
                if(source.toString().matches("^[a-z0-9ㄱ-ㅎㅏ-ㅣ가-힣 ]*$")){
                    return source;
                }
                else {
                    Log.e("TAG", "특수문자 및 영문대문자는 입력하실 수 없습니다.");
                    return "";
                }
            }
        };

        // 메모 제목, 메모 내용에 필터내용을 적용한다.
        editText1.setFilters(new InputFilter[] { filterKoEnNum2 });
        editText2.setFilters(new InputFilter[] { filterKoEnNum2 });
    }

    @Override
    public void onBackPressed(){
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;

        // 1초 내에 2번 누를 경우 저장 후 MainActivity로 이동 그렇지 않을 경우 토스트 메세지 팝업
        if(0 <= gapTime && 1000 >= gapTime) {
            System.out.println("Define.ins().chek : " + Define.ins().chek);

            // 추가 버튼을 통해 왔을 경우
            if(Define.ins().chek == 1){
                if(editText1.getText().toString().equals("")){  // 제목이 없을 경우
                    if(editText2.getText().toString().equals("")){  // 내용이 없을 경우
                        // 싱글톤에 특수문자와 함께 메모 제목, 메모 내용, 시간들을 저장한다.
                        // 값이 없다면 split이 정상적으로 안되므로 공백을 추가함
                        Define.ins().listFileTitle += " Φ";
                        Define.ins().listFileTime += getTime() + "¤";
                        Define.ins().listFileMemo += " Ψ";
                    }
                    else{   // 내용이 있을 경우
                        Define.ins().listFileTitle += " Φ";
                        Define.ins().listFileTime += getTime() + "¤";
                        Define.ins().listFileMemo += editText2.getText().toString() + "Ψ";
                    }
                }
                // 내용만 없을 경우
                else if(editText2.getText().toString().equals("")){
                    Define.ins().listFileTitle += editText1.getText().toString() + "Φ";
                    Define.ins().listFileTime += getTime() + "¤";
                    Define.ins().listFileMemo += " Ψ";
                }
                else{
                    Define.ins().listFileTitle += editText1.getText().toString() + "Φ";
                    Define.ins().listFileTime += getTime() + "¤";
                    Define.ins().listFileMemo += editText2.getText().toString() + "Ψ";
                }
            }
            // 수정 버튼을 통해 왔을 경우
            if(Define.ins().chek == 2){
                // 싱글톤을 변수에 저장
                String setTitle = Define.ins().listFileTitle;
                String setTime = Define.ins().listFileTime;
                String setMemo = Define.ins().listFileMemo;

                System.out.println("전자 : " + setMemo);

                if(editText1.getText().toString().equals("")){  // 값이 없다면 split이 정상적으로 안되므로 공백을 추가
                    if(editText2.getText().toString().equals("")){
                        // 수정할 데이터를 리스트에서 선택한 메모데이터을 비교하여 해당 데이터를 바꾼 데이터로 수정해준다.
                        setTitle = setTitle.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getTitle() + "Φ", " Φ");
                        setTime = setTime.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getTime() + "¤", getTime() + "¤");
                        setMemo = setMemo.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getMemo() + "Ψ", " Ψ");
                    }
                    else{
                        setTitle = setTitle.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getTitle() + "Φ", " Φ");
                        setTime = setTime.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getTime() + "¤", getTime() + "¤");
                        setMemo = setMemo.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getMemo() + "Ψ", editText2.getText().toString() + "Ψ");
                    }
                }
                else if(editText2.getText().toString().equals("")){
                    setTitle = setTitle.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getTitle() + "Φ", editText1.getText().toString() + "Φ");
                    setTime = setTime.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getTime() + "¤", getTime() + "¤");
                    setMemo = setMemo.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getMemo() + "Ψ", " Ψ");
                }
                else{
                    setTitle = setTitle.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getTitle() + "Φ", editText1.getText().toString() + "Φ");
                    setTime = setTime.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getTime() + "¤", getTime() + "¤");
                    setMemo = setMemo.replaceFirst(Define.ins().adapter.listViewItemList.get(pos).getMemo() + "Ψ", editText2.getText().toString() + "Ψ");
                }

                System.out.println("후자 : " + setMemo);

                // 수정한 데이터를 싱글톤에 저장
                Define.ins().listFileTitle = setTitle;
                Define.ins().listFileTime = setTime;
                Define.ins().listFileMemo = setMemo;
            }

            cFileIOStreamWrite = new FileIOStreamWrite(this);
            // 바뀐 싱글톤을 파일에 저장
            cFileIOStreamWrite.writeData("title", Define.ins().listFileTitle);
            cFileIOStreamWrite.writeData("time", Define.ins().listFileTime);
            cFileIOStreamWrite.writeData("memo", Define.ins().listFileMemo);

            // MainActivity로 이동
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();   // 현재 액티비티를 종료
        }
        else {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
        }
    }

    // 함수를 호출한 시간을 시스템에서 가져옴
    public static String getTime(){
        Long mNow;
        Date mDate;
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); // 시간 형식을 정해줌
        mNow = System.currentTimeMillis();  // 시스템 시간을 변수에 저장
        mDate = new Date(mNow); // 익숙한 시간 형식으로 바꿈
        return mFormat.format(mDate);   // 정해준 시간 형식으로 시간을 바꿔서 리턴해준다.
    }
}
