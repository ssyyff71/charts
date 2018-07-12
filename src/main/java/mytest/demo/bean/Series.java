package mytest.demo.bean;

import java.util.List;

/**
 * created by suyifei on 2018-07-10
 **/
public class Series {
    public String name;
    public String type;
    public List<Integer> data;
    public Series(String name, String type, List<Integer> data)
    {
        this.name = name;
        this.type = type;
        this.data = data;
    }

    public String toName()
    {
        return name;
    }
}
