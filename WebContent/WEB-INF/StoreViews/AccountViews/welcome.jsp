<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 id="welcomeMessageTitle" class="modal-title">设置粉丝关注后欢迎语 
             </h4>
          </div>
          <div class="modal-body">
          <input id="appidSign" type="hidden"/>
			<ul class="nav nav-tabs">
			  <li id="textType" class="active"><a href="#tab_a" data-toggle="tab">纯文字类型</a></li>
			  <li id="listType"><a href="#tab_b" data-toggle="tab">图文信息类型</a></li>
			</ul>
			<div class="tab-content">
		        <div class="tab-pane active" id="tab_a">
		           <textarea id="welcomeText" class="form-control" rows=4 placeholder="粉丝关注后系统将自动返回您设置的这段话"><c:if test="${welcome.type=='text'}">${welcome.contents[0].content}</c:if></textarea>
		        </div>
		        <div class="tab-pane" id="tab_b">
		        <div class="form-horizontal">
		        <button type="button" class="btn btn-primary" onclick="addMessageItem()">添加消息</button> 
		        <div id="messageItems">
		        <c:forEach items="${welcome.contents}" var="item">         
			    <div class="thumbnail form-group messageItem">
                    <input type="text" class="form-control" placeholder="消息标题" value="${item.content}"/>
                    <input type="text" class="form-control" placeholder="消息链接" value="${item.link}"/>
                    <button type="button" class="btn btn-danger" onclick="deleteItem(this)">删除</button>
			    </div>	
			    </c:forEach>
			    </div>				    					  					    
              <form  class="form-group" role="form" enctype="multipart/form-data" id="upload2">
                <label class="col-sm-3 control-label">为上面消息配图</label>
                <div class="col-sm-9">
                  <input type="file" name="file" class="image-file hidden" onchange="ye.value=value" accept="image/*">
                  <input type="text" name=ye class="form-control file-path">
                  <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
                  <input type="button" value="上传" class="image-multi btn btn-sm btn-info">
                </div>
              </form>  
              <div class="form-group">
                  <div class="col-md-7 col-md-offset-3">
                    <div class="row" id="upload2-images">
                    <c:if test="${welcome.type=='list'}">
                    <c:forEach items="${welcome.contents}" var="item">
                    <div id="${item.coverPic}" class="col-md-6 pic-preview-div"><img src="${item.coverPic}_original.jpg" class="pic-preview img-thumbnail img-responsive"/>
                    <span class="glyphicon glyphicon-trash" onclick="deleteThisImage('${item.coverPic}')"> </span>
                    </div>
                    </c:forEach>
                    </c:if>
                    </div>
                    <div id="upload2-links">
                    <c:if test="${welcome.type=='list'}">
                    <c:forEach items="${welcome.contents}" var="item">
                    <input id="${item.coverPic}-input" type="hidden" value="${item.coverPic}"/>
                    </c:forEach>
                    </c:if>
                    </div>
                  </div>
              </div> 				  
				  </div>
		        </div>
			</div><!-- tab content -->
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" onclick="submitWelcomeMessage()">确定</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->