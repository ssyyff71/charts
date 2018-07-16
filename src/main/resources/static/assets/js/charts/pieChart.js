/*----------------------饼状图-----------------------*/
//环形图
(function () {
// 异步加载数据
    $.ajax({
        type: 'post',
        async: false, //同步执行
        url: '/country',
        data: {}, //无参数
        dataType: 'json', //返回数据形式为json
        success: function (result) {
            if (result) {
                //把result(即Json数据)以参数形式放入Echarts代码中
                bind(result);
            }
        },
        error: function (errorMsg) {
            alert("加载数据失败");
        }
    })

    //JS成功后的代码
    function bind(result) {
        // 基于准备好的dom，初始化echarts图表
        var TypeSalesChart = echarts.init(document.getElementById('pie1'));
        var option = {
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                x: 'left',
                data: (function () {
                    var res = [];
                    var len = result.length;
                    for (var i = 0, size = len; i < size; i++) {
                        res.push({
                            name: result[i].country,
                        });
                    }
                    return res;
                })()

            },
            series: [
                {
                    name: '访问来源',
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '60%'],
                    data: (function () {
                        var res = [];
                        var len = result.length;
                        for (var i = 0, size = len; i < size; i++) {
                            res.push({
                                //通过把result进行遍历循环来获取数据并放入Echarts中
                                name: result[i].country,
                                value: result[i].count
                            });
                        }
                        return res;
                    })()
                }
            ]
        };
        TypeSalesChart.setOption(option);
    }
})();


//嵌套环形图
(function () {

    var pie2 = echarts.init(document.getElementById("pie2"));

    option = {

        title: {
            text: "环形图",
            x: 'center'
        },

        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data: ['直达', '营销广告', '搜索引擎', '邮件营销', '联盟广告', '视频广告', '百度', '谷歌', '必应', '其他']
        },
        series: [
            {
                name: '访问来源',
                type: 'pie',
                selectedMode: 'single',
                radius: [0, '30%'],

                label: {
                    normal: {
                        position: 'inner'
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data: [
                    {value: 335, name: '直达', selected: true},
                    {value: 679, name: '营销广告'},
                    {value: 1548, name: '搜索引擎'}
                ]
            },
            {
                name: '访问来源',
                type: 'pie',
                radius: ['40%', '55%'],

                data: [
                    {value: 335, name: '直达'},
                    {value: 310, name: '邮件营销'},
                    {value: 234, name: '联盟广告'},
                    {value: 135, name: '视频广告'},
                    {value: 1048, name: '百度'},
                    {value: 251, name: '谷歌'},
                    {value: 147, name: '必应'},
                    {value: 102, name: '其他'}
                ]
            }
        ]
    };
    pie2.setOption(option);
})();


//饼状图
(function () {

    var pie3 = echarts.init(document.getElementById("pie3"));

    option = {
        title: {
            text: '某站点用户访问来源',
            x: 'center'
        },
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
        },
        series: [
            {
                name: '访问来源',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: [
                    {value: 335, name: '直接访问'},
                    {value: 310, name: '邮件营销'},
                    {value: 234, name: '联盟广告'},
                    {value: 135, name: '视频广告'},
                    {value: 1548, name: '搜索引擎'}
                ],
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    pie3.setOption(option);
})();


//南丁格尔玫瑰图
(function () {
    $.ajax({
        type: 'post',
        async: false, //同步执行
        url: '/country',
        data: {}, //无参数
        dataType: 'json', //返回数据形式为json
        success: function (result) {
            if (result) {
                //把result(即Json数据)以参数形式放入Echarts代码中
                bind(result);
            }
        },
        error: function (errorMsg) {
            alert("加载数据失败");
        }
    })

    function bind(result) {
        var pie4 = echarts.init(document.getElementById("pie4"));
        var option = {
            title: {
                text: '南丁格尔玫瑰图',
                x: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                x: 'center',
                y: 'bottom',
                data: (function () {
                    var res = [];
                    var len = result.length;
                    for (var i = 0, size = len; i < size; i++) {
                        res.push({
                            name: result[i].country,
                        });
                    }
                    return res;
                })()
            },
            toolbox: {
                show: true,
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    magicType: {
                        show: true,
                        type: ['pie', 'funnel']
                    },
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            calculable: true,
            series: [
                {
                    name: '半径模式',
                    type: 'pie',
                    radius: [20, 110],
                    center: ['50%', '50%'],
                    roseType: 'radius',
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    lableLine: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    data: (function () {
                        var res = [];
                        var len = result.length;
                        for (var i = 0, size = len; i < size; i++) {
                            res.push({
                                //通过把result进行遍历循环来获取数据并放入Echarts中
                                name: result[i].country,
                                value: result[i].count
                            });
                        }
                        return res;
                    })()
                }
            ]
        };
        pie4.setOption(option);
    }
})();