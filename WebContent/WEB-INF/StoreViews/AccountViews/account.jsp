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
    <!-- include jQuery -->
		<script type="text/javascript" src="./js/store/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=PWFniUmG9SMyIVlp7Nm24MRC"></script>
  </head>
  <body>
    <!-- Fixed navbar -->
    <div class="navbar navbar-default navbar-white navbar-static-top">
        <div class="navbar-header">
          <a class="navbar-brand" href="#"><img src="./img/logo.png" alt="Zhonghe Software" /></a>
        </div>
        
        <div class="navbar-collapse collapse">
          <div class="btn-group navbar-right nav-user">
            <button type="button" class="btn btn-user dropdown-toggle" data-toggle="dropdown">
            <span class="glyphicon glyphicon-user"></span>  ${username}  <span class="caret"></span>
            </button>
            <ul class="dropdown-menu">
              <li><a href="#">设置</a></li>
              <li class="divider"></li>
              <li><a href="#">退出</a></li>
            </ul>
          </div>
        </div><!--/.nav-collapse -->
    </div><!-- navbar -->
    

    <div class="row">
      <div class="col-md-2 sidebar">
        <div class="container account-change">
          <p><strong>当前正在管理的微信账号</strong></p>
          <p>公共账号1</p>
          <p><strong>切换至其他公共账号</strong></p>
          <form>
            <select class="form-control account-select">
              <option>1</option>
              <option>2</option>
              <option>3</option>
              <option>4</option>
              <option>5</option>
            </select>
          </form>
        </div>
        <ul class="nav nav-pills nav-stacked">
          <li class="active"><a href="./manager.html">账号管理</a></li>
          <li><a href="./elove.html">Elove管理</a></li>
          <li><a data-toggle="collapse" data-toggle="collapse" data-parent="#accordion" href="#collapseOne">订餐管理</a>
            <div id="collapseOne" class="panel-collapse collapse">
              <ul class="nav submenu">
                <li><a href="./order-manager.html">订单管理</a></li>
                <li><a href="./menu-manager.html">菜单管理</a></li>
                <li><a href="./dish-manager.html">菜品管理</a></li>
              </ul>
            </div>
          </li>
          <li><a href="./vote.html">投票管理</a></li>
          <li><a href="./lottery.html">抽奖管理</a></li>
        </ul>
      </div>
        
      <div class="col-md-10 manager-content">
        <ol class="breadcrumb">
          <li class="active">账号管理</li>
        </ol>
        
        <div class="row">
          <div class="panel panel-info col-md-10 col-md-offset-1">
            <div class="panel-heading">
              <div class="row">
                <h4 class="col-md-9">基本信息</h4>
                <a class="btn btn-info pull-right" data-toggle="modal" data-target="#info_edit" onclick="setInitPoint()">编辑</a>
              </div>
            </div>
            <div class="panel-body">
              <dl class="dl-horizontal">
                <dt>用户名：</dt>
                <dd>${userInfo.username}</dd>
                <dt>创建时间：</dt>
                <dd>${userInfo.createDate}</dd>
                <dt>商户名称：</dt>
                <dd>${userInfo.storeName}</dd>
                <dt>邮箱：</dt>
                <dd>${userInfo.email}</dd>
                <dt>座机：</dt>
                <dd>${userInfo.phone}</dd>
                <dt>手机：</dt>
                <dd>${userInfo.cellPhone}</dd>
                <dt>介绍图片：</dt>
                <dd>
                  <a href="#" class="thumbnail intro-image">
                    <img src="${userInfo.majorImage}" alt="介绍图片">
                  </a>
                </dd>
                <dt>官网链接：</dt>
                <dd><a target="blank" href="${userInfo.corpMoreInfoLink}">${userInfo.corpMoreInfoLink}</a></dd>
                <dt>地址：</dt>
                <dd>${userInfo.address}</dd>
              </dl>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="panel panel-info col-md-10 col-md-offset-1">
            <div class="panel-heading">
              <div class="row">
                <h4 class="col-md-9">关联公共账号</h4>
                <a class="btn btn-info pull-right" data-toggle="modal" data-target="#related">关联新的公共</a>
              </div>
            </div>
            <table class="table table-striped table-bordered">
              <tr>
                <th>公共账号名称</th>
                <th>原始ID</th>
                <th>微信号</th>
                <th>管理状态</th>
                <th>微信平台接口配置</th>
                <th></th>
              </tr>
              <c:forEach items = "${appInfoList}" var = "appInfo" >
              <tr>
                <td> ${appInfo.wechatName}</td>
                <td>${appInfo.wechatOriginalId}</td>
                <td>${appInfo.wechatNumber}</td>
                <td>${appInfo.isCharged}</td>
                <td><a data-toggle="modal" data-target="#${appInfo.appid}">查看</a>
                
                </td>
                <td><a class="btn btn-sm btn-danger">删除</a></td>
              </tr>
              </c:forEach>
            </table>
          </div>
        </div>
        
        <div class="row">
          <div class="panel panel-info col-md-10 col-md-offset-1">
            <div class="panel-heading"><h4>权限信息</h4></div>
            <table class="table table-striped table-bordered">
              <tr>
                <th>权限名称</th>
                <th>单价</th>
              </tr>
              <c:forEach items = "${priceList}" var = "authPrice" >
              <tr>
                <td>${authPrice.authName}</td>
                <td>${authPrice.price}元</td>
              </tr>
              </c:forEach>
            </table>
          </div>
        </div>
        
      </div>
    </div>
    <div class="modal fade" id="info_edit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">基本信息</h4>
          </div>
          <div class="modal-body">
            <div class="form-horizontal">
              <div class="form-group">
                <label for="info_name" class="col-sm-3 control-label">商户名称</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="info_name" placeholder="" value="${userInfo.storeName}">
                </div>
              </div>
              <div class="form-group">
                <label for="info_email" class="col-sm-3 control-label">邮箱</label>
                <div class="col-sm-9">
                  <input type="email" class="form-control" id="info_email" placeholder="" value="${userInfo.email}">
                </div>
              </div>
              <div class="form-group">
                <label for="info_phone" class="col-sm-3 control-label">座机</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="info_phone" placeholder="" value="${userInfo.phone}">
                </div>
              </div>
              <div class="form-group">
                <label for="info_mobile" class="col-sm-3 control-label">手机</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="info_mobile" placeholder="" value="${userInfo.cellPhone}">
                </div>
              </div>
              <form  class="form-group" role="form" enctype="multipart/form-data" id="upload1">
                <label class="col-sm-3 control-label">介绍图片</label>
                <div class="col-sm-9">
                  <input type="file" name="file" class="image-file hidden" onchange="ye.value=value" accept="image/*">
                  <input type="text" name=ye class="form-control file-path">
                  <input type="button" value="选择文件" onclick="file.click()" class="btn btn-sm btn-info">
                  <input type="button" value="上传" class="image-multi btn btn-sm btn-info">
                </div>
              </form>  
              <div class="form-group">
                  <div class="col-md-7 col-md-offset-3">
                    <div class="row" id="upload1-images">
                      <div class="col-md-6">
                        <img src="./img/manager/theme2.png" class="pic-preview img-thumbnail img-responsive"/>
                      </div>
                      <div class="col-md-6">
                        <img src="./img/manager/theme2.png" class="pic-preview img-thumbnail img-responsive"/>
                      </div>
                      <div class="col-md-6">
                        <img src="./img/manager/theme2.png" class="pic-preview img-thumbnail img-responsive"/>
                      </div>
                      <div class="col-md-6">
                        <img src="./img/manager/theme2.png" class="pic-preview img-thumbnail img-responsive"/>
                      </div>
                    </div>
                  </div>
                </div>            
              <div class="form-group">
                <label for="info_link" class="col-sm-3 control-label">官网链接</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="info_link" placeholder="" value="${userInfo.corpMoreInfoLink}">
                </div>
              </div>
              <div class="form-group">
                <label for="info_addr" class="col-sm-3 control-label">公司地址</label>
                <div class="col-sm-9">
                  <div class="input-group">
                    <input type="text" class="form-control" id="info_addr" placeholder="" value="${userInfo.address}">
                    <input type="hidden" id="lng" value="${userInfo.lng}">
                    <input type="hidden" id="lat" value="${userInfo.lat}">
                    <span class="input-group-btn">
                      <button class="btn btn-info" type="button" onclick="setPoint()">定位</button>
                    </span>
                  </div><!-- /input-group -->
                </div>
              </div>
            </div>
            <div id="baidumap"></div>  
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary">确定</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <c:forEach items = "${appInfoList}" var = "appInfo" >
    <div class="modal fade" id="${appInfo.appid}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">查看公共账号接口连接</h4>
          </div>
          <div class="modal-body">
            <dl class="dl-horizontal">
              <dt><h4><strong>微信接口URL:</strong></h4></dt>
              <dd><pre>${appInfo.appid}</pre></dd>
              <dt><h4><strong>微信接口Token:</strong></h4></dt>
              <dd><pre>${appInfo.wechatToken}</pre></dd>
            </dl>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    </c:forEach>
    
    <div class="modal fade" id="related" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
            <h4 class="modal-title" id="myModalLabel">关联公共账号</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal" role="form">
              <div class="form-group">
                <label for="wechat_name" class="col-sm-3 control-label">微信账号名称</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="wechat_name" placeholder="">
                </div>
              </div>
              <div class="form-group">
                <label for="wechat_id" class="col-sm-3 control-label">微信账号原始ID</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="wechat_id" placeholder="">
                </div>
              </div>
              <div class="form-group">
                <label for="wechat_account" class="col-sm-3 control-label">微信号</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="wechat_account" placeholder="">
                </div>
              </div>
              <div class="form-group">
                <label for="wechat_addr" class="col-sm-3 control-label">微信账号地址</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="wechat_addr" placeholder="">
                </div>
              </div>
              <div class="form-group">
                <label for="wechat_trade" class="col-sm-3 control-label">所属行业</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="wechat_trade" placeholder="">
                </div>
              </div>
              <div class="form-group">
                <label for="wechat_token" class="col-sm-3 control-label">验证Token</label>
                <div class="col-sm-9">
                  <input type="text" class="form-control" id="wechat_token" placeholder="">
                </div>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" class="btn btn-primary">确定</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    
    <div id="footer">
      <div class="container text-center">
        <p class="text-muted credit">Copyright ? 2013 zhonghesoftware.com All Rights Reserved. 众合网络科技有限公司 版权所有</p>
      </div>
    </div><!-- footer -->
    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="./js/store/bootstrap.min.js"></script>
    <script src="js/store/upload.js"></script>
    <script type="text/javascript">
    var myGeo = new BMap.Geocoder();  
    var map = new BMap.Map("baidumap");
    map.centerAndZoom("上海", 12);  
    map.enableScrollWheelZoom();
    function setInitPoint(){
    	map.clearOverlays();
        myGeo.getPoint( "${userInfo.address}", function(point){  
        if (point) { 	 
           map.centerAndZoom(point, 16);
           document.getElementById("lng").value = point.lng;
           document.getElementById("lat").value = point.lat;  
           map.addOverlay(new BMap.Marker(point));  
           map.enableScrollWheelZoom(); 
         }  
        }, "上海市");
    }
    function setPoint(){
    map.clearOverlays();
    var address = document.getElementById("info_addr").value;
    myGeo.getPoint(address, function(point){  
    if (point) { 	 
       map.centerAndZoom(point, 16);
       document.getElementById("lng").value = point.lng;
       document.getElementById("lat").value = point.lat;  
       map.addOverlay(new BMap.Marker(point));  
       map.enableScrollWheelZoom(); 
     }  
    }, "上海市");
    } 
    map.addEventListener("click", function(e){
      map.clearOverlays();
      var point=new BMap.Point(e.point.lng, e.point.lat);
      document.getElementById("lng").value = point.lng;
      document.getElementById("lat").value = point.lat;  
      map.centerAndZoom(point, map.getZoom());  
      map.addOverlay(new BMap.Marker(point)); 
    });
    </script>
  </body>
</html>