function checkAll(){
	if($("#ids").prop("checked")){
		$("input[name='ids']").each(function(i){
			$(this).prop("checked","checked");
		 });
		}else{
			$("input[name='ids']").each(function(i){
				$(this).prop("checked","");
			 });
		}
}
//批量删除到垃圾箱
function toTrash(){
	var ids=new Array();
	$("input[name='ids']").each(function(i){
		if($(this).prop("checked")){
			ids.push($(this).val());
		}
	 });
	 if(ids.length>0){
		 if(!confirm("您确定要删除这些信息吗？")) {
				return;
			}
		 $.post("v_trash.html", {
				"ids" : ids
			}, function(data) {
				if(data.result){
					for(var i=0;i<ids.length;i++){
						$("#id_"+ids[i]).parent().parent().remove();
						}
					 $("#msgDiv").html("您选择的站内信息已被移动到垃圾箱 ");
				}else{
					alert("请先登录");
				}
			}, "json");
		 }else{
			 $("#msgDiv").html("请先选择信息");
			 }
}
//单条信息到垃圾箱
function trash(id){
	 if(!confirm("您确定要删除该条信息吗？")) {
			return;
		}
	 $.post("v_trash.html", {
			"ids" : id
		}, function(data) {
			if(data.result){
				$("#jvForm").attr("action","v_list.html");
				$("#jvForm").submit();
			}else{
				alert("请先登录");
			}
		}, "json");
}
function forward(){
	$("#jvForm").attr("action","v_forward.html");
	$("#jvForm").submit();
}
function empty(){
	var ids=new Array();
	$("input[name='ids']").each(function(i){
		if($(this).prop("checked")){
			ids.push($(this).val());
		}
	 });
	 if(ids.length>0){
		 if(!confirm("您确定要彻底删除这些信息吗？")) {
				return;
			}
		 $.post("v_empty.html", {
				"ids" : ids
			}, function(data) {
				if(data.result){
					for(var i=0;i<ids.length;i++){
						$("#id_"+ids[i]).parent().parent().remove();
						}
					 $("#msgDiv").html("您选择的站内信息已被彻底删除 ");
				}else{
					alert("请先登录");
				}
			}, "json");
		 }else{
			 $("#msgDiv").html("请先选择信息");
			 }
}
function emptySingle(id){
	 if(!confirm("您确定要彻底删除该信息吗？")) {
			return;
		}
	 $.post("v_empty.html", {
			"ids" : id
		}, function(data) {
			if(data.result){
				$("#jvForm").submit();
			}else{
				alert("请先登录");
			}
		}, "json");
}
function revert(){
	var ids=new Array();
	$("input[name='ids']").each(function(i){
		if($(this).prop("checked")){
			ids.push($(this).val());
		}
	 });
	 if(ids.length>0){
		 if(!confirm("您确定要还原这些信息吗？")) {
				return;
			}
		 $.post("v_revert.html", {
				"ids" : ids
			}, function(data) {
				if(data.result){
					for(var i=0;i<ids.length;i++){
						$("#id_"+ids[i]).parent().parent().remove();
						}
					 $("#msgDiv").html("您选择的站内信息已还原 ");
				}else{
					alert("请先登录");
				}
			}, "json");
		 }else{
			 $("#msgDiv").html("请先选择信息");
			 }
}
function toDraft(){
	$("#box").val(2);
	$("#nextUrl").val("v_list.html?box=2");
	$("#jvForm").attr("action","v_save.html");
	$("#jvForm").submit();
}
function toSend(){
	$("#nextUrl").val("v_list.html?box=1");
	$("#jvForm").attr("action","v_tosend.html");
	$("#jvForm").submit();
}
function reply(){
	$("#nextUrl").val("v_list.html?box=1");
	$("#jvForm").attr("action","v_reply.html");
	$("#jvForm").submit();
}
function find_user(){
	var username=$("#username").val();
	if(username!=""){
		$.post("v_findUser.html", {
			"username" : username
		}, function(data) {
			if(data.result){
				if(data.exist){
					$("#usernameMsg").html("没有此用户");
					$("#username").val("");
				}else{
					$("#usernameMsg").html("");
				}
			}else{
					alert("请先登录");
			}
		}, "json");
	}
}