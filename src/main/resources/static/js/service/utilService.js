/**
 * Created by zm on 2018/2/5.
 */
define(['app'], function (app) {
    console.log('util service init...');

    app.service('utilService', function () {
        /**
         * 判断是否是1025-65535之间的正整数,
         */
        this.isPort = function (obj) {
            if (isNaN(obj)) {
                return false;
            } else {
                var regu = /^[0-9]*[1-9][0-9]*$/;
                var re = new RegExp(regu);
                if (re.test(obj)) {
                    if (obj >= 1025 && obj <= 65535) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
                ;
            }
        },
            /**
             * 校验IP
             */
            this.isIP = function (ip) {
                var re = /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/;
                if (re.test(ip)) {
                    return true;
                } else {
                    return false;
                }
            },
            /**
             * 校验RTSP流地址
             * */
            this.isregisterUrl = function (registerUrl) {
                var re = /^[rtsp]{4}:\/\/*[^\s]*$/;
                if (re.test(registerUrl)) {
                    return true;
                } else {
                    return false;
                }
            };

        /**
         * @param number
         * @returns {boolean}
         */
        // this.isNumber = function (number) {
        //     var reg = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
        //     if (reg.test(number)) {
        //         return true;
        //     } else {
        //         return false;
        //     }
        // }
        /**
         * ^[0-9][0-9]{0,}$
         */
        this.isPhotoNumber = function (number) {
            var reg = /^[1-9][0-9]*$/;
            if (reg.test(number)) {
                return true;
            } else {
                return false;
            }
        };

        this.isCameraPort = function (obj) {
            if (isNaN(obj)) {
                return false;
            } else {
                var regu = /^[0-9]*[1-9][0-9]*$/;
                var re = new RegExp(regu);
                if (re.test(obj)) {
                    if (obj > 0 && obj < 65535) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
                ;
            }
        }

        /**
         * 匹配是否含有中文汉字
         * */
        this.isHanZi = function (obj) {
            var reg = new RegExp("[\\u4E00-\\u9FFF]+", "g");
            if (reg.test(obj)) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * 获取各种类型网关数据的提取方法
         * @param data
         * @param type
         * @returns {Array}
         */
        this.filterGatewayData = function (data, type) {
            if (data) {
                var gatewayInfo = [];
                for (var i = 0; i < data.length; i++) {
                    if (type == data[i].type) {
                        switch (type) {
                            case'phonegateway':
                                var phoneGateway = {
                                    deviceId: data[i].id,
                                    phoneName: data[i].name,
                                    phoneSip: data[i].desc.sip
                                };
                                gatewayInfo.push(phoneGateway);
                                break;
                            case'vmgateway':
                                var VMGateWayInfo = {
                                    id: data[i].id,
                                    value: data[i].name
                                };
                                gatewayInfo.push(VMGateWayInfo);
                                break;
                            case'cvgateway':
                                var colonyGateway = {
                                    ids: data[i].id,
                                    sip: data[i].desc.sip,
                                    values: data[i].name
                                };
                                gatewayInfo.push(colonyGateway);
                                break;
                            case'sentryGateway':
                                var sentryGateways = {
                                    id: data[i].id,
                                    name: data[i].name,
                                    sentryGatewayOnlyId: data[i].desc.otherId
                                };
                                gatewayInfo.push(sentryGateways);
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
            return gatewayInfo;
        }

        /**
         * 判断是否是正整数
         * @param num
         * @returns {boolean}
         */
        this.isNumber = function (num) {
            var regu = /^[0-9][0-9]*$/;
            if (regu.test(num)) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * 匹配输入的是否是正确的邮编
         * */
        this.isPostcode = function (number) {
            var reg = /^[0-9][0-9]{5}$/;
            if (reg.test(number)) {
                return true;
            } else {
                return false;
            }
        }
    });
});
