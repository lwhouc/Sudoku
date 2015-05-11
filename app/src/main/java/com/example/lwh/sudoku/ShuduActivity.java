package com.example.lwh.sudoku;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

import static android.app.AlertDialog.Builder;

public class ShuduActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode== KeyEvent.KEYCODE_BACK){
            Builder alertDialog=new Builder(ShuduActivity.this);
            alertDialog.setTitle("提示");
            alertDialog.setMessage("确认退出游戏？");
            alertDialog.setPositiveButton("确认",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alertDialog.setNegativeButton("取消",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
            alertDialog.show();
        };

        return super.onKeyDown(keyCode, event);
    }
}
