<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<ol class="breadcrumb">
  <li><a href="store/menu/wizard/initial">自定义菜单配置</a></li>
  <li class="active">编辑自定义菜单</li>
</ol>
<div class="row">
  <div class="panel panel-info col-md-10 col-md-offset-1">
    <div class="panel-heading">
      <h4>编辑自定义菜单（第二步，共两步）</h4>
    </div>
    <div class="panel-body row">
      <div class="form-horizontal">
        <div class="form-group">
          <div class="col-md-2">
          </div>可创建最多 3 个一级菜单，每个一级菜单下可创建最多 5 个二级菜单。
        </div>
        <div class="form-group col-md-2"></div>
        <div class="form-group col-md-10">
        <div class="col-md-6">
		<div class="panel panel-default">
		  <div class="panel-heading">菜单管理 
		  <button type="button" class="btn btn-sm btn-primary col-md-offset-2" onclick="addFirstMenuWindow()">添加</button></div>
		  <div class="panel-body">
		  <ul id="menuButtons" class="nav nav-pills nav-stacked">
          <li id="1" class="1st">
          <a data-toggle="collapse" href="#1_sub"><span id="1_name" class="col-md-6">账号管理</span>
				<button type="button" class="btn btn-default btn-xs col-md-offset-2" onclick="addSecondMenuWindow(this)">
				  <span class="glyphicon glyphicon-plus"></span>
				</button>  
				<button type="button" class="btn btn-default btn-xs" onclick="editButtonWindow(this)">
				  <span class="glyphicon glyphicon-pencil"></span>
				</button>
				<button type="button" class="btn btn-default btn-xs" onclick="deleteButtonWindow(this)">
				  <span class="glyphicon glyphicon-trash"></span>
				</button>                  
          </a>
          <div id="1_sub" class="panel-collapse collapse in">
            <ul id="1_ul" class="nav submenu">
            </ul>
          </div>
          </li>
          <li id="2" class="1st">
          <a data-toggle="collapse" href="#2_sub"><span id="2_name"  class="col-md-6">微官网管理</span>
				<button type="button" class="btn btn-default btn-xs col-md-offset-2" onclick="addSecondMenuWindow(this)">
				  <span class="glyphicon glyphicon-plus"></span>
				</button>  
				<button type="button" class="btn btn-default btn-xs" onclick="editButtonWindow(this)">
				  <span class="glyphicon glyphicon-pencil"></span>
				</button>
				<button type="button" class="btn btn-default btn-xs" onclick="deleteButtonWindow(this)">
				  <span class="glyphicon glyphicon-trash"></span>
				</button>          
		  </a>
          <div id="2_sub" class="panel-collapse collapse in">
            <ul id="2_ul" class="nav submenu">
              <li id="10" class="2_2nd">
              <a href="javascript:void(0)"><span id="10_name"  class="col-md-6">文章管理</span>				
                <button type="button" class="btn btn-default btn-xs col-md-offset-2" onclick="editButtonWindow(this)">
				  <span class="glyphicon glyphicon-pencil"></span>
				</button>
				<button type="button" class="btn btn-default btn-xs" onclick="deleteButtonWindow(this)">
				  <span class="glyphicon glyphicon-trash"></span>
				</button>
				</a>
			  </li>
            </ul>
          </div>
          </li>

        </ul>
		  </div>
		</div>
		</div>

		</div>

        <div class="form-group form-btn">
      <button type="button" class="btn btn-lg btn-info col-md-3 col-md-offset-2" onclick="cancel()">取消</button>
      <button type="button" class="btn btn-lg btn-info col-md-3 col-md-offset-1" onclick="nextStep('finish')">完成</button>
        </div>
      </div>
    </div>
  </div>
</div>

    <!-- 添加一级菜单按钮  -->
    <div class="modal fade" id="addFirstButton" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">添加一级菜单按钮</h4>
          </div>
          <div class="modal-body">
            <div class="form-horizontal">
              <div class="form-group">
                <label for="firstButtonName" class="col-sm-3 control-label">按钮名称</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="firstButtonName" placeholder="" value="">
                </div>
              </div>
              <div class="form-group">
                <label for="firstButtonLink" class="col-sm-3 control-label">关联链接</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="firstButtonLink" placeholder="" value="">
                </div>
              </div>
            </div>
 
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" onclick="addFirstMenu()">确定</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

   <!-- 添加二级菜单按钮  -->
    <div class="modal fade" id="addSecondButton" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">添加二级菜单按钮</h4>
            <input id="currentFirstMenu" type="hidden" />
          </div>
          <div class="modal-body">
            <div class="form-horizontal">
              <div class="form-group">
                <label for="secondButtonName" class="col-sm-3 control-label">按钮名称</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="secondButtonName" placeholder="" value="">
                </div>
              </div>
              <div class="form-group">
                <label for="secondButtonLink" class="col-sm-3 control-label">关联链接</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="secondButtonLink" placeholder="" value="">
                </div>
              </div>
            </div>
 
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" onclick="addSecondMenu()">确定</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <!-- 菜单按钮编辑  -->
    <div class="modal fade" id="editButton" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title">编辑菜单按钮</h4>
            <input id="currentButton" type="hidden" />
          </div>
          <div class="modal-body">
            <div class="form-horizontal">
              <div class="form-group">
                <label for="buttonName" class="col-sm-3 control-label">按钮名称</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="buttonName" placeholder="" value="">
                </div>
              </div>
              <div class="form-group">
                <label for="buttonLink" class="col-sm-3 control-label">关联链接</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="buttonLink" placeholder="" value="">
                </div>
              </div>
            </div>
 
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary" onclick="editButton()">确定</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
     
    <!-- 确认删除 -->
    <div class="modal fade" id="deleteButtonWindow" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4  class="modal-title text-danger">警告</h4>
          </div>
          <div class="modal-body">
            <h4 class="modal-title">确认删除这个按钮吗？</h4>
            <input id="deleteButtonId" type="hidden" value=""/>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-danger" data-dismiss="modal" onclick="deleteButton()">确认删除</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->  



