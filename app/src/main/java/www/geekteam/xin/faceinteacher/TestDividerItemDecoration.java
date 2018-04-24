package www.geekteam.xin.faceinteacher;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by PC on 2018/4/19.
 */

public class TestDividerItemDecoration extends RecyclerView.ItemDecoration
{
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if(parent.getChildAdapterPosition(view)!=0)
        {
            outRect.top=1;
        }
    }

}
