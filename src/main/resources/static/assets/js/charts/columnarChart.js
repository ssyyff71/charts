/*----------------------柱状图-----------------------*/
//坐标轴刻度与标签对齐
(function () {

    var myChart = echarts.init(document.getElementById('bar1'));
    // 显示标题，图例和空的坐标轴
    myChart.setOption({
        title: {
            text: 'I2P路由版本号'
        },
        color: ['#3398DB'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        legend: {
            data: ['个数']
        },
        xAxis: {
            data: []
        },
        yAxis: {},
        series: [{
            name: ' 个数',
            type: 'bar',
            data: []
        }]
    });

    myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画

    var versions = [];    //类别数组（实际用来盛放X轴坐标值）
    var numbers = [];    //销量数组（实际用来盛放Y坐标值）

    $.ajax({
        type: "post",
        async: true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/version",
        data: {},
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            //请求成功时执行该函数内容，result即为服务器返回的json对象
            if (result) {
                for (var i = 0; i < result.length; i++) {
                    versions.push(result[i].version);    //挨个取出类别并填入类别数组
                }
                for (var i = 0; i < result.length; i++) {
                    numbers.push(result[i].count);    //挨个取出销量并填入销量数组
                }
                myChart.hideLoading();    //隐藏加载动画
                myChart.setOption({        //加载数据图表
                    xAxis: {
                        data: versions
                    },
                    series: [{
                        // 根据名字对应到相应的系列
                        name: '个数',
                        data: numbers
                    }]
                });

            }

        },
        error: function (errorMsg) {
            //请求失败时执行该函数
            alert("图表请求数据失败!");
            myChart.hideLoading();
        }
    })
})();


//数量级500的I2P路由个数排名前十国家
(function () {
    var countryChart = echarts.init(document.getElementById('country500'));
    // 显示标题，图例和空的坐标轴
    countryChart.setOption({
        title: {
            text: 'I2P路由个数排名前五国家'
        },
        tooltip: {
            feature: {
                saveAsImage: {}
            }
        },
        legend: {
            data: ['个数']
        },
        xAxis: {
            data: []
        },
        yAxis: {},
        series: [{
            name: ' 个数',
            type: 'bar',
            data: []
        }]
    });

    countryChart.showLoading();    //数据加载完之前先显示一段简单的loading动画

    var country = [];    //类别数组（实际用来盛放X轴坐标值）
    var n = [];    //销量数组（实际用来盛放Y坐标值）

    $.ajax({
        type: "post",
        async: true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/topCountryForDS500",
        data: {},
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            //请求成功时执行该函数内容，result即为服务器返回的json对象
            if (result) {
                for (var i = 0; i < result.length; i++) {
                    country.push(result[i].country);    //挨个取出类别并填入类别数组
                }
                for (var i = 0; i < result.length; i++) {
                    n.push(result[i].count);    //挨个取出销量并填入销量数组
                }
                countryChart.hideLoading();    //隐藏加载动画
                countryChart.setOption({        //加载数据图表
                    xAxis: {
                        data: country
                    },
                    series: [{
                        // 根据名字对应到相应的系列
                        name: '个数',
                        data: n
                    }]
                });

            }

        },
        error: function (errorMsg) {
            //请求失败时执行该函数
            alert("图表请求数据失败!");
            countryChart.hideLoading();
        }
    })
})();

