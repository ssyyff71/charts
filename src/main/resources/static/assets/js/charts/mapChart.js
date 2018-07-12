(function(){
    $.getJSON('https://data.jianshukeji.com/jsonp?filename=json/world-population-density.json&callback=?', function (data) {
        // Add lower case codes to the data set for inclusion in the tooltip.pointFormat
        Highcharts.each(data, function (d) {
            d.flag = d.code.replace('UK', 'GB').toLowerCase();
        });
        // Initiate the chart
        Highcharts.mapChart('container', {
            title: {
                text: 'Fixed tooltip with HTML'
            },
            legend: {
                title: {
                    text: 'Population density per km²',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.textColor) || 'black'
                    }
                }
            },
            mapNavigation: {
                enabled: true,
                buttonOptions: {
                    verticalAlign: 'bottom'
                }
            },
            tooltip: {
                backgroundColor: 'none',
                borderWidth: 0,
                shadow: false,
                useHTML: true,
                padding: 0,
                pointFormat: '<span class="f32"><span class="flag {point.flag}"></span></span>'
                + ' {point.name}: <b>{point.value}</b>/km²',
                positioner: function () {
                    return { x: 0, y: 250 };
                }
            },
            colorAxis: {
                min: 1,
                max: 1000,
                type: 'logarithmic'
            },
            series : [{
                data : data,
                mapData: Highcharts.maps['custom/world'],
                joinBy: ['iso-a2', 'code'],
                name: 'Population density',
                states: {
                    hover: {
                        color: '#BADA55'
                    }
                }
            }]
        });
    });
})();