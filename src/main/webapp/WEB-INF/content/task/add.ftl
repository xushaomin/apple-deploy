<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>新增任务</title>
<#include "/content/commons/page_css.ftl" />
<#include "/content/commons/page_js.ftl" />

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
	
	$('#projectId').change(function(){
		var prid =$(this).children('option:selected').val();
		$.ajax({
			    url: "/project/json?id=" + prid,
			    dataType: "json",
			    type: "GET",
			    beforeSend: function() {
			        //请求前的处理
			    },
			    success: function(data) {			        
			 		var hosts = data.hosts;
			        var hostArr = hosts.split(",");
			        $('#hostsLabel').empty();
			    	for(var i = 0; i< hostArr.length; i++){
						var ckb = '<input name="hosts" type="checkbox" checked="checked" value="' + hostArr[i] + '" />' + hostArr[i] + '<br />';
						$('#hostsLabel').append(ckb);
					}
			    },
			    complete: function() {
			        //请求完成的处理
			    },
			    error: function() {
			        //请求出错处理
			    }
		});
	})
	
	
});
</script>

</head>

<body>
<form id="inputForm" method="post" action="save">
	<input type="hidden" name="action" value="1" />
	<input type="hidden" name="status" value="1" />
	
    <div id="auditTab" class="pop_main" style="width:600px;border: 0px solid;">

       <div class="pop_information_mod">
            <ul class="pop_list merchant_type_add">
                	<li class="clearfix">
                		<label for="projectId" class="tit">所属项目：<span class=" red">*</span></label>
                		<select class="c_select required" name="projectId" style="width:150px;" id="projectId">
                		<option value="">请选择项目</option>
                		<#list projectList as project>
							<option value="${project.id}">${project.name}</option>
						</#list>
						</select>
                	</li>
                	
                	<li class="clearfix">
                		<label for="hosts" class="tit">部署主机：<span class=" red">*</span></label>
                		<label id="hostsLabel">
                		<label>
                	</li>

					<li class="clearfix">
                		<label for="version" class="tit">发布版本：<span class=" red">*</span></label>
               			<input class="c_input_text required" type="text" style="width:200px;" name="version" value="${(info.version)!}" realValue="请输入版本号" maxlength="200" />
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