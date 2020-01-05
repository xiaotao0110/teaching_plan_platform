package com.hnieu.crtvn.controller.sys;

import com.hnieu.crtvn.util.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
public class LoginController {
	
	private final String SESSION_SECURITY_CODE = "imageCode";


	/**
	 * 跳转到登录页面
	 */
	@GetMapping(value = "/login")
	public String loginToView(Model model){
		model.addAttribute("code_msg", "");
		return "sys/login";
	}

	/**
	 * 首页
	 */
	@GetMapping(value = "/index")
	public String indexToView(Model model){
		return "sys/index";
	}


	/**
	 * 验证码
	 */
	@GetMapping(value = "/verificationCode")
	@ResponseBody
	public void verificationCode(HttpServletRequest req, HttpServletResponse response) {

		//todo: 保存到redis里面

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		String code = CaptchaUtil.drawImg(output);
		// 将四位数字的验证码保存到Session中。
		HttpSession session = req.getSession();
		session.setAttribute(SESSION_SECURITY_CODE, code);

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
