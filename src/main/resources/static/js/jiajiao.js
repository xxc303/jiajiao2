/**
 * 提交评论
 */
function post() {
    var parentId = $("#teacher_id").val();
    var item = $("#item").val();
    var content = $("#comment_content").val();
    var overview = $("#overview").val();
    $.ajax({
        type: "post",
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": parentId,
            "item": item,
            "content": content,
            "overview": overview
        }),
        success: function (response) {
            console.log(response);
            if (response.code === 200) {
                window.location.reload();

            } else if (response.code === 500) {
                alert(response.message);
            } else (response.code == 501)
            {
                alert(response.message);
            }
        },
        dataType: "json"
    });
    console.log(parentId);
}

/**
 * 展开二级评论
 */
function collapseComments(e) {
    var commentId = e.getAttribute("data-id");
    var comments = $("#comment-" + commentId);

    //获取二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        //折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + commentId);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/reply/" + commentId, function (data) {
                $.each(data.data.reverse(), function (index, reply) {
                    var mediaBodyElement = $("<div/>").append($("<span/>",{
                        "class": "text_right",
                        "html": reply.username
                    })).append($("<span/>",{
                        "class":"pull-right",
                        "html":moment(reply.createTime).format('YYYY-MM-DD')
                    })).append($("<div/>",{
                        "class":"reply-content",
                        "html":reply.content
                    })).append($("<hr/>"));
                    var li = $("<li/>").append(mediaBodyElement);
                    var ul = $("<ul/>").append(li);
                    subCommentContainer.prepend(ul);
                });
                //展开二级评论
                comments.addClass("in");
                //标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }

}

/**
 * 提交回复
 */
function reply(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    $.ajax({
        type: "post",
        url: "/reply",
        contentType: "application/json",
        data: JSON.stringify({
            "commentId": commentId,
            "content": content
        }),
        success: function (response) {
            console.log(response);
            if (response.code === 200) {
                window.location.reload();

            } else if (response.code === 500) {
                alert(response.message);
            }
        },
        dataType: "json"
    });

}

/**
 * 预约功能
 */
function reserve() {
    var teacherId = $("#teacher_id").val();
    console.log(teacherId);
    $.ajax({
        type: "post",
        url: "/student/reserve",
        contentType: "application/json",
        data: JSON.stringify({
            "teacherId": teacherId,
        }),
        success: function (response) {
            console.log(response);
            if (response.code === 200) {
                window.location.reload();

            } else if (response.code === 500) {
                alert("请先登录！");
            }
        },
        dataType: "json"
    });
}

