package www.geekteam.xin.faceinteacher.bean;

/**
 * Created by PC on 2018/4/20.
 */

public class Tongji {
    private String name;
    private String xuehao;
    private String time;
    public Tongji(String name,String xuehao,String time){
        this.name=name;
        this.xuehao=xuehao;
        this.time=time;

    }

    public String getName() {
        return name;
    }


    public String getXuehao() {
        return xuehao;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setXuehao(String xuehao) {
        this.xuehao = xuehao;
    }
}

