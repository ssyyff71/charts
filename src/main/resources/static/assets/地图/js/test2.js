(
    $(function(){   
      addEvent('map_1');addEvent('map_2');addEvent('map_3');addEvent('map_4');addEvent('map_5');addEvent('map_6');addEvent('map_7');addEvent('map_8');addEvent('map_9');addEvent('map_10');addEvent('map_11');addEvent('map_12');addEvent('map_13');addEvent('map_14');addEvent('map_15');addEvent('map_16');addEvent('map_17');addEvent('map_18');addEvent('map_19');addEvent('map_20');addEvent('map_21');addEvent('map_22');addEvent('map_23');addEvent('map_24');addEvent('map_25');addEvent('map_26');addEvent('map_27');addEvent('map_28');addEvent('map_29');addEvent('map_30');addEvent('map_31');addEvent('map_32');addEvent('map_33');addEvent('map_34');addEvent('map_35');addEvent('map_36');addEvent('map_37');addEvent('map_38');addEvent('map_39');addEvent('map_40');addEvent('map_41');addEvent('map_42');addEvent('map_43');addEvent('map_44');addEvent('map_45');addEvent('map_46');addEvent('map_47');addEvent('map_48');addEvent('map_49');addEvent('map_50');addEvent('map_51');addEvent('map_52');addEvent('map_53');addEvent('map_54');addEvent('map_55');addEvent('map_56');addEvent('map_57');addEvent('map_58');addEvent('map_59');addEvent('map_60');addEvent('map_61');addEvent('map_62');addEvent('map_63');addEvent('map_64');addEvent('map_65');addEvent('map_66');addEvent('map_67');addEvent('map_68');addEvent('map_69');addEvent('map_70');addEvent('map_71');addEvent('map_72');addEvent('map_73');addEvent('map_74');addEvent('map_75');addEvent('map_76');addEvent('map_77');addEvent('map_78');addEvent('map_79');addEvent('map_80');addEvent('map_81');addEvent('map_82');addEvent('map_83');addEvent('map_84');addEvent('map_85');addEvent('map_86');addEvent('map_87');addEvent('map_88');addEvent('map_89');addEvent('map_90');addEvent('map_91');addEvent('map_92');addEvent('map_93');addEvent('map_94');addEvent('map_95');addEvent('map_96');addEvent('map_97');addEvent('map_98');addEvent('map_99');addEvent('map_100');addEvent('map_101');addEvent('map_102');addEvent('map_103');addEvent('map_104');addEvent('map_105');addEvent('map_106');addEvent('map_107');addEvent('map_108');addEvent('map_109');addEvent('map_110');addEvent('map_111');addEvent('map_112');addEvent('map_113');addEvent('map_114');addEvent('map_115');addEvent('map_116');addEvent('map_117');addEvent('map_118');addEvent('map_119');addEvent('map_120');addEvent('map_121');addEvent('map_122');addEvent('map_123');addEvent('map_124');addEvent('map_125');addEvent('map_126');addEvent('map_127');addEvent('map_128');addEvent('map_129');addEvent('map_130');addEvent('map_131');addEvent('map_132');addEvent('map_133');addEvent('map_134');addEvent('map_135');addEvent('map_136');addEvent('map_137');addEvent('map_138');addEvent('map_139');addEvent('map_140');addEvent('map_141');addEvent('map_142');addEvent('map_143');addEvent('map_144');addEvent('map_145');addEvent('map_146');addEvent('map_147');addEvent('map_148');addEvent('map_149');addEvent('map_150');addEvent('map_151');addEvent('map_152');addEvent('map_153');addEvent('map_154');addEvent('map_155');addEvent('map_156');addEvent('map_157');addEvent('map_158');addEvent('map_159');addEvent('map_160');addEvent('map_161');addEvent('map_162');addEvent('map_163');addEvent('map_164');addEvent('map_165');addEvent('map_166');addEvent('map_167');addEvent('map_168');addEvent('map_169');addEvent('map_170');addEvent('map_171');addEvent('map_172');addEvent('map_173');addEvent('map_174');addEvent('map_175');addEvent('map_176');addEvent('map_177');addEvent('map_178');addEvent('map_179');addEvent('map_180');


      $(document).hover(
            function(e){
                var location=$(e.target).attr("id");
                var country = [];    //类别数组（实际用来盛放X轴坐标值）
                var number = [];    //销量数组（实际用来盛放Y坐标值）
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
                            location.hideLoading();    //隐藏加载动画
                            location.setOption({        //加载数据图表
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
                        location.hideLoading();
                    }
                })
                 
            });




  })
    


)