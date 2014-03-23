<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">新建相册集</h4>
          </div>
          <div class="modal-body">
            <div  class="form-horizontal">
              <div class="form-group">
                <label class="col-md-3 control-label">相册集名称</label>
                <div class="col-md-7">
                  <input type="text" class="form-control" placeholder="">
                </div>
              </div>
              <div class="form-group">
                <label class="col-md-3 control-label">添加相册</label>
                <div class="col-md-7">
                  <div class="checkbox">
                    <label>
                      <input type="checkbox" value=""> 相册1
                    </label>
                  </div>
                  <div class="checkbox">
                    <label>
                      <input type="checkbox" value=""> 相册2
                    </label>
                  </div>
                  <div class="checkbox">
                    <label>
                      <input type="checkbox" value=""> 相册3
                    </label>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-info" data-dismiss="modal">确定</button>
          </div>
        </div><!-- /.modal-content -->