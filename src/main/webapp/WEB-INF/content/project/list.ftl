<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>项目管理</title>

<#include "../commons/page_css.ftl" />
<#include "../commons/page_js.ftl" />
<script language="javascript" type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript">
	function callback() {
	 	$("#searchButton").click();
	}
	$().ready( function() {
		$("#listTable .btn_examine").bind("click", function(){
			var id = $(this).attr("operatId");
			var title = $(this).attr("title");
			
			art.dialog.open('copy?id=' + id, {
				id: 'viewFrame',
				title: title,
				close: function () {}
			}, false);
		});
		
		
	});
</script>


</head>

<body>
<!-- start of con_right_main -->
<div class="con_right_main">

	<form id="listForm" action="list" method="post">
	
    <!-- start of con_search -->
	<div class="con_search">
    	<span class="con_box_BL"></span>
        
        <!-- start of con_search_top -->
        <div class="con_search_top clearfix">
        	<div class="con_search_top_L left">
                <p>
                    <span class="group"><label>关键字：</label>
                    	<input name="name" class="c_input_text" type="text" realValue="关键字" value="${(so.name)!''}" />
                    </span>
                    <span class="group"><label>部署环境：</label>
                 		<select class="c_select" name="env" style="width:150px;" id="env">
                      <option value="">选择部署环境</option>
                      <#list envTypeList as env>
                      <option value="${env.getIndex()}"<#if (so.env?? && env.getIndex() == so.env)> selected="selected"</#if>>
                        ${env.getName()}
                      </option>
                      </#list>
                    </select>
                    </span>
                    <span class="group"><a id="searchButton" href="javascript:;" class="btn_search">搜索</a></span>
                </p>
            </div>
            <div class="con_search_btn right">
                <a class="btnA" href="javascript:;" onclick="openAddFrame('添加');">添加</a>
            </div>

        </div>
        <!-- end of con_search_top -->
        
        <span class="con_box_BR"></span>
    </div>
    <!-- end of con_search -->
    
	<div class="c_tipsA">截止到今日：已记录统计 <span class="red">${page.totalCount}</span> 个 </div>
        
    <!-- start of table_list -->
    <table id="listTable" class="table_list list">
        <tr>
        	<th width="5%">序号</th>
        	<th width="10%">项目</th>
        	<th width="6%">类型</th>
        	<th width="12%">发布环境</th>
        	<th width="8%">当前版本</th>
        	<th width="20%">安装目录</th>
        	<th width="10%">安装帐号</th>
        	<th width="8%">创建时间</th>
        	<th width="6%">审核状态</th>
        	<th width="6%">状态</th>
			<th width="15%">操作</th>
        </tr>
        <#list page.list as info>
        <tr class="even">
        	<td><!--<input type="checkbox" name="ids" value="${info.id}" />-->${info.id}</td>
        	<td>${(info.name)!''}</td>
        	<td><#if info.type==1>工程<#else>脚本</#if></td>
        	<td>${envTypeMap[info.env?string]}</td>
        	<td>${(info.version)!'-'}</td>
			<td style="text-align:left;">${(info.releaseTo)!'-'}</td>
			<td>${(info.releaseUser)!'-'}</td>
			<td>
				<#if info.createAd?exists>
				${info.createAd?string('yyyy-MM-dd')}
				<#else>-</#if>
			</td>
			<td><#if info.isAudit==true>是<#else>否</#if></td>
			<td><#if info.status==1>是<#else>否</#if></td>
			<td>
				<a class="btn_icon btn_edit"   href="javascript:;" operatId="${info.id}" title="编辑"></a>
                <a class="btn_icon btn_detail" href="javascript:;" operatId="${info.id}" title="详情"></a>
                <a class="btn_icon btn_delete" href="javascript:;" operatId="${info.id}" title="删除"></a>
                <a class="btn_icon btn_examine" href="javascript:;" operatId="${info.id}" title="拷贝"></a>
			</td>
        </tr>
        </#list>
        
    </table>
    <!-- end of table_list -->			
	
	<#if (page.list?size > 0)>
    
    	<!-- start of table_bottom -->
	    <div class="table_bottom clearfix">
	    	<!--
	    	<div class="table_bottom_checkbox left">
	    		<input id="selectAll" name="" type="checkbox" value=""><a id="deleteAll" class="btn" href="javascript:void(0);">删除选中</a>
	    	</div>
	    	-->
	        
	         <!-- start of 分页 -->
	   		<@paging pageNumber = page.pageNo totalPages = page.totalPage>
				<#include "../commons/pager.ftl">
			</@paging>
	        <!-- end of 分页 -->
	    </div>
	    <!-- end of table_bottom -->
			
	<#else>
		<div class="noRecord">没有找到任何记录!</div>
	</#if>
	
    </form>
</div>
<!-- end of con_right_main -->
</body>
</html>