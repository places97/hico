<%--&lt;%&ndash;--%>
<%--  Created by IntelliJ IDEA.--%>
<%--  User: place--%>
<%--  Date: 2025-11-27--%>
<%--  Time: 오후 2:42--%>
<%--  To change this template use File | Settings | File Templates.--%>
<%--&ndash;%&gt;--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<script src="/js/jquery/jquery-3.7.1.min.js"></script>--%>

<%--<script type="text/javascript">--%>
<%--  $(document).ready(function(){--%>
<%--    fnAddEvent(); // 페이지 이벤트 추가--%>
<%--  });--%>

<%--  function fnAddEvent() {--%>
<%--    $('#btn').on('click', fnRegister);--%>
<%--    $('#reset').on('click', fnReset);--%>
<%--    $('#sel').on('click', fnList);--%>
<%--    $('#move').on('click', fnMove);--%>
<%--    $('#err').on('click', fnErr);--%>
<%--  }--%>

<%--  function fnMove() {--%>
<%--    window.location.href = '/test/page.do';--%>
<%--  }--%>

<%--  function fnRegister() {--%>
<%--    let result = $('#ipt').val(); // #ipt의 값을 가져옵니다.--%>
<%--    $('#rst').val(result);        // 가져온 값을 #rst (세 번째 입력칸)에 설정합니다.--%>
<%--  }--%>
<%--  function fnReset() {--%>
<%--    $('#rst').val("");--%>
<%--  }--%>

<%--  function fnList() {--%>
<%--    $.ajax({--%>
<%--      url: '/test/list.do', // 서버의 목록 조회 URL--%>
<%--      type: 'GET',       // HTTP 메서드 (조회이므로 GET 사용)--%>
<%--      dataType: 'json',  // 서버로부터 받을 데이터 형식 (예: JSON)--%>
<%--      contentType: 'application/json; charset=utf-8', // 서버로 보내는 데이터 형식--%>
<%--      data: {},          // 서버에 보낼 데이터 (목록 조회에서는 보통 빈 객체 {} 또는 페이징 정보 등을 보냅니다)--%>

<%--      success: function(response) {--%>
<%--        // HTTP 상태 코드가 200번대일 때 실행--%>
<%--        console.log("목록 조회 성공:", response);--%>
<%--        console.log(response.data);--%>
<%--        alert("조회 성공");--%>
<%--      },--%>
<%--      error: function(response) {--%>
<%--        var errorResultVo = JSON.parse(response.responseText);--%>
<%--        console.error("서버 에러 응답 (ResultVo):", errorResultVo);--%>
<%--        alert("에러 발생: " + errorResultVo.resMsg);--%>
<%--      }--%>
<%--    });--%>
<%--  }--%>
<%--  function fnErr() {--%>
<%--    $.ajax({--%>
<%--      url: '/test/error-test.do', // 서버의 목록 조회 URL--%>
<%--      type: 'GET',       // HTTP 메서드 (조회이므로 GET 사용)--%>
<%--      dataType: 'json',  // 서버로부터 받을 데이터 형식 (예: JSON)--%>
<%--      contentType: 'application/json; charset=utf-8', // 서버로 보내는 데이터 형식--%>
<%--      data: {},          // 서버에 보낼 데이터 (목록 조회에서는 보통 빈 객체 {} 또는 페이징 정보 등을 보냅니다)--%>
<%--      timeout: 120000,--%>
<%--      success: function(response) {--%>
<%--        // HTTP 상태 코드가 200번대일 때 실행--%>
<%--        console.log("목록 조회 성공:", response);--%>
<%--        alert("조회 성공");--%>
<%--      },--%>
<%--      error: function(response) {--%>
<%--          var errorResultVo = JSON.parse(response.responseText);--%>
<%--          console.error("서버 에러 응답 (ResultVo):", errorResultVo);--%>
<%--          alert("에러 발생: " + errorResultVo.resMsg);--%>
<%--      }--%>
<%--    });--%>
<%--  }--%>
<%--</script>--%>
<%--<div>--%>
<%--    <input id="ipt" type="text"/>--%>
<%--    <button id="btn" >입력</button>--%>
<%--    <button id="reset" >초기화</button>--%>
<%--    <button id="sel" >조회</button>--%>
<%--    <button id="move" >이동</button>--%>
<%--    <button id="err" >에러발생</button>--%>
<%--    <br>--%>
<%--    <input id="rst" type="text"/>--%>
<%--</div>--%>
