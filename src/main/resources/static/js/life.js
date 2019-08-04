function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    //没有值为true
    if(!content){
        alert("回复内容不能为空...");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",

        contentType:'application/json',//重要步骤

        data: JSON.stringify({//重要步骤
            "parentId":questionId,
            "content":content,
            "type":1
        }),
        success: function (response) {
            if(response.code == 200){
                window.location.reload();
                //$("#comment_hide").hide()
            }else {
                if(response.code == 2003){
                    var isfix = confirm(response.message);
                    if(isfix){
                        window.open("https://github.com/login/oauth/authorize?client_id=eaca1a2763bc1ac3b0c4&redirect_uri=http://localhost:8080/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);//是否关闭网页,跳到index.xml
                    }
                }else {
                alert(response.message);
                }
            }
        },
        dataType: "json"
    })
    //console.log(questionId);
    //console.log(content);
}