package mytest.demo.bean;

/**
 * created by suyifei on 2018-06-29
 **/
public class TransData {
    private String hash;
    private String IP;
    private int isFloodfill;
    private int knownRouters;
    private int knownLeasesets;
    private String routerVersion;
    private String caps;
    private String country;
    private String createTime;


    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public int isFloodfill() {
        return isFloodfill;
    }

    public void setFloodfill(int floodfill) {
        isFloodfill = floodfill;
    }

    public int getKnownRouters() {
        return knownRouters;
    }

    public void setKnownRouters(int knownRouters) {
        this.knownRouters = knownRouters;
    }

    public int getKnownLeasesets() {
        return knownLeasesets;
    }

    public void setKnownLeasesets(int knownLeasesets) {
        this.knownLeasesets = knownLeasesets;
    }

    public String getRouterVersion() {
        return routerVersion;
    }

    public void setRouterVersion(String routerVersion) {
        this.routerVersion = routerVersion;
    }

    public String getCaps() {
        return caps;
    }

    public void setCaps(String caps) {
        this.caps = caps;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
