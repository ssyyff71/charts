package mytest.demo.Service;

import mytest.demo.Mapper.newStudentInfoDao;
import mytest.demo.bean.NewStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Elric on 2017/7/6.
 */
@Service
public class NewStudentInfoService  {
    @Autowired
    private newStudentInfoDao studentInfoDao;
    public List<NewStudent> getAllStudent() {
        return studentInfoDao.getAllStudent();
    }
}
