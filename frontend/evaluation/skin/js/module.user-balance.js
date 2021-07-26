define(function (require, exports, module) {
    module.exports = (function () {
        var obj = {
            el: '.mzt-main-vue',
            withdrawalListDataUrl: window.GLOBAL_CONFIG.mainUrl + '/evaBalance/queryUserCommissionWithdrawal',
            cashbackListDataUrl: window.GLOBAL_CONFIG.mainUrl + '/evaOrder/orderListData',
            orderStatusListDataUrl: window.GLOBAL_CONFIG.mainUrl + '/evaOrder/getOrderStatus'
        };
        obj.init = function () {
            return new Vue({
                el: obj.el,
                data() {
                    return {
                        withdrawalParam: {
                            p: 1,
                            status: '',
                            pageSize: 10
                        },
                        cashbackParam: {
                            p: 1,
                            orderNo: '',
                            status: 'all',
                            pageSize: 10
                        },
                        cashbackTotalRow: 0,
                        cashbackList: [],
                        cashbackTableLoading: false,
                        withdrawalTotalRow: 0,
                        withdrawalList: [],
                        tableLoading: false,
                        tabIndex: 'cashback',
                        closedData: 0,
                        payableData: 0,
                        pendingData: 0
                    }
                },
                mounted() {
                    if(this.tabIndex === 'cashback'){
                        this.findOrderListData()
                        this.getOrderStatus()
                    }else{
                        this.getWithdrawalData()
                    }
                },
                methods: {
                    getQueryClient() {
                        $.get(window.GLOBAL_CONFIG.mainUrl + '/client/queryClient', res => {
                            window.GLOBAL_VUE.withdrawalDialog.withdrawalForm.paypalAccount = res.data.paypalAccount
                        })
                    },
                    // 打开提现窗口
                    openWithdrawal(amount){
                        this.getQueryClient()
                        window.GLOBAL_VUE.withdrawalDialog.withdrawalForm.withdrawal = amount
                        window.GLOBAL_VUE.withdrawalDialog.openWithdrawal()
                    },
                    switchTab(tab) {
                        this.tabIndex = tab
                        if(this.tabIndex === 'cashback'){
                            this.findOrderListData()
                        }else{
                            this.getWithdrawalData()
                        }
                    },
                    getOrderStatus() {
                        $.get(obj.orderStatusListDataUrl, res => {
                            this.closedData = res.data.closed
                            this.payableData = res.data.payable
                            this.pendingData = res.data.pending
                        })
                    },
                    findOrderListData() {
                        this.cashbackTableLoading = true
                        $.get(obj.cashbackListDataUrl, this.cashbackParam, res => {
                            this.cashbackList = res.data.list
                            this.cashbackTotalRow = res.data.totalRow
                            // this.totalPage = res.data.totalPage
                            this.cashbackTableLoading = false
                        })
                    },
                    // 获取提现数据
                    getWithdrawalData() {
                        this.tableLoading = true
                        $.get(obj.withdrawalListDataUrl, this.withdrawalParam, res => {
                            this.withdrawalList = res.data.list
                            this.withdrawalTotalRow = res.data.totalRow
                            // this.totalPage = res.data.totalPage
                            this.tableLoading = false
                        })
                    },
                    handleWithdrawalSizeChange(size) {
                        this.withdrawalParam.pageSize = size
                        this.getWithdrawalData()
                    },
                    handleWithdrawalCurrentChange(currentPage) {
                        this.withdrawalParam.p = currentPage
                        this.getWithdrawalData()
                    },
                    switchWithdrawalTab(status) {
                        this.withdrawalParam.status = status
                        this.getWithdrawalData()
                    },
                    handleCashbackSizeChange(size) {
                        this.cashbackParam.pageSize = size
                        this.findOrderListData()
                    },
                    handleCashbackCurrentChange(currentPage) {
                        this.cashbackParam.p = currentPage
                        this.findOrderListData()
                    },
                    switchCashbackTab(status) {
                        this.cashbackParam.status = status
                        this.findOrderListData()
                    }
                }
            })
        };
        return obj;
    })()
});

