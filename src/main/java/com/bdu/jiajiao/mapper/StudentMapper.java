package com.bdu.jiajiao.mapper;

import com.bdu.jiajiao.pojo.Student;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 123
 * @create 2019/11/22
 * @since 1.0.0
 */
public interface StudentMapper {
    List<Student> queryAllStudent();

    List<Student> queryAllStudents();

    Student login(@Param("username")String username,@Param("password")String password);

    Student queryStudentByUsername(@Param("username") String username);

    Student queryStudentById(@Param("id")int id);

    int addStudent(Student student);

    Student findByToken(@Param("token") String token);

    int updateStudent(Student student);

    Student queryStudentByIdNumber(@Param("idNumber") String idNumber);

    List<Student> search(@Param("item") String item, @Param("area") String area);

    int delete(@Param("id") int id);

    List<Student> queryStudentsInfo();
}
