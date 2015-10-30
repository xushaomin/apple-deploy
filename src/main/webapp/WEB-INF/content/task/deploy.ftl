<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
<title>集群管理</title>

<link rel="stylesheet" type="text/css" href="/css/common.css" />
<link rel="stylesheet" type="text/css" href="/css/style.css" />

<script type="text/javascript" src="/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="/js/jquery.pager.js"></script>
<script type="text/javascript" src="/js/jquery.validate.js"></script>
<script type="text/javascript" src="/js/jquery.tools.js"></script>


<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript" src="/js/list.js"></script>
<script type="text/javascript" src="/js/input.js"></script>
<script type="text/javascript" src="/js/admin.js"></script>

<script type="text/javascript" src="/js/prompt.js"></script>
<script type="text/javascript" src="/js/artDialog/artDialog.source.js?skin=glsx"></script>
<script type="text/javascript" src="/js/artDialog/plugins/iframeTools.source.js"></script>
<script type="text/javascript" src="/js/pop.js"></script>
<script language="javascript" type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript"> 
            if (!window.WebSocket) {
                alert("WebSocket not supported by this browser!");
            } 
             
			function display() {
            	var valueLabel = $("#valueLabel");
                valueLabel.innerHTML = "";
                var ws = new WebSocket("ws://localhost:8080/push/deploy");
                ws.onmessage = function(evt) {
                    valueLabel.append(evt.data + "<br>");
                    GoBottom();
                }; 

                ws.onclose = function() {
                };

                ws.onopen = function() {
                    ws.send("taskId=${taskId}");
                };
            } 
            
            
            
			var currentPosition,timer; 
			function GoBottom(){ 
				timer = setInterval("runToBottom()",50); 
			} 
			function runToBottom(){ 
				currentPosition = document.documentElement.scrollTop || document.body.scrollTop; 
				currentPosition += 30; 
				if(currentPosition<document.body.scrollHeight && (document.body.clientHeight + document.body.scrollTop < document.body.scrollHeight)) 
				{ 
					//window.scrollTo(0,currentPosition); 
					//alert(document.documentElement.clientHeight + " " + document.documentElement.scrollTop + " " + document.documentElement.scrollHeight + "#" +document.body.clientHeight + " " + document.body.scrollTop + " " + document.body.scrollHeight); 
					document.body.scrollTop = currentPosition; 
				} 
				else 
				{ 
					document.body.scrollTop = document.body.scrollHeight; 
					clearInterval(timer); 
				} 
			}
</script> 


</head>

<body onload="display();"> 
	
    <div id="auditTab" class="pop_main" style="width:96%;border: 0px solid;">

       <div class="pop_information_mod" id="valueLabel">

        </div>

    </div>

</body>
</html>