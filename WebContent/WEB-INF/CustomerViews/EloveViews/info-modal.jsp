<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    
 
    <!-- Modal -->
    <div class="modal fade" id="attend" tabindex="-1">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-body">
            <input id="joinName" class="modal-input" type="text" placeholder="称呼" />
            <input id="joinContact" class="modal-input" type="text" placeholder="联系方式" />
            <input id="joinNum" class="modal-input" type="text" placeholder="人数" />
            <p>
              <a class="modal-btn btn btn-lg btn-elove" onclick="submitJoin()">确 定</a>
              <a class="modal-btn btn btn-lg btn-elove" data-dismiss="modal">取消</a>
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