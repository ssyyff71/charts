package mytest.demo.Mapper;

import mytest.demo.bean.TransData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * created by suyifei on 2018-07-02
 **/
@Mapper
public interface RouterMapper5000 {
    int insert5000(List<TransData> datas);

    int insertOne(TransData item);

    void deleteAll5000();

    List<TransData> selectAll5000();

    List<Map<String,Object>> countriesCount5000();

    List<Map<String,Object>> versionCount5000();

    List<Map<String,Object>> numberRank5000();
}
