package com.example.menojang;

public class Define {

    public String listFileTitle = "";    // 메모들의 제목을 모은 변수
    public String listFileTime = "";     // 메모들의 작성 시간(수정 시간)을 모은 변수
    public String listFileMemo = "";     // 메모들의 내용을 모은 변수
    public int chek = 0;                // 추가 = 1, 수정 = 2
    public ListViewAdapter adapter = new ListViewAdapter(); // 어댑터 객체 생성

    private static Define instance;
    public static Define ins() {
        if (instance == null) {
            instance = new Define();
        }
        return instance;
    }
}
