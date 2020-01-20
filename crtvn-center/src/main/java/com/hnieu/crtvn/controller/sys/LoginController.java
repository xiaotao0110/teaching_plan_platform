package com.hnieu.crtvn.controller.sys;

import com.hnieu.crtvn.entity.Academician;
import com.hnieu.crtvn.entity.Admin;
import com.hnieu.crtvn.entity.Course;
import com.hnieu.crtvn.service.IAcademicianService;
import com.hnieu.crtvn.service.IAdminService;
import com.hnieu.crtvn.util.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value = "/login")
@SuppressWarnings("unchecked")
public class LoginController {

    private final String SESSION_SECURITY_CODE = "imageCode";

    @Autowired
    public IAdminService adminService;

    @Autowired
    public IAcademicianService academicianService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CaptchaUtil captchaUtil;

    /**
     * 管理员登录验证
     */
    @RequestMapping(value = "/adminLogin")
    public String login(Admin admin, String code, final RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();

        String imageCode = (String) session.getAttribute(SESSION_SECURITY_CODE);
        if (code == null || code.length() == 0) {
            redirectAttributes.addFlashAttribute("code_msg", "验证码错误！");
            return "login";
        } else {
            if (code.toUpperCase().equals(imageCode.toUpperCase())) {
                Admin adminBean = adminService.findAdminByName(admin);
                if (adminBean != null) {
                    session.setAttribute("admin", adminBean);
                    return "admin/adminInfo";
                }
                redirectAttributes.addFlashAttribute("code_msg", "密码或账号错误！");
                return "login";
            } else {
                redirectAttributes.addFlashAttribute("code_msg", "验证码错误！");
                return "login";
            }
        }
    }

    /**
     * 教务人员登录验证
     */
    @ResponseBody
    @RequestMapping(value = "/academicianLogin")
    public String login(Academician academician, String code, Course course, final RedirectAttributes redirectAttributes) {

        HttpSession session = request.getSession();

        String imageCode = (String) session.getAttribute(SESSION_SECURITY_CODE);
        if (code == null || code.length() == 0) {
            redirectAttributes.addFlashAttribute("code_msg", "验证码错误！");
            return "login";
        } else {
            if (code.toUpperCase().equals(imageCode.toUpperCase())) {
                Academician academicianBean = academicianService.findAcademicianByName(academician);
                if (academicianBean != null) {

                    //记录时间
                    session.setAttribute("course", course);
                    session.setAttribute("academician", academicianBean);

                    // TODO: 2020/1/8  以后存入token或者redis ，废弃session方式  ,替换调用session的地方
                    redisTemplate.boundValueOps("crtvn:course").set(course, 10, TimeUnit.MINUTES);
                    redisTemplate.boundValueOps("crtvn:academician").set(academicianBean, 10, TimeUnit.MINUTES);


                    redirectAttributes.addFlashAttribute("adminName", academicianBean.getName());
                    return "sys/index";
                }
                redirectAttributes.addFlashAttribute("code_msg", "密码或账号错误！");
                return "login";

            } else {
                redirectAttributes.addFlashAttribute("code_msg", "验证码错误！");
                return "login";
            }
        }

    }


    /**
     * 验证码
     */
    @RequestMapping(value = "/verificationCode")
    @ResponseBody
    public void verificationCode(HttpServletRequest req, HttpServletResponse response) {

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        String code = captchaUtil.drawImg(output);

        // 将四位数字的验证码保存到Session中。
        HttpSession session = req.getSession();
        session.setAttribute(SESSION_SECURITY_CODE, code);


        // TODO: 2020/1/5  以后存入redis，为数组结构，判断是否存在
        //redisTemplate.boundValueOps("");

        try {
            // 将图像输出到Servlet输出流中
            ServletOutputStream out = response.getOutputStream();
            output.writeTo(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
