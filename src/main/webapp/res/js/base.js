// sb-admin-2.js  SB Admin 2 v3.3.7+1 (http://startbootstrap.com/template-overviews/sb-admin-2)
$(function(){$("#side-menu").metisMenu()}),$(function(){$(window).bind("load resize",function(){var i=50,n=this.window.innerWidth>0?this.window.innerWidth:this.screen.width;n<768?($("div.navbar-collapse").addClass("collapse"),i=100):$("div.navbar-collapse").removeClass("collapse");var e=(this.window.innerHeight>0?this.window.innerHeight:this.screen.height)-1;e-=i,e<1&&(e=1),e>i&&$("#page-wrapper").css("min-height",e+"px")});for(var i=window.location,n=$("ul.nav a").filter(function(){return this.href==i}).addClass("active").parent();;){if(!n.is("li"))break;n=n.parent().addClass("in").parent()}});

$.ajaxSetup({
	timeout: 10000,
	error: function( jqXHR, textStatus, errorThrown ) { // jqXHR jqXHR, String textStatus, String errorThrown
		notice("出现错误 ：" + textStatus + ", " + errorThrown);
	}
});

var base = {
		"privilege": function() {
			$("button[pnode]").each(function(){
				if (pnodes.indexOf( $(this).attr("pnode") ) == -1) {
					$(this).attr("disabled","disabled");
				}
			});
		},
		"checkid": function(id) {
			if (!id || id == 0 || id == "" || id == "undefined") {
				return false;
			}
			return true;
		}
};	// end base.

// add custom function below...

/**
 * 提示小窗口
 * @param msg	提示内容
 * @param success 是否是成功类型
 */
function notice(msg, success) {
	var alertDiv = $("#alertMsg");
	if (msg == "") {
		alertDiv.slideUp();
//		alertDiv.find("label").text("处理完成。");
//		setTimeout("$('#alertMsg').slideUp();", 1000);
		return;
	}
	var useCss, rmCss;
	if (success || 1==success) {
		useCss = "alert-success";
		rmCss = "alert-danger";
	} else {
		rmCss = "alert-success";
		useCss = "alert-danger";
	}
	alertDiv.removeClass(rmCss);
	alertDiv.addClass(useCss);
	alertDiv.slideDown();
	alertDiv.find("label").text(msg);
}

/**
 * 清空form里的元素值
 * @param formId
 */
function clear_form(formId) {
    $("#"+formId).find(':input').each(function() {
        switch(this.type) {
            case 'password':
            case 'select-multiple':
            case 'select-one':
            case 'text':
            case 'textarea':
                $(this).val('');
                break;
            case 'checkbox':
            case 'radio':
                this.checked = false;
        }
    });
}