package com.example.lwh.sudoku;

import android.util.Log;

/**
 * Created by LWH on 2015/3/29.
 */
public class ShuduGame {
    private final String str =
            "000000000000000000000000000" +
                    "000000000000000000000000000" +
                    "000000000000000000000000000";
    public int[] shuku = new int[9 * 9];
    //存储单元格不可用的数据
    private int used[][][] = new int[9][9][];

    //构造方法，获得一个含81项的一维数组
    public ShuduGame() {
        shuku = fromPuzzleString(str);
        calculateAllUsedTile();
    }


    //将字符串变量转化成数组
    protected int[] fromPuzzleString(String string) {
        int[] shu = new int[string.length()];
        for (int i = 0; i < shu.length; i++) {
            shu[i] = string.charAt(i) - '0';
        }
        return shu;
    }

    //取出某一坐标的数
    public int getTile(int x, int y) {
        return shuku[9 * y + x];
    }

    //取出某一坐标的数，并转化成字符，0转化成空。
    public String getTileString(int x, int y) {
        Log.e("asdf", "getTitleString" + x + y);
        int v = getTile(x, y);
        if (v == 0) {
            return "";
        } else {
            return String.valueOf(v);
        }
    }

    //计算某一单元格中不可用的数据
    public int[] calculateUsedTile(int x, int y) {
        int c[] = new int[9];

        //将竖排中有的数据加到数组c
        for (int i = 0; i < 9; i++) {
            if (i == y) {
                continue;
            }
            int t = getTile(x, i);
            if (t != 0) {
                c[t - 1] = t;
            }
        }
        //将横排中有的数加到数组c
        for (int i = 0; i < 9; i++) {
            if (i == x) {
                continue;
            }
            int t = getTile(i, y);
            if (t != 0) {
                c[t - 1] = t;
            }
        }
        //将小九宫格中有的数据加到数组c
        int startx = (x / 3) * 3;
        int starty = (y / 3) * 3;
        for (int i = startx; i < startx + 3; i++) {
            for (int j = starty; j < starty + 3; j++) {
                if (i == x && j == y) {
                    continue;
                }
                int t = getTile(i, j);
                if (t != 0) {
                    c[t - 1] = t;
                }
            }
        }

        //压缩数组（去掉等于0的项）
        int nused = 0;
        for (int t : c) {
            if (t != 0) {
                nused++;
            }
        }
        int c1[] = new int[nused];
        nused = 0;
        for (int t : c) {
            if (t != 0) {
                c1[nused++] = t;
            }
        }
        return c1;
    }

    //计算所有单元格中不可用的数据
    public void calculateAllUsedTile() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                used[i][j] = calculateUsedTile(i, j);
            }
        }
    }

    //取出某一单元格中已经不可用的数据
    public int[] getUsedTileByCoordinate(int x, int y) {
        return used[x][y];
    }

    //判断填入的数字是否符合要求，不符合不做处理，符合则填入数字
    protected boolean setTileItValid(int x, int y, int value) {
        int tiles[] = getUsedTile(x, y);
        if (value != 0) {
            for (int tile : tiles) {
                if (tile == value) {
                    return false;
                }
            }
        }
        setTile(x, y, value);
        calculateAllUsedTile();
        return true;
    }

    private void setTile(int x, int y, int value) {
        shuku[9 * y + x] = value;
    }

    protected int[] getUsedTile(int x, int y) {
        return used[x][y];
    }

    //判断是否过关
    public boolean winGame(int[] shuku) {
        for (int tile : shuku) {
            if (tile == 0) {
                return false;
            }
        }
        return true;
    }

}
