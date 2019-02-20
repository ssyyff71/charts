package mytest.demo.Mapper;

import mytest.demo.bean.TransData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * created by suyifei on 2018-07-02
 **/
@Mapper
public interface RouterMapper1000 {
    int insert1000(List<TransData> datas);

    int insertOne(TransData item);

    void deleteAll1000();

    List<TransData> selectAll1000();

    List<Map<String,Object>> countriesCount1000();

    List<Map<String,Object>> versionCount1000();

    List<Map<String,Object>> numberRank1000();
}
