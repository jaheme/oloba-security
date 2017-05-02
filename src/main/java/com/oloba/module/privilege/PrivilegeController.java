package com.oloba.module.privilege;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.oloba.module.common.LoginedService;
import com.oloba.module.common.URI;
import com.oloba.module.privilege.model.PMenu;
import com.oloba.module.privilege.model.PModule;
import com.oloba.module.privilege.model.PNode;

@Controller
public class PrivilegeController {
	
	/**
	 * 权限基本信息查看
	 * @return
	 */
	@RequestMapping(URI.PRIVILEGE_MANAGER)
	public ModelAndView manager() {
		String username = LoginedService.getUsername();
		ModelAndView mav = new ModelAndView("privilege/privilege_manager");
		mav.addObject(LoginedService.USERNAME, username);
		mav.addObject("menuList", PMenu.values());
		mav.addObject("moduleList", PModule.values());
		mav.addObject("nodeList", PNode.values());
		return mav;
	}
	
}
