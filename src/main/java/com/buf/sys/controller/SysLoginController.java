package com.buf.sys.controller;


import com.buf.common.utils.R;
import com.buf.main.controller.BaseController;
import com.buf.sys.shiro.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录相关
 *
 *
 *
 * @date 2016年11月10日 下午1:15:31
 */
@Controller
public class SysLoginController extends BaseController {
//	@Autowired
//	private Producer producer;
//
//	@RequestMapping("captcha.jpg")
//	public void captcha(HttpServletResponse response)throws IOException {
//        response.setHeader("Cache-Control", "no-store, no-cache");
//        response.setContentType("image/jpeg");
//
//        //生成文字验证码
//        String text = producer.createText();
//        //生成图片验证码
//        BufferedImage image = producer.createImage(text);
//        //保存到shiro session
//        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
//
//        ServletOutputStream out = response.getOutputStream();
//        ImageIO.write(image, "jpg", out);
//	}

	/**
	 * 登录
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "admin/login";
	}

	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public R login(String username, String password, String captcha,HttpServletRequest request) {
//		String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
//		if(!captcha.equalsIgnoreCase(kaptcha)){
//			return R.error("验证码不正确");
//		}
		System.out.println("登录验证");
		try{
            Subject subject = ShiroUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			subject.login(token);
        }catch (UnknownAccountException e) {
			return R.error(e.getMessage());
		}catch (IncorrectCredentialsException e) {
			return R.error("账号或密码不正确");
		}catch (LockedAccountException e) {
			return R.error("账号已被锁定,请联系管理员");
		}catch (AuthenticationException e) {
			return R.error("账户验证失败");
		}
		syslogin("用户登陆",request,"login");
		System.out.println("登录通过");

		return  new R();
	}

	@RequestMapping(value = "/refresh",method = RequestMethod.GET)
	public String refresh(){
		return "redirect:/";
	}

	/**
	 * 退出
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		ShiroUtils.logout();
		return "redirect:/";
	}
	
}
