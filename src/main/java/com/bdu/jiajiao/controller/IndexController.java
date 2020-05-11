package com.bdu.jiajiao.controller;

import com.bdu.jiajiao.mapper.StudentMapper;
import com.bdu.jiajiao.mapper.TeacherMapper;
import com.bdu.jiajiao.pojo.Student;
import com.bdu.jiajiao.pojo.Teacher;
import com.bdu.jiajiao.service.StudentService;
import com.bdu.jiajiao.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.List;

/**
 * @author 123
 * @create 2019/11/21
 * @since 1.0.0
 */
@Controller
public class IndexController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 登录页
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 首页
     */
    @GetMapping("/showIndex")
    public String showIndex(HttpServletRequest request, HttpSession session, Model model) {
        Object student = request.getSession().getAttribute("student");
        Object teacher = request.getSession().getAttribute("teacher");
        List<Teacher> teachers = teacherMapper.queryTeachersInfo();
        List<Student> students = studentMapper.queryStudentsInfo();
        List<Teacher> teacherByItem = teacherMapper.queryTeacherByItem("语文");
        List<Teacher> teacherByItem2 = teacherMapper.queryTeacherByItem("英语");
        List<Teacher> teacherByItem3 = teacherMapper.queryTeacherByItem("数学");
        model.addAttribute("teachers",teachers);
        model.addAttribute("students",students);
        model.addAttribute("teacherByItem",teacherByItem);
        model.addAttribute("teacherByItem2",teacherByItem2);
        model.addAttribute("teacherByItem3",teacherByItem3);
        session.setAttribute("url","showIndex");
        if (student != null){
            model.addAttribute("type","student");
        }else if (teacher != null){
            model.addAttribute("type","teacher");
        }
        return "showIndex";
    }

    @GetMapping("/dashBoard")
    public String dashBoard(HttpServletRequest request, Model model) {
        Object admin = request.getSession().getAttribute("admin");
        if (admin != null) {
            model.addAttribute("type","admin");
            model.addAttribute("admin",admin);
        }
        return "/dashboard";
    }

    /**
     * 退出登录
     */
    //@RequestMapping("/logout")
    //public String logout(HttpServletRequest request,
    //                     HttpServletResponse response){
    //    request.getSession().removeAttribute("student");
    //    Cookie cookie1 = new Cookie("token-student", null);
    //    Cookie cookie2 = new Cookie("token-teacher", null);
    //
    //    cookie1.setMaxAge(0);
    //    cookie2.setMaxAge(0);
    //    response.addCookie(cookie1);
    //    response.addCookie(cookie2);
    //    return "redirect:/";
    //}

}
