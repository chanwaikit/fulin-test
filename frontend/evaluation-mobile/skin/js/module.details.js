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
                        btnLoading: false,
                        showEarn: false,
                        showEarnType: 'share',
                    }
                },
                mounted() {
                },
                methods: {
                    showEarnPopup() {
                        this.showEarn = true
                        this.showEarnType = 'share'
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

                    getRouterUrl() {
                        const paramObj = $.Theme.Util.getParams(window.location.href)
                        Object.assign(this.search, paramObj)
                    },
                    toLogin (){
                        this.getRouterUrl()
                        this.btnLoading = true
                        if(this.search.cId){
                            window.open(window.GLOBAL_CONFIG.mainUrl+'/login?cId='+this.search.cId,'_self')
                        }else{
                            window.open(window.GLOBAL_CONFIG.mainUrl+'/login','_self')
                        }
                        this.btnLoading = false
                    },
                    toRefer (){
                        window.open(window.GLOBAL_CONFIG.mainUrl+'/evaClient/share','_self');
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
                                this.$message.success('Validation successfully')
                                if (!this.showEarn){
                                    this.showEarn = true
                                }
                                this.showEarnType = 'order'
                            }else {
                                this.$message.error(res.message)
                            }
                        }, 'json')
                    },
                    handleToAmazon(url){
                        window.open(url, '_blank')
                    },
                }
            })
        };
        return obj;
    })()
});

