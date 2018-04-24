package www.geekteam.xin.faceinteacher;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by PC on 2018/4/18.
 */

public class StaticPagerAdapter extends com.jude.rollviewpager.adapter.StaticPagerAdapter
{
    private int[] images = {
            R.drawable.screen_top_1,
            R.drawable.screen_top_2,
            R.drawable.screen_top_3,
    };


    @Override
    public View getView(ViewGroup container, int position)
    {
       ImageView imageView = new ImageView(container.getContext());
       imageView.setImageResource(images[position]);
       imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
       imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
       return  imageView;
    }
    @Override
    public int getCount() {
        return images.length;
    }

}
