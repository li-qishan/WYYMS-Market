/**
 * 扩展textbox方法
 * @param iconCls: 图标css样式
 */
$.extend($.fn.textbox.methods, {
	//在输入框中添加图标
	addIcon: function(jq, iconCls){
		return jq.each(function(){
			var t = $(this);
			var opts = t.textbox("options");
			opts.icons = opts.icons || [];
			opts.icons.unshift({
				iconCls: iconCls,
				handler: function(e){
					$(e.data.target).textbox("clear").textbox("textbox").focus();
					$(this).css("visibility","hidden");
				}
			});
			t.textbox();
		});
	},
	setButtonText: function(jq, buttonText){
		return jq.each(function(){
			var t = $(this);
			var opts = t.textbox("options");
			opts.buttonText = buttonText;
			t.textbox();
		});
	}
});
/**
 * 扩展pagination方法
 * @param param: 数组，第一个元素是图标css样式，第二个元素是按钮文字
 */
$.extend($.fn.pagination.methods, {
	//在分页栏添加按钮
	addBtn: function(jq, param){
		return jq.each(function(){
			var t = $(this);
			var opts = t.pagination("options");
			opts.buttons = opts.buttons || [];
			opts.buttons.unshift({
				text : param[1],
				iconCls: param[0],
				handler: function(e){
					alert("test");
					$(e.data.target);
				}
			});
			t.pagination();
		});
	}
});
/**
 * 扩展panel方法
 * @param iconCls: 图标css样式
 * @param href: 远程加载路径
 */
$.extend($.fn.panel.methods, {
	//添加标题栏图标
	setIcon: function(jq, iconCls){
		return jq.each(function(){
			$.data(this,"panel").options.iconCls = iconCls;
			$(this).panel("header").find("div.panel-icon").addClass(iconCls);
		});
	},
	//添加href远程加载路径
	setHref: function(jq, href){
		return jq.each(function(){
			$.data(this,"panel").options.href = href;
		});
	}
});
/**
 * 扩展datagrid方法
 * @param title: 表格标题
 */
$.extend($.fn.datagrid.methods, {
	//添加表格标题
	setTitle: function(jq, title){
		return jq.each(function(){
			$.data(this,"datagrid").options.title = title;
			$(this).datagrid("getPanel").panel().panel("header").find("div.panel-title").html(title);
		});
	}
});
