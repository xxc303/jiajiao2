package com.bdu.jiajiao.controller;

import com.bdu.jiajiao.dto.*;
import com.bdu.jiajiao.mapper.CommentMapper;
import com.bdu.jiajiao.mapper.ArticleMapper;
import com.bdu.jiajiao.mapper.OrderMapper;
import com.bdu.jiajiao.mapper.TeacherMapper;
import com.bdu.jiajiao.pojo.*;
import com.bdu.jiajiao.service.StudentService;
import com.bdu.jiajiao.service.TeacherService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author 123
 * @create 2019/11/21
 * @since 1.0.0
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @RequestMapping("/article/{id}")
    public String article(@PathVariable("id") int id,Model model,HttpServletRequest request){
        Article article1 = articleMapper.queryById(id);
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        Student student = (Student) request.getSession().getAttribute("student");
        Teacher teacher1 = teacherService.findById(article1.getCreatorId());
        List<Article> articleList = articleMapper.queryAllTeaArticle();
        if (teacher!= null){
            model.addAttribute("type", "teacher");
        }else if (student != null){
            model.addAttribute("type", "student");
        }
        model.addAttribute("article1",article1);
        model.addAttribute("teacher1",teacher1);
        model.addAttribute("articleList",articleList);
        return "article";
    }

    /**
     * 分享文章功能
     * @param model
     * @return
     */
    @RequestMapping("/toPublish")
    public String toPublish(Model model){
        model.addAttribute("type", "teacher");
        return "publish";
    }

    @RequestMapping("/publish")
    public String publish(Article article, Model model, HttpServletRequest request,RedirectAttributes modelMap){
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
            article.setCreator(teacher.getUsername());
            article.setCreatorId(teacher.getId());
            article.setCreateTime(new Date());
            article.setType(1);
        articleMapper.addArticle(article);
        model.addAttribute("type", "teacher");
        modelMap.addFlashAttribute("msg","发布成功");
        return "redirect:/teacher/toPublish";
    }

    /**
     * 取消订单
     */
    @RequestMapping("/deleteOrder/{studentName}")
    public String deleteOrder(@PathVariable("studentName") String studentName, Model model, HttpServletRequest request) {
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        Order order = orderMapper.queryOrder(teacher.getUsername(),studentName);
        //取消订单
        order.setType(1);
        orderMapper.updateOrder(order);
        model.addAttribute("type", "teacher");
        return "redirect:/teacher/toMyOrders";
    }

    /**
     * 预约
     */
    @ResponseBody
    @RequestMapping("/teaReserve")
    public Object teaReserve(@RequestBody OrderDTO orderDTO){
        Order order = new Order();
        BeanUtils.copyProperties(orderDTO, order);
        order.setCreateTime(new Date());
        order.setType(0);
        int count = orderMapper.addOrder(order);
        if(count < 0){
            return ResultDTO.errorOf(500, "预约失败！");
        }else {
            return ResultDTO.okOf();
        }
    }
    //@RequestMapping("/teaReserve")
    //public Object teaReserve(@RequestBody OrderDTO orderDTO,
    //                         HttpServletRequest request){
    //    Student studentdb = studentService.findById(orderDTO.getStudentId());
    //    Student student = (Student) request.getSession().getAttribute("student");
    //    Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
    //    if (student == null && teacher == null) {
    //        return ResultDTO.errorOf(500, "请登录");
    //    }
    //    Order order = new Order();
    //    order.setTeaName(teacher.getUsername());
    //    order.setTeaPhone(teacher.getPhone());
    //    order.setStuName(studentdb.getUsername());
    //    order.setStuPhone(studentdb.getPhone());//
    //    order.setItem(studentdb.getItem());//
    //    order.setAddress(studentdb.getAddress());//
    //    order.setPrice(studentdb.getPrice());
    //    order.setCreateTime(new Date());
    //    order.setType(0);
    //    orderMapper.addOrder(order);
    //    return ResultDTO.okOf();
    //}


    /**
     * 查询
     */
    @RequestMapping("/search")
    public String search(Model model,
                         String item,String area,
                         @RequestParam(defaultValue = "1")int pageNum,
                         @RequestParam(defaultValue = "10")int pageSize){
        List<Teacher> teachers = teacherService.search(item, area, pageNum, pageSize);
        PageInfo<Teacher> pageInfo = new PageInfo<>(teachers);

        List<Article> articleList = articleMapper.queryAllTeaArticle();
        List<Order> ordersInfo = orderMapper.queryOrderInfo();
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("type","teacher");
        model.addAttribute("search","search");
        model.addAttribute("articleList",articleList);
        model.addAttribute("ordersInfo",ordersInfo);
        return "teacher";
    }
    /**
     * 密码
     */
    @RequestMapping("/toChangePwd")
    public String toChangePwd(Model model) {
        model.addAttribute("type", "teacher");
        return "changePassword";
    }

    /**
     * 修改密码
     */
    @RequestMapping("changePWD")
    public String changePassword(PasswordDTO passwordDTO, Model model, HttpServletRequest request, RedirectAttributes modelMap) {
        model.addAttribute("type", "teacher");
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        if (teacher.getPassword().equals(passwordDTO.getOldPassword())) {
            teacher.setPassword(passwordDTO.getNewPassword());
            teacherService.updateTeacher(teacher);
            modelMap.addFlashAttribute("msg_success", "修改成功");
        } else {
            modelMap.addFlashAttribute("msg_fail", "原密码错误");
        }
        return "redirect:/teacher/toChangePwd";
    }

    /**
     * 查看个人信息
     */
    @RequestMapping("/toMyDesc")
    public String toMyDesc(Model model, HttpServletRequest request) {
        model.addAttribute("type", "teacher");
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        model.addAttribute("teacher", teacher);
        return "teaMyDesc";
    }

    /**
     * 修改个人信息
     */
    @RequestMapping("/updateTeacher")
    public String updateTeacher(BaseInfoDTO baseInfoDTO, Model model, HttpServletRequest request) {
        model.addAttribute("type", "teacher");
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        BeanUtils.copyProperties(baseInfoDTO, teacher);
        teacherService.updateTeacher(teacher);
        return "redirect:/teacher/toMyDesc";
    }


    /**
     * 查看我的家教订单
     */
    @RequestMapping("/toMyOrders")
    public String toMyOrders(Model model, HttpServletRequest request) {
        model.addAttribute("type", "teacher");
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        List<Order> orders = orderMapper.queryByTeaName(teacher.getUsername());
        model.addAttribute("orders",orders);
        return "myOrders";
    }

    /**
     * 查看个人发布信息（publicOrder）
     */
    @RequestMapping("/toPublicOrder")
    public String toPublicOrder(Model model, HttpServletRequest request) {
        model.addAttribute("type", "teacher");
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        Teacher teacher1 = teacherService.findById(teacher.getId());
        model.addAttribute("teacher", teacher1);
        return "teaPublicOrder";
    }

    /**
     * 发布/修改家教信息
     */
    @RequestMapping("/pubInfo")
    public String pubInfo(TeaPublicOrderDTO teaPublicOrderDTO, Model model, HttpServletRequest request,RedirectAttributes modelMap) {
        model.addAttribute("type", "teacher");
        Teacher teacher = (Teacher) request.getSession().getAttribute("teacher");
        model.addAttribute("teacher", teacher);
        if (teacher.getItem() != null) {
            //修改发布信息
            BeanUtils.copyProperties(teaPublicOrderDTO, teacher);
            teacherService.updateTeacher(teacher);
            modelMap.addFlashAttribute("msg_success", "修改成功");
        } else {
            //发布修改信息
            BeanUtils.copyProperties(teaPublicOrderDTO, teacher);
            teacher.setStatus(1);
            teacherService.addTeacher(teacher);
        }
        return "redirect:/teacher/toPublicOrder";
    }

    /**
     * 个人主页
     */
    @RequestMapping("/toSetting")
    public String toSetting(Model model) {
        model.addAttribute("type", "teacher");
        return "main";
    }

    /**
     * 教师详情页的展示
     */
    @RequestMapping("/toDetail/{id}")
    public String toDetail(Model model, @PathVariable(name = "id") int id) {
        model.addAttribute("type", "teacher");
        Teacher teacher = teacherMapper.queryTeacherById(id);
        String[] items = teacher.getItem().trim().split(" ");
        List<Comment> comments = commentMapper.queryCommentByParentId(id);
        int counts = commentMapper.countComment(id);
        List<Article> articleList = articleMapper.queryAllTeaArticle();
        List<Order> ordersInfo = orderMapper.queryOrderInfo();
        model.addAttribute("items", items);
        model.addAttribute("teacher", teacher);
        model.addAttribute("comments", comments);
        model.addAttribute("count", counts);
        model.addAttribute("articleList", articleList);
        model.addAttribute("ordersInfo",ordersInfo);
        return "teacherDetail";
    }

    /**
     * 登录
     */
    @RequestMapping("/toLogin")
    public String toLogin(HttpSession session, Model model) {
        model.addAttribute("title", "家教老师");
        model.addAttribute("type", "teacher");
        return "login";
    }

    @RequestMapping("login")
    public String Login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletResponse response,
                        HttpServletRequest request,
                        RedirectAttributes modelMap,
                        Model model) {
        Teacher teacher = teacherService.login(username, password);
        if (teacher == null) {
            modelMap.addFlashAttribute("msg", "用户名或密码错误");
            return "redirect:/teacher/toLogin";
        } else {
            String token = UUID.randomUUID().toString();
            teacher.setToken(token);
            teacherService.updateTeacher(teacher);
            Cookie cookie = new Cookie("token-teacher", token);
            response.addCookie(cookie);
            request.getSession().setAttribute("teacher", teacher);
            modelMap.addFlashAttribute("type", "teacher");
            return "redirect:/showIndex";
        }

    }

    /**
     * 注册
     */
    @RequestMapping("/toRegister")
    public String toRegister(Model model) {
        model.addAttribute("title", "家教老师");
        model.addAttribute("type", "teacher");
        return "register";
    }

    @RequestMapping("register")
    public String Register(Teacher teacher, RedirectAttributes modelMap, HttpSession session) {
        if (teacherMapper.queryTeacherByName(teacher.getUsername()) != null) {
            modelMap.addFlashAttribute("msg1", "用户名已存在，注册失败");
            System.out.println(teacher.getUsername());
            return "redirect:/teacher/toRegister";
        }
        int result = teacherMapper.addTeacher(teacher);
        if (result != 1) {
            modelMap.addFlashAttribute("msg", "未知错误，请联系管理员");
            return "redirect:/teacher/toRegister";
        }

        modelMap.addAttribute("type", "teacher");
        modelMap.addFlashAttribute("msg2", "注册成功，请登录");
        return "redirect:/teacher/toLogin";
    }


    /**
     * 退出登录
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("teacher");
        request.getSession().removeAttribute("student");
        Cookie cookie = new Cookie("token-teacher", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

    /**
     * 教师列表
     * @param model
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/teacher")
    public String teachers(Model model,
                           @RequestParam(defaultValue = "1") int pageNum,
                           @RequestParam(defaultValue = "1") int pageSize) {
        List<Teacher> teachersList = teacherService.queryAllTeacher(pageNum, pageSize);
        PageInfo<Teacher> pageInfo = new PageInfo<>(teachersList);

        List<Article> articleList = articleMapper.queryAllTeaArticle();
        List<Order> ordersInfo = orderMapper.queryOrderInfo();
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("articleList", articleList);
        model.addAttribute("ordersInfo",ordersInfo);
        model.addAttribute("type", "teacher");
        return "teacher";
    }


    /** @RequestMapping("/login") public String login(HttpSession session, String username, String password, RedirectAttributes modelMap){

    Teacher teacher = teacherService.login(username, password);
    if(teacher == null){
    modelMap.addFlashAttribute("msg","用户名或密码错误");
    modelMap.addFlashAttribute("msg_type","danger");
    modelMap.addFlashAttribute("title","家教老师");
    modelMap.addFlashAttribute("type","teacher");
    return "redirect:/teacher/toLogin";
    }
    if(teacher.getStatus() < 0 ){
    session.setAttribute("user",teacher);
    return "redirect:/teacher/toUpload";
    }
    session.setAttribute("user",teacher);
    session.setAttribute("type","teacher");
    return "redirect:/teacher/toMain";
    }*/

    //@RequestMapping("toUpload")
    //    public String toUpload(Model model){
    //        model.addAttribute("type","teacher");
    //        return "teacherUpload";
    //    }
    //
    //@RequestMapping("teacherUpload")
    //public String upLoad(Teacher teacher, @RequestBody int id,RedirectAttributes modelMap){
    //    Teacher teacher1 = teacherMapper.queryTeacherById(id);
    //    teacher1.setCreateTime(new Date());
    //    int idNumber = new Random().nextInt(999999);
    //    teacher1.setIdNumber(idNumber+"");
    //    BeanUtils.copyProperties(teacher,teacher1);
    //    teacherMapper.addTeacher(teacher1);
    //    modelMap.addFlashAttribute("msg2","注册成功，请登录");
    //    return "redirect:/teacher/toLogin";
    //}
}
