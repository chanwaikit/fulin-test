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
                            pageSize: 20,
                            minPrice: '',
                            maxPrice: '',
                            tag: '',
                            c: '',
                            o:'-sales',
                            k: '',
                            country: '',
                            cId: ''
                        },
                        countryFlag: '',
                        sort: '-sales',
                        sortList: [{'value': '-sales','text': 'Hot'},{'value': '-times','text': 'New To Old'},{'value': 'times','text': 'Old To New'},{'value': '-oldPrice','text': 'Price High To Low'},{'value': 'oldPrice','text': 'Price Low To High'}],
                        category: '',
                        categoryList: [],
                        productTagList: [],
                        filterTags: [
                            { type: 'c', text: 'All'},
                            { type: 'o', text: 'Hot'}
                        ],
                        pageList: [],
                        totalRow: 0,
                        totalPage: 0,
                        loading: false,
                        finished: true,
                        repeatLoading: false,
                        refreshing: false,
                        selected: '',
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
                    this.country = {
                        flag: this.$refs['countryFlag'].value,
                        code: this.$refs['countryCode'].value,
                        currency: this.$refs['countryCurrency'].value,
                    }
                    const paramObj = $.Theme.Util.getParams(window.location.href)
                    Object.assign(this.param, paramObj)
                    this.param.country = this.country.code

                    this.getCategoryList()
                    this.getProductTagList()
                    this.query()
                },
                methods: {
                    selectCategory(id) {
                        if (id) {
                            this.selected = id
                            this.param.c = id
                        } else {
                            this.selected = ''
                            this.param.c = ''
                        }
                    },
                    submitCategoryFilter() {
                        this.onRefresh()
                        this.$refs.item.toggle();
                    },
                    selectSort(val) {
                        this.param.o = val;
                        this.onRefresh();
                    },
                    // 搜索
                    query (){
                        $.Theme.Util.pushState(window.GLOBAL_CONFIG.mainUrl +'/productList', this.param)
                        $.get(window.GLOBAL_CONFIG.mainUrl +'/productIndex', this.param,(res) => {
                            if (this.refreshing){
                                this.pageList = res.list;
                            } else {
                                this.pageList =this.pageList.concat(res.list);
                            }
                            this.loading = false
                            this.repeatLoading = false
                            this.refreshing = false
                            this.finished = this.param.p >= res.totalPage
                        }, 'json')
                    },
                    onRefresh() {
                        // 清空列表数据
                        this.refreshing = true;
                        this.finished = true;
                        this.param.p = 1
                        this.query()
                    },
                    onLoad() {
                        if(!this.finished && !this.repeatLoading) {
                            this.param.p++
                            this.repeatLoading = true
                            this.query();
                        }
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

