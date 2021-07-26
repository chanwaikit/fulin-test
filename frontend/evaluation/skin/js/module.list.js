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
                        param: {
                            p: 1,
                            pageSize: 30,
                            minPrice: '',
                            maxPrice: '',
                            tag: '',
                            c: '',
                            o:'-sales',
                            k: '',
                            country: ''
                        },
                        sortList: [{'id': '-sales','name': 'Hot'},{'id': '-times','name': 'New To Old'},{'id': 'times','name': 'Old To New'},{'id': '-oldPrice','name': 'Price High To Low'},{'id': 'oldPrice','name': 'Price Low To High'}],
                        categoryList: [],
                        productTagList: [],
                        filterTags: [
                            { type: 'c', text: 'All'},
                            { type: 'o', text: 'Hot'}
                        ],
                        pageList: [],
                        totalRow: 0,
                        totalPage: 0,
                        country: {}
                    }
                },
                computed: {
                    noMore() {
                        return this.pageList.length !== 0 && this.param.p >= this.totalPage
                    },
                    disabled() {
                        return this.noMore
                    },
                },
                mounted() {
                    this.getCategoryList()
                    this.getProductTagList()
                    this.getRouterUrl()
                },
                methods: {
                    getRouterUrl() {
                        const paramObj = $.Theme.Util.getParams(window.location.href)
                        Object.assign(this.param, paramObj)
                        this.getCountryById()
                    },
                    queryToTag(type, selectList, load) {
                        let curIndex = this.filterTags.findIndex(item => { return item.type === type });
                        let selectItem = selectList.filter(item => item.id === this.param[type])[0]
                        if(curIndex > -1) {
                            this.filterTags.splice(curIndex, 1)
                        }
                        if(type === 'price') {
                            if(!this.param.minPrice) {
                                this.param.minPrice = 0
                            }
                            if(!this.param.maxPrice) {
                                this.param.maxPrice = 9999
                            }
                            this.filterTags.push({
                                type: 'price',
                                text: `$${this.param.minPrice} - $${this.param.maxPrice}`
                            })
                        } else if(type === 'c') {
                            if(selectItem) {
                                this.filterTags.push({
                                    type: type,
                                    text: selectItem.name
                                })
                            }
                        } else if(this.param[type]) {
                            if(selectItem) {
                                this.filterTags.push({
                                    type: type,
                                    text: selectItem.name
                                })
                            }
                        }
                        if(load) {
                            this.param.p = 1
                            this.query()
                        }
                    },
                    cleanFilterTags (index, item){
                        if(item.type === 'price') {
                            this.param.minPrice = ''
                            this.param.maxPrice = ''
                        } else {
                            this.param[item.type] = ''
                        }
                        this.filterTags.splice(index, 1)
                        this.reload()
                    },
                    reload() {
                        this.pageList = []
                        this.param.p = 1
                        this.query()
                    },
                    // 搜索
                    query (){
                        $.Theme.Util.pushState(window.GLOBAL_CONFIG.mainUrl +'/productList', this.param)
                        $.get(window.GLOBAL_CONFIG.mainUrl +'/productIndex', this.param,(res) => {
                            if(this.param.p === 1){
                                this.pageList = []
                            }
                            res.list.forEach((item) => {
                                this.pageList.push(item)
                            })
                            this.totalRow = res.totalRow
                            this.totalPage = res.totalPage
                        }, 'json')
                    },
                    loadMore() {
                        if (this.param.p >= this.totalPage) {
                            return
                        }
                        this.param.p ++
                        this.query()
                    },
                    getCountryById() {
                        this.country = window.GLOBAL_CONFIG.sCountry
                        this.param.country = window.GLOBAL_CONFIG.sCountry.countryCode
                        this.query()
                    },
                    // 获取商品分类
                    getCategoryList() {
                        $.get(window.GLOBAL_CONFIG.mainUrl +'/product/getProductTypes', res => {
                            res.forEach(item => {
                                this.categoryList.push({
                                    id: item.id,
                                    name: item.productTypeName
                                })
                            })
                            this.queryToTag('c', this.categoryList, false)
                        }, 'json')
                    },
                    // 获取商品标签
                    getProductTagList() {
                        $.get(window.GLOBAL_CONFIG.mainUrl +'/product/getProductTagList', (res) => {
                            this.productTagList = res
                        }, 'json')
                    }
                }
            })
        };
        return obj;
    })()
});

