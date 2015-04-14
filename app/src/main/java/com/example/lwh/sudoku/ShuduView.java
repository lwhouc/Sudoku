package com.example.lwh.sudoku;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.FileOutputStream;


/**
 * Created by LWH on 2015/3/28.
 */
public class ShuduView extends View {
    //单元格宽与高
    private float width;
    private float height;
    private String originalStr[][] = new String[9][9];
    private int originalShuku[][] = new int[9][9];
    //单元格坐标
    int selectedX;
    int selectedY;
    //创建ShuduGame对象
    private ShuduGame game = new ShuduGame();
    //创建选关对话框
    XgDialog xgDialog = new XgDialog(getContext(), this);

    private String str[] = new String[9];
    private String string = "str";
    private int guanshu = xgDialog.guanshu;

    //构造函数
    public ShuduView(Context context) {
        super(context);
        xgDialog.show();
    }


    //覆盖父类的onDraw方法，绘制自定义View.
    @Override
    protected void onDraw(Canvas canvas) {
        //绘制背景和网格
        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(getResources().getColor(R.color.shudu_background));
        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);

        Paint darkPaint = new Paint();
        darkPaint.setColor(getResources().getColor(R.color.shudu_dark));

        Paint whitePaint = new Paint();
        whitePaint.setColor(getResources().getColor(R.color.shudu_light1));

        Paint lightPaint = new Paint();
        lightPaint.setColor(getResources().getColor(R.color.shudu_light));

        for (int i = 0; i < 10; i++) {
            canvas.drawLine(0, i * height, getWidth(), i * height, lightPaint);
            canvas.drawLine(0, i * height + 1, getWidth(), i * height, whitePaint);
            canvas.drawLine(i * width, 0, i * width, getWidth(), lightPaint);
            canvas.drawLine(i * width + 1, 0, i * width + 1, getWidth(), whitePaint);
            if (i % 3 == 0) {
                canvas.drawLine(0, i * height, getWidth(), i * height, darkPaint);
                canvas.drawLine(i * width, 0, i * width, getWidth(), darkPaint);
            }
        }

        //绘制数字
        Paint numberPaint = new Paint();
        numberPaint.setColor(Color.BLACK);
        numberPaint.setTextSize(height * 0.75f);
        numberPaint.setStyle(Paint.Style.STROKE);
        numberPaint.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics fontMetrics = numberPaint.getFontMetrics();
        float x = width / 2;
        float y = height / 2 - (fontMetrics.ascent + fontMetrics.descent) / 2;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                numberPaint.setColor(Color.GREEN);
                canvas.drawText(game.getTileString(i, j), i * width + x, j * height + y, numberPaint);
                Log.e("asdf", "drawtext" + i + j);
                if (xgDialog.guanshu != -1) {
                    numberPaint.setColor(Color.BLACK);
                    canvas.drawText(originalStr[i][j], i * width + x, j * height + y, numberPaint);
                }
            }
        }

        canvas.drawText("选关", 4 * width + x, 10 * height + y, numberPaint);
        canvas.drawRect(3 * width, 10 * height, 6 * width, 11 * height, darkPaint);


        super.onDraw(canvas);
    }

    //重写onTouchEvent方法，在限定区域对ACTION_DOWN触摸事件作出相应
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //计算触摸点对应的九宫格坐标
            selectedX = (int) (event.getX() / width);
            selectedY = (int) (event.getY() / height);
            //获得该格子中不能使用的数字

            if (selectedY < 9 && originalShuku[selectedX][selectedY] == 0) {
                int used[] = game.getUsedTileByCoordinate(selectedX, selectedY);
                //生成KeyDialog对象
                KeyDialog keyDialog = new KeyDialog(getContext(), used, this);
                //设置对话框的显示位置
                int marginBotton = 5;
                Window window = keyDialog.getWindow();
                WindowManager.LayoutParams wmlp = window.getAttributes();
                wmlp.gravity = Gravity.BOTTOM;
                wmlp.x = marginBotton;
                Log.i("123456", "wmlp=" + wmlp);
                window.setAttributes(wmlp);

                keyDialog.show();
            } else if (selectedY == 10 && selectedX > 2 && selectedX < 6) {
                xgDialog.show();
            }


            /*
            StringBuffer sb=new StringBuffer();
            for (int i=0;i<used.length;i++){
                sb.append(used[i]);
            }

            //生成layoutIflater对象
            LayoutInflater layoutInflater=LayoutInflater.from(this.getContext());
            //使用layoutIflater对象，根据一个布局文件生成一个View对象
            View layoutView=layoutInflater.inflate(R.layout.dialog,null);
            //取出相应的控件
            TextView textView=(TextView)layoutView.findViewById(R.id.tvUsedNumbers);
            //设置textView的内容
            textView.setText(sb.toString());
            //生成一个对话框的builder对象
            AlertDialog.Builder builder=new AlertDialog.Builder(this.getContext());
            //使用builder对象设置对话空内容
            builder.setView(layoutView);
            //生成对话框，并显示。
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
            */

            return true;
        }
        return super.onTouchEvent(event);

    }

    //重写onSizeChanged方法，从其传入的宽高计算出九宫格的宽高
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //计算单元格宽与高
        this.width = w / 9f;
        this.height = w / 9f;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    //判断填入数字是否符合要求，符合则填入，重绘视图，判断是否过关。
    public void setSelectedTile(int tile) {
        if (game.setTileItValid(selectedX, selectedY, tile)) {
            invalidate();
            if (game.winGame(game.shuku)) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setMessage("恭喜你过关了！");
                alertDialog.setNegativeButton("重玩", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        xuanguan(xgDialog.str[guanshu]);
                    }
                });
                alertDialog.setPositiveButton("下一关", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (guanshu > 8) {
                            guanshu = 0;
                        } else {
                            guanshu = guanshu + 1;
                        }
                        xuanguan(xgDialog.str[guanshu]);
                    }
                });
                alertDialog.show();
            }
        }
    }

    //选关。
    public void xuanguan(String str) {
        game.shuku = game.fromPuzzleString(str);
        game.calculateAllUsedTile();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                originalShuku[i][j] = game.getTile(i, j);
                originalStr[i][j] = game.getTileString(i, j);
            }
        }

        invalidate();
    }

    //保存游戏进度
    public void saveGame(){
  //      FileOutputStream fos=new FileOutputStream()
    }

}
