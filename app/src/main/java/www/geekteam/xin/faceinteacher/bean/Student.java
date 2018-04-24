package www.geekteam.xin.faceinteacher.bean;

import static www.geekteam.xin.faceinteacher.RegisterActivity.name;

/**
 * Created by Toky on 2018/4/23.
 */

public class Student {
    private String stu_name;
    private String sign_time;
    private String sID;
    public Student(){

    }
    public Student(String stu_name,String sID){
        this.stu_name=stu_name;
        this.sID=sID;
    }
    public Student(String stu_name,String sign_time,String sID){
        this.sign_time=sign_time;
        this.stu_name=stu_name;
        this.sID=sID;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public String getSign_time() {
        return sign_time;
    }

    public void setSign_time(String sign_time) {
        this.sign_time = sign_time;
    }
    @Override
    public String toString() {
        return "Stuudent [name+"+name+"+sign_time"+sign_time+"]";
    }

    public String getsID() {
        return sID;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }
}
