function post() {/*onclick方法之回复*/
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    commentTarget(questionId,1,content);
}
/*封装*/
function commentTarget(targetId,type,content) {

    //没有值为true
    if(!content){
        alert("回复内容不能为空...");
        return;
    }

    $.ajax({//jquery
        type: "POST",
        url: "/comment",

        contentType:'application/json',//重要步骤

        data: JSON.stringify({//重要步骤
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success: function (response) {
            if(response.code == 200){
                window.location.reload();//刷新当前页面
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
function comment(e) {
    var commentId = e.getAttribute("data-id");//获取id
    var content = $("#input-"+commentId).val();

    console.log(content);
    commentTarget(commentId,2,content);
}



function collapseComments(e) {/*onclick之展开二级评论*/
   var id = e.getAttribute("data-id");//获取id
    var comments = $("#comment-"+id);
    //获取二级评论展开状态
    var sta = e.getAttribute("state");//获取data-collapse值
    if(sta){//如果不为空,说明已展开
        //折叠二级评论
        comments.removeClass("in");
        //删除展开状态
        e.removeAttribute("state");
        //删除高亮
        e.classList.remove("active");
    }else{//https://api.jquery.com/jQuery.getJSON/
        //拿到页面定义的元素
        var subCommentContainer = $("#comment-"+id);
        //判断是否有子元素
        if(subCommentContainer.children().length!=1) {
            //展开二级评论
            comments.addClass("in");
            //标记二级评论展开状态state为in
            e.setAttribute("state", "in");
            //高亮
            e.classList.add("active");
            //没有子元素就请求服务器端
        }else{
            $.getJSON( "/comment/"+id, function( data ) {
                $.each(data.data.reverse(), function( index,comment ) {//reverse(),颠倒数组中元素的顺序
                    //左
                    var mediaLeftElement = $("<div/>",{
                        "class":"media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.userModel.avatarUrl
                    }));
                    //右
                    var mediaBodyElement =  $("<div/>",{
                        "class":"media-left"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.userModel.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "theme",
                    }).append($("<span/>", {
                        "class": "theme",
                        "html":moment(comment.gmtCreate).format('YYYY-MM-DD HH:mm')
                    })));
                   // mediaLeftElement.append(avatarElement);c a n
                    //整体
                    var mediaElement = $("<div/>",{
                       "class":"media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>",{
                        "class":"col-lg-12 col-md-12 col-sm-12 col-xs-12 firstComment"//子标签
                    }).append(mediaElement);//append到最外层循环体上//在每个div元素的结尾添加内容

                    subCommentContainer.prepend(commentElement) ;//追加到父类元素的子元素上
                });
                //展开二级评论
                comments.addClass("in");
                //标记二级评论展开状态state为in
                e.setAttribute("state","in");
                //高亮
                e.classList.add("active");
            });
        }

    }
}
