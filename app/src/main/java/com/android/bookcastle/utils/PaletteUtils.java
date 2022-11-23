package com.android.bookcastle.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;

import android.graphics.Shader;

import android.util.Log;

import androidx.palette.graphics.Palette;

import java.util.List;

public class PaletteUtils {

    public static int getUpperSideDominantColor(Bitmap bitmap) {
        Palette.Builder builder = new Palette.Builder(bitmap)
                .setRegion(0, 0, bitmap.getWidth(), bitmap.getHeight() / 2);
        int defaultValue = 0xFFFFFF;
        Palette p = builder.generate();
        return p.getDominantColor(defaultValue);
    }

    public static int getLowerSideDominantColor(Bitmap bitmap) {
        int defaultValue = 0xFFFFFF;

        Palette.Builder builder = new Palette.Builder(bitmap)
                .setRegion(0, bitmap.getHeight() / 2, bitmap.getWidth(), bitmap.getHeight());
        return builder.generate().getDominantColor(defaultValue);
    }

    public static Bitmap getDominantGradient(Bitmap bitmap, int height, int width) {
        int topColor=0, bottomColor=0;
        topColor = getUpperSideDominantColor(bitmap);
        bottomColor = getLowerSideDominantColor(bitmap);
        String topHex = Integer.toHexString(topColor);
        topHex =topHex.trim();
        topHex = '#'+topHex;

        String bottomHex = Integer.toHexString(bottomColor);
        bottomHex = bottomHex.trim();
        bottomHex = '#'+bottomHex;

        Log.e("color ",topHex);
        Log.e("color " , bottomHex);
        int[] colors = new int[]{Color.parseColor(topHex), Color.parseColor(bottomHex)};
        //int[]colors = new int[]{ Color.GREEN,Color.BLACK};

        Shader mShader = new LinearGradient(0, 0, width/2, height/2, colors,
                null, Shader.TileMode.CLAMP);
        Matrix m = new Matrix();
        m.setRotate(90);
        mShader.setLocalMatrix(m);
        Paint paint=new Paint();
        paint.setShader(mShader);
        Bitmap resultBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(resultBitmap);
        canvas.drawRect(0,0,width,height,paint);
        //canvas.drawRect(0,0,640,1137,paint);
        Matrix matrix = new Matrix();
        canvas.drawBitmap(resultBitmap,matrix,paint);

        return resultBitmap;
    }

    public static int getDominantColor(Bitmap imageBitmap) {
        int color = 0;
        Palette palette = Palette.from(imageBitmap).generate();
        List<Palette.Swatch> swatches = palette.getSwatches();
        if (swatches.size() > 0) {
            color = swatches.get(0).getRgb();
        }
        return color;
    }
}