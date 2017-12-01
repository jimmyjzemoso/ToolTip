package mindhive.spike.jimmy.tooltip;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.ViewGroup;

/**
 * Created by Jimmy on 1/12/17.
 */

public class MHToolTip {
    private static Context mContext;

    public static MHToolTip getInstance(Context context){
        if(mContext==null){
           mContext = context;
        }
        return new MHToolTip();
    }
    public void show(Point point,String mText,int bgColor,int textColor,boolean arrowPresent,boolean inverted,ViewGroup parent){
        CustomToast customToast = new CustomToast(mContext,point.x,point.y,mText, bgColor,textColor,arrowPresent,inverted);
        parent.addView(customToast);
        customToast = null;
        mContext = null;
    }

}
