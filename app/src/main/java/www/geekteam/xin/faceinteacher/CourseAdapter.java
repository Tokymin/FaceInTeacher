package www.geekteam.xin.faceinteacher;



import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import www.geekteam.xin.faceinteacher.bean.Course;

/**
 * Created by PC on 2018/4/18.
 */

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder>
{
    private List<Course> mCourseList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View CourseView;
        TextView CourseName;
        TextView CourseTime;
        TextView CourseRoom;

        public ViewHolder(View view) {
            super(view);
            CourseView=view;
            CourseName=view.findViewById(R.id.courseName);
            CourseTime=view.findViewById(R.id.courseTime);
            CourseRoom=view.findViewById(R.id.courseRoom);
        }

    }
    public CourseAdapter(List<Course> CourseList) {
        mCourseList=CourseList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item,parent,false);
        final  ViewHolder holder=new ViewHolder(view);
        holder.CourseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Course course=mCourseList.get(position);
                Toast.makeText(v.getContext()," "+course.getCoursename(),Toast.LENGTH_SHORT).show();
            }
        });
        holder.CourseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Course course=mCourseList.get(position);
                Intent intent=new Intent(MainActivity.activity,Test.class);
                MainActivity.activity.startActivity(intent);
                Toast.makeText(v.getContext()," "+course.getCoursename(),Toast.LENGTH_SHORT).show();
            }
        });



        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Course course=mCourseList.get(position);
        holder.CourseName.setText(course.getCoursename());
        holder.CourseTime.setText("第"+(course.getJieci())+"-"+(course.getJieci()+1)+"节");
        holder.CourseRoom.setText(course.getClassRoomName());

    }

    @Override
    public int getItemCount() {
        return mCourseList.size();
    }

}
