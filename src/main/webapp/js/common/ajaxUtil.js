/**
 * ajaxUtil.js
 * 공통 AJAX 유틸 모듈 (CSRF 토큰은 $.ajaxSetup으로 처리됨)
 * JSON용, HTML(fragment)용 호출을 모두 지원
 */
var AjaxUtil = (function() {

    /**
     * 공통 기본 옵션
     */
    const defaultOptions = {
        type: 'GET', // 기본값은 GET으로 설정
        cache: false,
        timeout: 5000,
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8' // 기본 콘텐츠 타입
    };

    /**
     * 기본 AJAX 요청 처리 함수
     * @param url - 호출 URL
     * @param data - 파라미터 객체
     * @param dataType - 기대하는 응답 데이터 타입 ('json', 'html', 'text')
     * @param successFn - 성공 콜백
     * @param errorFn - 에러 콜백
     * @param method - HTTP 메서드 ('GET', 'POST', 'PUT', 'DELETE')
     * @param options - 추가적인 jQuery AJAX 옵션
     */
    function baseAjax(url, data, dataType, successFn, errorFn, method, options = {}) {
        
        // 1. HTTP 메서드에 따라 contentType 변경 (POST, PUT 시 JSON 데이터 전송을 위한 설정)
        let requestOptions = $.extend({}, defaultOptions, {
            url: url,
            type: method,
            dataType: dataType,
            success: successFn,
            error: errorFn || function(xhr, status, error) {
                console.error(`ajax ${method} 실패 (${dataType}):`, error, xhr.responseText);
            }
        });

        // POST, PUT 요청의 경우, data를 문자열화하고 JSON Content-Type 설정 (서버가 JSON을 기대할 때)
        if (['POST', 'PUT', 'DELETE'].includes(method.toUpperCase())) {
            // 이 유틸리티는 기본적으로 form data 대신 JSON payload를 보내도록 설계합니다.
            // form data를 보낼 경우 data: data, contentType: 'application/x-www-form-urlencoded' 유지
            
            // JSON 데이터 전송 예시:
            requestOptions.data = JSON.stringify(data);
            requestOptions.contentType = 'application/json; charset=UTF-8';
        } else {
            // GET 요청은 일반 쿼리스트링 형태로 data를 전송합니다.
            requestOptions.data = data;
        }

        // 추가 옵션 병합
        requestOptions = $.extend(requestOptions, options);

        // 2. AJAX 호출 (CSRF 토큰은 $.ajaxSetup에서 자동 처리)
        $.ajax(requestOptions);
    }
    
    // =========================================================================
    // 💡 1. JSON 응답을 기대하는 메서드 (데이터 전송용)
    // =========================================================================

    /**
     * HTTP GET 요청 (JSON)
     */
    function ajaxGetJson(url, data = {}, successFn, errorFn = null) {
        baseAjax(url, data, 'json', successFn, errorFn, 'GET');
    }

    /**
     * HTTP POST 요청 (JSON)
     * 🚀 POST 요청 시 CSRF 토큰이 헤더에 자동으로 포함됩니다.
     */
    function ajaxPostJson(url, data = {}, successFn, errorFn = null) {
        baseAjax(url, data, 'json', successFn, errorFn, 'POST');
    }

    /**
     * HTTP PUT 요청 (JSON)
     * 🚀 PUT 요청 시 CSRF 토큰이 헤더에 자동으로 포함됩니다.
     */
    function ajaxPutJson(url, data = {}, successFn, errorFn = null) {
        baseAjax(url, data, 'json', successFn, errorFn, 'PUT');
    }

    /**
     * HTTP DELETE 요청 (JSON)
     * 🚀 DELETE 요청 시 CSRF 토큰이 헤더에 자동으로 포함됩니다.
     */
    function ajaxDeleteJson(url, data = {}, successFn, errorFn = null) {
        baseAjax(url, data, 'json', successFn, errorFn, 'DELETE');
    }
    
    // =========================================================================
    // 💡 2. HTML 응답을 기대하는 메서드 (화면 fragment 로딩용)
    // =========================================================================

    /**
     * HTML(fragment) 호출 (GET)
     * @param url - fragment URL
     * @param data - 파라미터 객체
     * @param targetSelector - 삽입할 DOM 선택자
     * @param callback - fragment 삽입 후 추가 처리
     */
    function ajaxHtml(url, data = {}, targetSelector, callback = null) {
        baseAjax(
            url, 
            data, 
            'html', 
            function(html) { // Success Callback
                if(targetSelector) {
                    $(targetSelector).html(html);
                }
                if(callback && typeof callback === 'function') {
                    callback(html);
                }
            }, 
            function(xhr, status, error) { // Error Callback
                console.error('ajaxHtml 실패:', error);
                if(targetSelector) {
                    $(targetSelector).html('<tr><td colspan="99">조회 실패</td></tr>');
                }
            }, 
            'GET', 
            { contentType: defaultOptions.contentType } // GET 요청은 일반 form content type 유지
        );
    }


    // 공개 함수 업데이트
    return {
        ajaxGetJson: ajaxGetJson,
        ajaxPostJson: ajaxPostJson,
        ajaxPutJson: ajaxPutJson,
        ajaxDeleteJson: ajaxDeleteJson,
        ajaxHtml: ajaxHtml
    };
})();