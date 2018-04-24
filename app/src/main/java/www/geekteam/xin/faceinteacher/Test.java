package www.geekteam.xin.faceinteacher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC on 2018/4/19.
 */

public class Test extends AppCompatActivity {
        private List<Tongji> tongjis=new ArrayList<Tongji>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        Toolbar toolbar=findViewById(R.id.toorbar);
        setSupportActionBar(toolbar);
        initStatistics();
        ListViewAdapter adapter=new ListViewAdapter(Test.this,R.layout.list_item1,tongjis);
        ListView listView=findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toorbar,menu);
      return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
           case R.id.item1:
               Toast.makeText(this, "item1", Toast.LENGTH_SHORT).show();
               break;
           case R.id.item2:
               Toast.makeText(this, "item2", Toast.LENGTH_SHORT).show();
               break;
       }
        return true;
    }

    //初始化数据
    private  void  initStatistics(){

        Tongji tongji=new Tongji("张明","123456","09:00");
        tongjis.add(tongji);
        Tongji tongji1=new Tongji("张明","123456","09:00");
        tongjis.add(tongji1);
        Tongji tongji2=new Tongji("张明","123456","09:00");
        tongjis.add(tongji2);
        Tongji tongji3=new Tongji("张明","123456","09:00");
        tongjis.add(tongji3);
        Tongji tongji4=new Tongji("张明","123456","09:00");
        tongjis.add(tongji4);
        Tongji tongji5=new Tongji("张明","123456","09:00");
        tongjis.add(tongji5);
        Tongji tongji6=new Tongji("张明","123456","09:00");
        tongjis.add(tongji6);


    }



}
