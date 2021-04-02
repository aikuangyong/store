var _REQ_URL= {
    login:"/auth_user"
}
App.IndexControl = {
    login:function(scope,callback,param){
        $.ajax({
           url:_REQ_URL.login,
           async:false,
           data:param,
           type:"post",
           dataType:"json",
           success:function(rep){
               callback.call(scope,rep);
           },
           error:function(e){

           }
        });
    }
}