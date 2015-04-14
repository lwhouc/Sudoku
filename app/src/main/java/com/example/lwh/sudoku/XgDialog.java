package com.example.lwh.sudoku;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;


/**
 * Created by LWH on 2015/4/2.
 */
public class XgDialog extends Dialog {
    private final View vKeys[] = new View[9];
    public String str[] = new String[9];
    public int guanshu = -1;
    private ShuduView shuduView;

    //构造函数，第二个参数中保存着当前单元格已经使用过的数据
    public XgDialog(Context context, ShuduView shuduView) {
        super(context);
        this.shuduView = shuduView;
        setCancelable(false);
    }

    //重写onCreate方法，设定ContentView，给变量找到、绑定控件，设置监听器。
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("选关");
        setContentView(R.layout.key_pad);
        findView();
        setListener();
        initializeStr();
    }

    private void initializeStr() {
        str[0] = "905243167326701954741965302" +
                "402879631193604278678132509" +
                "509417826817506493264398705";
        str[1] = "709000300406350900000000016" +
                "040029063900405008560730090" +
                "320000000008047605004000102";
        str[2] = "065000093708030600090070801" +
                "017800952030254080582009360" +
                "804090010006080409970000530";
        str[3] = "105706020030209051020000700" +
                "003008500040305070001600900" +
                "008000010310807090070902804";
        str[4] = "035900000900002540020060709" +
                "000720800000806000008039000" +
                "402080070053100006000004310";
        str[5] = "360000000004230800000004200" +
                "070460003820000014500013020" +
                "001900000007048300000000045";
        str[6] = "100070600090000700008051003" +
                "054019006010305040200740510" +
                "900530100001000030007060004";
        str[7] = "706900000003600019900001000" +
                "097230000200000003000094520" +
                "000300006680009100000002805";
        str[8] = "005102900300000425009400001" +
                "800900002090207040400005007" +
                "900004300123000004004306200";
    }

    //找到静态控件
    private void findView() {
        vKeys[0] = findViewById(R.id.key_pad_1);
        vKeys[1] = findViewById(R.id.key_pad_2);
        vKeys[2] = findViewById(R.id.key_pad_3);
        vKeys[3] = findViewById(R.id.key_pad_4);
        vKeys[4] = findViewById(R.id.key_pad_5);
        vKeys[5] = findViewById(R.id.key_pad_6);
        vKeys[6] = findViewById(R.id.key_pad_7);
        vKeys[7] = findViewById(R.id.key_pad_8);
        vKeys[8] = findViewById(R.id.key_pad_9);
    }

    //返回结果：向shuduView传入一个数字，对话框消失。


    //监听器：对点击事件作出相应
    private void setListener() {
        for (int i = 0; i < vKeys.length; i++) {
            final int t = i;
            vKeys[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    guanshu = t;
                    shuduView.xuanguan(str[guanshu]);
                    dismiss();
                }
            });
        }
    }

}
