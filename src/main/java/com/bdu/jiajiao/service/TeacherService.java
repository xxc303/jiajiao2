package com.bdu.jiajiao.service;

import com.bdu.jiajiao.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 123
 * @version 1.0
 * @date 2020 2020/1/13 13:30
 */
public interface TeacherService {

    List<Teacher> queryAllTeacher(int pageNum, int pageSize);

    Teacher login(String username, String password);

    int updateTeacher(Teacher teacher);

    Teacher findByToken(String token);

    Teacher findById(int id);

    int addTeacher(Teacher teacher);

    List<Teacher> search(String item, String area,  int pageNum, int pageSize);

    int delete(int id);
}
