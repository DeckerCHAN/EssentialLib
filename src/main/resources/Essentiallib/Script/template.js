//thin window fix
$(function ()
{
    var e = $("#sidebar > ul");
    var t = $("#sidebar > ul > li.open ul");
    $(window).on("resize", function ()
    {
        var wwidth = $(window).width();
        if (wwidth >= 768 && wwidth <= 991)
        {
            $("#sidebar > ul > li.open ul").attr("style", "").parent().removeClass("open");
            e.css({
                display: "block"
            });
        }
        if (wwidth <= 767)
        {
            $("#sidebar").niceScroll();
            $("#sidebar").getNiceScroll().resize();
            if ($(window).scrollTop() > 35)
            {
                $("body").addClass("fixed");
            }
            $(window).scroll(function ()
            {
                if ($(window).scrollTop() > 35)
                {
                    $("body").addClass("fixed");
                } else
                {
                    $("body").removeClass("fixed");
                }
            });
        }
        if (wwidth > 767)
        {
            e.css({
                display: "block"
            });
            $("body").removeClass("menu-open");
            $("#sidebar").attr("style", "");
            $("#user-nav > ul").css({
                width: "auto",
                margin: "0"
            });
        }
    });

    if ($(window).width() <= 767)
    {
        if ($(window).scrollTop() > 35)
        {
            $("body").addClass("fixed");
        }
        $(window).scroll(function ()
        {
            if ($(window).scrollTop() > 35)
            {
                $("body").addClass("fixed");
            } else
            {
                $("body").removeClass("fixed");
            }
        });
        $("#sidebar").niceScroll({
            zindex: "9999"
        });
        $("#sidebar").getNiceScroll().resize();
    }
    if ($(window).width() > 767)
    {
        e.css({
            display: "block"
        });
    }
    if ($(window).width() > 767 && $(window).width() < 992)
    {
        t.css({
            display: "none"
        });
    }
    $("#menu-trigger").on("click", function ()
    {
        if ($(window).width() <= 767)
        {
            if ($("body").hasClass("menu-open"))
            {
                $("body").removeClass("menu-open");
            } else
            {
                $("body").addClass("menu-open");
            }
        }
        return false;
    });
    $(".tip").tooltip();
    $(".tip-left").tooltip({
        placement: "left"
    });
    $(".tip-right").tooltip({
        placement: "right"
    });
    $(".tip-top").tooltip({
        placement: "top"
    });
    $(".tip-bottom").tooltip({
        placement: "bottom"
    });
    $("#style-switcher i").click(function ()
    {
        if ($(this).hasClass("open"))
        {
            $(this).parent().animate({
                right: "-=220"
            });
            $(this).removeClass("open");
        } else
        {
            $(this).parent().animate({
                right: "+=220"
            });
            $(this).addClass("open");
        }
        $(this).toggleClass("icon-arrow-left");
        $(this).toggleClass("icon-arrow-right");
    });
    $("#style-switcher a").click(function ()
    {
        var e = $(this).attr("href").replace("#", "");
        $(".skin-color").attr("href", "css/unicorn." + e + ".css");
        $(this).siblings("a").css({
            "border-color": "transparent"
        });
        $(this).css({
            "border-color": "#aaaaaa"
        });
    });
    $(document).on("click", ".submenu > a", function (e)
    {
        e.preventDefault();
        var t = $(this).siblings("ul");
        var n = $(this).parents("li");
        var r = $("#sidebar li.submenu ul");
        var i = $("#sidebar li.submenu");
        if (n.hasClass("open"))
        {
            if ($(window).width() > 976 || $(window).width() < 768)
            {
                t.slideUp();
            } else
            {
                t.fadeOut(150);
            }
            n.removeClass("open");
        } else
        {
            if ($(window).width() > 976 || $(window).width() < 768)
            {
                r.slideUp();
                t.slideDown();
            } else
            {
                r.fadeOut(150);
                t.fadeIn(150);
            }
            i.removeClass("open");
            n.addClass("open");
        }
        $("#sidebar").getNiceScroll().resize();
    });
    $("#sidebar li.submenu ul").on("mouseleave", function ()
    {
        if ($(window).width() >= 768 && $(window).width() < 977)
        {
            $(this).fadeOut(150).parent().removeClass("open");
        }
    });
    $(".go-full-screen").click(function ()
    {
        var backdrop = $(".white-backdrop");
        var wbox = $(this).parents(".widget-box");
        if (wbox.hasClass("widget-full-screen"))
        {
            backdrop.fadeIn(200, function ()
            {
                wbox.removeClass("widget-full-screen", function ()
                {
                    backdrop.fadeOut(200);
                });
            });
        } else
        {
            backdrop.fadeIn(200, function ()
            {
                wbox.addClass("widget-full-screen", function ()
                {
                    backdrop.fadeOut(200);
                });
            });
        }
    });
    $(function (e)
    {
        e("input[type=text], input[type=password], textarea").bind("focus blur", function ()
        {
            e(this).toggleClass("focus");
        });
    });
    var switcherBtn = $("#switcher-button");
    var switcherPanel = $("#switcher-inner");
    switcherBtn.click(function ()
    {
        if (switcherPanel.hasClass("open"))
        {
            switcherPanel.hide(300);
            switcherPanel.removeClass("open");
        } else
        {
            switcherPanel.show(300);
            switcherPanel.addClass("open");
        }
    });
    var color = $("body").data("color");
    $("#color-style a[data-color=" + color + "]").addClass("active");
    $("#color-style a").click(function ()
    {
        var e = $(this).attr("data-color");
        $(this).parent().find("a").removeClass("active");
        $(this).addClass("active");
        $("body").attr("data-color", e);
        return false;
    });
    if ($("body").hasClass("flat"))
    {
        $('#layout-type a[data-option="flat"]').addClass("active");
    } else
    {
        $('#layout-type a[data-option="old"]').addClass("active");
    }
    $("#layout-type a").click(function ()
    {
        var e = $(this).attr("data-option");
        if (e == "flat")
        {
            $("body").addClass("flat");
        } else
        {
            $("body").removeClass("flat");
        }
        $(this).parent().find("a").removeClass("active");
        $(this).addClass("active");
    });

    //set active tag
    $(".sidebar-child").click(function ()
    {
        $(".sidebar-child").each(function ()
        {
            $(this).removeClass("active");
        });
        $(this).addClass("active");
    });
    //prevent enter submit
    $(window).keydown(function(event){
        if(event.keyCode == 13) {
          event.preventDefault();
          return false;
        }
      });
});

