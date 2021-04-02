<!DOCTYPE html>
<html>
<head>
    <title></title>
</head>
<body>
<span>入参字段</span>
<#list fieldList as fieldObj>
          <span>${fieldObj.fieldName};</span>
</#list>

<form action="${requestUrl}" method="${method}">
<table>
        <#list fieldList as fieldObj>
            <tr>
                <td>${fieldObj.fieldName}</td>
                <td><input type="text" value="${fieldObj.defaultValue}" name="${fieldObj.fieldName}" width="300px"/></td>
                <td>${fieldObj.fieldContent}</td>
                <td>是否必填写：${fieldObj.isRequired}</td>
            </tr>
        </#list>
    <tr>
        <td colspan="3"> <input type="submit" text="提交"/></td>
    </tr>
</table>
</form>
</body>
</html>