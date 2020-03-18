package com.cobona.vici.modular.system.controller;

import com.cobona.vici.common.exception.InvalidKaptchaException;
import com.cobona.vici.common.persistence.dao.UserMapper;
import com.cobona.vici.common.persistence.model.User;
import com.cobona.vici.core.base.controller.BaseController;
import com.cobona.vici.core.log.LogManager;
import com.cobona.vici.core.log.factory.LogTaskFactory;
import com.cobona.vici.core.node.MenuNode;
import com.cobona.vici.core.shiro.ShiroKit;
import com.cobona.vici.core.shiro.ShiroUser;
import com.cobona.vici.core.util.ApiMenuFilter;
import com.cobona.vici.core.util.KaptchaUtil;
import com.cobona.vici.core.util.ToolUtil;
import com.cobona.vici.modular.system.dao.MenuDao;
import com.google.code.kaptcha.Constants;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.cobona.vici.core.support.HttpKit.getIp;

import java.util.List;

/**
 * 登录控制器
 *
 * @author cobona
 * @Date 2017年1月10日 下午8:25:24
 */
@Controller
@RequestMapping("/admin")
public class LoginController extends BaseController {

    @Autowired
    MenuDao menuDao;

    @Autowired
    UserMapper userMapper;
    private static String PREFIX = "/system/admin/";
    /**
     * 跳转到主页
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
    	if (!ShiroKit.isAuthenticated() || ShiroKit.getUser() == null) {
            return PREFIX+"login.html";
        }
        //获取菜单列表
        List<Integer> roleList = ShiroKit.getUser().getRoleList();
        Integer deptId=ShiroKit.getUser().deptId;
        Integer userid = ShiroKit.getUser().getId();
        if (roleList == null || roleList.size() == 0) {
            ShiroKit.getSubject().logout();
            model.addAttribute("tips", "该用户没有角色，无法登陆");
            return PREFIX+"login.html";
        }
        
       // List<MenuNode> menus = menuDao.getMenusByRoleIds(roleList,1);
        List<MenuNode> menus = menuDao.getMenusOfUser(roleList,deptId,userid,1);
        List<MenuNode> titles = MenuNode.buildTitle(menus);
        titles = ApiMenuFilter.build(titles);

        model.addAttribute("titles", titles);

        //获取用户头像
       
        User user = userMapper.selectById(userid);
        String avatar = user.getAvatar();
        model.addAttribute("avatar", avatar);

        return PREFIX+"index.html";
    }

    /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            return REDIRECT + "/admin/";
        } else {
            return PREFIX+"login.html";
        }
    }

    /**
     * 点击登录执行的动作
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginVali() {

        String username = super.getPara("username").trim();
        String password = super.getPara("password").trim();
        String remember = super.getPara("remember");

        //验证验证码是否正确
        if (KaptchaUtil.getKaptchaOnOff()) {
            String kaptcha = super.getPara("kaptcha").trim();
            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            if (ToolUtil.isEmpty(kaptcha) || !kaptcha.equalsIgnoreCase(code)) {
                throw new InvalidKaptchaException();
            }
        }

        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());

        if ("on".equals(remember)) {
            token.setRememberMe(true);
        } else {
            token.setRememberMe(false);
        }

        currentUser.login(token);

        ShiroUser shiroUser = ShiroKit.getUser();
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());

        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));

        ShiroKit.getSession().setAttribute("sessionFlag", true);

        return REDIRECT + "/admin/";
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut() {
        LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), getIp()));
        ShiroKit.getSubject().logout();
        return REDIRECT + "/admin/";
    }
}
