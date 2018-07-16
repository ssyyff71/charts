package mytest.demo.Mapper;

import mytest.demo.bean.NewStudent;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Elric on 2017/7/6.
 */
@Mapper
public interface newStudentInfoDao {
    List<NewStudent> getAllStudent();
}
