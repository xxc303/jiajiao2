package com.bdu.jiajiao.controller;

import com.bdu.jiajiao.dto.PasswordDTO;
import com.bdu.jiajiao.mapper.ArticleMapper;
import com.bdu.jiajiao.mapper.CommentMapper;
import com.bdu.jiajiao.pojo.*;
import com.bdu.jiajiao.service.AdminService;
import com.bdu.jiajiao.service.CommentService;
import com.bdu.jiajiao.service.StudentService;
import com.bdu.jiajiao.service.TeacherService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author 123
 * @create 2019/11/23
 * @since 1.0.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentMapper commentMapper;


    /**
     * 修改密码
     */
    @RequestMapping("/toChangePwd")
    public String toCPW(Model model) {
        model.addAttribute("type", "admin");
        return "/admin/changePassword";
    }

    @RequestMapping("/changePWD")
    public String changePWD(PasswordDTO passwordDTO, Model model, HttpServletRequest request, RedirectAttributes modelMap) {
        model.addAttribute("type", "admin");
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        if (admin.getPassword().equals(passwordDTO.getOldPassword())) {
            admin.setPassword(passwordDTO.getNewPassword());
            adminService.updateAdmin(admin);
            modelMap.addFlashAttribute("msg_success", "修改成功");
        } else {
            modelMap.addFlashAttribute("msg_fail", "原密码错误");
        }
        return "redirect:/admin/toChangePwd";
    }

    /**
     * 分享功能
     *
     * @param model
     * @return
     */
    @RequestMapping("/toPublish")
    public String toPublish(Model model) {
        model.addAttribute("type", "admin");
        return "/admin/publish";
    }

    @RequestMapping("/publish")
    public String publish(Article article, Model model, HttpServletRequest request) {
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        article.setCreator(admin.getUsername());
        article.setCreatorId(admin.getId());
        article.setCreateTime(new Date());
        article.setType(3);
        articleMapper.addArticle(article);
        model.addAttribute("type", "admin");
        return "redirect:/admin/toPublish";
    }

    /**
     * 评论删除
     */
    @RequestMapping("deleteComment/{id}")
    public String deleteComment(Model model, @PathVariable("id") int id) {
        commentMapper.deleteCommentById(id);
        model.addAttribute("type", "admin");
        return "redirect:/admin/commentInfo";
    }

    /**
     * 评论管理
     */
    @RequestMapping("/commentInfo")
    public String comment(Model model, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "1") int pageSize) {
        List<Comment> comments = commentService.queryAllComment(pageNum,pageSize);
        PageInfo<Comment> commentList = new PageInfo<>(comments);
        model.addAttribute("commentList", commentList);
        model.addAttribute("type", "admin");
        return "/admin/commentInfo";
    }

    /**
     * 回复删除
     */
    @RequestMapping("deleteReply/{id}")
    public String deleteReply(Model model, @PathVariable("id") int id) {
        commentMapper.deleteReplyById(id);
        model.addAttribute("type", "admin");
        return "redirect:/admin/replyInfo";
    }

    /**
     * 回复管理
     */
    @RequestMapping("/replyInfo")
    public String reply(Model model, @RequestParam(defaultValue = "1") int pageNum, @RequestParam(defaultValue = "1") int pageSize){
        List<Reply> replies = commentService.queryAllReply(pageNum,pageSize);
        PageInfo<Reply> replyList = new PageInfo<>(replies);
        model.addAttribute("replyList", replyList);
        model.addAttribute("type", "admin");
        return "/admin/replyInfo";
    }

    /**
     * 学员删除
     */
    @RequestMapping("deleteStu/{id}")
    public String deleteStu(Model model, @PathVariable("id") int id) {
        Student student = studentService.findById(id);
        student.setStatus(0);
        studentService.updateStudent(student);
        model.addAttribute("type", "admin");
        return "redirect:/admin/studentInfo";
    }

    /**
     * 教师删除
     */
    @RequestMapping("/deleteTea/{id}")
    public String deleteTea(Model model, @PathVariable("id") int id) {
        Teacher teacher = teacherService.findById(id);
        teacher.setStatus(0);
        teacherService.updateTeacher(teacher);
        model.addAttribute("type", "admin");
        return "redirect:/admin/teacherInfo";
    }

    /**
     * 家教信息
     */
    @RequestMapping("/teacherInfo")
    public String teacherInfo(Model model,
                              @RequestParam(defaultValue = "1") int pageNum,
                              @RequestParam(defaultValue = "1") int pageSize) {
        List<Teacher> teachers = teacherService.queryAllTeacher(pageNum, pageSize);
        PageInfo<Teacher> pageInfo = new PageInfo<>(teachers);

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("type", "admin");
        return "/admin/teacherInfo";
    }

    /**
     * 学生信息
     */
    @RequestMapping("/studentInfo")
    public String studentInfo(Model model,
                              @RequestParam(defaultValue = "1") int pageNum,
                              @RequestParam(defaultValue = "1") int pageSize) {
        List<Student> students = studentService.queryAllStudent(pageNum, pageSize);
        PageInfo<Student> pageInfo = new PageInfo<>(students);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("type", "admin");
        return "/admin/studentInfo";
    }

    /**
     * 登录
     */
    @RequestMapping("/toLogin")
    public String toLogin(HttpSession session, Model model) {
        model.addAttribute("title", "管理员");
        model.addAttribute("type", "admin");
        return "login";
    }

    @RequestMapping("login")
    public String Login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpServletResponse response,
                        HttpServletRequest request,
                        RedirectAttributes modelMap,
                        Model model) {
        Admin admin = adminService.login(username, password);
        if (admin == null) {
            modelMap.addFlashAttribute("msg", "用户名或密码错误");
            return "redirect:/admin/toLogin";
        } else {
            String token = UUID.randomUUID().toString();
            admin.setToken(token);
            adminService.updateAdmin(admin);
            Cookie cookie = new Cookie("token-admin", token);
            response.addCookie(cookie);
            request.getSession().setAttribute("admin", admin);
            modelMap.addFlashAttribute("type", "admin");
            return "redirect:/admin/dashBoard";
        }
    }

    @GetMapping("/dashBoard")
    public String dashBoard(HttpServletRequest request, Model model) {
        Object admin = request.getSession().getAttribute("admin");
        if (admin != null) {
            model.addAttribute("type", "admin");
            model.addAttribute("admin", admin);
        }
        return "/dashboard";
    }

    /**
     * 退出登录
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("admin");
        Cookie cookie = new Cookie("token-admin", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
