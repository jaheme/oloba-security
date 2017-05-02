<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>权限信息</title>
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
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                        	<ul class="nav nav-tabs">
                                <li class="active"><a href="#pmenu" data-toggle="tab">菜单</a>
                                </li>
                                <li><a href="#pmodule" data-toggle="tab">模块</a>
                                </li>
                                <li><a href="#pnode" data-toggle="tab">节点</a>
                                </li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane fade in active" id="pmenu"> <br>
		                             <table class="table tree table-striped table-bordered table-hover">
		                             	<thead>
		                             		<tr><td>菜单</td><td>父菜单</td><td>模块对象 - 名称 - 值称</td></tr>
		                             	</thead>
		                                 <tbody>
		                                 	<c:forEach items="${menuList }" var="menu">  <!-- 第一层菜单 -->
		                                 		<tr>
		                                          	<td><label>${menu.desc }</label> - ${menu }</td>
		                                          	<td>${menu.parent_menu }</td>
		                                          	<td>
		                                          		<c:forEach items="${menu.modules }" var="module">
		                                          			<label>${module }</label> - 
		                                          			<span>${module.desc }</span> - 
		                                          			${module.pname } <br>
		                                          		</c:forEach>
		                                          	</td>
		                                      	</tr>
		                                     </c:forEach>
		                                 </tbody>
		                             </table>
	                         	</div>
                                <div class="tab-pane fade in" id="pmodule"> <br>
                                	<table class="table tree table-striped table-bordered table-hover">
		                             	<thead>
		                             		<tr><td>模块</td><td>节点对象 - 名称 - 值称</td></tr>
		                             	</thead>
		                                 <tbody>
		                                 	<c:forEach items="${moduleList }" var="module">  <!-- 第一层菜单 -->
		                                 		<tr>
		                                          	<td><label>${module.desc }</label> - ${module }</td>
		                                          	<td>
		                                          		<c:forEach items="${module.nodes }" var="node">
		                                          			<label> ${node } </label> - 
		                                          			<span>${node.desc }</span> - 
		                                          			${node.pname } <br>
		                                          		</c:forEach>
		                                          	</td>
		                                      	</tr>
		                                     </c:forEach>
		                                 </tbody>
		                             </table>
                                </div>
                                <div class="tab-pane fade in" id="pnode"> <br>
                                	<table class="table tree table-striped table-bordered table-hover">
		                             	<thead>
		                             		<tr><td>序号 对象</td><td>值称</td><td>说明</td></tr>
		                             	</thead>
		                                 <tbody>
		                                 	<c:forEach items="${nodeList }" var="node">  <!-- 第一层菜单 -->
		                                 		<tr>
		                                          	<td><label>${node.index } - ${node }</label></td>
		                                          	<td>${node.pname }</td>
		                                          	<td>${node.desc }</td>
		                                      	</tr>
		                                     </c:forEach>
		                                 </tbody>
		                             </table>
                                </div>
	                       	</div> <!-- / end .tab-content  -->
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

</body>
<script src="/res/jquery/jquery.treegrid.min.js"></script>
<script src="/res/jquery/jquery.treegrid.bootstrap3.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $('.tree').treegrid({
            expanderExpandedClass: 'glyphicon glyphicon-minus',
            expanderCollapsedClass: 'glyphicon glyphicon-plus'
        });
    });
</script>
</html>