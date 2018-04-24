package www.geekteam.xin.faceinteacher.Sigin;

import java.util.Date;

/**
 * Created by PC on 2018/4/20.
 */

public class MyDateBean
{
    private boolean isSelect;
    Date date;

    public MyDateBean(boolean isSelect, Date date) {
        this.isSelect = isSelect;
        this.date = date;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
