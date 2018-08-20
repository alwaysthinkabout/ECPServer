/*公共函数,全局变量*/
var nameValid = true;
//扩充string,增加format函数
String.prototype.format = function(args) {
    var result = this;
    if (arguments.length > 0) {
        if (arguments.length == 1 && typeof (args) == "object") {
            for (var key in args) {
                if(args[key]!=undefined){
                    var reg = new RegExp("({" + key + "})", "g");
                    result = result.replace(reg, args[key]);
                }
            }
        }
        else {
            for (var i = 0; i < arguments.length; i++) {
                if (arguments[i] != undefined) {
                    var reg = new RegExp("({[" + i + "]})", "g");
                    result = result.replace(reg, arguments[i]);
                }
            }
        }
    }
    return result;
}
//删除函数，包括删除已选择的关联品牌和母公司，以及联系人
function deleteB(t){
	$($(t).data("target")).remove();
}
//检查公司名称是否为空
function checkName(value){
	if(value!=""){
		$("#nameCheck").addClass("hidden");
		nameValid = true;
	}else{
		nameValid = false;
		$("#nameCheck span").text('公司名称不能为空');
		$("#nameCheck").removeClass("hidden");
	}
}
$(document).ready(function(){

	$("input[name='clientAudit.brefName']").maxlength({
    	maxCharacters:10,
    	status:false,
    	showAlert:true,
    	alertText:"您输入的长度过长！"
    })
    $("input[name='clientAudit.address']").maxlength({
    	maxCharacters:50,
    	status:false,
    	showAlert:true,
    	alertText:"您输入的长度过长！"
    })
    $("input[name='clientAudit.phone']").maxlength({
    	maxCharacters:20,
    	status:false,
    	showAlert:true,
    	alertText:"您输入的长度过长！"
    })
    $("input[name='clientAudit.fax']").maxlength({
    	maxCharacters:20,
    	status:false,
    	showAlert:true,
    	alertText:"您输入的长度过长！"
    })
    $("input[name='clientAudit.website']").maxlength({
    	maxCharacters:30,
    	status:false,
    	showAlert:true,
    	alertText:"您输入的长度过长！"
    })

	$("#save").click(function(){
		checkName($("input[name='clientAudit.name']").val());
		if(nameValid){
			var check = true;
			var brands = "";
			$(".selectBrand").each(function(){
				brands += $(this).data("target")+"_";
			})
			brands = brands.substring(0,brands.length-1);

			//设置跟联系人有关的表单属性
			$("#contactsView > .row").each(function(i){
				console.log("联系人下标"+i);
				$(this).find("input[data-name='name']").each(function(){
					if($(this).val() == ""){
						alert("联系人姓名不能为空！");
						$(this).focus();
						check = false;
						return false;
					}
				})
				if(check){
					$(this).find("input[data-name='cellphone']").each(function(){
						if($(this).val() == ""){
							alert("手机号码不能为空!");
							$(this).focus();
							check = false;
							return false;
						}
					})
				}
				if(check){
					$(this).find("input[data-name='position']").each(function(){
						if($(this).val() == ""){
							alert("职位不能不能为空!");
							$(this).focus();
							check = false;
							return false;
						}
					})
				}
				if(check){
					$(this).find("input[data-name='cellphone']").each(function(){
						if($(this).val() == ""){
							alert("请上传名片!");
							$(this).focus();
							check = false;
							return false;
						}
					})
				}
				if(check){
					$(this).find("input[type='file']").each(function(){
						if($(this).data("create")!="old" && $(this).val() == ""){
							alert("请上传名片!");
							$(this).focus();
							check = false;
							return false;
						}
					})
				}else{
					return false;
				}
				if(!check)
					return false;
				$(this).find("select,input").attr("name",function(){
					if($(this).data("name")){
						if($(this).data("name")!="exclude")
							return "contactAudits["+i+"]."+$(this).data("name");
					}
					else
						return "upload";
				});
			})

			if(!check){
				return;
			}
			var websiteVal = $("input[name='clientAudit.website']").val();
			if(websiteVal!="" && websiteVal.search(/^http:\/\/|^https:\/\//)===-1){
				$("input[name='clientAudit.website']").val("http://"+websiteVal);
			}
			var options = {
				url:save_url+brands,
				type:"post",
				dataType:"json",
				resetForm:true,
				success:function(data){
					if(data.info){
						alert(success);
						afterSave();
					}else{
						alert(failed);
					}
				},
				error:function(data){
					alert(failed);
				}
			}

			$("#form_save").ajaxForm(options).submit();
		}
	})
})




/*增删联系人部分*/
var contactHTML='<div class="row" style="margin-bottom:20px;" id="c{row_index}">'
					+'<div class="col-sm-8">'
						+'<div class="input-group input-group-sm"><span class="input-group-addon">状态</span><select class="form-control input-sm" data-name="state"><option value="O">在职</option><option value="L">离职</option></select></div>'
						+'<div class="input-group input-group-sm"><span class="input-group-addon">姓名 <span class="text-danger">*</span></span><input type="text" class="form-control" data-name="name" maxlength="20"></div>'
						+'<div class="input-group input-group-sm"><span class="input-group-addon">座机</span><input type="text" class="form-control" data-name="telphone" maxlength="20"></div>'
						+'<div class="input-group input-group-sm"><span class="input-group-addon">邮箱</span><input type="text" class="form-control" data-name="email" maxlength="30"></div>'
						+'<div class="input-group input-group-sm"><span class="input-group-addon">手机</span><input type="text" class="form-control" data-name="cellphone" maxlength="20"></div>'
						+'<div class="input-group input-group-sm"><span class="input-group-addon">职位</span><input type="text" class="form-control" data-name="position" maxlength="20"></div>'
						+'<div class="input-group input-group-sm"><span class="input-group-addon">上级联系人</span><input type="text" class="form-control" data-name="upperContact" maxlength="50"></div>'
						+'<div class="input-group input-group-sm"><span class="input-group-addon">QQ</span><input type="text" class="form-control" data-name="qq" maxlength="20"></div>'
						+'<div class="input-group input-group-sm"><span class="input-group-addon">微信号</span><input type="text" class="form-control"  data-name="weixin" maxlength="20"></div>'
						+'<div class="input-group input-group-sm"><span class="input-group-addon">学历</span><input type="text" class="form-control"  data-name="education" maxlength="10"></div>'
						+'<div class="input-group input-group-sm"><span class="input-group-addon">兴趣爱好</span><input type="text" class="form-control"  data-name="hobbies" maxlength="50"></div>'
						+'<div class="input-group input-group-sm"><span class="input-group-addon">地址</span><input type="text" class="form-control" data-name="address" maxlength="50"></div>'
						+'<div class="input-group input-group-sm date" id="form_date{date_index}" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input{time_index}" data-link-format="yyyy-mm-dd">'
							+'<span class="input-group-addon">出生日期</span>'
		                    +'<input class="form-control" size="16" type="text" value="" data-name="exclude" readonly>'
		                    +'<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>'
							+'<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>'
		                +'</div>'
						+'<input type="hidden" id="dtp_input{time_index}" value="" data-name="birthday"/>'
						+'<div class="input-group input-group-sm">'
							+'<span class="input-group-addon">星座</span>'
							+'<select class="form-control input-sm"  data-name="constellation">'
								+'<option value="白羊座">白羊座</option>'
								+'<option value="金牛座">金牛座</option>'
								+'<option value="双子座">双子座</option>'
								+'<option value="巨蟹座">巨蟹座</option>'
								+'<option value="狮子座">狮子座</option>'
								+'<option value="处女座">处女座</option>'
								+'<option value="天秤座">天秤座</option>'
								+'<option value="天蝎座">天蝎座</option>'
								+'<option value="射手座">射手座</option>'
								+'<option value="摩羯座">摩羯座</option>'
								+'<option value="水瓶座">水瓶座</option>'
								+'<option value="双鱼座">双鱼座</option>'
							+'</select>'
						+'</div>'
						+'<div class="input-group input-group-sm"><span class="input-group-addon">其他</span><input type="text" class="form-control" data-name="remark" maxlength="50"></div>'
						+'<div class="input-group input-group-sm"><span class="input-group-addon">名片 <span class="text-danger">*</span></span><input type="file" class="input-sm"></div>'
					+'</div>'
					+'<button type="button" class="btn btn-danger btn-sm" data-target="#c{row_index}" onclick="deleteB(this);">删除</button>'
				+'</div>';
$(document).ready(function(){
	i = 0;
	$("#addContact").click(function(){
		$("#contactsView").append(contactHTML.format({"time_index":i,"row_index":i,"date_index":i}));
		//日期选择器
		$('#form_date'+i).datetimepicker({
	        language:  'zh-CN',
	        weekStart: 1,
	        todayBtn:  1,
	        autoclose: 1,
	        todayHighlight: 1,
	        startView: 2,
	        minView: 2,
	        forceParse: 0
	    });
	    i++;
	})
})




/*关联品牌部分*/
function formatUser(cellvalue, options, rowObject){
	if(cellvalue.id == null){
		return "";
	}
	return cellvalue.name;
	//return "<a href='userManage/userInfoDo?tid="+cellvalue.id+"'>"+cellvalue.name+"</a>";
}
function formatBrand(cellvalue, options, rowObject){
	if(cellvalue.id == null){
		return "";
	}
	return cellvalue.name;
	//return "<a href='brandManage/brandInfoDo?tid="+cellvalue.id+"'>"+cellvalue.name+"</a>";
}
$(document).ready(function(){
	$("body").prepend("<div id='brand-modal_box'></div>");
	$("#brand-modal_box").load('brandCommonPool.html',function(){
		var t1 = $("#brand-jqgrid");
		$("#brand-jqgrid").length > 0 && (t1.jqGrid({
		    url: "brandManage/listBrand2.ajax",
		    mtype: "GET",
		    datatype: "local",
		    colNames: ['','品牌名称','修改时间','修改人'],
		    colModel: [{
		    	name:"name",
		    	sortable:false,
		    	search: !1,
		    	hidden: !0
		    },{
		        name: "nameLink",
		        width:170,
		        index: "name",
		        align:"center",
		        formatter:formatBrand
		    },
		    {
		    	name:"alterTimestamp",
		    	width:170,
				index:"alterTimestamp",
				align:"center"
		    },
		    {
		    	name:"alterUserName",
		    	width:170,
				index:"alterUser.name",
				align:"center",
				formatter:formatUser
		    }],
		    height: 200,
		    autowidth:!0,
		    rowNum: 10,
		    rowList: [10, 20, 30],
		    pager: "brand-jqgrid-pager",
		    sortname: "name",
		    viewrecords: !0,
		    sortorder: "asc",
		    multiselect: !0,
		    gridComplete:function(){
		    	$(".selectBrand").each(function(){
					t1.jqGrid('setSelection',$(this).data("target"),false);
				})
		    }
		}).navGrid('#brand-jqgrid-pager',{edit:false,add:false,del:false,search:false}));

		$("#brand-modal-id").on('show.bs.modal',function(){
			t1.jqGrid('setGridParam',{datatype:'json'}).trigger("reloadGrid");
		})

		$("#brand-ok").click(function(){
			var ids = t1.jqGrid('getGridParam','selarrrow');
			$("#brandsView").empty();
			for(var i=0;i<ids.length;i++){
				var name = t1.jqGrid('getCell',ids[i],'name');
				console.log("选中的行id["+ids[i]+"]name["+name+"]");
				$("#brandsView").append('<p class="form-control-static selectBrand" id="selectBrand'+i+'" data-target="'+ids[i]+'"><span>'+name+'</span><button type="button" class="btn btn-link btn-xs pull-right deleteSelectBrand" data-target="#selectBrand'+i+'" onclick="deleteB(this)">删除</button></p>');
			}
			$("#brand-modal-id").modal('hide');
		})

		//模糊搜索品牌
	    $("#brand-searchText").keypress(function(event){
	    	if(event.keyCode == "13"){
	    		$("#brand-searchButton").click();
	    	}
	    });
	    $("#brand-searchButton").click(function(){
	    	var searchFilter = $("#brand-searchText").val();
	    	if(searchFilter.length === 0){
	    		t1[0].p.search = false;
	    		$.extend(t1[0].p.postData,{searchString:"",searchField:"",searchOper:""});
	    	}else{
	    		t1[0].p.search = true;
	    		$.extend(t1[0].p.postData,{searchString:searchFilter,searchField:"allfieldsearch",searchOper:"cn"});
	    	}
	    	t1.trigger("reloadGrid",[{page:1,current:true}]);
	    })
	})
})


/*选择母公司部分*/
var lastSel = {'id':undefined,'name':''};
function formatClient(cellvalue, options, rowObject){
	if(cellvalue.id == null){
		return "";
	}
	return cellvalue.name;
	//return "<a href='clientManage/clientInfoDo?tid="+cellvalue.id+"'>"+cellvalue.name+"</a>";
}
$(document).ready(function(){
	$("body").prepend("<div id='client-modal_box'></div>");
	$("#client-modal_box").load("clientCommonPool.html",function(){
		var t = $("#client-jqgrid");
		$("#client-jqgrid").length > 0 && (t.jqGrid({
		    url: "clientManage/listClient2.ajax",
		    mtype: "GET",
		    datatype: "local",
		    colNames: ['','公司名称','客户简称','行业类型'],
		    colModel: [{
		    	name:"name",
		    	sortable:false,
		    	search: !1,
		    	hidden: !0
		    },{
		        name: "nameLink",
		        width:170,
		        index: "name",
		        align:"center",
		        formatter:formatClient
		    },
		    {
		    	name:"brefName",
		    	width:170,
				index:"brefName",
				align:"center"
		    },
		    {
		    	name:"industry",
		    	width:170,
				index:"industry",
				align:"center",
		    }],
		    height: 200,
		    autowidth:!0,
		    rowNum: 10,
		    rowList: [10, 20, 30],
		    pager: "client-jqgrid-pager",
		    sortname: "name",
		    viewrecords: !0,
		    sortorder: "asc",
		    multiselect: !0,
		    gridComplete:function(){
	        	if(lastSel.id!=undefined)
	        		t.jqGrid('setSelection',lastSel.id,false);
	        },
	        beforeSelectRow:function(rowid, e){
	            t.jqGrid('resetSelection');
	            return true;
	        },
	        onSelectRow: function(rowId, status, e) {
		        if (rowId == lastSel.id) {
		            $(this).jqGrid("resetSelection");
		            lastSel.id = undefined;
		            status = false;
		        } else {
		            lastSel.id = rowId;
		            lastSel.name = t.jqGrid('getCell',rowId,'name');
		        }
		    }
		}).navGrid('#client-jqgrid-pager',{edit:false,add:false,del:false,search:false}));


		$("#client-modal-id").on('show.bs.modal',function(){
			if($("#for_pid").val() == ""){
				lastSel = {'id':undefined,'name':''};
				t.jqGrid('setGridParam',{datatype:'json'}).trigger("reloadGrid",[{page:1}]);
			}else{
				lastSel.id = $("#for_pid").val();
				lastSel.name =  $("#selectBrand span").text();
				t.jqGrid('setGridParam',{datatype:'json'}).trigger("reloadGrid");
			}
			$('#cb_client-jqgrid').addClass('hidden');
		})

		$("#client-ok").click(function(){
			if(lastSel.id!=undefined){
				$("#selectClient span").text(lastSel.name);
				$("#selectClient").removeClass("hidden");
				$("#deleteSelectClient").removeClass("hidden");
				$("#for_pid").val(lastSel.id);
			}else{
				$("#deleteSelect").click();
			}
			$("#client-modal-id").modal('hide');
		})
		$("#deleteSelectClient").click(function(){
			$("#selectClient span").text("");
			$("#selectClient").addClass("hidden");
			$("#for_pid").val("");
			$(this).addClass("hidden");
		})

		//模糊搜索客户
		$("#searchClient").keypress(function(event){
			if(event.keyCode == "13"){
				$("#searchClientButton").click();
			}
		});
		$("#searchClientButton").click(function(){
			var searchFilter = $("#searchClient").val();
			if(searchFilter.length === 0){
				t[0].p.search = false;
				$.extend(t[0].p.postData,{searchString:"",searchField:"",searchOper:""});
			}else{
				t[0].p.search = true;
				$.extend(t[0].p.postData,{searchString:searchFilter,searchField:"allfieldsearch",searchOper:"cn"});
			}
			t.trigger("reloadGrid",[{page:1,current:true}]);
		})
	})
})