package mindhive.spike.jimmy.tooltip;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by zemoso on 2/11/17.
 */

public class CustomToast extends View{

    private RectF mRect;
    private Paint mPaint,paint;
    private TextPaint mTextPaint;
    private int xPosOfArrow;
    private StaticLayout mTextLayout;
    private boolean arrowPresent;
    private int paddingHorizontal,paddingVertical,cornerRadius,arrowHeight,arrowBase;
    private int margin;
    private boolean inverted;
    private int yPosOfArrow;
    private int backgroundColor;
    private int textColor;
    private int width;
    private String mText;
    private Path mPath;
    private int screenWidth;

    public CustomToast(Context context, int xPosOfArrow, int yPosOfArrow, String mText, int backgroundColor, int textColor,boolean arrowPresent,boolean inverted ) {
        super(context);
        this.yPosOfArrow = yPosOfArrow;
        this.xPosOfArrow = xPosOfArrow;
        this.arrowPresent= arrowPresent;
        this.inverted = inverted;
        this.mText = mText;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        init(null);
    }

    public CustomToast(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomToast(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public CustomToast(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(@Nullable AttributeSet attributeSet)
    {
        if(attributeSet!=null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attributeSet, R.styleable.CustomToast);
            inverted = typedArray.getBoolean(R.styleable.CustomToast_inverted,false);
            mText   = typedArray.getString(R.styleable.CustomToast_text);
            xPosOfArrow = typedArray.getInteger(R.styleable.CustomToast_x_pos_of_Arrow,0);
            yPosOfArrow = typedArray.getInteger(R.styleable.CustomToast_y_pos_of_Arrow,0);
            backgroundColor = typedArray.getColor(R.styleable.CustomToast_backgroundColor,Color.BLACK);
            textColor = typedArray.getColor(R.styleable.CustomToast_textColor,Color.WHITE);
            typedArray.recycle();
        }

        paddingHorizontal = (int) getResources().getDimension(R.dimen.padding_horizontal);
        paddingVertical = (int) getResources().getDimension(R.dimen.padding_vertical);
        cornerRadius = (int) getResources().getDimension(R.dimen.corner_radius);
        arrowHeight = (int) getResources().getDimension(R.dimen.arrow_height);
        arrowBase = (int) getResources().getDimension(R.dimen.arrow_base);
        margin = (int) getResources().getDimension(R.dimen.margin);
        int text_size = (int) getResources().getDimension(R.dimen.text_size);

        mRect = new RectF();
        mPaint = new Paint();
        mTextPaint = new TextPaint();
        mPath = new Path();
        paint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(text_size);
        mTextPaint.setColor(textColor) ;
        screenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        float widthOfString =mTextPaint.measureText(mText);
        mTextLayout = new StaticLayout(mText, mTextPaint, screenWidth -margin*2, Layout.Alignment.ALIGN_NORMAL,1.0f,0.0f,false);
        if(widthOfString > screenWidth -margin*2){
            width = screenWidth -margin*2;
        }else{
            width = Math.round(widthOfString);
        }
        Log.d("width",String.valueOf(widthOfString));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(backgroundColor);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        int xTranslate;
        if(xPosOfArrow > screenWidth/2) {
            if(xPosOfArrow+width/2 > screenWidth-margin) {
                mRect.left = screenWidth-margin-width-paddingHorizontal;
                xTranslate = screenWidth - width/2-margin;
                mRect.right = screenWidth - margin+paddingHorizontal;
            }
            else {
                mRect.left = xPosOfArrow -width/2-paddingHorizontal;
                xTranslate = xPosOfArrow;
                mRect.right = xPosOfArrow +width/2 + paddingHorizontal;
            }
        }
        else{
            if(xPosOfArrow-width/2<margin) {
                mRect.left = margin-paddingHorizontal;
                xTranslate = width/2+margin;
                mRect.right = mRect.left+width+paddingHorizontal*2;
            }
            else
            {
                mRect.left = xPosOfArrow - width/2-paddingHorizontal;
                xTranslate = xPosOfArrow;
                mRect.right = xPosOfArrow + width/2 + paddingHorizontal;
            }
        }
        float yPos;
        if(inverted) {
            yPos = yPosOfArrow - arrowHeight;
            mRect.top = yPos - 2*paddingVertical-mTextLayout.getHeight();
        }
        else {
            yPos = yPosOfArrow + arrowHeight;
            mRect.top = yPos;
        }
        mRect.bottom = mRect.top+ paddingVertical*2 + mTextLayout.getHeight();
        canvas.drawRoundRect(mRect,cornerRadius,cornerRadius,mPaint);
        if(arrowPresent) {
            mPath.moveTo(xPosOfArrow, yPosOfArrow);
            mPath.lineTo(xPosOfArrow - arrowBase, yPos);
            mPath.lineTo(xPosOfArrow + arrowBase, yPos);
            mPath.lineTo(xPosOfArrow, yPosOfArrow);
            mPath.close();
            canvas.drawPath(mPath, paint);
        }
        canvas.save();
        canvas.translate(xTranslate,mRect.top + paddingVertical);
        mTextLayout.draw(canvas);
        canvas.restore();
    }

    void show(ViewGroup parent){
        parent.addView(this);
    }
}
