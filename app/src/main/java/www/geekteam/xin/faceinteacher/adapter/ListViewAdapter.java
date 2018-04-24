package www.geekteam.xin.faceinteacher.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import www.geekteam.xin.faceinteacher.R;
import www.geekteam.xin.faceinteacher.bean.Tongji;

/**
 * Created by PC on 2018/4/20.
 */

public class ListViewAdapter extends ArrayAdapter {
    private final int resourceId;

    public ListViewAdapter(Context context, int textViewResourceId, List<Tongji> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Tongji tongji=(Tongji)getItem(position);
        View view1= LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView name=view1.findViewById(R.id.name);
        TextView xuehao=view1.findViewById(R.id.xuehao);
        TextView time=view1.findViewById(R.id.time);

        name.setText(tongji.getName());
        xuehao.setText(tongji.getXuehao());
        time.setText(tongji.getTime());
        return view1;
    }
}
