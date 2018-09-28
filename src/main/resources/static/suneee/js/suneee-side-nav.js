/**
 * suneee-side-nav.js
 * @author kenson 公司侧滑栏的js，
 * 需要用到侧滑栏的时候，在引入jquery之后，.
 * 再引入这个js就可以了，但是还有一点限制就是需要有
 * burger这个类的div来点击出现侧边栏，其实就是目前页面上左上角那个menu图标（这个待定，可以修改）
 * 
 */
(function (window, undefined) {
    "use strict";
    var suneeeSideNav = function (data) {
        this.create(data);
    };
    suneeeSideNav.prototype = {
        create: function () {
            function GetQueryString(name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                var r = window.location.search.substr(1).match(reg);
                if (r != null) return unescape(r[2]);
                return null;
            }
            if (GetQueryString("sessionId")) {
                console.log(GetQueryString("sessionId"));
                sessionStorage.__session = GetQueryString("sessionId");
            }
            // 侧滑栏的html字符串，
            var html = ['	<!--侧滑菜单-->',
                '	<aside style="z-index: 1001;left: 0px" class="side-nav sweep-nav primary-background-color color-tertiary-font-color" role="navigation">',
                '		<header class="header">',
                '			<div class="account-box" aria-label="Account" role="region">',
                '				<div class="info primary-background-color">',
                '					<div class="thumb" data-status="online">',
                '						<div class="avatar">',
                '							<div id="head-image" class="avatar-image" style="background-image:url(../image/avatar4.png);"></div>',
                '						</div>',
                '					</div>',
                '					<div class="data" id="suneee-side-nav-showuser">',
                '						<h4 id="userid_h4"></h4>',
                '					</div>',
                '				</div>',
                '			</div>',
                '			<div class="toolbar">',
                '			<div class="toolbar-wrapper">',
                '				<div class="toolbar-search">',
                '					<i class="toolbar-search__icon fa fa-search"></i>',
                '					<input type="text" id="search-key" name="toolbar-input" class="toolbar-search__input background-transparent-light color-content-background-color border-transparent-lighter" placeholder="搜索 (Ctrl+K)">',
                '					<i id="remove-key" class="toolbar-search__icon fa fa-times toolbar-search__icon--cancel"></i>',
                '				</div>',
                '			</div>',
                '		</div>',
                '		</header>',
                '		<div class="rooms-list" aria-label="频道" role="region">',
                '			<div class="wrapper" id="suneee-side-nav-showmenu">',
                '			</div>',
                '		</div>',
                '		<span class="arrow bottom"></span>',
                '	</aside>',
                '	<!--侧滑菜单 end-->'
            ].join("");
            // 加入到body里面
            $("body").append(html);
            $(document).ready(function () {
                var screenWidth = $(window).width() - 260; //获得当前网页可视宽度
                $("#page_con").width(screenWidth);
            });
            var click = 1;
            $(".burger").click(function () {
                click++;
                if (click % 2 == 1) {
                    $(".side-nav").animate({
                        left: 0
                    }, 200)
                    $(this).parent().animate({
                        "left": "260px"
                    }, 200)
                    $(this).parent().parent().animate({
                        left: 260,
                        width: $(".burger").parent().parent().width() - 260
                    }, 200)
                } else {

                    $(".burger").parent().animate({
                        "left": "0"
                    }, 200)
                    $(".burger").parent().parent().animate({
                        left: 0,
                        width: "100%"
                    }, 200)
                    $(".side-nav").animate({
                        left: "-260px"
                    }, 200)
                }
            });
            $.ajax({
                type: "get",
                url: httpurl + "/businessCenter/login?sessionId=" + GetQueryString("sessionId"),
                dataType: "json",
                success: function (response) {
                    if (response.returnCode == 1) {
                        document.getElementById('suneee-side-nav-showuser').innerHTML = '<h4>' + response.data[0].name + '</h4>'; //显示名称
                        if (response.data[0].photo) {
                            document.getElementById("head-image").style.backgroundImage = "url(" + response.data[0].photo + ")"; //显示头像
                        }
                    }
                }
            });
        },
        init: function (data) {
            // 测试数据，侧滑菜单按照请求得到的数据来渲染,如果不需要服务器的菜单，也可以在下面的obj里面自己配置
            var ul = $("<ul>")
            data.forEach(function (e) {
                var html = ['			<li class="background-transparent-darker-hover">',
                    '						<a class="open-room" style ="cursor:pointer" url="' + e.url + '" title="' + e.title + '">',
                    '							<i class="icon-outbound" aria-label=""></i>',
                    '							<span class="name"><i class="fa ' + e.iconClass + '"></i> &nbsp ' + e.title + '</span>',
                    '						</a>',
                    '					</li>'
                ].join("");
                ul.append(html);
            });
            $("#suneee-side-nav-showmenu").append(ul);
            $(".open-room").on("click", function () {
                var str=$(this).attr("url");
                if (str.indexOf("?erp=true")>0) {
                    var url=str.replace("?erp=true","");
                    location.href ="business_center_erp.html?url=" +url + "&sessionId=" + GetQueryString("sessionId")+ "&titlename=" + $(this).attr("title")+ "&name=" + GetQueryString("name");
                } else {
                    var ss=$(this).attr("url").split("?")
                    location.href = $(this).attr("url") + "?sessionId=" + GetQueryString("sessionId");
                }
            })
        },
        // 事件框的处理，集中在这里好管理
        initLoading: function (sendData) {
            sendData.pageNo = 1;
            // 获取操作
            $.ajax({
                type: "get",
                url: httpurl + "/system/qryOperationLog",
                data: sendData,
                dataType: "json",
                success: function (response) {
                    if (response.returnCode == 1)
                        czinit(response);
                }
            });

            function czinit(response) {
                var curreDate = new Date();
                response.data[0].forEach(function (element, index) {
                    // if(index>1)return;
                    var newDate = new Date(element.operateTime);
                    var date3 = curreDate.getTime() - newDate.getTime(); //时间差的毫秒数  
                    //计算出相差天数  
                    var days = Math.floor(date3 / (24 * 3600 * 1000))
                    //计算出小时数  
                    var leave1 = date3 % (24 * 3600 * 1000) //计算天数后剩余的毫秒数  
                    var hours = Math.floor(leave1 / (3600 * 1000))
                    //计算相差分钟数  
                    var leave2 = leave1 % (3600 * 1000) //计算小时数后剩余的毫秒数  
                    var minutes = Math.floor(leave2 / (60 * 1000))
                    //计算相差秒数  
                    var leave3 = leave2 % (60 * 1000) //计算分钟数后剩余的毫秒数  
                    var seconds = Math.round(leave3 / 1000)

                    var xctime;
                    if (days > 0) {
                        xctime = days + "天"
                    } else if (hours > 0) {
                        xctime = hours + "小时"
                    } else if (minutes > 0) {
                        xctime = minutes + "分"
                    } else {
                        xctime = seconds + "秒"
                    }
                    var html = ['<li class="row">',
                        '					<div class="log_time col-xs-4">',
                        '						<i class="fa fa-file-text fa-fw"></i>',
                        '						<h5>' + newDate.toLocaleDateString() + '</h5>',
                        '						<span>' + xctime + '前</span>',
                        '					</div>',
                        '					<div class="event_con col-xs-8">',
                        '						<h5>' + element.operatorAccount + '</h5>',
                        '						<p>' + element.operateContent + '</p>',
                        '					</div>',
                        '				</li>'
                    ].join("");
                    $(".cz_container").append(html);

                }, this);



            }

            var nScrollHight = 0; //滚动距离总长(注意不是滚动条的长度)
            var nScrollTop = 0; //滚动到的当前位置
            var nDivHight = $(".cz_container").height();
            var isloading = false;
            var isEnd = false;
            $(".cz_container").scroll(function () {
                if (isloading == true || isEnd == true) return;
                nScrollHight = $(this)[0].scrollHeight;
                nScrollTop = $(this)[0].scrollTop;
                if (nScrollTop + nDivHight >= nScrollHight - 80) {
                    sendData.pageNo++;
                    isloading = true;
                    $.ajax({
                        type: "get",
                        url: httpurl + "/system/qryOperationLog",
                        data: sendData,
                        dataType: "json",
                        success: function (response) {
                            if (response.returnCode == 1) {
                                console.log(response.data[0].length);
                                if (response.data[0].length) {
                                    czinit(response);
                                } else {
                                    isEnd = true;
                                }
                            }
                            isloading = false;
                        }
                    });
                }
            });
        },
    };
    window.suneeeSideNav = suneeeSideNav;
})(window);