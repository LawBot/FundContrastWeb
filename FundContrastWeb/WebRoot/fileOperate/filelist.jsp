<%--
  Created by IntelliJ IDEA.
  User: Jinhu_Lu
  Date: 2017/9/27
  Time: 17:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html>
<head>
    <base href="<%=basePath%>">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
    <title>文件列表</title>
    <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.0/css/bootstrap.min.css">
    <script src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://apps.bdimg.com/libs/bootstrap/3.3.0/js/bootstrap.min.js"></script>
</head>

<body>
<div>

    <div style="display:block;width:50%;position:absolute;left:5%;top:50px">
        <div class="table-responsive" style="width:90%;margin-left:0px;overflow:hidden">
            <form id="fileDataForm" action="<%=basePath %>upload.do" enctype="multipart/form-data" method="post">
                <table class="table table-striped">
                    <thead>
                    <%--<tr>--%>
                    <%--<td style="width:80px">....</td>--%>
                    <%--<td>文件列表</td>--%>
                    <%--</tr>--%>
                    <%--</thead>--%>
                    <tbody>
                    <tr>
                        <%--<td>....</td>--%>
                        <td>
                            <div>
                                <input type="file" name="targetFile1" id="targetFile1">
                                <%--<input type="file" name="targetFile2" id="targetFile2">--%>
                                <%--<input type="file" name="targetFile3" id="targetFile3">--%>
                                <%--<input type="file" name="targetFile4" id="targetFile4">--%>
                            </div>
                        </td>
                        <td><button type="button" class="btn btn-primary"  id="uploadButton">上传文件</button></td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
    </div>

    <div style="border:1px solid #BFBFBF;margin:120px 5%"></div>

    <div style="display:block;width:80%;position:absolute;left:-7%;top:98px">
        <div class="table-responsive" style="width:80%;margin-top:50px;margin-left:15%">
            <table id="dataTable" class="table table-bordered">
            </table>
        </div>
    </div>
</div>
</body>
<script>
    var option;
    $(function(){
        initForm("dataTable");

        $("#uploadButton").on("click",function(){
            var fileForm = $("#fileDataForm");
            $(fileForm).submit();
            $("#uploadModel").modal("hide");
        });
    });

    function initForm(table){
        var html="<thead>"+
            "<tr>"+
            "<td>#编号</td>"+
            "<td>文件名</td>"+
            "<td>文件类型</td>"+
            "<td colspan=2>文件大小</td>"+
            "</tr>"+
            "</thead>"+
            "<tbody>";
        $.ajax({
            type:"POST",
            url:"<%=basePath%>admin/file/dataList",
            success:function(data){
                $(data).each(function(index,value){
                    html+="<tr>"+
                        "<input type='hidden' name='fileName' value='"+value.fileName+"'/>"+
                        "<td>"+(index+1)+".</td>"+
                        "<td><a href='<%=basePath%>admin/file/download?fileName="+value.fileName+"'>"+value.fileName+"</a></td>"+
                        "<td>"+value.fileType+"</td>"+
                        "<td>"+value.size+"B</td>"+
                        "<td><a href='javascript:void(0);' class='btn btn-primary btn-lg active' role='button' onclick='removeFile(this)'>删除</a></td>"+
                        "</tr>";
                });
                html+="</tbody>";
                $("#"+table).html(html);
            }
        });
    }

    function removeFile(obj){
        var flag = confirm("确定删除当前文件吗");
        var fileName=$(obj).parents("tr").children().eq(0).val();
        if(flag){
            $.ajax({
                type:"POST",
                url:"<%=basePath%>admin/file/remove",
                dataType:"json",
                data:{fileName:fileName},
                success:function(data){
                    console.log(data.result);
                    if(data.result=="success"){
                        $(obj).parents("tr").remove();
                    }
                }
            });
        }
    }
</script>
</html>

