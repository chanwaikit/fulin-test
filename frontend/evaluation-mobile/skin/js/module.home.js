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
                        search: {
                            cId: ''
                        }
                    }
                },
                methods: {
                    getRouterUrl() {
                        const paramObj = $.Theme.Util.getParams(window.location.href)
                        Object.assign(this.search, paramObj)
                    },
                    learnMore: function(){
                        this.getRouterUrl()
                        if(this.search.cId){
                            window.open(window.GLOBAL_CONFIG.mainUrl+'/userGuide?cId='+this.search.cId,'_self');
                        }else{
                            window.open(window.GLOBAL_CONFIG.mainUrl+'/userGuide','_self');
                        }
                    },
                    viewMore: function(){
                        this.getRouterUrl()
                        if(this.search.cId){
                            window.open(window.GLOBAL_CONFIG.mainUrl+'/productList?cId='+this.search.cId,'_self');
                        }else{
                            window.open(window.GLOBAL_CONFIG.mainUrl+'/productList','_self');
                        }
                    }

                }
            })
        };
        return obj;
    })()
});

