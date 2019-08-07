package com.xhwu.myjxc.controller;

import com.xhwu.myjxc.enums.REnum;
import com.xhwu.myjxc.utils.RUtil;
import com.xhwu.myjxc.utils.ShiroUtil;
import com.xhwu.myjxc.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * author: 小宇宙
 * date: 2018/4/7
 */
@Controller
@RequestMapping("/anno")
@Slf4j
public class AnnoController {

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "admin/signin";
    }

    @RequestMapping("/login")
    public String login(String account, String password, Model model){
        //Assert.isBlank(map.get("account"),"账号不能为空");
        //Assert.isBlank(map.get("password"),"密码不能为空");

        String msg = "";
        if(StringUtils.isNotBlank(account) && StringUtils.isNotBlank(password)){
            try{
                Subject subject = ShiroUtil.getSubject();
                UsernamePasswordToken token = new UsernamePasswordToken(account,password);
                subject.login(token);
            }catch (UnknownAccountException e) {
                //return RUtil.error(REnum.USERNAME_OR_PASSWORD_ERROR.getCode(),REnum.USERNAME_OR_PASSWORD_ERROR.getMessage());
                msg = "登录失败："+ REnum.USERNAME_OR_PASSWORD_ERROR.getCode()+"-"+REnum.USERNAME_OR_PASSWORD_ERROR.getMessage();
                model.addAttribute("msg",msg);
                return "admin/signin";
            }catch (IncorrectCredentialsException e) {
                //return RUtil.error(REnum.USERNAME_OR_PASSWORD_ERROR.getCode(),REnum.USERNAME_OR_PASSWORD_ERROR.getMessage());
                msg = "登录失败："+REnum.USERNAME_OR_PASSWORD_ERROR.getCode()+"-"+REnum.USERNAME_OR_PASSWORD_ERROR.getMessage();
                model.addAttribute("msg",msg);
                return "admin/signin";
            }catch (DisabledAccountException e) {
                // return RUtil.error(REnum.ACCOUNT_DISABLE.getCode(),REnum.ACCOUNT_DISABLE.getMessage());
                msg = "登录失败："+REnum.ACCOUNT_DISABLE.getCode()+"-"+REnum.ACCOUNT_DISABLE.getMessage();
                model.addAttribute("msg",msg);
                return "admin/signin";
            }catch (AuthenticationException e) {
                //return RUtil.error(REnum.AUTH_ERROR.getCode(),REnum.AUTH_ERROR.getMessage());
                msg = "登录失败："+REnum.AUTH_ERROR.getCode()+"-"+REnum.AUTH_ERROR.getMessage();
                model.addAttribute("msg",msg);
                return "admin/signin";
            }
            //return RUtil.success();
            return "admin/index";
        }else {
            msg = "账号或密码不能为空！";
            model.addAttribute("msg",msg);
            return "admin/signin";
        }



    }

    /**
     * 退出
     * @return
     */
    @GetMapping("/logout")
    public R logout(){
        ShiroUtil.logout();
        return RUtil.success();
    }

    @RequestMapping("/loginout")
    public String loginout(){
        ShiroUtil.logout();
        return "admin/signin";
    }

    /**
     * 登录页面
     * @return
     */
    @GetMapping("/notLogin")
    public R notLogin(){
       return RUtil.error(REnum.NOT_LOGIN.getCode(),REnum.NOT_LOGIN.getMessage());
    }
}
