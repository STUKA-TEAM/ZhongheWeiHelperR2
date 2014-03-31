<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <%@ include file="../CommonViews/head.jsp"%>
    <!-- Bootstrap core CSS -->
    <link href="./css/store/bootstrap.min.css" rel="stylesheet">
    <link href="./css/store/zhonghe-wechat.css" rel="stylesheet">
    <link href="./css/store/zhonghe-manager.css" rel="stylesheet">
    <link rel="shortcut icon" href="./img/favicon.png">
  </head>
  <body>
    <%@ include file="../CommonViews/navBar.jsp"%>
    <div class="row">
      <%@ include file="../CommonViews/leftSide.jsp"%>    
      <div class="col-md-10 manager-content">
        <ol class="breadcrumb">
          <li><a href="store/vote/list">投票管理</a></li>
          <li class="active">编辑投票</li>
        </ol>
        <div class="row website-tab">
          <div class="panel panel-info col-md-10 col-md-offset-1">
            <div class="panel-heading">
              <h4>编辑投票</h4>
            </div>
            <div class="panel-body">
              <div  class="form-horizontal">
               
		     <div class="form-group">
		       <label for="voteName" class="col-md-3 control-label">投票名称</label>
		       <div class="col-md-7">
		         <input type="text" class="form-control" id="voteName" placeholder="" value="${vote.voteName}">
		       </div>
		     </div>
		     <form  class="form-group" role="form" enctype="multipart/form-data" id="upload1single">
		       <label for="coverPic" class="col-md-3 control-label">投票封面</label>
		       <div class="col-md-7">
		         <input type="file" name="file" class="image-file hidden" onchange="ye.value=value" accept="image/*">
		         <input type="text" name=ye class="form-control file-path-elove">
		         <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
		         <input type="button" value="上传" class="image-multi btn btn-sm btn-info">
		       </div>
		     </form>
		   <div class="form-group">
		       <div class="col-md-7 col-md-offset-3">
		         <div class="row" id="upload1single-images">
		         <c:if test="${vote.coverPic!=null}">
		         <div id="${vote.coverPic}" class="col-md-6 pic-preview-div"><img src="${vote.coverPic}_original.jpg" class="pic-preview img-thumbnail img-responsive"/>
		           <span class="glyphicon glyphicon-trash" onclick="deleteThisImage('${vote.coverPic}')"> </span>
		         </div>
		         </c:if>
		         </div>
		         <div id="upload1single-links">
		         <c:if test="${vote.coverPic!=null}">
		         <input id="${vote.coverPic}-input" type="hidden" value="${vote.coverPic}"/>
		         </c:if>
		         </div>
		       </div>
		   </div>  
		     <div class="form-group">
		       <label for="voteDesc" class="col-md-3 control-label">投票介绍</label>
		       <div class="col-md-7">
		         <textarea id="voteDesc" class="form-control" rows="3" cols="20">${vote.voteDesc}</textarea>
		       </div>
		     </div>
		     <div class="form-group">
		       <label for=maxSelected class="col-md-3 control-label">选项可选数量</label>
		       <div class="col-md-7">
		         <input type="text" class="form-control" id="maxSelected" placeholder="" value="${vote.maxSelected}">
		       </div>
		     </div>
            <div class="form-group">
              <div class="col-md-8 col-md-offset-2">
                <table class="table table-striped table-bordered">
                  <thead>
                    <tr>
                      <th class="col-md-4">图片</th>
                      <th class="col-md-5">描述</th>
                      <th class="col-md-3">操作</th>
                    </tr>
                  </thead>
                  <tbody id="items">
                    <c:forEach items="${vote.itemList}" var="item">
                    <tr id="${item.itemid}">
                      <c:if test="${item.itemPic != null && item.itemPic != ''}">
                      <td class="${item.itemPic}"><img src="${item.itemPic}_original.jpg" class="pic-preview img-thumbnail img-responsive"/></td>
                      </c:if> 
                      <c:if test="${item.itemPic == null || item.itemPic == ''}">
                      <td class="null"></td>
                      </c:if> 
                      <td>${item.itemName}</td>
                      <td>
                      <a class="btn btn-sm btn-info" onclick="editItemWindow(this)">编辑</a>
                      </td>
                    </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>
            </div>              
               <input type="hidden" id="editvoteid" value="${vote.voteid}"/>
              </div>
              <div class="row">
                <a class="btn btn-info col-md-2 col-md-offset-3 text-center" onclick="submitVote('edit')">更新投票</a>
                <a class="btn btn-info col-md-1 col-md-offset-1 text-center" href="store/vote/list">取消</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    
     <div class="modal fade" id="itemEdit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">编辑投票选项</h4>
          </div>
          <div class="modal-body">
            <div  class="form-horizontal">
		     <form  class="form-group" role="form" enctype="multipart/form-data" id="upload3single">
		       <label for="coverPic" class="col-md-3 control-label">投票选项图片</label>
		       <div class="col-md-7">
		         <input type="file" name="file" class="image-file hidden" onchange="ye.value=value" accept="image/*">
		         <input id="upload2single-text" type="text" name=ye class="form-control file-path-elove">
		         <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
		         <input type="button" value="上传" class="image-multi btn btn-sm btn-info">
		       </div>
		     </form>
			   <div class="form-group">
			       <div class="col-md-7 col-md-offset-3">
			         <div class="row" id="upload3single-images">
	
			         </div>
			         <div id="upload3single-links">
	
			         </div>
			       </div>
			   </div>  
 
              <div class="form-group">
                <label class="col-md-3 control-label">选项描述</label>
                <div class="col-md-7">
                  <textarea id="descTextEdit" class="form-control" rows="3"></textarea>
                </div>
              </div>
              
              <input id="uuidhidden" type="hidden" value=""/> 
              
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-info" onclick="editItem()">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <!-- 确认删除弹框 -->
    <div class="modal fade" id="confirmModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 id="confirmModalTitle" class="modal-title text-danger"></h4>
          </div>
          <div class="modal-body">
            <h4 id="confirmModalMes" class="modal-title"></h4>
            <input id="idhidden" type="hidden" value=""/>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="confirmDeleteVoteItem()">确认删除</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->  

    <%@ include file="../CommonViews/footer.jsp"%>
    
    <!-- 通用提示框 -->
    <%@ include file="../CommonViews/commonDialog.jsp"%>

    <!-- include jQuery -->
    <%@ include file="../CommonViews/commonJSList.jsp"%>
    <script type="text/javascript" src="js/store/vote.js"></script>
    <script type="text/javascript" src="js/store/uuid.js"></script>
    <script type="text/javascript" src="js/store/upload.js"></script>
  </body>
</html>