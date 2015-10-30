<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>添加部署项目</title>
<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />

<script type="text/javascript">
$().ready(function() {

	var $inputForm = $("#inputForm");
		
	// 表单验证
	$inputForm.validate({
		rules: {
			
		},
		messages: {
			
		},
		submitHandler:function(form){
            form.submit();
        }
	});
	
	$("#btn_pop_submitA").click(function(){
 		$inputForm.submit();
	});
	
	
});
</script>

</head>

<body>
<form id="inputForm" method="post" action="save">
    <div id="auditTab" class="pop_main" style="width:600px;border: 0px solid;">

       <div class="pop_information_mod">
            <ul class="pop_list merchant_type_add">
            
                	<li class="clearfix">
                		<label for="name require" class="tit">项目名称：<span class=" red">*</span></label>
                		<input class="c_input_text required" type="text" style="width:200px;" name="name" id="name" realValue="请输入名称" maxlength="32" />
               		</li>

                	<li class="clearfix">
                		<label for="nexusUrl" class="tit">Nexus地址：<span class=" red">*</span></label>
                		<input class="c_input_text required" type="text" style="width:200px;" name="nexusUrl" id="nexusUrl" realValue="请输入私服地址" maxlength="32" />
                	</li>
                	<li class="clearfix">
                		<label for="nexusGroup" class="tit">Maven groupId：<span class=" red">*</span></label>
                		<input class="c_input_text required" type="text" style="width:200px;" name="nexusGroup" id="nexusGroup" realValue="请输入groupId" maxlength="32" />
                	</li>
                	
                	<li class="clearfix">
                		<label for="nexusArtifact" class="tit">Maven artifactId：<span class=" red">*</span></label>
                		<input class="c_input_text required" type="text" style="width:200px;" name="nexusArtifact" id="nexusArtifact" realValue="请输入artifactId" maxlength="32" />
                	</li>
                	
                	<li class="clearfix">
                		<label for="version" class="tit">当前线上版本：<span class=" red">*</span></label>
                		<input class="c_input_text required" type="text" style="width:200px;" name="version" id="version" realValue="请输入当前线上版本" maxlength="32" />
                	</li>
                	
                	<li class="clearfix">
                		<label for="releaseUser" class="tit">部署帐号：<span class=" red">*</span></label>
                		<input class="c_input_text required" type="text" style="width:200px;" name="releaseUser" id="releaseUser" realValue="请输入部署帐号" maxlength="32" />
                	</li>
                	
                	<li class="clearfix">
                		<label for="releaseTo" class="tit">部署目录：<span class=" red">*</span></label>
                		<input class="c_input_text required" type="text" style="width:200px;" name="releaseTo" id="releaseTo" realValue="请输入部署目录" maxlength="32" />
                	</li>
                	<li class="clearfix">
                		<label for="host" class="tit">部署主机：<span class=" red">*</span></label>
                		<span class="textarea_show">
                    		<textarea class="c_textarea wordCount" name="host" cols="" id="host" rows="" maxlength="100" showCount="hostLen"></textarea>
                    		<span class="in_num_text" style="color:red;"  id="hostLen">0/100</span>
                    		<span class="in_num_text" >/100</span>
                    	</span>
                	</li>
                	
                	<li class="clearfix">
                		<label for="preDeploy" class="tit">部署前置任务：<span class=" red">*</span></label>
                		<span class="textarea_show">
                    		<textarea class="c_textarea wordCount" name="preDeploy" cols="" id="preDeploy" rows="" maxlength="100" showCount="preDeployLen"></textarea>
                    		<span class="in_num_text" style="color:red;"  id="preDeployLen">0/100</span>
                    		<span class="in_num_text" >/100</span>
                    	</span>
                	</li>
                	
                	<li class="clearfix">
                		<label for="postDeploy" class="tit">部署任务：<span class=" red">*</span></label>
                		<span class="textarea_show">
                    		<textarea class="c_textarea wordCount" name="postDeploy" cols="" id="postDeploy" rows="" maxlength="100" showCount="postDeployLen"></textarea>
                    		<span class="in_num_text" style="color:red;"  id="postDeployLen">0/100</span>
                    		<span class="in_num_text" >/100</span>
                    	</span>
                	</li>
                	<li class="clearfix">
                		<label for="state" class="tit">状态：<span class=" red">*</span></label>
               			<input type="radio" name="state" value="1" />启用
                		<input type="radio" name="state" value="0" checked="checked" />停用
                	</li>
                	
                	<li class="clearfix">
                		<label for="state" class="tit">需要审核：<span class=" red">*</span></label>
               			<input type="radio" name="isAudit" value="true" />是
                		<input type="radio" name="isAudit" value="false" checked="checked" />否
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