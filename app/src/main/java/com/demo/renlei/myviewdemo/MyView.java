package com.demo.renlei.myviewdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {
    private Paint mPaint; //画笔
    private Bitmap mBitmap; //用来缓存画好内容
    private Path mPath; //路径
    private Canvas mCanvas; //内存中创建的Canvas

    //移动的最后位置
    private float lastX;
    private float lastY;

    public MyView(Context context) {
        super(context);
        init();
    }


    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init() {
        mPaint = new Paint();//画笔初始化
        mPath = new Path();//路径初始化
        mPaint.setAntiAlias(true); //抗锯齿
        mPaint.setDither(true);//图像抖动处理
        mPaint.setColor(Color.GREEN); //画笔颜色
        mPaint.setStrokeJoin(Paint.Join.ROUND);//结合处为圆角
        mPaint.setStrokeCap(Paint.Cap.ROUND);//转弯处为圆角
        mPaint.setStyle(Paint.Style.STROKE); //画笔风格
//        mPaint.setTextSize(36); //绘制文字大小，单位PX
        mPaint.setStrokeWidth(20);//画笔粗细
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        //初始化bitmap,Canvas
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    //重写方法，在这儿绘图
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPath();
//        canvas.drawColor(getResources().getColor(R.color.colorAccent));//设置画布颜色
        canvas.drawBitmap(mBitmap, 0, 0, null);

    }

    private void drawPath() {
        mCanvas.drawPath(mPath, mPaint);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                mPath.moveTo(lastX, lastY);
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - lastX);
                float dy = Math.abs(y - lastY);
                if (dx > 3 || dy > 3)
                    mPath.lineTo(lastX, lastY);
                lastX = x;
                lastY = y;
                break;
        }
        invalidate();
        return true;
    }

    /**
     * 下面可以拓展方法
     */
    public void save(){
        if(mPath.isEmpty()){

        }
    }
}
