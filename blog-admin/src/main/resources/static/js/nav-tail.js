function heightRsize(){
    if($(window).height() > height){
        $(".footer").css({"position":"absolute","width":"100%","bottom":"0"})
    }else{
        $(".footer").removeAttr("style");
    }
}
