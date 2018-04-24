package www.geekteam.xin.faceinteacher;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by PC on 2018/4/19.
 */

public class ColorDividerItemDecoration extends RecyclerView.ItemDecoration
{
    private float mDividerHeight;
    private Paint mPaint;
    public ColorDividerItemDecoration()
    {
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GRAY);

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount=parent.getChildCount();
        for(int i=0;i<childCount;i++)
        {
            View view=parent.getChildAt(i);
            int index=parent.getChildAdapterPosition(view);
            if(index==0){
                continue;
            }
            float dividerTop=view.getTop()-mDividerHeight;
            float dividerLeft=parent.getPaddingLeft();
            float dividerBottom=parent.getTop();
            float dividerRight=parent.getWidth()-parent.getPaddingRight();
            c.drawRect(dividerLeft,dividerTop,dividerRight,dividerBottom,mPaint);

        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if(parent.getChildAdapterPosition(view)!=0)
        {
            outRect.top=1;
            mDividerHeight=1;
        }

    }
}
