package com.example.intent02;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class IntentReceiveActivity extends Activity {

	//リストに表示する項目を保持する配列。（staticでないと毎回クリアされてしまう。）
	static ArrayAdapter<String> adapter;			
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        if (adapter == null){
        	adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        }
        
    	ListView list = (ListView)findViewById(R.id.list);
    	list.setAdapter(adapter);

    	processIntent(getIntent());
    }
	
	private void processIntent(Intent intent) {
    	
    	if (Intent.ACTION_VIEW.equals(intent.getAction()) ){
    		
			String url = intent.getDataString();
			
    		//追加済みでなければ、リスト表示用の配列に追加
			if (findUrl(url) < 0){
				adapter.insert(url, 0);				
			}
			
			//標準ブラウザで開く
			intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
			startActivity(intent);
    	}

		//TextViewに件数を表示
		TextView txt = (TextView)findViewById(R.id.txtCount);
    	if (adapter.getCount() > 0){
    		txt.setText("Count: " + adapter.getCount());
    	} else {
    		txt.setText(R.string.initial_msg);
    	}
	}
	
	//URLが既にリストにあるかどうかチェックする。あればその位置、なければ-1を返す。
	private int findUrl(String url){
		for (int i = adapter.getCount()-1; i >=0 ; i--) {
			if (adapter.getItem(i).equals(url) ){
				return i;
			}
		}
		return -1;
	}



}