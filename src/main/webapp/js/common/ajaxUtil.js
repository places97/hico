/**
 * ajaxUtil.js
 * 공통 AJAX 유틸 모듈 (CSRF 토큰 자동 감지 및 설정 포함)
 */
var AjaxUtil = (function() {

    /**
     * [내부 함수] 페이지 내에서 CSRF 토큰을 찾아 반환합니다.
     * meta 태그(표준) 또는 hidden input 태그를 모두 확인합니다.
     */
    function _getCsrfConfig() {
		
        // 1. Meta 태그 확인 (Spring Security 기본값 등)
        const token = $('meta[name="_csrf"]').attr('content');
        const header = $('meta[name="_csrf_header"]').attr('content') || 'X-CSRF-TOKEN';

        // 2. Meta 태그가 없다면 hidden input 확인 (Thymeleaf 기본 input 등)
        const tokenFromInput = $('input[name="_csrf"]').val();

        return {
            token: token || tokenFromInput,
            header: header
        };
    }

    /**
     * [초기화] 모든 AJAX 요청에 CSRF 헤더를 자동 설정 ($.ajaxSetup)
     */
    function _initCsrf() {
		
        const csrf = _getCsrfConfig();
        
        if (csrf.token) {
            $.ajaxSetup({
                beforeSend: function(xhr, settings) {
                    // GET, HEAD, OPTIONS, TRACE 이외의 요청(데이터를 변경하는 요청)에만 헤더 추가
                    if (!/^(GET|HEAD|OPTIONS|TRACE)$/i.test(settings.type)) {
                        xhr.setRequestHeader(csrf.header, csrf.token);
                    }
                }
            });
            // console.log("AJAX CSRF 토큰이 설정되었습니다.");
        } else {
            // console.warn("CSRF 토큰을 찾을 수 없습니다. POST 요청 시 오류가 발생할 수 있습니다.");
        }
    }

    // 모듈 로드 시 즉시 CSRF 설정 실행
    $(function() {
        _initCsrf();
    });

    /**
     * 공통 기본 옵션
     */
    const defaultOptions = {
        type: 'GET',
        cache: false,
        timeout: 5000,
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8'
    };

    /**
     * 기본 AJAX 요청 처리 함수 (내부용)
     */
    function baseAjax(url, data, dataType, successFn, errorFn, method, options = {}) {
        let requestOptions = $.extend({}, defaultOptions, {
            url: url,
            type: method,
            dataType: dataType,
            success: successFn,
            error: errorFn || function(xhr) {
                console.error(`ajax ${method} 실패:`, xhr.status, xhr.responseText);
            }
        });

        if (['POST', 'PUT', 'DELETE'].includes(method.toUpperCase())) {
            requestOptions.data = JSON.stringify(data);
            requestOptions.contentType = 'application/json; charset=UTF-8';
        } else {
            requestOptions.data = data;
        }

        $.ajax($.extend(requestOptions, options));
    }
    
    function fnAjax(obj, success, error){
		if(!obj.dataType == "html" || obj.dataType == null || obj.dataType == ""){
			//dataType 이 html 아닐때
			baseAjax(obj.url, obj.data, obj.dataType, success, error, obj.method);
		}else{
			//dataType 이 html 일때
			baseAjax(obj.url, obj.data, obj.dataType, function(html) {
                if(targetSelector) $(targetSelector).html(html);
                if(callback) callback(html);
            }, null, 'GET');
		}
		
		
	}

    // --- 공개 API ---

    return {
		
		fnAjax : fnAjax
		/*
        ajaxGetJson: function(url, data, success, error) {
            baseAjax(url, data, 'json', success, error, 'GET');
        },
        ajaxPostJson: function(url, data, success, error) {
            baseAjax(url, data, 'json', success, error, 'POST');
        },
        ajaxPutJson: function(url, data, success, error) {
            baseAjax(url, data, 'json', success, error, 'PUT');
        },
        ajaxDeleteJson: function(url, data, success, error) {
            baseAjax(url, data, 'json', success, error, 'DELETE');
        },
        ajaxHtml: function(url, data, targetSelector, callback) {
            baseAjax(url, data, 'html', function(html) {
                if(targetSelector) $(targetSelector).html(html);
                if(callback) callback(html);
            }, null, 'GET');
        }
        */
    };
})();