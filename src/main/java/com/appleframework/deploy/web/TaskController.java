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

import com.appleframework.config.core.PropertyConfigurer;
import com.appleframework.deploy.entity.Project;
import com.appleframework.deploy.entity.Task;
import com.appleframework.deploy.model.ActionType;
import com.appleframework.deploy.model.StatusType;
import com.appleframework.deploy.model.TaskSo;
import com.appleframework.deploy.service.DeployService;
import com.appleframework.deploy.service.ProjectService;
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
	
	@Resource
	private ProjectService projectService;
		
	private String viewModel = "task/";
	
	@RequestMapping(value = "/list")
	public String list(Model model, TaskSo so, Pagination page, HttpServletRequest request) {
		page = taskService.findPage(page, so);
		List<Project> projectList = this.getProjectList();
		model.addAttribute("so", so);
		model.addAttribute("page", page);
		model.addAttribute("projectList", projectList);
		model.addAttribute("projectMap", this.getProjectMap(projectList));
		model.addAttribute("actionTypeMap", this.getActionTypeMap());
		model.addAttribute("actionTypeList", this.getActionTypeList());
		model.addAttribute("statusTypeMap", this.getStatusTypeMap());
		model.addAttribute("statusTypeList", this.getStatusTypeList());
		return viewModel + "list";
	}
	
	@RequestMapping(value = "/get")
	public @ResponseBody Task get(Model model, Integer id, HttpServletRequest request) {
		Task info = taskService.get(id);
		return info;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletResponse response) throws Exception {
		List<Project> projectList = this.getProjectList();
		model.addAttribute("projectList", projectList);
		model.addAttribute("projectMap", this.getProjectMap(projectList));
		return viewModel +"/add";
	}
	
	@RequestMapping(value = "/save")
	public String save(Model model, Task task, HttpServletRequest request) {
		try {
			Project project = projectService.get(task.getProjectId());
			task.setProjectName(project.getName());
			task.setExVersion(project.getVersion());
			taskService.save(task);
		} catch (Exception e) {
			addErrorMessage(model, e.getMessage());
			return ERROR_AJAX;
		}
		addSuccessMessage(model, "添加成功");
		return SUCCESS_AJAX;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Model model, Integer id, HttpServletResponse response) throws Exception {
		Task info = taskService.get(id);
		List<Project> projectList = this.getProjectList();
		model.addAttribute("actionTypeMap", this.getActionTypeMap());
		model.addAttribute("actionTypeList", this.getActionTypeList());
		model.addAttribute("statusTypeMap", this.getStatusTypeMap());
		model.addAttribute("statusTypeList", this.getStatusTypeList());
		model.addAttribute("projectList", projectList);
		model.addAttribute("projectMap", this.getProjectMap(projectList));
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
			Project project = projectService.get(task.getProjectId());
			task.setProjectName(project.getName());
			task.setExVersion(project.getVersion());
			Task old = taskService.get(task.getId());
			old.setUpdateAt(new Date());
			String[] ignoreProperties = {"createAt", "create_by"};
			BeanUtils.copyProperties(task, old, ignoreProperties);
			taskService.update(old);
		} catch (AppleException e) {
			addErrorMessage(model, e.getMessage());
			return ERROR_AJAX;
		}
		addSuccessMessage(model, "修改应用成功");
		return SUCCESS_AJAX;
	}
	
	@RequestMapping(value = "/deploy")
	public String deploy(Model model, Integer id, HttpServletResponse response) {
		try {
			Boolean isOk = Constants.BOOT_STATUS_MAP.get(id);
			if (null == isOk || isOk == true) {
				deployService.doDeploy(id);
			}
			model.addAttribute("taskId", id);
			model.addAttribute("websocketUrl", PropertyConfigurer.getProperty("websocket.url"));
		} catch (AppleException e) {
			e.printStackTrace();
		}
		return viewModel + "/deploy";
	}
	
	
	public List<StatusType> getStatusTypeList() {
		return Arrays.asList(StatusType.values());
	}
	
	public Map<String, StatusType> getStatusTypeMap() {
		List<StatusType> list = this.getStatusTypeList();
		Map<String, StatusType> map = new HashMap<String, StatusType>();
		for (StatusType statusType : list) {
			map.put(statusType.getIndex() + "", statusType);
		}
		return map;
	}
	
	public List<ActionType> getActionTypeList() {
		return Arrays.asList(ActionType.values());
	}
	
	public Map<String, ActionType> getActionTypeMap() {
		List<ActionType> list = this.getActionTypeList();
		Map<String, ActionType> map = new HashMap<String, ActionType>();
		for (ActionType actionType : list) {
			map.put(actionType.getIndex() + "", actionType);
		}
		return map;
	}
	
	public List<Project> getProjectList() {
		return projectService.findAll();
	}
	
	public Map<String, Project> getProjectMap(List<Project> projectList) {
		Map<String, Project> map = new HashMap<String, Project>();
		for (Project project : projectList) {
			map.put(project.getId() + "", project);
		}
		return map;
	}
	
	@RequestMapping(value = "/delete")
	public String delete(Model model, Integer id, HttpServletRequest request) {
		try {
			taskService.delete(id);
		} catch (Exception e) {
			addErrorMessage(model, e.getMessage());
			return ERROR_AJAX;
		}
		addSuccessMessage(model, "添加成功");
		return SUCCESS_AJAX;
	}
}
