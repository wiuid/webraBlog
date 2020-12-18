function costomAjax(url,form,callback){
    $.ajax({
        url: url,
        type: 'post',
        contentType: false,
        processData: false,
        dataType: "json",
        async:false,
        data: form,
        success: function (data){
            if (data.code === 200){
                toastr.success(data.mes)
                callback();
            }else {
                toastr.error(data.mes)
            }
        },
        error: function (){
            toastr.error("后端异常")
        }
    })
}
