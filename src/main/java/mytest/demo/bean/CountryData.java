package mytest.demo.bean;

/**
 * created by suyifei on 2018-07-15
 **/
public class CountryData {
    private String country;
    private Integer count;

    public CountryData(String country, Integer count) {
        this.country = country;
        this.count = count;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
