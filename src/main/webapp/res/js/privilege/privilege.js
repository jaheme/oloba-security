
$(":checkbox[name='module']").change(function(){
	var module_name = this.value;
	if (this.checked) {
		$(":checkbox[name='"+module_name+"']").first().prop("checked", true);
	} else {
		$(":checkbox[name='"+module_name+"']").each(function(){
			$(this).prop("checked", false);
		});
	}
});

/**
 * 返回权限的Array表现<br>
 * Array["module#node1|node2|node3|..."]
 * @returns {Array}
 */
function getPrivilege() {
	var moduleArr = new Array();
	var moduleV, nodeV;
	$("input[name='module']:checked").each(function(){
		moduleV = $(this).val();
		nodeV = $("input[name='"+moduleV+"']:checked").map(function() {
			  return this.value;
			}).get().join('|');
		moduleArr.push(moduleV+"#"+nodeV);
	});
	return moduleArr;
}

/**
 * 给权限树赋值
 * @param data List<Map<"module","node1,node2,node3,...">>
 */ 
function setPrivilege(data) {
	var val_array;
	$.each(data, function(index, ele){
		for (var name in ele) {
			$(":checkbox[value='"+name+"']").prop("checked", true);
			val_array = ele[name].toString().split(",");
			for (var i in val_array) {
				$(":checkbox[name='"+name+"'][value='"+val_array[i]+"']").prop("checked", true);
			}
		}
		//alert(ele.module + "   " + ele.nodes);
	});
}