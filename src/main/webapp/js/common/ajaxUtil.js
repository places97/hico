/**
 * ajaxUtil.js
 * 공통 AJAX 유틸 모듈
 * JSON용, HTML(fragment)용 호출을 모두 지원
 */

var AjaxUtil = (function() {

    /**
     * 공통 기본 옵션
     */
    const defaultOptions = {
        type: 'GET',
        cache: false,
        timeout: 5000
    };

    /**
     * JSON 호출
     * @param url - 호출 URL
     * @param data - 파라미터 객체
     * @param successFn - 성공 콜백
     * @param errorFn - 에러 콜백 (선택)
     */
    function ajaxJson(url, data = {}, successFn, errorFn = null) {
        $.ajax($.extend({}, defaultOptions, {
            url: url,
            data: data,
            dataType: 'json',
            success: successFn,
            error: errorFn || function(xhr, status, error) {
                console.error('ajaxJson 실패:', error);
            }
        }));
    }

    /**
     * HTML(fragment) 호출
     * @param url - fragment URL
     * @param data - 파라미터 객체
     * @param targetSelector - 삽입할 DOM 선택자
     * @param callback - fragment 삽입 후 추가 처리
     */
    function ajaxHtml(url, data = {}, targetSelector, callback = null) {
        $.ajax($.extend({}, defaultOptions, {
            url: url,
            data: data,
            dataType: 'html',
            success: function(html) {
                if(targetSelector) {
                    $(targetSelector).html(html);
                }
                if(callback && typeof callback === 'function') {
                    callback(html);
                }
            },
            error: function(xhr, status, error) {
                console.error('ajaxHtml 실패:', error);
                if(targetSelector) {
                    $(targetSelector).html('<tr><td colspan="99">조회 실패</td></tr>');
                }
            }
        }));
    }

    // 공개 함수
    return {
        ajaxJson: ajaxJson,
        ajaxHtml: ajaxHtml
    };
})();
