<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="modal-content">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <h4 class="modal-title" id="myModalLabel">编辑类别</h4>
  </div>
  <div class="modal-body">
    <div  class="form-horizontal">
      <div class="form-group">
        <label for="story_groom" class="col-md-3 control-label">类别名称</label>
        <div class="col-md-7">
          <input type="text" class="form-control" id="className" placeholder="" value="${articleclass.className}">
        </div>
      </div>
      <div class="form-group">
        <label for="story_groom" class="col-md-3 control-label">添加文章</label>
        <div class="col-md-7">
          <div class="panel panel-default">
            <div class="panel-body">
              <ul class="list-unstyled">
              <c:forEach items="${articleList}" var="item">
                <li>
                  <div class="checkbox">
                    <label>
                      <input type="checkbox" name="options" value="${item.articleid}" <c:if test="${item.selected}">checked</c:if> >${item.title}
                    </label>
                  </div>
                </li>
              </c:forEach>
              </ul>
              <input id="editclassid" type="hidden" value="${articleclass.classid}">
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-info" onclick="submitArticleclass('update')" >确定</button>
  </div>
</div><!-- /.modal-content -->