$(".submit_btn").click(function () {
    var userName = $(".login_txtbx").val();
    var password = $(".login_pass").val();
    $.ajax({
        type: "POST",
        url: "/login",
        data: {
            "userName": userName,
            "password": password
        },
        async: false,
        success: function (data) {
            if (data.msg == "登录成功") {
                location = "/index";
            }
        },
        error: function (data) {
            alert(data)
        }
    });
});
// $(".submit_btn").keypress(function(event) {
//     if (event.which == 13) {
//         var password = $(".login_pass").val();
//         if ($.isEmpty(password)) {
//             $(".login_pass").focus();
//         } else {
//             login();
//         }
//     }
// });
function submitFun(){
    var username = $('.login_txtbx').val().trim();
    var password = $('.login_pass').val().trim();
    if(username==""||password==""){
        // $('.loginTi').show();
        // $('.new-tan-dele').html('用户名和密码不能为空！');
        alert('2i3h13');
        return false;
    }else{
        $('.inTxt input').blur();   //防止手机键盘不自动收回去
        $("#form").submit();
        // location = "/index";
    }
}
