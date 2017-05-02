<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>角色管理配置</title>
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
            	<div class="col-lg-4">
                    <div class="panel panel-default">
                    	<div class="panel-heading">
                    		<label>我的角色</label>
                    		<div class="pull-right">
	                        	<button pnode="new" onclick="javascript:loadRole(false);" class="btn btn-outline btn-primary btn-sm" data-toggle="modal" data-target="#roleModal">新增</button>
	                        	<button pnode="edit" onclick="javascript:loadRole(true);" class="btn btn-outline btn-primary btn-sm" data-toggle="modal" data-target="#roleModal">编辑</button>
                        	</div>
                    	</div>
                        <div class="panel-body">
                        	<table class="table tree table-striped table-bordered table-hover">
                        		<tbody>
                        			<c:forEach items="${roleList }" var="r">
                        			<tr data-toggle="tooltip" data-placement="left">
                                      	<td title="${r.role_desc }">
                                      		<label class="radio-inline" style="width:260px">
                                      			<input name="roleList" id="roleList" type="radio" value="${r.role_id }"/>
                                      			${r.role_name }
                                      		</label>
                                      	</td>
                                  	</tr>
                                  	</c:forEach>
                                </tbody>
                            </table>
                         </div>
                     </div>
                </div>
                <div class="col-lg-8">
                    <div class="panel panel-default">
                    	<div class="panel-heading">
                            <label>配置角色的权限</label>
                    		<div class="pull-right">
                            	<button pnode="setting" onclick="javascript:role_menu_setting();" class="btn btn-outline btn-primary btn-sm">保存配置</button>
                            </div>
                        </div>
                        <div class="panel-body">
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
                             </form>
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
    
    <div class="modal fade" id="roleModal" tabindex="-1" role="dialog" aria-labelledby="roleModalLabel" aria-hidden="true">
       <div class="modal-dialog">
           <div class="modal-content">
               <div class="modal-header">
                   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                   <h4 class="modal-title" id="roleModalLabel"></h4>
               </div>
               <form id="roleForm" role="form">
               		<input type="hidden" name="role_id" id="role_id" value="0" />
	               <div class="modal-body">
                   		<div class="form-group">
                   			<label>角色名称</label>
                            <input type="text" name="role_name" id="role_name" class="form-control" placeholder="建议有字面意思的中方角色名称">
                        </div>    
                   		<div class="form-group">
                            <label>描述：</label>
                            <textarea name="role_desc" id="role_desc" class="form-control" rows="3" placeholder="描述角色基本的职责、权限信息"></textarea>
                        </div>
	               </div>
	               <div class="modal-footer">
	                   <div id="roleModalAlert" class="alert alert-success alert-dismissable" style="display:none">
                           <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
                           <label>保存成功</label>.
                       </div>
	                   <button class="btn btn-default" data-dismiss="modal">关闭/取消</button>
	                   <button type="button" id="btnSubmit" onclick="javascript:roleNewEdit();" class="btn btn-primary">提交保存</button>
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
<script src="/res/js/privilege/role.js"></script>
<script type="text/javascript">
var new_uri = "${new_uri}";
var edit_uri = "${edit_uri}";
var edit_save_uri = "${edit_save_uri}";
var get_privilege_uri = "${get_privilege_uri}";
var setting_privilege_uri = "${setting_privilege_uri}";
</script>
</html>