define(function (require, exports, module) {
    module.exports = (function () {
        var obj = {
            el: '.mzt-userper-main',
            orderListDataUrl: window.GLOBAL_CONFIG.mainUrl + '/evaOrder/orderListData',
        };
        obj.init = function () {
            return new Vue({
                el: obj.el,
                data() {
                    return {
                        param: {
                            p: 1,
                            orderNo: '',
                            status: 'all',
                            pageSize: 10
                        },
                        totalRow: 0,
                        orderList: [],
                        tableLoading: false,
                        showPopover: false,
                        showState: false,
                        commission: ''
                    }
                },
                mounted() {
                    this.getCommission()
                    this.findOrderListData()
                },
                methods: {
                    copyAmazonUrl() {
                        var clipboard = new ClipboardJS('.blackBtn')
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
                    // 获取分享金
                    getCommission() {
                        var that = this;
                        $.get(window.GLOBAL_CONFIG.mainUrl +'/evaOrder/commissionRulesInfo', function (res) {
                            that.commission = res.data.site_commission_rules.optionValue
                        }, 'json')
                    },
                    viewOrder(orderId) {
                        window.GLOBAL_VUE.viewOrderDialog.openView(orderId)
                    },
                    openOrder(orderId,step) {
                        if (step==2){
                            this.getQueryClient()
                        }
                        window.GLOBAL_VUE.orderDialog.openOrder(orderId,step)
                    },
                    getQueryClient() {
                        $.get(window.GLOBAL_CONFIG.mainUrl + '/client/queryClient', res => {
                            window.GLOBAL_VUE.orderDialog.applyForm.paypalAccount = res.data.paypalAccount
                        })
                    },
                    switchTab(status) {
                        this.param.status = status
                        this.findOrderListData()
                    },
                    findOrderListData() {
                        this.tableLoading = true
                        $.get(obj.orderListDataUrl, this.param, res => {
                            this.orderList = res.data.list
                            this.totalRow = res.data.totalRow
                            this.totalPage = res.data.totalPage
                            this.tableLoading = false
                        })
                    },
                    handleSizeChange(size) {
                        this.param.pageSize = size
                        this.findOrderListData()
                    },
                    handleCurrentChange(currentPage) {
                        this.param.p = currentPage
                        this.findOrderListData()
                    },
                    // 删除订单
                    deleteOrder(orderId) {
                        var that = this;
                        $.get(window.GLOBAL_CONFIG.mainUrl +'/evaOrder/deleteOrder/' + orderId, function (res) {
                            that.$message.success('Successfully deleted');
                            that.findOrderListData()
                        }, 'json')
                    },
                    // 关闭订单
                    closeOrder(orderId) {
                        var that = this;
                        $.get(window.GLOBAL_CONFIG.mainUrl +'/evaOrder/close/' + orderId, function (res) {
                            if (res.success){
                                that.$message.success('Successfully close');
                                that.findOrderListData()
                            } else {
                                this.$message.error(res.message)
                            }
                        }, 'json')
                    },
                    openOrderDialog(item, source) {
                        window.GLOBAL_VUE.orderDialog.handleOpen(item, source,false)
                    }
                }
            })
        };
        return obj;
    })()
});

