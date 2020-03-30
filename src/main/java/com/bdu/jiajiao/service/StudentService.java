package com.bdu.jiajiao.service;

import com.bdu.jiajiao.pojo.Student;

import java.util.List;

/**
 * @author 123
 * @create 2019/11/24
 * @since 1.0.0
 */
public interface StudentService {

    List<Student> queryAllStudent(int pageNum,int pageSize);

    Student findByToken(String token);

    Student findById(int id);

    Student login(String username, String password);

    Student queryStudentByName(String username);

    int addStudent(Student student);

    int updateStudent(Student student);

    List<Student> search(String param,int pageNum,int pageSize);

    int delete(int id);
}
