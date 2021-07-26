define(function (require, exports, module) {
    module.exports = (function () {
        var obj = {
            el: '.mzt-main-vue'
        };
        obj.init = function () {
            return new Vue({
                el: obj.el,
                data() {
                    return {
                        faqList: [],
                        faqParam: {
                            type: '',
                            index: 0,
                            title: ''
                        },
                        faqActiveName: '',
                        faqActiveKey: 0
                    }
                },
                mounted() {
                    this.getFaqUrl()
                    if(this.faqParam.type){
                        this.getFaqList()
                    }else{
                        this.getFaqTypeList()
                    }
                },
                methods: {
                    faqContent(faqType, title, index){
                        window.open(window.GLOBAL_CONFIG.mainUrl+'/faqContent?type='+faqType + '&title=' + title + '&index=' + index,'_self');
                        this.faqActiveKey = index
                    },
                    getFaqUrl() {
                        const paramObj = $.Theme.Util.getParams(window.location.href)
                        Object.assign(this.faqParam, paramObj)
                        this.faqActiveKey = this.faqParam.index
                    },
                    getFaqTypeList() {
                        var that = this;
                        $.get(window.GLOBAL_CONFIG.mainUrl +'/getFaqTypeList', function (res) {
                            that.faqParam.type = res[0].id
                            that.faqParam.title = res[0].title
                            that.getFaqList()
                        }, 'json')
                    },
                    getFaqList() {
                        var that = this;
                        $.get(window.GLOBAL_CONFIG.mainUrl +'/getFaqList/'+this.faqParam.type, function (res) {
                            that.faqList = res
                        }, 'json')
                    }
                }
            })
        };
        return obj;
    })()
});

