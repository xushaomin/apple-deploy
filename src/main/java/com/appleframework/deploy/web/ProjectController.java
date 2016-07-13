package com.appleframework.deploy.web;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appleframework.deploy.entity.Project;
import com.appleframework.deploy.entity.ProjectWithBLOBs;
import com.appleframework.deploy.model.EnvType;
import com.appleframework.deploy.model.PlusType;
import com.appleframework.deploy.model.ProjectSo;
import com.appleframework.deploy.service.ProjectService;
import com.appleframework.exception.AppleException;
import com.appleframework.model.page.Pagination;
import com.appleframework.web.bean.Message;
import com.appleframework.web.springmvc.controller.BaseController;

@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {
	
	@Resource
	private ProjectService projectService;
	
	private String viewModel = "project/";
	
	@RequestMapping(value = "/list")
	public String list(Model model, ProjectSo so, Pagination page, HttpServletRequest request) {
		page = projectService.findPage(page, so);
		model.addAttribute("so", so);
		model.addAttribute("page", page);
		model.addAttribute("envTypeMap", this.getEnvTypeMap());
		model.addAttribute("envTypeList", this.getEnvTypeList());
		model.addAttribute("plusTypeMap", this.getPlusTypeMap());
		model.addAttribute("plusTypeList", this.getPlusTypeList());
		return viewModel + "list";
	}
	
	@RequestMapping(value = "/get")
	public @ResponseBody Project get(Model model, Integer id, HttpServletRequest request) {
		model.addAttribute("envTypeList", this.getEnvTypeList());
		Project info = projectService.get(id);
		return info;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletResponse response) throws Exception {
		model.addAttribute("envTypeList", this.getEnvTypeList());
		model.addAttribute("plusTypeList", this.getPlusTypeList());
		return viewModel +"/add";
	}
	
	@RequestMapping(value = "/save")
	public String save(Model model, ProjectWithBLOBs project, HttpServletRequest request) {
		try {
			projectService.save(project);
		} catch (Exception e) {
			addErrorMessage(model, e.getMessage());
			return ERROR_AJAX;
		}
		addSuccessMessage(model, "添加项目成功");
		return SUCCESS_AJAX;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model, Integer id, HttpServletResponse response) throws Exception {
		ProjectWithBLOBs info = projectService.get(id);
		model.addAttribute("info", info);
		model.addAttribute("envTypeList", this.getEnvTypeList());
		model.addAttribute("plusTypeList", this.getPlusTypeList());
		return viewModel + "/edit";
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Model model, Integer id, HttpServletResponse response) throws Exception {
		Project info = projectService.get(id);
        model.addAttribute("info", info);
		return viewModel + "/view";
	}
	
	@RequestMapping(value = "/copy", method = RequestMethod.GET)
	public String copy(Model model, Integer id, HttpServletResponse response) throws Exception {
		ProjectWithBLOBs info = projectService.get(id);
		model.addAttribute("info", info);
		model.addAttribute("envTypeList", this.getEnvTypeList());
		model.addAttribute("plusTypeList", this.getPlusTypeList());
		return viewModel + "/copy";
	}
	
	@RequestMapping(value = "/update")
	public String update(Model model, ProjectWithBLOBs project, HttpServletResponse response) {
		try {
			ProjectWithBLOBs old = projectService.get(project.getId());
			String[] ignoreProperties = {"createTime"};
			BeanUtils.copyProperties(project, old, ignoreProperties);
			old.setUpdateAt(new Date());
			projectService.update(old);
		} catch (AppleException e) {
			addErrorMessage(model, e.getMessage());
			return ERROR_AJAX;
		}
		addSuccessMessage(model, "修改成功");
		return SUCCESS_AJAX;
	}
	
	@RequestMapping(value = "/json")
	public @ResponseBody ProjectWithBLOBs json(Integer id) {
		ProjectWithBLOBs info = projectService.get(id);
		return info;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Message delete(Integer id, HttpServletRequest request) {
		try {
			projectService.delete(id);
			return SUCCESS_MESSAGE;
		} catch (AppleException e) {
			return Message.error(e.getMessage());
		}
	}
		
	public List<EnvType> getEnvTypeList() {
		return Arrays.asList(EnvType.values());
	}
	
	public Map<String, EnvType> getEnvTypeMap() {
		List<EnvType> list = this.getEnvTypeList();
		Map<String, EnvType> map = new HashMap<String, EnvType>();
		for (EnvType envType : list) {
			map.put(envType.getIndex() + "", envType);
		}
		return map;
	}
	
	public List<PlusType> getPlusTypeList() {
		return Arrays.asList(PlusType.values());
	}
	
	public Map<String, PlusType> getPlusTypeMap() {
		List<PlusType> list = this.getPlusTypeList();
		Map<String, PlusType> map = new HashMap<String, PlusType>();
		for (PlusType plusType : list) {
			map.put(plusType.getIndex() + "", plusType);
		}
		return map;
	}
	
	// AJAX唯一验证
	@RequestMapping(value = "/check_name", method = RequestMethod.GET)
	public @ResponseBody String checkName(String oldName, String name) {
		if (projectService.isUniqueByName(oldName, name)) {
			return ajax("true");
		} else {
			return ajax("false");
		}
	}
			
}
