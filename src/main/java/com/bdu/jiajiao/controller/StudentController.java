package com.bdu.jiajiao.controller;

import com.bdu.jiajiao.dto.*;
import com.bdu.jiajiao.mapper.ArticleMapper;
import com.bdu.jiajiao.mapper.OrderMapper;
import com.bdu.jiajiao.mapper.StudentMapper;
import com.bdu.jiajiao.pojo.Article;
import com.bdu.jiajiao.pojo.Order;
import com.bdu.jiajiao.pojo.Student;
import com.bdu.jiajiao.pojo.Teacher;
import com.bdu.jiajiao.service.StudentService;
import com.bdu.jiajiao.service.TeacherService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author 123
 * @create 2019/11/22
 * @since 1.0.0
 */
@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @RequestMapping("/article/{id}")
    public String article(@PathVariable("id") int id,Model model,HttpServletRequest request) {
        Article article1 = articleMapper.queryById(id);
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        Student student = (Student) request.getSession().getAttribute("student");
        List<Article> articleList = articleMapper.queryAllStuArticle();
        if (teacher!= null){
            model.addAttribute("type", "teacher");
        }else if (student != null){
            model.addAttribute("type", "student");
        }
        model.addAttribute("article1",article1);
        model.addAttribute("articleList",articleList);
        return "article1";
    }

    /**
     * 分享功能
     * @param model
     * @return
     */
    @RequestMapping("/toPublish")
    public String toPublish(Model model){
        model.addAttribute("type", "student");
        return "publish";
    }

    @RequestMapping("/publish")
    public String publish(Article article, Model model, HttpServletRequest request){
        Student student = (Student) request.getSession().getAttribute("student");
        article.setCreator(student.getUsername());
        article.setCreatorId(student.getId());
        article.setCreateTime(new Date());
        article.setType(0);
        articleMapper.addArticle(article);
        model.addAttribute("type", "student");
        return "redirect:/student/toPublish";
    }

    /**
     * 取消订单
     */
    @RequestMapping("/deleteOrder/{teaName}")
    public String deleteOrder(@PathVariable("teaName") String teaName, Model model, HttpServletRequest request) {
        Student student = (Student) request.getSession().getAttribute("student");
        Order order = orderMapper.queryOrder(teaName, student.getUsername());
        //取消订单
        order.setType(1);
        orderMapper.updateOrder(order);
        model.addAttribute("type", "student");
        return "redirect:/student/toMyOrders";
    }

    /**
     * 预约
     */
    @ResponseBody
    @RequestMapping(value = "/reserve", method = RequestMethod.POST)
    public Object reserve(@RequestBody OrderDTO orderDTO,
                          HttpServletRequest request) {
        //通过前端传id查询老师
        Teacher teacherdb = teacherService.findById(orderDTO.getTeacherId());
        Student student = (Student) request.getSession().getAttribute("student");
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        if (student == null && teacher == null) {
            return ResultDTO.errorOf(500, "请登录");
        }
        Order order = new Order();
        order.setTeaName(teacherdb.getUsername());
        order.setTeaPhone(teacherdb.getPhone());
        order.setStuName(student.getUsername());
        order.setStuPhone(student.getPhone());
        order.setItem(student.getItem());
        order.setAddress(student.getAddress());
        order.setPrice(student.getPrice());
        order.setCreateTime(new Date());
        order.setType(0);
        orderMapper.addOrder(order);
        return ResultDTO.okOf();
    }

    /**
     * 搜索功能
     */
    @PostMapping("/search")
    public String search(Model model,
                         String item,String area,
                         @RequestParam(defaultValue = "1") int pageNum,
                         @RequestParam(defaultValue = "10") int pageSize) {
        List<Student> students = studentService.search(item, area, pageNum, pageSize);
        PageInfo<Student> pageInfo = new PageInfo<>(students);

        List<Article> articleList = articleMapper.queryAllStuArticle();
        List<Student> studentsInfo = studentMapper.queryStudentsInfo();
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("articleList",articleList);
        model.addAttribute("studentsInfo",studentsInfo);
        model.addAttribute("type", "student");
        return "student";
    }

    /**
     * 密码
     */
    @RequestMapping("/toChangePwd")
    public String toChangePwd(Model model) {
        model.addAttribute("type", "student");
        return "changePassword";
    }

    /**
     * 修改密码
     */
    @RequestMapping("changePWD")
    public String changePassword(PasswordDTO passwordDTO, Model model, HttpServletRequest request, RedirectAttributes modelMap) {
        model.addAttribute("type", "student");
        Student student = (Student) request.getSession().getAttribute("student");
        if (student.getPassword().equals(passwordDTO.getOldPassword())) {
            student.setPassword(passwordDTO.getNewPassword());
            studentService.updateStudent(student);
            modelMap.addFlashAttribute("msg_success", "修改成功");
        } else {
            modelMap.addFlashAttribute("msg_fail", "原密码错误");
        }
        return "redirect:/student/toChangePwd";
    }

    /**
     * 查看个人信息
     */
    @RequestMapping("/toMyDesc")
    public String toMyDesc(Model model, HttpServletRequest request) {
        model.addAttribute("type", "student");
        Student student = (Student) request.getSession().getAttribute("student");
        model.addAttribute("student", student);
        return "myDesc";
    }

    /**
     * 修改个人信息
     */
    @RequestMapping("/updateUser")
    public String updateUser(BaseInfoDTO baseInfoDTO, Model model, HttpServletRequest request) {
        model.addAttribute("type", "student");
        Student student = (Student) request.getSession().getAttribute("student");
        model.addAttribute("student", student);
        BeanUtils.copyProperties(baseInfoDTO, student);
        studentService.updateStudent(student);
        return "redirect:/student/toMyDesc";
    }

    /**
     * 查看我的家教订单
     */
    @RequestMapping("/toMyOrders")
    public String toMyOrders(Model model, HttpServletRequest request) {
        model.addAttribute("type", "student");
        Student student = (Student) request.getSession().getAttribute("student");
        List<Order> orders = orderMapper.queryByStuName(student.getUsername());
        model.addAttribute("orders", orders);
        return "myOrders";
    }

    /**
     * 查看家教信息（publicOrder）
     */
    @RequestMapping("/toPublicOrder")
    public String toPublicOrder(Model model, HttpServletRequest request) {
        model.addAttribute("type", "student");
        Student student = (Student) request.getSession().getAttribute("student");
        Student student1 = studentService.findById(student.getId());
        model.addAttribute("student", student1);
        return "publicOrder";
    }

    /**
     * 修改/发布学生信息
     */
    @RequestMapping("/pubInfo")
    public String pubInfo(StuPublicOrderDTO stuPublicOrderDTO, Model model, HttpServletRequest request) {
        model.addAttribute("type", "student");
        Student student = (Student) request.getSession().getAttribute("student");
        model.addAttribute("student", student);
        if (student.getItem() != null) {
            //修改信息
            BeanUtils.copyProperties(stuPublicOrderDTO, student);
            studentService.updateStudent(student);
        } else {
            //发布信息
            BeanUtils.copyProperties(stuPublicOrderDTO, student);
            student.setStatus(1);
            studentService.addStudent(student);
        }
        return "redirect:/student/toPublicOrder";
    }

    /**
     * 个人主页
     */
    @RequestMapping("/toSetting")
    public String toSetting(Model model) {
        model.addAttribute("type", "student");
        return "main";
    }


    /**
     * 学生详细信息
     */
    @RequestMapping("/toDetail/{id}")
    public String todetail(Model model, @PathVariable(name = "id") int id) {
        Student student = studentMapper.queryStudentById(id);
        List<Article> articleList = articleMapper.queryAllStuArticle();
        List<Student> studentsInfo = studentMapper.queryStudentsInfo();

        model.addAttribute("student", student);
        model.addAttribute("articleList", articleList);
        model.addAttribute("studentsInfo", studentsInfo);
        return "studentDetail";
    }

    /**
     * 登录
     */
    @RequestMapping("/toLogin")
    public String toLogin(HttpSession session, Model model) {
        model.addAttribute("title", "家长/学生");
        model.addAttribute("type", "student");
        return "login";
    }

    @RequestMapping("login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletResponse response,
                        HttpServletRequest request,
                        RedirectAttributes modelMap) {

        Student student = studentService.login(username, password);
        if (student == null) {
            modelMap.addFlashAttribute("msg", "用户名或密码错误");
            return "redirect:/student/toLogin";
        } else {
            String token = UUID.randomUUID().toString();
            // Student dbStudent = studentService.findById(student.getId());
            // dbStudent.setToken(token);
            student.setToken(token);
            studentService.updateStudent(student);
            Cookie cookie = new Cookie("token-student", token);
            response.addCookie(cookie);
            request.getSession().setAttribute("student", student);
            modelMap.addFlashAttribute("type", "student");
            return "redirect:/showIndex";
        }
    }

    /**
     * 注册
     */
    @RequestMapping("/toRegister")
    public String toRegister(Model model) {
        model.addAttribute("title", "家长/学生");
        model.addAttribute("type", "student");
        return "register";
    }

    @RequestMapping("register")
    public String Register(Student student, RedirectAttributes modelMap) {
        if (studentService.queryStudentByName(student.getUsername()) != null) {
            modelMap.addFlashAttribute("msg1", "用户名已存在，注册失败");
            System.out.println(student.getUsername());
            return "redirect:/student/toRegister";
        }
        int result = studentService.addStudent(student);
        if (result != 1) {
            modelMap.addFlashAttribute("msg", "未知错误，请联系管理员");
            return "redirect:/student/toRegister";
        }
        modelMap.addFlashAttribute("msg2", "注册成功，请登录");
        return "redirect:/student/toLogin";
    }


    /**
     * 退出登录
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("student");
        request.getSession().removeAttribute("teacher");
        Cookie cookie = new Cookie("token-student", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

    /**
     * 学生列表
     */
    @GetMapping("/student")
    public String students(Model model,
                           @RequestParam(defaultValue = "1") int pageNum,
                           @RequestParam(defaultValue = "1") int pageSize) {
        List<Student> studentsList = studentService.queryAllStudent(pageNum, pageSize);
        PageInfo<Student> pageInfo = new PageInfo<>(studentsList);

        List<Article> articleList = articleMapper.queryAllStuArticle();
        List<Student> studentsInfo = studentMapper.queryStudentsInfo();
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("type", "student");
        model.addAttribute("articleList", articleList);
        model.addAttribute("studentsInfo",studentsInfo);
        return "student";
    }

    //@ResponseBody
    //@RequestMapping("/users")
    //public PageInfo<Student> lists(@RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "10") int pageSize) {
    //    List<Student> studentList = studentService.queryAllStudent(pageNum, pageSize);
    //    PageInfo<Student> students = new PageInfo<>(studentList);
    //
    //    System.out.println(students);
    //    return students;
    //}

    //@RequestMapping("toUpload")
    //public String toUpload(Model model) {
    //    model.addAttribute("type", "student");
    //    return "studentUpload";
    //}
    //
    //@RequestMapping("studentUpload")
    //public String upLoad(Student student, RedirectAttributes modelMap) {
    //    student.setCreateTime(new Date());
    //    int idNumber = new Random().nextInt(999999);
    //    student.setIdNumber(idNumber + "");
    //    studentService.addStudent(student);
    //    modelMap.addFlashAttribute("msg2", "注册成功，请登录");
    //    return "redirect:/teacher/toLogin";
    //}


}
