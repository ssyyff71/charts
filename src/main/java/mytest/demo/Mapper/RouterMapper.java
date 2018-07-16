package mytest.demo.Mapper;

import mytest.demo.bean.TransData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * created by suyifei on 2018-07-02
 **/
@Mapper
public interface RouterMapper {
    int insert(List<TransData> datas);

    int insertOne(TransData item);

    void deleteAll();

    List<Map<String,Object>> countriesCount();
}
