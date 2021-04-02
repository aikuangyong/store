<!DOCTYPE html>
<html>
<head>
    <title></title>
</head>
<body>
<table>
        <#list mainList as pageObj>
            <tr>
                <td><a href="${pageObj.fileName}" target="mainFrame">${pageObj.fileShowName}</a></td>
            </tr>
        </#list>
</table>
</body>
</html>