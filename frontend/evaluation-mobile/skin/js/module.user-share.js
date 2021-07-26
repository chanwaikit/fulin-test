define(function (require, exports, module) {
    module.exports = (function () {
        var obj = {
            el: '.mzt-main-vue',
            shareListDataUrl: window.GLOBAL_CONFIG.mainUrl + '/evaClient/queryUserRecommendation',
        };
        obj.init = function () {
            return new Vue({
                el: obj.el,
                data() {
                    return {
                        param: {
                            p: 1,
                            status: '',
                            pageSize: 10
                        },
                        totalRow: 0,
                        shareList: [],
                        tableLoading: false,
                        tabIndex: 'index'
                    }
                },
                mounted() {
                    this.getShareData()
                },
                methods: {
                    // 复制分享链接
                    copyShareLinks() {
                        var clipboard = new ClipboardJS('.copyBtn')
                        clipboard.on('success', e => {
                            this.$message.success('Copy succeeded')
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
                    // 获取提现数据
                    getShareData() {
                        this.tableLoading = true
                        $.get(obj.shareListDataUrl, this.param, res => {
                            this.shareList = res.data.list
                            this.totalRow = res.data.totalRow
                            this.totalPage = res.data.totalPage
                            this.tableLoading = false
                        })
                    },
                    handleSizeChange(size) {
                        this.param.pageSize = size
                        this.getShareData()
                    },
                    handleCurrentChange(currentPage) {
                        this.param.p = currentPage
                        this.getShareData()
                    },
                    switchTab(tab) {
                        this.tabIndex = tab
                    }
                }
            })
        };
        return obj;
    })()
});

