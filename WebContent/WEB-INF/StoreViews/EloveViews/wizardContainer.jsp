<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="zhonghe">
    <c:set var="request" value="${pageContext.request}" />
    <base href="${fn:replace(request.requestURL, request.requestURI, request.contextPath)}/" />
    <title>众合微信公共账号管理平台</title>
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
      <div id="operationContent" class="col-md-10 manager-content">
        <ol class="breadcrumb">
          <li><a href="store/elove/detail">Elove管理</a></li>
          <li class="active">基本信息</li>
        </ol>
        <div class="row">
          <div class="panel panel-info col-md-10 col-md-offset-1">
            <div class="panel-heading">
              <h4>基本信息</h4>
            </div>
            <div class="panel-body row">
              <div class="form-horizontal">
                <div class="form-group">
                  <label for="elove_title" class="col-md-3 control-label">Elove标题栏</label>
                  <div class="col-md-7">
                    <input type="text" class="form-control" id="elove_title" placeholder="" value="${eloveWizard.title}">
                  </div>
                </div>
                <div class="form-group">
                  <label for="elove_pwd" class="col-md-3 control-label">新人索取密码</label>
                  <div class="col-md-7">
                    <input type="text" class="form-control" id="elove_pwd" placeholder="" value="${eloveWizard.password}">
                  </div>
                </div>
                <form  class="form-group" role="form" enctype="multipart/form-data" id="upload1single">
                  <label for="elove_pic" class="col-md-3 control-label">图文消息图片</label>
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
                    <c:if test="${eloveWizard.coverPic!=null}">
                    <div id="${eloveWizard.coverPic}" class="col-md-6 pic-preview-div"><img src="./img/manager/theme2.png" class="pic-preview img-thumbnail img-responsive"/>
                      <span class="glyphicon glyphicon-trash" onclick="deleteThisImage('${eloveWizard.coverPic}')"> </span>
                    </div>
                    </c:if>
                    </div>
                    <div id="upload1single-links">
                    <c:if test="${eloveWizard.coverPic!=null}">
                    <input id="${eloveWizard.coverPic}-input" type="hidden" value="${eloveWizard.coverPic}"/>
                    </c:if>
                    </div>
                  </div>
              </div>                                
                <div class="form-group">
                  <label for="elove_txt" class="col-md-3 control-label">图文消息文字</label>
                  <div class="col-md-7">
                    <input type="text" class="form-control" id="elove_txt" placeholder="" value="${eloveWizard.coverText}">
                  </div>
                </div>               
                <div class="form-group">
                  <label for="share_title" class="col-md-3 control-label">分享消息标题</label>
                  <div class="col-md-7">
                    <input type="text" class="form-control" id="share_title" placeholder="" value="${eloveWizard.shareTitle}">
                  </div>
                </div>
                <div class="form-group">
                  <label for="share_content" class="col-md-3 control-label">分享消息内容</label>
                  <div class="col-md-7">
                    <input type="text" class="form-control" id="share_content" placeholder="" value="${eloveWizard.shareContent}">
                  </div>
                </div>
				 <form  class="form-group" role="form" enctype="multipart/form-data" id="upload2single">
                  <label for="elove_pic" class="col-md-3 control-label">新人婚纱主图片</label>
                  <div class="col-md-7">
                    <input type="file" name="file" class="image-file hidden" onchange="ye.value=value" accept="image/*">
                    <input type="text" name=ye class="form-control file-path-elove">
                    <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
                    <input type="button" value="上传" class="image-square btn btn-sm btn-info">
                  </div>
                </form>
                <div class="form-group">
                  <div class="col-md-7 col-md-offset-3">
                    <div class="row" id="upload2single-images">
                    <c:if test="${eloveWizard.majorGroupPhoto!=null}">
                    <div id="${eloveWizard.majorGroupPhoto}" class="col-md-6 pic-preview-div"><img src="./img/manager/theme2.png" class="pic-preview img-thumbnail img-responsive"/>
                      <span class="glyphicon glyphicon-trash" onclick="deleteThisImage('${eloveWizard.majorGroupPhoto}')"> </span>
                    </div>
                    </c:if>
                    </div>
                    <div id="upload2single-links">
                    <input id="${image}-input" type="hidden" value="${image}"/>
                    </div>
                  </div>
                </div>
                <div class="form-group">
                  <label for="theme_select" class="col-md-3 control-label">主题风格选择</label>
                  <div class="col-md-7">
                    <div class="row">
                      <div class="col-md-4">
                        <div class="radio">
                          <label>
                            <input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>
                            简约主题
                          </label>
                        </div>
                      </div>
                      <div class="col-md-4">
                        <div class="radio">
                          <label>
                            <input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">
                            温馨主题
                          </label>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
				 <form  class="form-group" role="form" enctype="multipart/form-data" id="upload3single">
                  <label for="elove_pic" class="col-md-3 control-label">新人婚纱主图片</label>
                  <div class="col-md-7">
                    <input type="file" name="file" class="image-file hidden" onchange="ye.value=value" accept="image/*">
                    <input type="text" name=ye class="form-control file-path-elove">
                    <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
                    <input type="button" value="上传" class="image-square btn btn-sm btn-info">
                  </div>
                </form>
                <div class="form-group">
                  <div class="col-md-7 col-md-offset-3">
                    <div class="row" id="upload3single-images">
                    <div id="${image}" class="col-md-6 pic-preview-div"><img src="./img/manager/theme2.png" class="pic-preview img-thumbnail img-responsive"/>
                      <span class="glyphicon glyphicon-trash" onclick="deleteThisImage('${image}')"> </span>
                    </div>
                    </div>
                    <div id="upload3single-links">
                    <input id="${image}-input" type="hidden" value="${image}"/>
                    </div>
                  </div>
                </div>
                <div class="form-group form-btn">
		            <button type="button" class="btn btn-lg btn-info col-md-3 col-md-offset-2" onclick="cancel()">取消</button>
		            <button type="button" class="btn btn-lg btn-info col-md-3 col-md-offset-1" onclick="nextStep('step2')">下一步</button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <div id="footer">
      <div class="container text-center">
        <p class="text-muted credit">Copyright ? 2013 zhonghesoftware.com All Rights Reserved. 众合网络科技有限公司 版权所有</p>
      </div>
    </div><!-- footer -->
    <!-- include jQuery + carouFredSel plugin -->
    <script type="text/javascript" src="./js/store/jquery-1.10.2.min.js"></script>
    <script src="js/store/upload.js"></script>
    <script type="text/javascript" src="./js/store/eloveWizard.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=PWFniUmG9SMyIVlp7Nm24MRC"></script>
  </body>
</html>