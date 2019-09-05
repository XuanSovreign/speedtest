package com.space.testspeed;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by licht on 2019/6/19.
 */

public class SpeedView extends View {

    private Paint mDPaint;
    private RectF mDRectF;
    private Paint mLpaint;
    private Paint mApaint;
    private Path mPath;
    private int mBRadius;
    private int mSRadius;
    private int sLineWidth = 5;
    private int bLineWidth = 15;
    private int textWidth = 45;
    private int stextWidth = 15;
    private int mBigDistance = 800;
    private int lineCount=34;
    private int useLineCount=20;
    private float degreeNum=8f;
    private float cavansDegree=-132f;
    private float startAngle=-225;
    private float endAngle=270;
    private float lineStartY=90;
    private float lineEndY=140;
    private Paint mUsepaint;
    private Paint mTextpaint;
    private String text="0";
    private String sText="Mbps";
    private Paint mSTextpaint;
    private Context mContext;

    public SpeedView(Context context) {
        this(context, null);
    }

    public SpeedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        initPaint();
    }


    public SpeedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mBigDistance, mBigDistance);

    }

    private void initPaint() {
        mDPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDPaint.setStrokeWidth(bLineWidth);
        mDPaint.setStyle(Paint.Style.STROKE);
        mBRadius = mBigDistance / 2;
        mSRadius = mBRadius / 2;
//        Log.e("radius", "initPaint:mBRadius=" + mBRadius + "  mSRadius" + mSRadius);
        int[] colors = {Color.parseColor("#104CE6"), Color.parseColor("#935AF2"), Color.parseColor("#104CE6")};
        LinearGradient gradient = new LinearGradient(mSRadius, mBRadius, mSRadius + mBRadius, mBRadius, colors, null, Shader.TileMode.MIRROR);
        mDRectF = new RectF(mSRadius, mSRadius, mSRadius + mBRadius, mSRadius + mBRadius);
        mDPaint.setShader(gradient);


        mLpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLpaint.setStrokeWidth(sLineWidth);
        mLpaint.setColor(Color.WHITE);

        mUsepaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUsepaint.setStrokeWidth(sLineWidth);
        mUsepaint.setColor(Color.parseColor("#104CE6"));

        mApaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mApaint.setStrokeWidth(sLineWidth);
        mApaint.setColor(Color.parseColor("#B969F6"));
        mPath = new Path();
        mPath.moveTo(mBRadius-5, lineStartY);
        mPath.lineTo(mBRadius+5, lineStartY);
        mPath.lineTo(mBRadius, mSRadius-5);
        mPath.close();

        mTextpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextpaint.setStyle(Paint.Style.FILL);
        mTextpaint.setFakeBoldText(true);
        mTextpaint.setTextSize(DPUtil.dp2px(mContext,textWidth));
        mTextpaint.setTextScaleX(1.5f);
        mTextpaint.setColor(Color.WHITE);

        mSTextpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSTextpaint.setStyle(Paint.Style.FILL);
        mSTextpaint.setTextSize(DPUtil.dp2px(mContext,stextWidth));
        mSTextpaint.setColor(Color.WHITE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mDRectF, startAngle, endAngle, false, mDPaint);

        //画背景色刻度线
        canvas.save();
        canvas.rotate(cavansDegree, mBRadius, mBRadius);
        for (int i = 0; i < lineCount; i++) {
            canvas.drawLine(mBRadius, lineStartY,
                    mBRadius, lineEndY, mLpaint);
            canvas.rotate(degreeNum, mBRadius, mBRadius);
        }
        canvas.restore();

        canvas.save();
        canvas.rotate(cavansDegree+2f, mBRadius, mBRadius);
        for (int i = 0; i < 27; i++) {
            canvas.drawLine(mBRadius, 175,
                    mBRadius, 185, mLpaint);
            canvas.rotate(10f, mBRadius, mBRadius);
        }
        canvas.restore();

        canvas.save();
        canvas.rotate(cavansDegree, mBRadius, mBRadius);
        for (int i = 0; i < useLineCount; i++) {
            canvas.drawLine(mBRadius, lineStartY,
                    mBRadius, lineEndY, mUsepaint);
            canvas.rotate(degreeNum, mBRadius, mBRadius);
        }
        canvas.rotate(-degreeNum, mBRadius, mBRadius);
        canvas.drawPath(mPath, mApaint);
        canvas.restore();
        float measureText = mTextpaint.measureText(text);
        float distance = mTextpaint.descent() - mTextpaint.ascent();
//        Log.e("measureText", "onDraw: "+measureText );
        canvas.drawText(text,mBRadius-measureText/2,mBRadius,mTextpaint);
        float sTextWidth = mSTextpaint.measureText(sText);
        canvas.drawText(sText,mBRadius-sTextWidth/2,mBRadius+distance-40,mSTextpaint);
    }

    public void setUseLineCount(int useLineCount) {
        this.useLineCount = useLineCount;
        invalidate();
    }

    public int getBRadius() {
        return mBRadius;
    }

    public void setBRadius(int BRadius) {
        mBRadius = BRadius;
    }

    public int getSRadius() {
        return mSRadius;
    }

    public void setSRadius(int SRadius) {
        mSRadius = SRadius;
    }

    public int getsLineWidth() {
        return sLineWidth;
    }

    public void setsLineWidth(int sLineWidth) {
        this.sLineWidth = sLineWidth;
    }

    public int getbLineWidth() {
        return bLineWidth;
    }

    public void setbLineWidth(int bLineWidth) {
        this.bLineWidth = bLineWidth;
    }

    public int getTextWidth() {
        return textWidth;
    }

    public void setTextWidth(int textWidth) {
        this.textWidth = textWidth;
    }

    public int getStextWidth() {
        return stextWidth;
    }

    public void setStextWidth(int stextWidth) {
        this.stextWidth = stextWidth;
    }

    public int getBigDistance() {
        return mBigDistance;
    }

    public void setBigDistance(int bigDistance) {
        mBigDistance = bigDistance;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    public int getUseLineCount() {
        return useLineCount;
    }

    public float getDegreeNum() {
        return degreeNum;
    }

    public void setDegreeNum(float degreeNum) {
        this.degreeNum = degreeNum;
    }

    public float getCavansDegree() {
        return cavansDegree;
    }

    public void setCavansDegree(float cavansDegree) {
        this.cavansDegree = cavansDegree;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public float getEndAngle() {
        return endAngle;
    }

    public void setEndAngle(float endAngle) {
        this.endAngle = endAngle;
    }

    public float getLineStartY() {
        return lineStartY;
    }

    public void setLineStartY(float lineStartY) {
        this.lineStartY = lineStartY;
    }

    public float getLineEndY() {
        return lineEndY;
    }

    public void setLineEndY(float lineEndY) {
        this.lineEndY = lineEndY;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        invalidate();
    }

    public String getsText() {
        return sText;
    }

    public void setsText(String sText) {
        this.sText = sText;
    }
}
