package mytest.demo.Mapper;

import mytest.demo.bean.TransData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * created by suyifei on 2018-07-02
 **/
@Mapper
public interface RouterMapper {
    int insert500(List<TransData> datas);

    /**
     * 将处理好的数据全部存入数据库
     * @param dataList
     * @param tName
     * @return
     */
    int insertAll(@Param("dataList") List<TransData> dataList, @Param("tName") String tName);

    int insertOne(TransData item);

    void deleteAll500();

    List<TransData> selectAll500();

    List<Map<String,Object>> countriesCount500();

    List<Map<String,Object>> versionCount500();

    List<Map<String,Object>> numberRank500();
}
