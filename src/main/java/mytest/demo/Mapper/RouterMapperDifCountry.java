package mytest.demo.Mapper;

import mytest.demo.bean.TransData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * created by suyifei on 2018-07-02
 **/
@Mapper
public interface RouterMapperDifCountry {

    int insertAll(List<TransData> dataList);

}
