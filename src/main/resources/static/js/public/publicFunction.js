//下拉框、复选框绑定
$(document).ready(function () {
	$("select[codetype]").each(function(){
		var id = $(this).attr("id");
		var val = $(this).attr("value");
		var codeType = $(this).attr("codeType");
		$("#"+id).empty();
		var html ="<option></option>";
		if (codeType){
			$.ajax({
				url : '/common/dict/listByExample',
				type : 'POST',
				dataType : 'json',
				data : {"type":codeType},
				async : false,
				success : function(data) {
					for (var i in data) {
						if (val == data[i].value){
							html += '<option selected value="' + data[i].value + '">' + data[i].name + '</option>';
						}else {
							html += '<option value="' + data[i].value + '">' + data[i].name + '</option>';
						}
					}
				}
			});
		}
		$("#"+id).append(html);
	});
})

var loading;
layui.use(['laydate', 'laypage', 'layer', 'form','jquery', 'element', 'table','layedit','laydate'], function(){
	var laydate = layui.laydate //日期
		,laypage = layui.laypage //分页
		,layer = layui.layer //弹层
		,table = layui.table //表格
		,element = layui.element //元素操作
		,form = layui.form
		,$ = layui.$
		,layedit = layui.layedit
		,laydate = layui.laydate;
	laydate.render({
		elem: '.datetimeLayuiClick'
		,type: 'datetime'
		,trigger: 'click'
	});

	laydate.render({
		elem: '.datetimeLayuiClick2'
		,type: 'datetime'
		,trigger: 'click'
	});

	laydate.render({
		elem: '.datetimeLayuiClickRange'
		,type: 'datetime'
		,trigger: 'click'
		,range: '~'
	});

	laydate.render({
		elem: '.datetimeLayui'
		,type: 'datetime'
	});

	laydate.render({
		elem: '.datetimeNowLayui'
		,type: 'datetime'
		,value:new Date()
	});
});

function showLoading() {
	layui.use('layer', function(){
		var layer = layui.layer;
		loading = layer.load(1);
	});
}

function hideLoading() {
	layui.use('layer', function(){
		layer.close(loading);
	});
}

function checkKeyupPositiveInteger(obj){
	var oldVal = $(obj).val();
	var newVal = oldVal.replace(/^0(0+)|[^\d]+/g,'');
	if(!checkIsBlank(newVal)){
		$(obj).val(newVal);
	}else{
		$(obj).val('');
	}
}

function checkKeyupDouble_2(obj){
	// 清除"数字"和"."以外的字符
	obj.value = obj.value.replace(/[^\d.]/g,"");
	// 验证第一个字符是数字
	obj.value = obj.value.replace(/^\./g,"");
	// 只保留第一个, 清除多余的
	obj.value = obj.value.replace(/\.{2,}/g,".");
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	// 只能输入两个小数
	obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3');
}

function checkIsBlank(obj){
	if(obj == '' || obj == null || !obj){
		return true;
	}else{
		return false;
	}
}

function addDay(datetime, days) {
	var old_time = new Date(datetime.replace(/-/g, "/")); //替换字符，js不认2013-01-31,只认2013/01/31
	var fd = new Date(old_time.valueOf() + days * 24 * 60 * 60 * 1000); //日期加上指定的天数
	var new_time = fd.getFullYear() + "-";
	var month = fd.getMonth() + 1;
	if (month >= 10) {
		new_time += month + "-";
	} else {
		//在小于10的月份上补0
		new_time += "0" + month + "-";
	}
	if (fd.getDate() >= 10) {
		new_time += fd.getDate();
	} else {
		//在小于10的日期上补0
		new_time += "0" + fd.getDate();
	}
	return new_time; //输出格式：2013-01-02
}

//获取随机数的方法
function getUUID() {
	var s = [];
	var hexDigits = "0123456789abcdef";
	for (var i = 0; i < 36; i++) {
		s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	}
	s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
	s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
	s[8] = s[13] = s[18] = s[23] = "-";

	var uuid = s.join("");
	return uuid;
}

function getTypeListByTypeName(typeName) {
	var typeList;
	$.ajax({
		url : '/common/dict/listByExample',
		type : 'POST',
		dataType : 'json',
		data : {"type":typeName},
		async : false,
		success : function(data) {
			typeList = data;
		}
	});
	return typeList;
}

function AddStyles() {
	var toolbar = $("#my_table").find(".fixed-table-toolbar").eq(0);
	toolbar.addClass("my_toolbar");
	toolbar.find(".pull-left").addClass("my_pull_left");
	toolbar.find(".pull-right").addClass("my_pull_right");
}