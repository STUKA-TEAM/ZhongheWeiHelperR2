<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
   
    <!-- Modal -->
    <div class="modal fade" id="bless" tabindex="-1">
      <div class="modal-dialog">
        <div class="modal-content">
          <div id="modalbody" class="modal-body">
            <textarea id="wishcontent" class="modal-text" rows=4 placeholder="请留下您对我们的祝福吧！" ></textarea>
            <input id="wishname" class="modal-input" type="text" placeholder="您的称呼" />
            <p>
              <a class="modal-btn btn btn-elove" onclick="submitWish()">发送祝福</a>
              <a class="modal-btn btn btn-elove" data-dismiss="modal">取消</a>
            </p>
          </div>
        </div>
      </div>
    </div>
    
    <div class="modal fade" id="modalmesbox" tabindex="-1">
      <div class="modal-dialog">
        <div class="modal-content">
          <div id="modalmes" class="modal-body">

          </div>
        </div>
      </div>
    </div>