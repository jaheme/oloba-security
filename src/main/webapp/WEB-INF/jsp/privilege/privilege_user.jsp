<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>用户管理配置</title>
    <link rel="stylesheet" href="/res/css/jquery.treegrid.css">
</head>

<body>

    <div id="wrapper">
    	<%@ include file="../menu.jsp" %>
        <!-- Page Content -->
        <div id="page-wrapper">
           <div class="row">
                <div class="col-lg-12">
                &nbsp;
                </div>
            </div>
            <div class="row">
            	<div class="col-lg-7">
                    <div class="panel panel-default">
                    	<div class="panel-heading">
                    		<label>我的用户</label>
                    		<div class="pull-right">
	                        	<button pnode="new" onclick="javascript:loadUser(false);" class="btn btn-outline btn-primary btn-sm" data-toggle="modal" data-target="#userModal">新增</button>
	                        	<button pnode="edit" onclick="javascript:loadUser(true);" class="btn btn-outline btn-primary btn-sm" data-toggle="modal" data-target="#userModal">编辑</button>
	                        	<button pnode="setting" onclick="javascript:loadUserPrivilege();" class="btn btn-outline btn-primary btn-sm" data-toggle="modal" data-target="#menuModal">配置用户权限</button>
                        	</div>
                    	</div>
                        <div class="panel-body">
                        	<table class="table tree table-striped table-bordered table-hover">
                        		<tbody>
                        			<c:forEach items="${userList }" var="user">
                        			<tr>
                                      	<td>
                                      		<label class="radio-inline" style="width:260px">
                                      			<input name="userList" id="userList" type="radio" value="${user.id }"/>
                                      			${user.nickname } - ${user.username }
                                      		</label>
                                      	</td>
                                      	<td>${user.enabled ? "在启用" : "已禁用" }</td>
                                  	</tr>
                                  	</c:forEach>
                                </tbody>
                            </table>
                         </div>
                     </div>
                </div>
                <div class="col-lg-5">
                    <div class="panel panel-default">
                    	<div class="panel-heading">
                            <label>我的角色</label>
                    		<div class="pull-right">
                            	<button pnode="get" onclick="javascript:loadRolePrivilege();" 
                            		class="btn btn-outline btn-primary btn-sm"
                            		data-toggle="modal" data-target="#menuModal">查看角色权限</button>
                            	<button pnode="setting" onclick="javascript:rolePrivilege2User();" title="将此角色的权限配置给指定的用户"
                            		class="btn btn-outline btn-primary btn-sm">采用角色权限</button>
                            </div>
                        </div>
                        <div class="panel-body"><input type="hidden" name="role_id" id="role_id" value="0" />
                        	<table class="table tree table-striped table-bordered table-hover">
                        		<tbody>
                        			<c:forEach items="${roleList }" var="r">
                        			<tr data-toggle="tooltip" data-placement="left">
                                      	<td title="${r.role_desc }">
                                      		<label class="radio-inline" style="width:260px">
                                      			<input name="roleList" type="radio" value="${r.role_id }"/>
                                      			${r.role_name }
                                      		</label>
                                      	</td>
                                  	</tr>
                                  	</c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                
            </div>
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->
    
    <div class="modal fade" id="userModal" tabindex="-1" role="dialog" aria-labelledby="userModalLabel" aria-hidden="true">
       <div class="modal-dialog">
           <div class="modal-content">
               <div class="modal-header">
                   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                   <h4 class="modal-title" id="userModalLabel"></h4>
               </div>
               <form id="userForm" role="form">
               		<input type="hidden" name="id" id="id" value="0" />
	               <div class="modal-body">
                   		<div class="form-group">
                   			<label>姓名</label>
                            <input type="text" name="nickname" id="nickname" class="form-control" placeholder="建议中文名称">
                        </div>
                   		<div class="form-group">
                   			<label>帐号</label>
                            <input type="text" name="username" id="username" class="form-control" placeholder="【必填】登录帐号，建议用英文/拼音">
                        </div>
                   		<div class="form-group" id="passwordDiv">
                   			<label>登录密码</label>
                            <input type="text" name="password" id="password" class="form-control" placeholder="【必填】六位密码">
                        </div>    
                   		<div class="form-group">
                            <label>启用：</label>
                            <label class="radio-inline">
                      			<input name="enabled" id="enabledYes" type="radio" value="1" checked/> 启用
                      		</label>
                            <label class="radio-inline">
                      			<input name="enabled" id="enabledNo" type="radio" value="0"/> 禁用
                      		</label>
                        </div>
	               </div>
	               <div class="modal-footer">
	                   <button class="btn btn-default" data-dismiss="modal">关闭/取消</button>
	                   <button type="button" id="userBtnSubmit" onclick="javascript:userNewEdit();" class="btn btn-primary">提交保存</button>
	               </div>
               </form>
           </div>
           <!-- /.modal-content -->
       </div>
       <!-- /.modal-dialog -->
   </div>
   
   <div class="modal fade" id="menuModal" tabindex="-1" role="dialog" aria-labelledby="menuModalLabel" aria-hidden="true">
       <div class="modal-dialog">
           <div class="modal-content">
               <div class="modal-header">
                   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                   <h4 class="modal-title" id="menuModalLabel"></h4>
               </div>
               <form id="menuForm" role="form" action="">
	           	<table class="table tree table-striped table-bordered table-hover">
	                 <tbody>
	                 	<c:forEach items="${menuList }" var="menu">  <!-- 第一层菜单 -->
                    		<tr class="treegrid-${menu.id }">
                             	<td style="width:330px"><label>${menu.name }</label></td>
                             	<td></td>
                         	</tr>
                         	<!-- sub menu 子菜单-->
                         	<c:forEach items="${menu.subList }" var="subMenu">
                           	<tr class="treegrid-${subMenu.id } treegrid-parent-${subMenu.pid}">
                               	<td style="width:330px"><label>${subMenu.name }</label></td>
                               	<td></td>
                           	</tr>
                           	<!-- 子菜单的子模块 -->
                           	<c:forEach items="${subMenu.moduleList }" var="subm"> 
                           	<tr class="treegrid-${subm.id } treegrid-parent-${subm.pid}">
                               	<td>
                                    <label class="checkbox-inline">
                                        <input name="module" type="checkbox" value="${subm.pname }">${subm.desc } （${subm.pname }）
                                    </label>
                               	</td>
                               	<td>
                               		<c:forEach items="${subm.nodeList }" var="node">
                                     <label class="checkbox-inline">
                                         <input name="${subm.pname }" type="checkbox" value="${node.pname }">${node.desc } (${node.pname })
                                     </label><br>
                               	</c:forEach>
                               	</td>
                           	</tr>
                           	</c:forEach>
                         	</c:forEach><!-- sub menu end -->
                         <!-- 第一层菜单的子模块 -->
                    		<c:forEach items="${menu.moduleList }" var="ml"> 
                     	 	<tr class="treegrid-${ml.id } treegrid-parent-${ml.pid}">
                              	<td>
                                    <label class="checkbox-inline">
                                        <input name="module" type="checkbox" value="${ml.pname }">${ml.desc } （${ml.pname }）
                                    </label>
                              	</td>
                              	<td>
                              		<c:forEach items="${ml.nodeList }" var="node">
                               	 	<label class="checkbox-inline">
                                      <input name="${ml.pname }" type="checkbox" value="${node.pname }">${node.desc } (${node.pname })
                                  </label><br>
                              	</c:forEach>
                              	</td>
                          	</tr>
                         	</c:forEach>
                        </c:forEach>
	                 </tbody>
	             </table>
               <div class="modal-footer" id="menuFooterDiv">
                   <button class="btn btn-default" data-dismiss="modal">关闭/取消</button>
                   <button pnode="setting" id="menuBtnSubmit" onclick="javascript:settingUserPrivilege();" class="btn btn-primary">提交保存</button>
               </div>
	           	</form>
           </div>
           <!-- /.modal-content -->
       </div>
       <!-- /.modal-dialog -->
   </div>
   
   
                        	

</body>
<script src="/res/jquery/jquery.treegrid.min.js"></script>
<script src="/res/jquery/jquery.treegrid.bootstrap3.js"></script>
<script src="/res/js/privilege/privilege.js"></script>
<script src="/res/js/privilege/user_privilege.js"></script>
<script type="text/javascript">
var new_uri = "${new_uri}";
var edit_uri = "${edit_uri}";
var edit_save_uri = "${edit_save_uri}";
var user_privilege_uri = "${user_privilege_uri}";
var role_privilege_uri = "${role_privilege_uri}";
var setting_privilege_uri = "${setting_privilege_uri}";
var setting_privilege_withrole_uri = "${setting_privilege_withrole_uri}";
</script>
</html>