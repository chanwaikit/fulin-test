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
                        guideIndex: 0
                    }
                },
                mounted() {
                },
                methods: {
                    handleScrollView(index) {
                        this.guideIndex = index
                        const title = document.getElementsByClassName('contentBoxWrap__textTitle')
                        title[index].scrollIntoView({behavior: "smooth"})
                    }
                }
            })
        };
        return obj;
    })()
});

