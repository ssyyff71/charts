package mytest.demo.bean;

/**
 * Created by Elric on 2017/7/6.
 */
public class NewStudent {
    public Integer id;
    public String name;
    public Integer score;
    public Integer subject;
    public Integer teacherId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getSubject() {
        return subject;
    }

    public void setSubject(Integer subject) {
        this.subject = subject;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public String toString() {
        return "NewStudent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", score=" + score +
                ", subject=" + subject +
                ", teacheridl=" + teacherId +
                '}';
    }
}
