package com.example.menojang;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    public ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        final Context context = viewGroup.getContext();
        if (convertView == null) {  // 뷰가 null일 경우
            // 뷰를 설정한다.
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, viewGroup, false);
        }

        // id 연결
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
        TextView timeTextView = (TextView) convertView.findViewById(R.id.textView2) ;

        ListViewItem listViewItem = listViewItemList.get(i);

        // 리스트 아이템(텍스트뷰)에 알맞는 값을 설정
        titleTextView.setText(listViewItem.getTitle());
        timeTextView.setText(listViewItem.getTime());

        return convertView;
    }

    // 리스트에 값을 편하게 추가하기 위한 클래스
    public void addItem(String title, String time, String memo){
        ListViewItem item = new ListViewItem(); // 임의의 자료형 변수 생성

        // 전달 받은 값을 변수에 저장
        item.setName(title);
        item.setTime(time);
        item.setMemo(memo);

        // 저장한 값들을 모든 값을 담는 리스트에 추가
        listViewItemList.add(item);
    }
}
