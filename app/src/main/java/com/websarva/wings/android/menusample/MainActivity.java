package com.websarva.wings.android.menusample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

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

        registerForContextMenu(_lvMenu);
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

    private List<Map<String,Object>> createCurryList() {
        //カレーメニューリスト用のListオブジェクトを用意
        List<Map<String, Object>> menuList = new ArrayList<>();
        //「ビーフカレー」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録
        Map<String, Object> menu = new HashMap<>();
        menu.put("name", "ビーフカレー");
        menu.put("price", "520");
        menu.put("desc", "特選スパイスをきかせた国産ビーフ100％のカレーです。");
        menuList.add(menu);
        //「ポークカレー」のデータを格納するMapオブジェクトの用意とmenuListへのデータ登録
        menu = new HashMap<>();
        menu.put("name", "ポークカレー");
        menu.put("price", "420");
        menu.put("desc", "特選スパイスをきかせた国産ポーク100％のカレーです");
        menuList.add(menu);

        return menuList;
    }

    //リストがタップされたときの処理が記述されたメンバクラス
    private class ListItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            //タップされた行のデータを取得。SimpleAdapterでは1行分のMap型
            Map<String,Object> item=(Map<String,Object>)parent.getItemAtPosition(position);
            //注文処理
            order(item);
        }
    }

    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        //メニューインフレーターの取得
        MenuInflater inflater =getMenuInflater();
        //オプションメニュー用.xmlファイルをインフレート
        inflater.inflate(R.menu.menu_options_menu_list,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //戻り値用の変数を初期値trueで用意
        boolean returnVal =true;
        //選択されたメニューのIDを取得
        int itemId=item.getItemId();
        //IDのR値による処理の分岐
        if(itemId==R.id.menuListOptionTeishoku) {
            //定食メニューが選択された場合の処理
            // 定食メニューリストデータの生成
            _menuList = createTeishokuList();
        }else if(itemId==R.id.menuListOptionCurry){
            //カレーメニューが選択された場合の処理
            //カレーメニューリストデータの生成
            _menuList = createCurryList();
        }
        else{
            //親クラスの同名メソッドを呼び出し、その戻り値をreturnValとする
            returnVal=super.onOptionsItemSelected(item);
        }

        //SimpleAdapterを選択されたメニューデータで生成
        SimpleAdapter adapter=new SimpleAdapter(MainActivity.this,_menuList,R.layout.row,FROM,TO);
        //アダプタの登録
        _lvMenu.setAdapter(adapter);
        return returnVal;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu,View view,ContextMenu.ContextMenuInfo menuInfo){
        //親クラスの同名メソッドの呼び出し
        super.onCreateContextMenu(menu,view,menuInfo);
        //メニューインフレーターの取得
        MenuInflater inflater=getMenuInflater();
        //コンテキストメニュー用.xmlファイルをインフレート
        inflater.inflate(R.menu.menu_context_menu_list,menu);
        //コンテキストメニューのヘッダタイトルを設定
        menu.setHeaderTitle(R.string.menu_list_context_header);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        //戻り値用の変数を初期値trueで用意
        boolean returnVal =true;
        //長押しされたビューに関する情報が格納されたオブジェクトを取得
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        //長押しされたリストのポジションを取得
        int listPosition = info.position;
        //ポジションから長押しされたメニュー情報Mapオブジェクトを取得
        Map<String,Object> menu=_menuList.get(listPosition);

        //選択されたメニューIDを取得
        int itemId=item.getItemId();
        //IDのR値による処理の分岐
        if(itemId==R.id.menuListContextDesc) {
            //説明を表示メニューが選択されたときの処理
            //メニューの説明文字を取得
            String desc=(String) menu.get("desc");
            //トーストを表示
            Toast.makeText(MainActivity.this,desc,Toast.LENGTH_LONG).show();
        }else if(itemId==R.id.menuListContextOrder){
            //ご注文メニューが選択されたときの処理
            //注文処理
            order(menu);
        }
        else{
            //親クラスの同名メソッドを呼び出し、その戻り値をreturnValとする
            returnVal=super.onOptionsItemSelected(item);
        }

        //SimpleAdapterを選択されたメニューデータで生成
        SimpleAdapter adapter=new SimpleAdapter(MainActivity.this,_menuList,R.layout.row,FROM,TO);
        //アダプタの登録
        _lvMenu.setAdapter(adapter);
        return returnVal;
    }


    private void order(Map<String,Object> menu){
        //定食名と金額を取得。Mapの値部分がObject型なのでキャストが必要
        String menuName=(String) menu.get("name");
        String menuPrice=(String) menu.get("price");
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