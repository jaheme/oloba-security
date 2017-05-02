$(document).ready(function() {
    $('.tree').treegrid({
        expanderExpandedClass: 'glyphicon glyphicon-minus',
        expanderCollapsedClass: 'glyphicon glyphicon-plus'
    });
    
});


function loadUser(flag) {
	clear_form("userForm");
	notice("");
	if (!flag) { //new
		$("#passwordDiv").show();
    	$("#userModalLabel").text("新增用户");
		return;
	}
	// edit
	var uid = $("input[name='userList']:checked").val();
	if (false == base.checkid(uid)) {
		notice("先选择用户");
		return;
	}
	notice("用户资料加载中……", 1);
	$("#passwordDiv").hide();
	$("#userModalLabel").text("编辑用户");
	$("#id").val(uid);
	$.post(edit_uri, {"userid":$("#id").val()}, function(result, textStatus){
		if (result.code == 200) {
			var rd = result.data;
			$("#username").val(rd.username);
			$("#nickname").val(rd.nickname);
//			$("#dept_id").val(rd.dept_id);
			if (rd.enabled) {
				$("#enabledYes").prop("checked", true);
			} else {
				$("#enabledNo").prop("checked", true);
			}
			notice("");
		} else {
			notice(result.msg);
		}
	});
}

function userNewEdit() {
	var id = $("#id").val();
	var cp = $("#username").val();
	if (cp.length < 3) {
		notice("帐号至少要三个字 "+cp.length);
		return;
	}
	if (false == base.checkid(id)) {	// 新增操作才有密码。编辑操作不修改密码
		cp = $("#password").val();
		if (cp.length < 6) {
			notice("密码要六位");
			return;
		}
		saveUser(new_uri);
	} else {
		saveUser(edit_save_uri);
	}
}

/** 新增、编辑时调用  */
function saveUser(uri) {
	var data = $("#userForm").serialize();
	$.post(uri, data, function(result, textStatus){
		if (result.code == 200) {
			notice("已保存", 1);
			window.location.reload();
		} else {
			notice(result.msg);
		}
	});
}


function loadUserPrivilege() {
	clear_form("menuForm");
	$("#menuFooterDiv").show();	// 角色权限查看时会隐藏
	var $user = $("input[name='userList']:checked");
	var uid = $user.val();
	if (false == base.checkid(uid)) {
		notice("请选择用户 ");
		return;
	}
	notice("正在加载用户权限，请稍候......",1);
	$("#id").val(uid);
	$("#menuModalLabel").text($user.parent().text()+"的权限配置");
	$.post(user_privilege_uri, {"userid": $("#id").val()}, function(result, textStatus){
		if (result.code == 200) {
			var data = result.data;
			setPrivilege(data);
			notice("");
		} else {
			notice(result.msg);
		}
	});
}

//提交更新角色的权限信息
function settingUserPrivilege() {
	notice("正在配置角色，请稍候......",1);
	$("#id").val($("input[name='userList']:checked").val());
	var data = {"userid": $("#id").val(), "privilegeList":getPrivilege()};
	$.post(setting_privilege_uri, data, function(result, textStatus){
		if (result.code == 200) {
			notice("权限配置完成.",1);
		} else {
			notice(result.msg);
		}
	});
}


//加载显示角色的权限
function loadRolePrivilege() {
	var $role = $("input[name='roleList']:checked");
	if ($role.parent().text() == "") {
		notice("角色未选择。");
		return;
	}
	clear_form("menuForm");
	$("#menuFooterDiv").hide();
	notice("角色的权限加载中......", 1);
	$("#menuModalLabel").text($role.parent().text()+"的权限配置");
	$.post(role_privilege_uri, {"role_id": $role.val()}, function(result, textStatus){
		if (result.code == 200) {
			var data = result.data;
			setPrivilege(data);
			notice("");
		} else {
			notice(result.msg);
		}
	});
}

// 通过角色配置用户的权限
function rolePrivilege2User() {
	var $user = $("input[name='userList']:checked");
	if ($user.parent().text() == "") {
		notice("请选择 用户 。");
		return;
	}
	var $role = $("input[name='roleList']:checked");
	if ($role.parent().text() == "") {
		notice("请选择 角色。");
		return;
	}
	$.confirm({
	    title: '确认',
	    content: '确定要通过角色【'+$role.parent().text()+'】更改用户【'+$user.parent().text()+'】的权限？',
	    animation: 'Rotate',
	    closeAnimation: 'Rotate',
	    columnClass: 'col-md-4 col-md-offset-8',
	    buttons: {
	        ' 确 定 ': {
	        	btnClass: 'btn-orange',
	        	action: function () {
	        		settingPrivilegeWithRole($user.val(), $role.val());
	        	}
	        },
	        ' 取 消 ': {
	        	btnClass: 'btn-blur',
	        	action: function () { }
	        }
	    }
	});
}

// 提交更新角色的权限信息
function settingPrivilegeWithRole(userid, role_id) {
	notice("正在配置角色，请稍候......",1);
	$("#id").val(userid);
	$("#role_id").val(role_id);
	var data = {"userid": $("#id").val(), "role_id": $("#role_id").val()};
	$.post(setting_privilege_withrole_uri, data, function(result, textStatus){
		if (result.code == 200) {
			notice("权限配置完成.",1);
		} else {
			notice(result.msg);
		}
	});
}
