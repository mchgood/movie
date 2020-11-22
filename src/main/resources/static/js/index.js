var indexPage={
    myChart:null,
    pieOption:function(nameList,dataList,titleText){
        return option = {
            title:{
                text:titleText,
                left:'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b}: {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                left: 10,
                data: nameList
            },
            series: [
                {
                    name: '',
                    type: 'pie',
                    radius: ['50%', '70%'],
                    avoidLabelOverlap: false,
                    label: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        label: {
                            show: true,
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    },
                    labelLine: {
                        show: false
                    },
                    data: dataList
                }
            ]
        };
    },
    barOption:function(nameList,valueList,titleText){
        return option = {
            title:{
                text:titleText
            },
            color: ['#3398DB'],
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: nameList,
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series: [
                {
                    name: titleText,
                    type: 'bar',
                    barWidth: '60%',
                    data: valueList
                }
            ]
        };
    },
    clickPieCountryBoxOffice:function(){
        var _this = this;
        $.ajax({
            //请求方式
            type : "GET",
            //请求的媒体类型
            contentType: "application/json;charset=UTF-8",
            //请求地址
            url : "/movie/pie?pieType=countryBoxOffice",
            //请求成功
            success : function(result) {
                var option = _this.pieOption(result.nameList,result.dataList,"国产片和外国片总票房单位（亿元）");
                _this.refresh(option);
            },
            //请求失败，包含具体的错误信息
            error : function(e){
                console.log(e.status);
                console.log(e.responseText);
            }
        });
    },
    clickPieTypeBoxOffice:function(){
        var _this = this;
        $.ajax({
            //请求方式
            type : "GET",
            //请求的媒体类型
            contentType: "application/json;charset=UTF-8",
            //请求地址
            url : "/movie/pie?pieType=typeBoxOffice",
            //请求成功
            success : function(result) {
                var option = _this.pieOption(result.nameList,result.dataList,"各类型总票房单位（亿元）");
                _this.refresh(option);
            },
            //请求失败，包含具体的错误信息
            error : function(e){
                console.log(e.status);
                console.log(e.responseText);
            }
        });
    },
    clickBarBoxOffice:function(){
        var _this = this;
        $.ajax({
            //请求方式
            type : "GET",
            //请求的媒体类型
            contentType: "application/json;charset=UTF-8",
            //请求地址
            url : "/movie/bar?barType=barBoxOffice",
            //请求成功
            success : function(result) {
                var option = _this.barOption(result.nameList,result.valueList,"年度总票房单位（亿元）");
                _this.refresh(option);

            },
            //请求失败，包含具体的错误信息
            error : function(e){
                console.log(e.status);
                console.log(e.responseText);
            }
        });
    },
    clickBarYearBoxOffice:function(){
        var _this = this;
        $.ajax({
            //请求方式
            type : "GET",
            //请求的媒体类型
            contentType: "application/json;charset=UTF-8",
            //请求地址
            url : "/movie/bar?barType=barYearBoxOffice",
            //请求成功
            success : function(result) {
                var movieNameList = result.movieNameList;
                var option = _this.barOption(result.nameList,result.valueList,"年度票房冠军单位（万元）");
                option.series[0]['label'] = {
                    normal:{
                        show: true,
                        position: 'top',
                        formatter: function(a) {
                            var dataIndex = a.dataIndex;
                            return movieNameList[dataIndex];
                        }
                    }
                }
                _this.refresh(option);
            },
            //请求失败，包含具体的错误信息
            error : function(e){
                console.log(e.status);
                console.log(e.responseText);
            }
        });
    },
    clickImg:function(){
        window.open("/other.html");
    },
    init:function () {
        this.myChart = echarts.init(document.getElementById('main'));
    },
    refresh:function (option) {
        this.myChart.clear();
        this.myChart.setOption(option);
    },
    creat:function () {
        this.init();
        this.clickBarBoxOffice();
    }
}