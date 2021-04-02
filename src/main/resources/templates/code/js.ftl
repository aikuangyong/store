var mainControl = null;
App.AdminModule.${name} = {
	primary:module,
	init:function(){
		var scope = this;
		var id = "dept";
		var domObj = {
			gridId:id+"_datagrid",
			toolBarId:id+"_toolbar"
		}
		App.MainDom.append(scope.createView(domObj));
		mainControl = App.MainControl[module];
		scope.initToolBar(domObj);
		scope.initSearchPanel(domObj);
		scope.initDataGrid(domObj);
	},
	initSearchPanel:function(domObj){
		var scope = this;
		var searchDom = $("#searchPanel");
<#list modelList as modelObj>
	<#if modelObj.isKey != '' >
	<#elseif modelObj.hidden == "0">
	<#elseif modelObj.htmlType = 'select' >
		var ${modelObj.property}Dom = searchDom.find(".${modelObj.property}");
		${modelObj.property}Dom.combobox({
			data:[],
			editable:false,
			width:"120px",
			panelWidth:"120px",
			panelHeight:"140"
		});
	<#else>
		var ${modelObj.property}Dom = searchDom.find(".${modelObj.property}");
		${modelObj.property}Dom.textbox({
			width:"120px"
		});
	</#if>
</#list>

		var searchDom = $("#search");
		$("#search").linkbutton({
			text:"查询",
			width:"75px",
			onClick:function(){
				var gridDom = $("#"+domObj.gridId);
				var param = scope.getParam();
				gridDom.datagrid('load',param);
			}
		});
	},
	initToolBar:function(domObj){
		var scope = this;
		var buttonDom = $("#"+domObj.toolBarId+" a");
		buttonDom.linkbutton({
			onClick:function(){
				var dataBtn = $(this).attr("data-btn");
				switch(dataBtn){
					case "add":
						scope.openAddPanel(domObj);
					break;
					case "export-data":
						scope.exportData(domObj);
					break;
					case "batch-delete":
						scope.batchDelete(domObj);
					break;
				}
			}
		});
	},
	batchDelete:function(domObj){
		var scope = this , gridId = domObj.gridId,gridDom = $("#"+gridId);
		var checkData = gridDom.datagrid("getChecked");
		var idList = [];
		for(var i = 0 ; i < checkData.length ; i++){
			var rowData = checkData[i];
			idList.push(rowData.${primaryKey});
		}
		if(idList.legth == 0){
			$.messager.alert("提示","请选择一条记录");
			return ;
		}
		var batchDeleteParam = {
			ids:idList.join(",")
		} 
		mainControl.batchDelete(scope,function(rep,gridDom){
			if(rep.state == "SUCCESS"){
				$.messager.alert("提示","操作成功");
				gridDom.datagrid("reload");
			}else{
				$.messager.alert("提示","操作失败");
			}
		},batchDeleteParam,gridDom)
	},
	exportData:function(domObj){
		var scope = this;
		var param = scope.getParam();
		window.open(onDomain+"/m/"+scope.primary+"/export");
	},
	openAddPanel:function(domObj){
		var scope = this;
		var winObjId = domObj.gridId+"_win";
		var winObj = {
			id:winObjId,
			gridId:domObj.gridId,
			model:"widget",
			onOpen:function(){
			},
			option:{
				height:"450",
				width:"500",
				title:"编辑数据",
				onOk:function(){
					scope.saveAdd(winObj);
				},
				onCancel:function(){
				}
			},
			widget:[
				<#list modelList as modelObj>
					<#if modelObj.isKey != ''>
					<#elseif modelObj.hidden == "0">
					<#elseif modelObj_index = modelList?size-1 >
						{label:"${modelObj.comment}",type:"${modelObj.htmlType}",cls:"${modelObj.property}-add"}
					<#else>
						{label:"${modelObj.comment}",type:"${modelObj.htmlType}",cls:"${modelObj.property}-add"},
					</#if>
				</#list>
			]
		}
		App.CommanView.createDialog(winObj);
		scope.createAddPanel(winObj);
	},
	createAddPanel:function(winObj){
		var scope = this ;
		<#list modelList as modelObj>
				<#if modelObj.isKey != ''>
			    <#elseif modelObj.hidden == "0">
				<#elseif modelObj.htmlType = 'select' >
					$(".${modelObj.property}-add").combobox({
						data:[],
						editable:false,
						width:"280px",
						textField:"text",
						valueField:"value",
						prompt:"请选择${modelObj.comment}",
						panelHeight:"140px",
						required:true
					});
				<#else>
					$(".${modelObj.property}-add").textbox({
						width:"280px",
						prompt:"请输入${modelObj.comment}",
						required:true
					});
				</#if>
		</#list>
	},
	saveAdd:function(winObj){

		var scope = this,gridDom = $("#"+winObj.gridId);
		var winObjId = winObj.id,winDom = $("#"+winObjId);

		<#list modelList as modelObj>
			<#if modelObj.isKey != ''>
			<#elseif modelObj.hidden == "0">
			<#elseif modelObj.htmlType = 'select' >
		var ${modelObj.property}Dom = winDom.find(".${modelObj.property}-add");
		var ${modelObj.property}Val = ${modelObj.property}Dom.combobox("getValue");
			<#else>
		var ${modelObj.property}Dom = winDom.find(".${modelObj.property}-add");
		var ${modelObj.property}Val = ${modelObj.property}Dom.textbox("getValue");
			</#if>
		</#list>

		var param = {
			<#list modelList as modelObj>
				<#if modelObj.isKey != ''>
				<#elseif modelObj.hidden == "0">
				<#else>
					${modelObj.property}:${modelObj.property}Val,
				</#if>
			</#list>
			1:1
		}
		if(!scope.saveOrUpdateValidate(param)){
			$.messager.alert("提示","必填字段不能为空");
			return;
		}
		mainControl.save(param,function(data,gridDom){
			if(data.state == "SUCCESS"){
				$.messager.alert("提示","添加成功");
				gridDom.datagrid("reload");
				winDom.window("destroy");
			}else{
				$.messager.alert("提示",data.msg);
			}
		},param,gridDom);
	},
	saveOrUpdateValidate:function(param){
		<#list modelList as modelObj>
			<#if modelObj.isRequired = "0" >
				if(param.${modelObj.property} == ""){
					return false;
				}
			</#if>
		</#list>
		return true;		
	},
	initDataGrid:function(domObj){
		var scope = this;
		var gridDom = $("#"+domObj.gridId);
		var gridHeight = App.CommanView.getPanelHeight();
		gridDom.datagrid({
			data:[],
			checkOnSelect:true,
			selectOnCheck:true,
			singleSelect:false,
			striped:true,
			pagination:true,
			rownumbers:true,
			pagePosition:"bottom",
			height:gridHeight,
			bodyCls:"data-body-panel",
			pageSize:15,
			pageList: [15,30,60],
		    columns:[[
		    	{field:'checkbox',checkbox:true,title:'',width:'120px'},
				<#list modelList as modelObj>
					<#if modelObj.hidden == "1" >
				{field:'${modelObj.property}',title:'${modelObj.comment}',hidden:false,width:'10%'},
					<#else>
				{field:'${modelObj.property}',title:'${modelObj.comment}',hidden:true,width:'10%'},
					</#if>
				</#list>
		        {field:'${primaryKey}-oper',title:'操作',width:'18%',formatter:function(value,row,index){
		        	var group = [];
		        	group.push("<a href=\"javascript:void(0);\" target=\"_blank\" onclick=\"App.AdminModule.${name}.edit${name?cap_first}(this);\">编辑</a>");
		        	group.push("<a href=\"javascript:void(0);\" target=\"_blank\" onclick=\"App.AdminModule.${name}.delete${name?cap_first}(this);\">删除</a>");
		        	group.push("<a href=\"javascript:void(0);\" target=\"_blank\" onclick=\"App.AdminModule.${name}.detail${name?cap_first}(this);\">查看</a>");
		        	return group.join("");
		        }}
		    ]],
		    loader:function(param,successFn,error){
		    		mainControl.getData(scope,function(rep){
		    			if(rep.state == "SUCCESS"){
		    				successFn(rep,scope);
		    			}else{
		    				error();
		    			}
		    		},param)
		    }
		});
	},
	delete${name?cap_first}:function(domEl){
		var scope = this;
		var param = App.CommanView.getDataGridParam(domEl);
		var gridDom = $("#"+param.gridId);
		var deleteParam = {
			${primaryKey}:param.rowData.${primaryKey}
		};
		mainControl.deleteById(scope,function(rep,gridDom){
			gridDom.datagrid("reload");
		},deleteParam,gridDom);
	},
	edit${name?cap_first}:function(domEl){
		var scope = this;
		var param = App.CommanView.getDataGridParam(domEl);
		var rowData = param.rowData,primaryKey = rowData.${primaryKey};
		var winObjId = param.gridId+"_edit_win";
		var cls = winObjId+"-div-dialog-panel";
		var detailData = null;
		var dataJson = {
			${primaryKey}:rowData.${primaryKey}
		};
				
		var winObj = {
			id:winObjId,
			gridId:param.gridId,
			model:"widget",
			cls:cls,
			onOpen:function(){
			},
			option:{
				height:"600",
				width:"800",
				title:"编辑信息",
				onOk:function(){
					scope.saveEdit(winObj,detailData);
				},
				onCancel:function(){
				}
			},
			widget:[
<#list modelList as modelObj>
	<#if modelObj.isKey != ''>
	<#elseif modelObj.hidden == "0">
	<#elseif modelObj_index = modelList?size-1 >
			{label:"${modelObj.comment}",type:"${modelObj.htmlType}",cls:"${modelObj.property}-edit"}
	<#else>
			{label:"${modelObj.comment}",type:"${modelObj.htmlType}",cls:"${modelObj.property}-edit"},
	</#if>
</#list>
			]
		};
		mainControl.getDataById(scope,function(rep,winDom){
			detailData = rep.dataObj;
		},dataJson,$("#"+winObjId));
		App.CommanView.createDialog(winObj);
		scope.createEditPanel(winObj,detailData,param);
	},
	saveEdit:function(winObj,detailData){
		var scope = this;
		var winDom = $("#"+winObj.id),gridDom = $("#"+winObj.gridId);
<#list modelList as modelObj>
	<#if modelObj.isKey != ''>
	<#elseif modelObj.hidden == "0">
	<#elseif modelObj.htmlType = 'select' >
		var ${modelObj.property}Dom = winDom.find(".${modelObj.property}-edit");
		var ${modelObj.property}Val = ${modelObj.property}Dom.combobox("getValue");
	<#else>
		var ${modelObj.property}Dom = winDom.find(".${modelObj.property}-edit");
		var ${modelObj.property}Val = ${modelObj.property}Dom.textbox("getValue");
	</#if>
</#list>

		var editParam = {
			<#list modelList as modelObj>
				<#if modelObj.isKey != "" >
				<#elseif modelObj.hidden == "0">
				<#elseif modelObj.htmlType == "select">
					${modelObj.property}:${modelObj.property}Dom.combobox("getValue"),
				<#else>
					${modelObj.property}:${modelObj.property}Dom.textbox("getValue"),
				</#if>
			</#list>
			${primaryKey}:detailData.${primaryKey}
		}
		mainControl.update(scope,function(rep,gridDom){
			if(rep.state == "SUCCESS"){
				$.messager.alert("提示","修改成功");
				gridDom.datagrid("reload");
				winDom.window("destroy");
			}else{
				$.messager.alert("提示",data.msg);
			}
		},editParam,gridDom);
	},
	createEditPanel:function(winObj,detailData,param){
		var scope = this;
		var winDom = $("#dialogWidgetPanel_"+winObj.id),rowData = param.rowData;

		<#list modelList as modelObj>
			<#if modelObj.isKey != "" >
			<#elseif modelObj.hidden == "0">
			<#elseif modelObj.htmlType == "select">
		var ${modelObj.property}Dom = winDom.find(".${modelObj.property}-edit");
		${modelObj.property}Dom.combobox({
			data:[],
			editable:false,
			value:detailData.${modelObj.property},
			width:"280px"
		});
			<#else>
		var ${modelObj.property}Dom = winDom.find(".${modelObj.property}-edit");
		${modelObj.property}Dom.textbox({
			value:detailData.${modelObj.property},
			width:"280px"
		});
			</#if>
		</#list>
	},
	getParam:function(){
		var searchPanel = $("#searchPanel");
		<#list modelList as modelObj>
			<#if modelObj.isKey == "PRI" >
			<#elseif modelObj.hidden == "0">
			<#elseif modelObj.htmlType == "select">
		var ${modelObj.property}Val = searchPanel.find(".${modelObj.property}").combobox("getValue");
			<#else>
		var ${modelObj.property}Val = searchPanel.find(".${modelObj.property}").textbox("getValue");
			</#if>
		</#list>
		return {
			<#list modelList as modelObj>
				<#if modelObj.isKey == "PRI" >
				<#elseif modelObj.hidden == "0">
				<#else>
					${modelObj.property}:${modelObj.property}Val,
				</#if>
			</#list>
			1:1
		};
	},
	createView:function(domObj){
		var scope = this,group = [];
		group.push(scope.createToolbarView(domObj));
		group.push("<div class=\"hr-line\" ></div>");
		group.push("<table id=\""+domObj.gridId+"\" class=\"data-table\" ></table>");
		return group.join("");
	},
	createToolbarView:function(domObj){
		var group = [];
		group.push("<div id=\""+domObj.toolBarId+"\" class=\"handle-box grid_tool_bar\">");
		group.push("<ul>");
		group.push("<li class=\"handle-item layui-clear\">");
		group.push("<span class=\"fr\" id=\"searchPanel\" style=\"float: left;\">");
		<#list modelList as modelObj>
			<#if modelObj.isKey == "PRI" >
			<#elseif modelObj.hidden == "0">
			<#elseif modelObj.htmlType == "select">
		group.push("<span class=\"condition-filter-panel\"><label >${modelObj.comment}:</label><select type=\"text\" class=\"${modelObj.property}\" ></select></span>");
			<#else>
		group.push("<span class=\"condition-filter-panel\"><label >${modelObj.comment}:</label><input type=\"text\" class=\"${modelObj.property}\" ></input></span>");
			</#if>
		</#list>
		group.push("<span class=\"condition-filter-panel\"><a id=\"search\"></a></span>");
		group.push("</span>");
		group.push("<span class=\"fr button-bar\" style=\"float: right;\">");
		group.push("<a data-btn=\"add\" >新增</a>");
		group.push("<a data-btn=\"export-data\">导出</a>");
		group.push("<a data-btn=\"batch-delete\">批量删除</a>");
		group.push("</span>");
		group.push("</li>");
		group.push("</ul>");
		group.push("</div>");
		return group.join("");
	}
};