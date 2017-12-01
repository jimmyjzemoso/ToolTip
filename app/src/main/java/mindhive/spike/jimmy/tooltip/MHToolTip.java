package mindhive.spike.jimmy.tooltip;

import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;

import java.util.Map;

/**
 * Created by Jimmy on 1/12/17.
 */

public class MHToolTip implements View.OnClickListener{

    private Context mContext;
    private int mBackgroundColor;
    private int mTextColor;
    private boolean direction;
    private boolean arrowPresent;
    private ViewGroup mParent;
    private CustomToast customToast;

    public static MHToolTip getInstance(Context context, int backgroundColor, int textColor, boolean direction, boolean arrowPresent, ViewGroup parent){
        return new MHToolTip(
                context,
                backgroundColor,
                textColor,
                direction,
                arrowPresent,
                parent
        );
    }

    private MHToolTip(Context mContext, int backgroundColor, int mTextColor, boolean direction, boolean arrowPresent, ViewGroup mParent) {
        this.mContext = mContext;
        this.mBackgroundColor = backgroundColor;
        this.mTextColor = mTextColor;
        this.direction = direction;
        this.arrowPresent = arrowPresent;
        this.mParent = mParent;
    }

    public void show(String tooltipText, Point arrowPoint){

        if(customToast != null){
            mParent.removeView(customToast);
        }
        customToast = new CustomToast(
                mContext,
                arrowPoint.x,
                arrowPoint.y,
                tooltipText,
                mBackgroundColor,
                mTextColor,
                arrowPresent,
                direction
        );
        customToast.setOnClickListener(this);
        mParent.addView(customToast);
    }

    @Override
    public void onClick(View view) {
        if(view.equals(customToast)){
            if(mParent != null){
                mParent.removeView(customToast);
            }
        }
    }
}
