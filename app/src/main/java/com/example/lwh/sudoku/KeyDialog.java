package com.example.lwh.sudoku;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.lwh.sudoku.R;

/**
 * Created by LWH on 2015/3/30.
 */
public class KeyDialog extends Dialog {

    private final View vKeys[] = new View[9];
    private final int used[];
    private ShuduView shuduView;

    //构造函数，第二个参数中保存着当前单元格已经使用过的数据
    public KeyDialog(Context context, int[] used,ShuduView shuduView) {
        super(context);
        this.used = used;
        this.shuduView=shuduView;
    }

    //重写onCreate方法，设定ContentView，给变量找到、绑定控件，设置监听器。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("可选的数字");
        setContentView(R.layout.key_pad);
        findView();
        for (int i=0;i<used.length;i++){
            vKeys[used[i]-1].setVisibility(View.INVISIBLE);
        }
        setListener();
    }

    //找到静态控件
    private void findView(){
        vKeys[0]=findViewById(R.id.key_pad_1);
        vKeys[1]=findViewById(R.id.key_pad_2);
        vKeys[2]=findViewById(R.id.key_pad_3);
        vKeys[3]=findViewById(R.id.key_pad_4);
        vKeys[4]=findViewById(R.id.key_pad_5);
        vKeys[5]=findViewById(R.id.key_pad_6);
        vKeys[6]=findViewById(R.id.key_pad_7);
        vKeys[7]=findViewById(R.id.key_pad_8);
        vKeys[8]=findViewById(R.id.key_pad_9);
    }

    //返回结果：向shuduView传入一个数字，对话框消失。
    private void returnResult(int tile){
        shuduView.setSelectedTile(tile);
        dismiss();
    }

    //监听器：对点击事件作出相应
    private void setListener(){
        for (int i=0;i<vKeys.length;i++){
            final int t=i+1;
            vKeys[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    returnResult(t);
                }
            });
        }
    }



}
