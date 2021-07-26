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
                        },
                        showEarn: false,
                        showEarnType: 'share',
                    }
                },
                methods: {
                    copyAmazonUrl() {
                        var clipboard = new ClipboardJS('.copy')
                        clipboard.on('success', e => {
                            this.$message.success('Copy Amazon keyword successfully')
                            // 释放内存
                            clipboard.destroy()
                        })
                        clipboard.on('error', e => {
                            // 不支持复制
                            this.$message.error('The browser does not support automatic copying')
                            // 释放内存
                            clipboard.destroy()
                        })
                    },
                    getRouterUrl() {
                        const paramObj = $.Theme.Util.getParams(window.location.href)
                        Object.assign(this.search, paramObj)
                    },
                    // 加入订单
                    openOrderDialog: function (id, type) {
                        $.get(window.GLOBAL_CONFIG.mainUrl +'/evaOrder/checkOrder/'+id, res => {
                            if(res.data.state == 200) {
                                window.GLOBAL_VUE.orderDialog.open(id, type,true)
                            } else {
                                this.$message.warning(res.message)
                            }
                        })
                    },
                    toLogin (){
                        this.getRouterUrl()
                        if(this.search.cId){
                            window.open(window.GLOBAL_CONFIG.mainUrl+'/login?cId='+this.search.cId,'_self')
                        }else{
                            window.open(window.GLOBAL_CONFIG.mainUrl+'/login','_self')
                        }
                    },
                    toRefer (){
                        this.showEarn = true
                        this.showEarnType = 'share'
                    },
                    learnMore (){
                        this.getRouterUrl()
                        if(this.search.cId){
                            window.open(window.GLOBAL_CONFIG.mainUrl+'/userGuide?cId='+this.search.cId,'_self');
                        }else{
                            window.open(window.GLOBAL_CONFIG.mainUrl+'/userGuide','_self');
                        }
                    },
                    percentageFormat(percentage) {
                        let total = document.getElementById('progressPage').getAttribute('data-total')
                        let count = document.getElementById('progressPage').getAttribute('data-count')
                        return 'Only ' + parseInt(`${total-count}`) + ' left';
                    },
                    handleAddOrder(id){
                        // 先验证是否加入订单
                        $.post(window.GLOBAL_CONFIG.mainUrl +'/evaOrder/addOrder',{'fkProductId': id}, res => {
                            if (res.success){
                                $.Theme.event.addToCart()
                                this.showEarn = true
                                this.showEarnType = 'order'
                            }else {
                                this.$message.error(res.message)
                            }
                        }, 'json')
                    },
                }
            })
        };
        return obj;
    })()
});

