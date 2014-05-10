<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="modal-content">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
    <h4 class="modal-title" id="myModalLabel">编辑分点区域</h4>
  </div>
  <div class="modal-body">
    <div  class="form-horizontal">
      <div class="form-group">
        <label for="story_groom" class="col-md-3 control-label">区域名称</label>
        <div class="col-md-7">
          <input type="text" class="form-control" id="className" placeholder="" value="${branchclass.className}">
        </div>
      </div>
      <div class="form-group">
        <label for="story_groom" class="col-md-3 control-label">添加分店</label>
        <div class="col-md-7">
          <div class="panel panel-default">
            <div class="panel-body">
              <ul class="list-unstyled">
              <c:forEach items="${branchList}" var="item">
                <li>
                  <div class="checkbox">
                    <label>
                      <input type="checkbox" name="options" value="${item.branchSid}" <c:if test="${item.selected}">checked</c:if> >${item.storeName}
                    </label>
                  </div>
                </li>
              </c:forEach>
              </ul>
              <input id="editclassid" type="hidden" value="${branchclass.classid}">
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-info" onclick="submitClass('update')" >确定</button>
  </div>
</div><!-- /.modal-content -->