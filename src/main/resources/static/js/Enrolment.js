$(document).ready(function(){
	
	var btn=$('.submit_btn');
	var ipt=$(".ipt");
	ipt.focus(function(){
            btn.css("right","0px");
        });
        ipt.blur(function(){
           btn.css("right","50px");
        });
	var a1=$('.a1');
	a1.click(function (){
		$(".windows").css("display","block");
	})
	$('.cancel').click(function(){
		$(".windows").css("display","none");
	})
	
	
	
})
