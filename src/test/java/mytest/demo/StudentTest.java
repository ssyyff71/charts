package mytest.demo;

import mytest.demo.Mapper.newStudentInfoDao;
import mytest.demo.bean.NewStudent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * created by suyifei on 2018-07-15
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentTest {
    @Autowired
    private newStudentInfoDao newStudentInfoDao;
    @Test
    public void test(){
        List<NewStudent> allStudent = newStudentInfoDao.getAllStudent();
        System.out.println(allStudent);
    }
}
