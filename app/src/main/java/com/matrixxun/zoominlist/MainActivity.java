package com.matrixxun.zoominlist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.matrixxun.starry.PullToZoomListView;

public class MainActivity extends AppCompatActivity {
    private PullToZoomListView listView;
    private String[] adapterData;
    public int last_index;
    public int total_index;
    public boolean isLoading = false;//表示是否正处于加载状态
    public View loadmoreView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (PullToZoomListView)findViewById(R.id.list_view);
        adapterData = new String[] { "Activity","Service","Content Provider","Intent","BroadcastReceiver",
                "ADT","Sqlite3","HttpClient","DDMS","Android Studio","Fragment","Loader",
                "ADT","Sqlite3","HttpClient","DDMS","Android Studio","Fragment","Loader"};

        listView.setAdapter(new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_list_item_1, adapterData));
        listView.getHeaderView().setImageResource(R.drawable.splash);
        listView.getHeaderView().setScaleType(ImageView.ScaleType.CENTER_CROP);
        loadmoreView=LayoutInflater.from(this).inflate(R.layout.foot_layout,null);
        listView.addFooterView(loadmoreView);
//        listView.setShadow(R.drawable.shadow_bottom);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"click index:"+position,Toast.LENGTH_SHORT).show();
            }
        });
        View  view=LayoutInflater.from(this).inflate(R.layout.head_layout,null);
        listView.setHeaderContent(view);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                                         @Override
                                         public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                                             last_index = firstVisibleItem+visibleItemCount;
                                             total_index = totalItemCount;
                                             System.out.println("last:  "+last_index);
                                             System.out.println("total:  "+total_index);
                                         }

                                         @Override
                                         public void onScrollStateChanged(AbsListView view, int scrollState) {
                                             if(last_index == total_index && (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE))
                                             {
                                                 //表示此时需要显示刷新视图界面进行新数据的加载(要等滑动停止)
                                                 if(!isLoading)
                                                 {
                                                     //不处于加载状态的话对其进行加载
                                                     isLoading = true;
                                                     //设置刷新界面可见
                                                     loadmoreView.setVisibility(View.VISIBLE);
                                                     loadmoreView.postDelayed(new Runnable() {
                                                         @Override
                                                         public void run() {
                                                             loadmoreView.setVisibility(View.GONE);
//                                                             listView.removeFooterView(loadmoreView);
                                                         }
                                                     },4000);
                                                 }
                                             }
                                         }
                                     }
        );
    }

}
