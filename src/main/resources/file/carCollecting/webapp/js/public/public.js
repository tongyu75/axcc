
//轮播图
var mySwiper = new Swiper('.swiper-container',{
        autoplay: 3000,   //自动轮播参数
        pagination: '.swiper-pagination',   //分页器class
        loop:true,   //无限循环
        grabCursor: true,    //鼠标放上时变成手的形状
        paginationClickable :true,   //点击分页切换图像
        autoplayDisableOnInteraction : false,//点击屏幕后仍然可以自动播放
        // 如果需要前进后退按钮
            nextButton: '.swiper-button-next',
            prevButton: '.swiper-button-prev'

    })