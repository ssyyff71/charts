package mytest.demo.bean;

/**
 * created by suyifei on 2018-07-16
 **/
public class VersionCount{
    private String version;
    private Integer count;

    public VersionCount(String version, Integer count) {
        this.version = version;
        this.count = count;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
