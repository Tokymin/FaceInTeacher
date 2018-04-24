package www.geekteam.xin.faceinteacher.Sigin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * Created by PC on 2018/4/20.
 */

public class MyGridView extends GridView
{
    private int oldY;
    private int gridAllHeight,changeHeight;
    private int newY;
    private int childCount;
    private int lineNum;

    public MyGridView(Context context) {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int action= event.getAction();
        switch (action){
            case  MotionEvent.ACTION_DOWN:
                oldY= (int) event.getY();
                Log.e("MotionEventACTION_DOWN","oldY:"+oldY);
                setGridviewAnmation(0);
                break;
            case  MotionEvent.ACTION_MOVE:
                newY= (int) event.getY();
                Log.e("MotionEventACTION_MOVE","newY:"+newY);
                setGridviewAnmation(newY-oldY);
                break;
            case  MotionEvent.ACTION_UP:
            case  MotionEvent.ACTION_CANCEL:
                Log.e("MotionEventACTION_UP","newY:"+newY);
                newY= (int) event.getY();
                newGetHeight(newY-oldY);
                if(scroListener!=null){
                    if(Math.abs(newY-oldY)<10){
                        scroListener.scrol(false);
                    }else{
                        scroListener.scrol(true);
                    }
                }
                break;
        }
        if(action==MotionEvent.ACTION_MOVE){
            return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 随着手指的移动改变高度
     * @param dy
     */
    private void setGridviewAnmation(int dy){
        if(gridAllHeight==0){
            gridAllHeight=getHeight();
            changeHeight=getHeight();
            childCount=getChildCount();
            lineNum=childCount/7;
        }
        if(dy!=0&&changeHeight+dy<=gridAllHeight&&changeHeight+dy>gridAllHeight/5){
            ViewGroup.LayoutParams lp=getLayoutParams();
            lp.height=changeHeight+dy;
            setLayoutParams(lp);
        }
    }


    /**
     * 松开时动画
     * @param y
     */
    private void newGetHeight(int y){
        ValueAnimator anitor;
        if(Math.abs(y)<gridAllHeight/3){      //滑动距离小于总高的三分之一 回到原位置
            anitor=ValueAnimator.ofInt(getHeight(),changeHeight).setDuration(200);
        }else{
            if(y<0){   //向上滑动
                anitor=ValueAnimator.ofInt(getHeight(),gridAllHeight/lineNum).setDuration(200);
            }else{    // 向下滑动
                anitor=ValueAnimator.ofInt(getHeight(),gridAllHeight).setDuration(200);
            }
        }
        anitor.setRepeatCount(0);
        anitor.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                ViewGroup.LayoutParams lp=getLayoutParams();
                int value= (int) valueAnimator.getAnimatedValue();
                lp.height=value;
                setLayoutParams(lp);
            }
        });
        anitor.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                changeHeight=getHeight();
            }
        });
        anitor.start();
    }

    ScroListener scroListener;
    public  interface  ScroListener{
        void  scrol(boolean bl);
    }


    public  void IsScrol(ScroListener scroListener){
        this.scroListener=scroListener;

    }
}