//数量级1000的I2P路由个数排名前十国家
(function () {
    var countryChartFor1000 = echarts.init(document.getElementById('country1000'));
    // 显示标题，图例和空的坐标轴
    countryChartFor1000.setOption({
        title: {
            text: 'I2P路由个数排名前五国家'
        },
        tooltip: {
            feature: {
                saveAsImage: {}
            }
        },
        legend: {
            data: ['个数']
        },
        xAxis: {
            data: []
        },
        yAxis: {},
        series: [{
            name: ' 个数',
            type: 'bar',
            data: []
        }]
    });

    countryChartFor1000.showLoading();    //数据加载完之前先显示一段简单的loading动画

    var countryFor1000 = [];    //类别数组（实际用来盛放X轴坐标值）
    var countFor1000 = [];    //销量数组（实际用来盛放Y坐标值）

    $.ajax({
        type: "post",
        async: true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/topCountryForDS1000",
        data: {},
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            //请求成功时执行该函数内容，result即为服务器返回的json对象
            if (result) {
                for (var i = 0; i < result.length; i++) {
                    countryFor1000.push(result[i].country);    //挨个取出类别并填入类别数组
                }
                for (var i = 0; i < result.length; i++) {
                    countFor1000.push(result[i].count);    //挨个取出销量并填入销量数组
                }
                countryChartFor1000.hideLoading();    //隐藏加载动画
                countryChartFor1000.setOption({        //加载数据图表
                    xAxis: {
                        data: countryFor1000
                    },
                    series: [{
                        // 根据名字对应到相应的系列
                        name: '个数',
                        data: countFor1000
                    }]
                });

            }

        },
        error: function (errorMsg) {
            //请求失败时执行该函数
            alert("图表请求数据失败!");
            countryChartFor1000.hideLoading();
        }
    })
})();
//数量级5000的I2P路由个数排名前十国家
(function () {
    var countryChartFor5000 = echarts.init(document.getElementById('country5000'));
    // 显示标题，图例和空的坐标轴
    countryChartFor5000.setOption({
        title: {
            text: 'I2P路由个数排名前五国家'
        },
        tooltip: {
            feature: {
                saveAsImage: {}
            }
        },
        legend: {
            data: ['个数']
        },
        xAxis: {
            data: []
        },
        yAxis: {},
        series: [{
            name: ' 个数',
            type: 'bar',
            data: []
        }]
    });

    countryChartFor5000.showLoading();    //数据加载完之前先显示一段简单的loading动画

    var countryFor5000 = [];    //类别数组（实际用来盛放X轴坐标值）
    var countFor5000 = [];    //销量数组（实际用来盛放Y坐标值）

    $.ajax({
        type: "post",
        async: true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/topCountryForDS5000",
        data: {},
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            //请求成功时执行该函数内容，result即为服务器返回的json对象
            if (result) {
                for (var i = 0; i < result.length; i++) {
                    countryFor5000.push(result[i].country);    //挨个取出类别并填入类别数组
                }
                for (var i = 0; i < result.length; i++) {
                    countFor5000.push(result[i].count);    //挨个取出销量并填入销量数组
                }
                countryChartFor5000.hideLoading();    //隐藏加载动画
                countryChartFor5000.setOption({        //加载数据图表
                    xAxis: {
                        data: countryFor5000
                    },
                    series: [{
                        // 根据名字对应到相应的系列
                        name: '个数',
                        data: countFor5000
                    }]
                });

            }

        },
        error: function (errorMsg) {
            //请求失败时执行该函数
            alert("图表请求数据失败!");
            countryChartFor5000.hideLoading();
        }
    })
})();

//正负条形图
(function () {

    var columnar3 = echarts.init(document.getElementById("columnar3"));

    option = {

        title: {
            text: 'I2P路由个数前十排行'
        },
        color: ['#c29edb'],
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        toolbox: {
            feature: {
                saveAsImage: {}
            }
        },
        legend: {
            data: ['个数']
        },
        xAxis: {
            data: []
        },
        yAxis: {},
        series: [{
            name: ' 个数',
            type: 'bar',
            data: []
        }]
    };
    columnar3.showLoading();    //数据加载完之前先显示一段简单的loading动画

    var top_country = [];    //类别数组（实际用来盛放X轴坐标值）
    var top_number = [];    //销量数组（实际用来盛放Y坐标值）

    $.ajax({
        type: "post",
        async: true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url: "/topNumber",
        data: {},
        dataType: "json",        //返回数据形式为json
        success: function (result) {
            //请求成功时执行该函数内容，result即为服务器返回的json对象
            if (result) {
                for (var i = 0; i < result.length; i++) {
                    top_country.push(result[i].country);    //挨个取出类别并填入类别数组
                }
                for (var i = 0; i < result.length; i++) {
                    top_number.push(result[i].count);    //挨个取出销量并填入销量数组
                }
                columnar3.hideLoading();    //隐藏加载动画
                columnar3.setOption({        //加载数据图表
                    xAxis: {
                        data: top_country
                    },
                    series: [{
                        // 根据名字对应到相应的系列
                        name: '个数',
                        data: top_number
                    }]
                });

            }

        },
        error: function (errorMsg) {
            //请求失败时执行该函数
            alert("图表请求数据失败!");
            columnar3.hideLoading();
        }
    })

    columnar3.setOption(option);
})();


//折柱混合
(function () {

    var columnar4 = echarts.init(document.getElementById("columnar4"));


    option = {

        title: {
            text: "折柱混合",
            x: "left"
        },

        tooltip: {
            trigger: 'axis'
        },
        toolbox: {
            feature: {
                saveAsImage: {show: true}
            }
        },
        legend: {
            data: ['蒸发量', '降水量', '平均温度']
        },
        xAxis: [
            {
                type: 'category',
                data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '水量',
                min: 0,
                max: 250,
                interval: 50,
                axisLabel: {
                    formatter: '{value} ml'
                }
            },
            {
                type: 'value',
                name: '温度',
                min: 0,
                max: 25,
                interval: 5,
                axisLabel: {
                    formatter: '{value} °C'
                }
            }
        ],
        series: [
            {
                name: '蒸发量',
                type: 'bar',
                data: [2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
            },
            {
                name: '降水量',
                type: 'bar',
                data: [2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
            },
            {
                name: '平均温度',
                type: 'line',
                yAxisIndex: 1,
                data: [2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
            }
        ]
    };


    columnar4.setOption(option);
})();
