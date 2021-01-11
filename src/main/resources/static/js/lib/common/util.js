/**
 * Created by Administrator on 2016/9/20 0020.
 */
define(function () {
    /**
     * 扩展ie8下数组的forEach功能
     */
    Array.prototype.forEach = Array.prototype.forEach ? Array.prototype.forEach : function (fn) {
        var isFunc = typeof fn === 'function' ? true : false;
        if (!isFunc) {
            return this;
        }
        //开始 action
        for (var i = 0, j = this.length; i < j; i++) {
            if (this[i]) {
                fn.call(this, this[i], i);
            }
        }
        return this;
    }
    /**
     * 扩展数组的排序（只根据元素的type 来进行排序），应该使用一个委托来处理，根据客户指定的条件来进行排序，（先这样实现，以后再优化）
     * @param field {string}一个字符串，代表需要排序的字段
     * @param arg {array}数组或者字符串，目前只支持字符串数组，
     */
    Array.prototype.ascendingBy = function (field, arg) {
        var isString = typeof field === 'string' ? true : false,
            isArray = arg instanceof Array ? true : false;
        if (!isString || !isArray) {
            return this;
        }
        var tmp = [];
        for (var i = 0, j = arg.length; i < j; i++) {
            var filter = arg[i];
            //此处是为处理ie8 数组length 会多1而造成filter 为空的情况，以及filter不是字符串
            if (!filter || !typeof filter === 'string') {
                continue;
            }
            for (var k = this.length - 1; k >= 0; k--) {
                var item = this[k];
                //此处是为处理ie8 数组length 会多1而造成filter 为空的情况
                if (!item) {
                    continue;
                }
                if (item[field] && item[field] === filter) {
                    tmp.push(item);
                    this.splice(k, 1);
                }
            }
        }
        //如果给出的排序条件类型，不包括所有的类型，则把this中剩下的数据项，加入到tmp数组中
        if (this.length !== 0) {
            tmp = tmp.concat(this);
        }
        return tmp;
    }
    /**
     * 扩展ie8下数组的indexOf功能
     */
    Array.prototype.indexOf = Array.prototype.indexOf ? Array.prototype.indexOf : function (val) {
        //此处查找算法可优化
        for (var i = 0; i < this.length; i++) {
            if (this[i] == val) return i;
        }
        return -1;
    }
    /**
     * 扩展ie8下数组的remove功能
     */
    Array.prototype.remove = Array.prototype.remove ? Array.prototype.remove : function (obj) {
        for (var i = 0; i < this.length; i++) {
            var temp = this[i];
            if (!isNaN(obj)) {
                temp = i;
            }
            if (temp == obj) {
                for (var j = i; j < this.length; j++) {
                    this[j] = this[j + 1];
                }
                this.length = this.length - 1;
            }
        }
    }
    /**
     *根据指定条件，删除数组元素
     * @param fn
     * @returns {array}
     */
    Array.prototype.removeBy = function (fn) {
        var isFunc = typeof fn === 'function' ? true : false;
        if (!isFunc) {
            return this;
        }
        //此处查找算法可优化
        for (var i = this.length; i > 0; i--) {
            if (this[i]) {
                //如果找到了 则返回结果
                if (fn.call(this, item, i)) {
                    this.splice(i, 1);
                    break;
                }
            }
        }
        return this;
    }
    /**
     * 返回满足指定条件的元素
     * @param fn {function} predicate 函数
     */
    Array.prototype.getItem = function (fn) {
        var isFunc = typeof fn === 'function' ? true : false;
        if (!isFunc) {
            return;
        }
        //此处查找算法可优化
        var rslt = null, item;
        for (var i = 0, j = this.length; i < j; i++) {
            if (item = this[i]) {
                //如果找到了 则返回结果
                if (fn.call(this, item, i)) {
                    rslt = item;
                    break;
                }
            }
        }
        return rslt;
    }
    /**
     * 查找指定的对象是否 在数组中
     * @param obj 要测试的对象
     * @returns {boolean} true  表示存在，false表示不存在
     */
    Array.prototype.exist = function (obj) {
        var item;
        for (var i = 0, j = this.length; i < j; i++) {
            item = this[i]
            if (item && item === obj) {
                return true;
            }
        }
        return false;
    }
    /**
     * 过滤
     * @param fn
     */
    Array.prototype.filter = Array.prototype.filter ? Array.prototype.filter : function (fn) {
        var isFunc = typeof fn === 'function' ? true : false;
        if (!isFunc) {
            return;
        }
        var rslt = [], item;
        for (var i = 0, j = this.length; i < j; i++) {
            item = this[i];
            if (item) {
                //如果找到了 则返回结果
                if (fn.call(this, item, i)) {
                    rslt.push(item);
                }
            }
        }
        return rslt;
    }
    /**
     * 根据条件过滤完 生成字典
     * @param field 根据指定的field 生成字典
     * @param fn 过滤条件猥琐
     * @returns {object} 字典
     */
    Array.prototype.filterDic = function (field, fn) {
        var isFunc = typeof fn === 'function' ? true : false;
        if (!isFunc) {
            return;
        }
        var rslt = {}, item;
        for (var i = 0, j = this.length; i < j; i++) {
            item = this[i];
            if (item) {
                if (fn.call(this, item, i)) {
                    rslt[item[field]] = item;
                }
            }
        }
        return rslt;
    }

    Date.prototype.Format = function (fmt) { //author: meizz
        if (!fmt) {
            fmt = "yyyy-MM-dd hh:mm:ss";
        }
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }


})