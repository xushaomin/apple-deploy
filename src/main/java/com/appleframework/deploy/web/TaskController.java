package com.appleframework.deploy.web;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appleframework.deploy.entity.Task;
import com.appleframework.deploy.model.EnvType;
import com.appleframework.deploy.service.DeployService;
import com.appleframework.deploy.service.TaskService;
import com.appleframework.deploy.utils.Constants;
import com.appleframework.exception.AppleException;
import com.appleframework.model.page.Pagination;
import com.appleframework.web.springmvc.controller.BaseController;

@Controller
@RequestMapping("/task")
public class TaskController extends BaseController {
	
	@Resource
	private TaskService taskService;
	
	@Resource
	private DeployService deployService;
		
	private String viewModel = "task/";
	
	@RequestMapping(value = "/list")
	public String list(Model model, String keyword, Integer projectId, Pagination page, HttpServletRequest request) {
		page = taskService.findPage(page, projectId, keyword);
		model.addAttribute("projectId", projectId);
		model.addAttribute("keyword", keyword);
		model.addAttribute("page", page);
		model.addAttribute("envTypeMap", this.getEnvTypeMap());
		model.addAttribute("envTypeList", this.getEnvTypeList());
		return viewModel + "list";
	}
	
	@RequestMapping(value = "/get")
	public @ResponseBody Task get(Model model, Integer id, HttpServletRequest request) {
		Task info = taskService.get(id);
		return info;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletResponse response) throws Exception {
		return viewModel +"/add";
	}
	
	/*@RequestMapping(value = "/save")
	public String save(Model model, Task task, HttpServletRequest request) {
		try {
			taskService.insert(task);
		} catch (ServiceException e) {
			addErrorMessage(model, e.getMessage());
			return ERROR_VIEW;
		}
		addSuccessMessage(model, "添加应用成功", "list");
		return SUCCESS_VIEW;
	}*/
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model, Integer id, HttpServletResponse response) throws Exception {
		Task info = taskService.get(id);
		model.addAttribute("info", info);
		return viewModel + "/edit";
	}
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view(Model model, Integer id, HttpServletResponse response) throws Exception {
		Task info = taskService.get(id);
        model.addAttribute("info", info);
		return viewModel + "/view";
	}
	
	@RequestMapping(value = "/update")
	public String update(Model model, Task task, HttpServletResponse response) {
		try {
			Task old = taskService.get(task.getId());
			old.setUpdateAt(new Date());
			taskService.update(old);
		} catch (AppleException e) {
			addErrorMessage(model, e.getMessage());
			return "/commons/error_ajax";
		}
		addSuccessMessage(model, "修改应用成功", "list");
		return "/commons/success_ajax";
	}
	
	@RequestMapping(value = "/deploy")
	public String deploy(Model model, Integer id, HttpServletResponse response) {
		try {
			Boolean isOk = Constants.BOOT_STATUS_MAP.get(id);
			if (null == isOk || isOk == true) {
				deployService.doDeploy(id);
			}
			model.addAttribute("taskId", id);
		} catch (AppleException e) {
			e.printStackTrace();
		}
		return viewModel + "/deploy";
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
		System.out.println(map);
		return map;
	}
		
}
