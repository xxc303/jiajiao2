package com.bdu.jiajiao.service.impl;

import com.bdu.jiajiao.dto.ResultDTO;
import com.bdu.jiajiao.mapper.StudentMapper;
import com.bdu.jiajiao.pojo.Student;
import com.bdu.jiajiao.service.StudentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 123
 * @create 2019/11/24
 * @since 1.0.0
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public List<Student> queryAllStudent(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Student> studentList = studentMapper.queryAllStudent();
        return studentList;
    }

    @Override
    public List<Student> queryAllStudents(int pageNum,int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Student> studentList = studentMapper.queryAllStudents();
        return studentList;
    }

    @Override
    public Student findByToken(String token) {
        return studentMapper.findByToken(token);
    }

    @Override
    public Student findById(int id) {
        return studentMapper.queryStudentById(id);
    }

    @Override
    public Student login(String username,String password) {
        Student student1 = studentMapper.login(username, password);
        return student1;
    }

    @Override
    public Student queryStudentByName(String username) {
        return studentMapper.queryStudentByUsername(username);
    }

    @Override
    public int addStudent(Student student) {
        student.setCreateTime(new Date());
        return studentMapper.addStudent(student);
    }

    @Override
    public int updateStudent(Student student) {

        return studentMapper.updateStudent(student);
    }

    @Override
    public List<Student> search(String item, String area, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Student> studentList = studentMapper.search(item, area);
        return studentList;
    }

    @Override
    public int delete(int id) {
        return studentMapper.delete(id);
    }
}
