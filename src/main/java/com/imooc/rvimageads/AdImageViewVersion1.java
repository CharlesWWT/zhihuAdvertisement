package com.imooc.rvimageads;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by zhanghongyang01 on 17/11/23.
 */

public class AdImageViewVersion1 extends AppCompatImageView {
    private Context context;

    public AdImageViewVersion1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    private RectF mBitmapRectF;//图片框
    private Bitmap mBitmap;

    private int mMinDy;//imageview高度（px）


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //w:1080 控件的宽
        //h:540 控件的高
//        Log.e("onSizeChanged", "w:" + w + "h:" + h + "oldw:" + oldw + "oldh:" + oldh);
        mMinDy = h;//  540px
        //imageView中获取图片
        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        mBitmap = drawableToBitamp(drawable);
//        Log.e("图片的高", mBitmap.getHeight() + "");//960
//        Log.e("图片的宽", mBitmap.getWidth() + "");//640
        mBitmapRectF = new RectF(0, 0, w, mBitmap.getHeight() * w / mBitmap.getWidth());//等额放大

    }

    //drawable TO bitmap
    private Bitmap drawableToBitamp(Drawable drawable) {
//        if (drawable instanceof BitmapDrawable) {
//            BitmapDrawable bd = (BitmapDrawable) drawable;
//            return bd.getBitmap();
//        }
        //获取drawable的宽高
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
//        Log.e("drawableH", h + "");//图片的高960
//        Log.e("drawableW", w + "");//图片的宽640

        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);//相对于canvas左上角为坐标原点，drawable在canvas内部绘制
        drawable.draw(canvas);
        return bitmap;
    }

    private int mDy;
    private int offSet = 0;

    public void setDy(int dy) {
//        Log.e("dy",dy+"");
//        Log.e("mBitmapRectF.height()",mBitmapRectF.height()+"");
//         mMinDy 为控件高度 dy为图片顶部到屏幕底部的距离 mBitmapRectF.height() - mMinDy 为未显示距离 mdy为图片底部到屏幕底部的距离
        mDy = dy - mMinDy; //mDy:图片底部到屏幕底部的距离
        //当控件没有完全显示出来时，不进行偏移
        if (mDy <= 0) {
            offSet = 0;
        }
        //当图片完全显示时
        if(mDy>0) {
            offSet=mDy;
            if(dy>=mBitmapRectF.height()) {
                int temp= (int) mBitmapRectF.height();
                offSet=temp-mMinDy;
            }
//            offSet=(int) mBitmapRectF.height()-mDy;
//            if(offSet==(int)mBitmapRectF.height()) {
//                offSet= (int) mBitmapRectF.height();
//            }
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        Log.e("offset",offSet+"");
        canvas.translate(0, -offSet);
//        Log.e("-mDy",-mDy+"");
        canvas.drawBitmap(mBitmap, null, mBitmapRectF, null);
        canvas.restore();
    }
//    /**
//     * 根据手机分辨率从DP转成PX
//     * @param context
//     * @param dpValue
//     * @return
//     */
//    public static int dip2px(Context context, float dpValue) {
//        float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (dpValue * scale + 0.5f);
//    }
//    /**
//     * 根据手机的分辨率PX(像素)转成DP
//     * @param context
//     * @param pxValue
//     * @return
//     */
//    public static int px2dip(Context context, float pxValue) {
//        float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (pxValue / scale + 0.5f);
//    }
}
