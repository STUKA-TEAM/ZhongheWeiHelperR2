<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<ol class="breadcrumb">
  <li><a href="store/website/home">微官网配置</a></li>
  <li class="active">新建微官网</li>
</ol>

<div class="row website-tab">
  <div class="panel panel-info col-md-10 col-md-offset-1">
    <div class="panel-heading">
      <h4>栏目管理 </h4>
    </div>
    <div class="panel-body">
      <div class="row">
          <button type="button" class="btn btn-md btn-info col-md-2 col-md-offset-1 text-center" onclick="addNodeWindow()">添加栏目</button>
          <button id = "fatherholder" type="button" class="btn btn-md btn-info col-md-2 col-md-offset-2 text-center" onclick="generateFatherLayer()">返回上一级<div id="fatherName"></div></button>
          <button type="button" class="btn btn-md btn-info col-md-2 col-md-offset-1 text-center" onclick="showList()">显示当前节点情况</button>
      </div>
      <table id="nodeItems" class="table table-bordered table-space">
        <tr>
          <th>栏目名称</th>
          <th>栏目图片</th>
          <th>此栏目按钮关联的内容</th>
          <th></th>
          <th></th>
          <th></th>
          <th></th>
        </tr>

      </table>
      <div class="row">
        <button type="button" class="btn btn-lg btn-info col-md-2 col-md-offset-2 text-center" onclick="cancel()">取消</button>
        <button type="button" class="btn btn-lg btn-info col-md-2 col-md-offset-1 text-center" onclick="backStep('backstep1')">上一步</button>
        <button type="button" class="btn btn-lg btn-info col-md-2 col-md-offset-1 text-center" onclick="nextStep('finish')">完成微官网创建</button>
      </div>
    </div>
  </div>
</div>
    <div class="modal fade" id="add_child_column" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">编辑类别</h4>
          </div>
          <div class="modal-body">
            <div  class="form-horizontal">
              <div class="form-group">
                <label for="node-Name" class="col-md-3 control-label">栏目名称</label>
                <div class="col-md-7">
                  <input type="text" class="form-control" id="node-Name" placeholder="">
                </div>
              </div>
		        <form  class="form-group" role="form" enctype="multipart/form-data" id="upload1single">
		          <label for="elove_pic" class="col-md-3 control-label">图文消息图片</label>
		          <div class="col-md-7">
		            <input type="file" name="file" class="image-file hidden" onchange="ye.value=value" accept="image/*">
		            <input id="filetext" type="text" name=ye class="form-control file-path-elove">
		            <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
		            <input type="button" value="上传" class="image-multi btn btn-sm btn-info">
		          </div>
		        </form>
		      <div class="form-group">
		          <div class="col-md-7 col-md-offset-3">
		            <div class="row" id="upload1single-images">

		            </div>
		            <div id="upload1single-links">

		            </div>
		          </div>
		      </div>   
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-info" onclick="addNode()">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div class="modal fade" id="edit_child_column" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <input id="editNodeUUID" type="hidden"/>
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">编辑类别</h4>
          </div>
          <div class="modal-body">
            <div  class="form-horizontal">
              <div class="form-group">
                <label for="node-Name" class="col-md-3 control-label">栏目名称</label>
                <div class="col-md-7">
                  <input type="text" class="form-control" id="edit-node-Name" placeholder="">
                </div>
              </div>
		        <form  class="form-group" role="form" enctype="multipart/form-data" id="upload2single">
		          <label for="elove_pic" class="col-md-3 control-label">图文消息图片</label>
		          <div class="col-md-7">
		            <input type="file" name="file" class="image-file hidden" onchange="ye.value=value" accept="image/*">
		            <input id="edit-filetext" type="text" name=ye class="form-control file-path-elove">
		            <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
		            <input type="button" value="上传" class="image-multi btn btn-sm btn-info">
		          </div>
		        </form>
		      <div class="form-group">
		          <div class="col-md-7 col-md-offset-3">
		            <div class="row" id="upload2single-images">

		            </div>
		            <div id="upload2single-links">

		            </div>
		          </div>
		      </div>   
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-info" onclick="editNode()">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div class="modal fade" id="relate_doc" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <input type="hidden" id="articleEditCurrentNode"/>
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">关联文章</h4>
          </div>
          <div id="relateArticle" class="modal-body">
  
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-info" onclick="relateArticle()">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

    <div class="modal fade" id="relate_doc_type" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <input type="hidden" id="articleClassEditCurrentNode"/>
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">关联文章类别</h4>
          </div>
          <div id="relateArticleClass" class="modal-body">

          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-info" onclick="relateClass()">确定</button>
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->