$(document).ready(function() {
	$("input[name='roleList']").change(function(){
    	getRoleMenu($(this).val());
    });
    
    $('.tree').treegrid({
        expanderExpandedClass: 'glyphicon glyphicon-minus',
        expanderCollapsedClass: 'glyphicon glyphicon-plus'
    });
    
});

function roleNewEdit() {
	var rid = $("#role_id").val();
	if ($("#role_name").val().length < 3) {
		notice("角色名称需要填写");
		return;
	}
	if (base.checkid(rid)) {
		saveRole(new_uri);
	} else {
		saveRole(edit_save_uri);
	}
}
function loadRole(flag) {
	clear_form("roleForm");
	notice("");
	if (!flag) { //new
    	$("#roleModalLabel").text("新增角色");
		return;
	}
	// edit
	var rid = $("input[name='roleList']:checked").val();
	if (false == base.checkid(rid)) {
		notice("先选择角色");
		return;
	}
	$("#roleModalLabel").text("编辑角色");
	$("#role_id").val(rid);
	$.post(edit_uri, {"role_id":$("#role_id").val()}, function(result, textStatus){
		if (result.code == 200) {
			var rd = result.data;
			$("#role_name").val(rd.role_name);
			$("#role_desc").val(rd.role_desc);
		} else {
			notice(result.msg);
		}
	});
}

/** 新增、编辑时调用  */
function saveRole(uri) {
	var data = $("#roleForm").serialize();
	$.post(uri, data, function(result, textStatus){
		if (result.code == 200) {
			notice("已保存", 1);
			window.location.reload();
		} else {
			notice(result.msg);
		}
	});
}

function getRoleMenu(role_id) {
	notice("加载中......", 1);
	$("input[type=checkbox]").each(function(){
		this.checked = false;
	});
	$("#role_id").val(role_id);
	$.post(get_privilege_uri, {"role_id":$("#role_id").val()}, function(result, textStatus){
		if (result.code == 200) {
			var data = result.data;
			setPrivilege(data);
			notice("");
		} else {
			notice(result.msg);
		}
	});
}

// 提交更新角色的权限信息
function role_menu_setting() {
	var $role = $("input[name='roleList']:checked");
	var selectId = $role.val();
	if (false == base.checkid(selectId)) {
		notice("请选择角色 ");
		return;
	}
	$.confirm({
	    title: '确认',
	    content: '确定更改角色【'+$role.parent().text()+'】的权限？',
	    animation: 'Rotate',
	    closeAnimation: 'Rotate',
	    columnClass: 'col-md-4 col-md-offset-8',
	    buttons: {
	        ' 确 定 ': {
	        	btnClass: 'btn-orange',
	        	action: function () {
	        		$("#role_id").val(selectId);
	        		var moduleArr = getPrivilege();
	        		var data = {"role_id": $("#role_id").val(), "rolePrivilegeList":moduleArr};
	        		$.post(setting_privilege_uri, data, function(result, textStatus){
	        			if (result.code == 200) {
	        				notice("权限配置完成.",1);
	        			} else {
	        				notice(result.msg);
	        			}
	        		});
	        	}
	        },
	        ' 取 消 ': {
	        	btnClass: 'btn-blur',
	        	action: function () { }
	        }
	    }
	});
}