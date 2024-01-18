package com.websarva.wings.android.menusample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    //リストビューを表すフィールド
    private ListView _lvMenu;
    //リストビューに表示するリストデータ
    private List<Map<String,Object>> _menuList;
    //SimpleAdapterの第４引数fromに使用する定数フィールド
    private static final String[] FROM={"name","price"};
    //SimpleAdapterの第５引数toに使用する定数フィールド
    private static final int[] TO={R.id.tvMenuNameRow,R.id.tvMenuPriceRow};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //画面部品ListView取得し、フィールドに格納
        _lvMenu=findViewById(R.id.lvMenu);
        //定食メニューListオブジェクトをprivateメソッドを利用して用意し、フィールドに格納
        _menuList=createTeishokuList();
        //SimpleAdapterを生成
        SimpleAdapter adapter=new SimpleAdapter(MainActivity.this, _menuList,R.layout.row,FROM,TO);
        //アダプタの登録
        _lvMenu.setAdapter(adapter);
        //リストタップのリスナクラス登録
        _lvMenu.setOnItemClickListener(new ListItemClickListener());
    }

    private List<Map<String,Object>> createTeishokuList() {
        //定食メニューリスト用のListオブジェクトを用意
        List<Map<String, Object>> menuList = new ArrayList<>();
        //「から揚げ定食」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録
        Map<String, Object> menu = new HashMap<>();
        menu.put("name", "から揚げ定食");
        menu.put("price", "800");
        menu.put("desc", "若鶏のから揚げにサラダ、ご飯とお味噌汁が付きます。");
        menuList.add(menu);
        //「ハンバーグ定食」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録
        menu = new HashMap<>();
        menu.put("name", "ハンバーグ定食");
        menu.put("price", "850");
        menu.put("desc", "手ごねハンバーグにサラダ、ご飯とお味噌汁が付きます。");
        menuList.add(menu);

        return menuList;
    }

    //リストがタップされたときの処理が記述されたメンバクラス
    private class ListItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            //タップされた行のデータを取得。SimpleAdapterでは1行分のMap型
            Map<String,Object> item=(Map<String,Object>)parent.getItemAtPosition(position);
            //定食名と金額を取得。Mapの値部分がObject型なのでキャストが必要
            String menuName=(String) item.get("name");
            int menuPrice=(Integer) item.get("price");
            /*Integer integer = Integer.valueOf(item.get("price"));*/
            //インテントオブジェクトを生成
            Intent intent=new Intent(MainActivity.this,MenuThanksActivity.class);
            //第2画面に送るデータを格納
            intent.putExtra("menuName",menuName);
            //MenuThanksActivityでのデータ受け取りと合わせるために、金額にここで「円」を追加する
            intent.putExtra("menuPrice", menuPrice +"円" );
            //第2画面の起動
            startActivity(intent);
        }
    }
}