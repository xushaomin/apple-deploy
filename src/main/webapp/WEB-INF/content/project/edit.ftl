<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>编辑项目</title>
<#include "/content/commons/page_css.ftl" />
<#include "/content/commons/page_js.ftl" />

<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
		
	// 表单验证
	$inputForm.validate({
		rules: {
			"name": {
				required: true,
				remote: "check_name?oldName=${(info.name)!''}"
			}
		},
		messages: {
			"name": {
				required: "必填",
				remote: "项目已存在"
			}
		},
		submitHandler:function(form){
            form.submit();
        }
	});
	
	$("#btn_pop_submitA").click(function(){
 		$inputForm.submit();
	});
	
	$(".projectType").click(function(){
    	proccessType();
	});
	
	proccessType();
	
});

function proccessType() {
		var val = $('input:radio[name="type"]:checked').val();
        if(val == null){
            return false;
       	}
        else{
			if(val == 1) {
				$(".noshell").show();
			}
			else {
				$(".noshell").hide();
			}
        }
}
</script>

</head>

<body>
<form id="inputForm" method="post" action="update">
	<input type="hidden" name="id" value="${info.id}" />
	
    <div id="auditTab" class="pop_main" style="width:600px;border: 0px solid;">

       <div class="pop_information_mod">
            <ul class="pop_list merchant_type_add">
            
                	<li class="clearfix">
                		<label for="name require" class="tit">名称：<span class=" red">*</span></label>
                		<input class="c_input_text required" type="text" style="width:200px;" name="name" value="${(info.name)!}" realValue="请输入名称" />
               		</li>

                	<li class="clearfix">
                		<label for="type" class="tit">类型：<span class=" red">*</span></label>
               			<input class="projectType" type="radio" name="type" value="1" <#if info.type == 1> checked="checked" </#if> />工程项目
                		<input class="projectType" type="radio" name="type" value="0" <#if info.type == 0> checked="checked" </#if> />脚本项目
                	</li>
                	
                	<li class="clearfix">
                		<label for="type" class="tit">状态：<span class=" red">*</span></label>
               			<input type="radio" name="status" value="1" <#if info.status == 1> checked="checked" </#if> />启用
                		<input type="radio" name="status" value="0" <#if info.status == 0> checked="checked" </#if> />停用
                	</li>
                	
                	<li class="clearfix">
                		<label for="type" class="tit">审核：<span class=" red">*</span></label>
               			<input type="radio" name="isAudit" value="1" <#if info.status == 1> checked="checked" </#if> />是
                		<input type="radio" name="isAudit" value="0" <#if info.status == 0> checked="checked" </#if> />否
                	</li>

					<li class="clearfix">
                		<label for="env" class="tit">部署环境：<span class=" red">*</span></label>
                		<select class="c_select" name="env" style="width:150px;" id="env">
                		<#list envTypeList as env>
							<option value="${env.getIndex()}" <#if (info.env?? && env.getIndex() == info.env)> selected="selected"</#if>>
								${env.getName()}
							</option>
						</#list>
						</select>
                	</li>
                	
                	<li class="clearfix">
                		<label for="plus" class="tit">部署插件：<span class=" red">*</span></label>
                		<select class="c_select" name="plus" style="width:150px;" id="plus">
                		<#list plusTypeList as plus>
							<option value="${plus.getIndex()}" <#if (info.plus?? && plus.getIndex() == info.plus)> selected="selected"</#if>>
								${plus.getName()}
							</option>
						</#list>
						</select>
                	</li>
                	
                	<li class="clearfix">
                		<label for="version " class="tit">当前版本：<span class=" red">*</span></label>
                		<input class="c_input_text required" type="text" style="width:200px;" name="version" value="${(info.version)!}" realValue="请输入当前版本" />
               		</li>
               		
                	<li class="clearfix noshell">
	                    <label for="nexusUrl" class="tit">Nexus'地址：</label>
	                    <input class="c_input_text" type="text" style="width:200px;" name="nexusUrl" value="${(info.nexusUrl)!}" realValue="请输入Nexus'地址" />
                	</li>
                	<li class="clearfix noshell">
	                    <label for="nexusGroup" class="tit">Nexus'GROUP：</label>
	                    <input class="c_input_text" type="text" style="width:200px;" name="nexusGroup" value="${(info.nexusGroup)!}" realValue="请输入Nexus'GROUP" />
                	</li>
            
                	<li class="clearfix noshell">
	                    <label for="nexusArtifact" class="tit">Nexus'ARTIFACT：</label>
	                    <input class="c_input_text" type="text" style="width:200px;" name="nexusArtifact" value="${(info.nexusArtifact)!}" realValue="请输入Nexus'ARTIFACT" />
                	</li>
                	
                	<li class="clearfix">
	                    <label for="releaseUser" class="tit">部署用户：</label>
	                    <input class="c_input_text required" type="text" style="width:200px;" name="releaseUser" value="${(info.releaseUser)!}" realValue="请输入部署用户" />
                	</li>
                	<li class="clearfix">
	                    <label for="releaseTo" class="tit">部署目录：</label>
	                    <input class="c_input_text required" type="text" style="width:200px;" name="releaseTo" value="${(info.releaseTo)!}" realValue="请输入部署目录" />
                	</li>
                	
                	<li class="clearfix">
	                    <label for="hosts" class="tit">服务器列表：</label>
	                    <span class="textarea_show">
	                    	<textarea class="c_textarea" name="hosts" cols="" id="hosts" rows="">${(info.hosts)!}</textarea>
	                    </span>
                	</li>
                	<li class="clearfix">
	                    <label for="preDeploy" class="tit">部署前置任务：</label>
	                    <span class="textarea_show">
	                    	<textarea class="c_textarea" name="preDeploy" cols="" id="preDeploy" rows="">${(info.preDeploy)!}</textarea>
	                    </span>
                	</li>
                	<li class="clearfix">
	                    <label for="postDeploy" class="tit">部署任务：</label>
	                    <span class="textarea_show">
	                    	<textarea class="c_textarea" name="postDeploy" cols="" id="postDeploy" rows="">${(info.postDeploy)!}</textarea>
	                    </span>
                	</li>
                	<li class="clearfix">
	                    <label for="afterDeploy" class="tit">部署后置任务：</label>
	                    <span class="textarea_show">
	                    	<textarea class="c_textarea" name="afterDeploy" cols="" id="afterDeploy" rows="">${(info.afterDeploy)!}</textarea>
	                    </span>
                	</li>
            </ul>

        </div>

    </div>
    <div class="pop_footer">
        <a id="btn_pop_submitA" class="btn_pop_submitA" href="javascript:void(0)">确定</a>
        <a id="btn_pop_submitB" class="btn_pop_submitB" href="javascript:void(0)" onclick="art.dialog.close();">取消</a>
    </div>


</form>


</body>
</html>