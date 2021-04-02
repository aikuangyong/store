App.MainControl.${name} = {
	save:function(scope,callback,param,domEl){
		$.ajax({
			url : onDomain + "/m/${name}/save",
			async : false,
			data : param,
			type : "post",
			dataType : "json",
			success : function(response) {
				callback.call(scope, response, domEl);
			},
			error : function(e) {

			}
		});
	},
	getDataById:function(scope,callback,param,domEl){
		$.ajax({
			url : onDomain + "/m/${name}/getDataById",
			async : false,
			data : param,
			type : "get",
			dataType : "json",
			success : function(response) {
				callback.call(scope, response, domEl);
			},
			error : function(e) {
			}
		});
	},
	deleteById:function(scope,callback,param,domEl){
		$.ajax({
			url : onDomain + "/m/${name}/deleteById",
			async : false,
			data : param,
			type : "post",
			dataType : "json",
			success : function(response) {
				callback.call(scope, response, domEl);
			},
			error : function(e) {

			}
		});
	},
	batchDelete:function(scope,callback,param,domEl){
		$.ajax({
			url : onDomain + "/m/${name}/batchDelete",
			async : false,
			data : param,
			type : "post",
			dataType : "json",
			success : function(response) {
				callback.call(scope, response, domEl);
			},
			error : function(e) {

			}
		});
	},
	update:function(scope,callback,param,domEl){
		$.ajax({
			url : onDomain + "/m/${name}/update",
			async : false,
			data : param,
			type : "post",
			dataType : "json",
			success : function(response) {
				callback.call(scope, response, domEl);
			},
			error : function(e) {

			}
		});
	},
	getData : function(scope, callback, param, domEl) {
		$.ajax({
			url : onDomain + "/m/${name}/getData",
			async : false,
			data : param,
			type : "post",
			dataType : "json",
			success : function(response) {
				callback.call(scope, response, domEl);
			},
			error : function(e) {

			}
		});
	},
	exportData : function(scope, callback, param) {
		$.ajax({
			url : onDomain + "/m/${name}/getData",
			async : false,
			data : param,
			type : "post",
			dataType : "json",
			success : function(response) {
				callback.call(scope, response, domEl);
			},
			error : function(e) {

			}
		});
	}
};