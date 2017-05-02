<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="renderer" content="webkit" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>HR-SAAS</title>
	<link href="/res/css/bootstrap.min.css" rel="stylesheet">
    <link href="/res/css/sb-admin-2.css" rel="stylesheet">

</head>
<body>
	<div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">登录窗口 ${time }</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form" id="login_form" action="/hs/jss_login_check" method="POST">
                            <fieldset>
                                <div class="form-group">
                                    <input value="super" class="form-control" placeholder="帐号" name="username" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input value="this" class="form-control" placeholder="密码" name="password" id="password" type="password">
                                </div>
                                <div class="form-group">
                                	<div class="row">
                                		<div class="col-sm-5">
                                    		<input class="form-control" placeholder="输入验证码" name="vcode" id="vcode" type="text"/>
                                    	</div>
                                    	<div class="col-sm-7">
                                    		<img src="/hs/verify_code">
                                    	</div>
                                    </div>
                                </div>
                                <button onclick="javascript:login(this);" class="btn btn-lg btn-success btn-block"> 登 录 </button>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
<script src="/res/jquery/jquery-3.2.1.min.js"></script>
<script src="/res/js/bootstrap.min.js"></script>
<script src="/res/js/jquery.md5.js"></script>
<script type="text/javascript">
function login(obj) {
	var pmd5 = $.md5($("#password").val());
	var vcode = $("#vcode").val().toLowerCase();
	$("#password").val( $.md5(pmd5+vcode) );
	$("#vcode").val("");
	$("#vcode").attr("disabled", true);
	$(obj).attr("disabled", true);
	$(obj).text("正在登录……");
	$("#login_form").submit();
}
</script>
</html>