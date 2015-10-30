package com.appleframework.deploy.web;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appleframework.deploy.entity.Project;
import com.appleframework.deploy.entity.ProjectWithBLOBs;
import com.appleframework.deploy.service.ProjectService;
import com.appleframework.exception.AppleException;
import com.appleframework.model.page.Pagination;
import com.appleframework.web.springmvc.controller.BaseController;

@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {
	
	@Resource
	private ProjectService projectService;
	
	private String viewModel = "project/";
	
	@RequestMapping(value = "/list")
	public String list(Model model, String keyword, Pagination page, HttpServletRequest request) {
		page = projectService.findPageByName(page, keyword);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		return viewModel + "list";
	}
	
	@RequestMapping(value = "/get")
	public @ResponseBody Project get(Model model, Integer id, HttpServletRequest request) {
		Project info = projectService.get(id);
		return info;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletResponse response) throws Exception {
		return viewModel +"/add";
	}
	
	/*@RequestMapping(value = "/save")
	public String save(Model model, Project project, HttpServletRequest request) {
		try {
			projectService.insert(project);
		} catch (ServiceException e) {
			addErrorMessage(model, e.getMessage());
			return ERROR_VIEW;
		}
		addSuccessMessage(model, "添加应用成功", "list");
		return SUCCESS_VIEW;
	}*/
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model, Integer id, HttpServletResponse response) throws Exception {
		Project info = projectService.get(id);
		model.addAttribute("info", info);
		return viewModel + "/edit";
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Model model, Integer id, HttpServletResponse response) throws Exception {
		Project info = projectService.get(id);
        model.addAttribute("info", info);
		return viewModel + "/view";
	}
	
	@RequestMapping(value = "/update")
	public String update(Model model, ProjectWithBLOBs project, HttpServletResponse response) {
		try {
			ProjectWithBLOBs old = projectService.get(project.getId());
			old.setName(project.getName());
			old.setName(project.getName());
			old.setUpdateAt(new Date());
			projectService.update(old);
		} catch (AppleException e) {
			addErrorMessage(model, e.getMessage());
			return "/commons/error_ajax";
		}
		addSuccessMessage(model, "修改应用成功", "list");
		return "/commons/success_ajax";
	}
	
	// AJAX唯一验证
	@RequestMapping(value = "/check_name", method = RequestMethod.GET)
	public @ResponseBody String checkRoleName(String oldName, String name) {
		if (projectService.isUniqueByName(oldName, name)) {
			return ajax("true");
		} else {
			return ajax("false");
		}
	}
			
}
