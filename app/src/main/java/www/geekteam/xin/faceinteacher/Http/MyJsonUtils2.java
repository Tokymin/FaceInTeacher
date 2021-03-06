package www.geekteam.xin.faceinteacher.Http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import www.geekteam.xin.faceinteacher.bean.Student;

//解析json的工具类
public class MyJsonUtils2 {
	public List<Student> parseJson(String jsonStr){
		List<Student> list=new ArrayList<Student>();
		try {
			JSONObject jsonObject=new JSONObject(jsonStr);
			JSONArray jsonArray=new JSONArray(jsonObject.getString("notSign"));
			for(int i=0;i<jsonArray.length();i++){
				JSONObject obj2=jsonArray.optJSONObject(i);
				String stu_Id=obj2.optString("s_id");//学生ID
				String stu_name=obj2.optString("s_name");//学生姓名

				Student student=new Student(stu_name,stu_Id);

				list.add(student);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}
}
