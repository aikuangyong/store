$(function () {
    App.loginModel.login();
});

App.loginModel = {
    login:function(){
        var scope = this;
        var loginDom = $("#login");
        loginDom.click(function(){
           var param = {
               userName:"11",
               passWord:"22",
               verifyCode:$("#verifyCode").val()
           }
           App.IndexControl.login(scope,function(rep){
               var data = rep || {};
               if(data.state == "SUCCESS"){
                   location.href = "/index";
               }else{
                   layer.open({
                       title: '提示',
                       content: data.message || '信息填写错误'
                   });
               }
           },param)
        });
    }
}