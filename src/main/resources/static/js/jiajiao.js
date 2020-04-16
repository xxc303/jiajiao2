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
                window.location.href = "http://localhost:8081";
            } else (response.code == 501)
            {
                alert(response.message);
                window.location.href = "http://localhost:8081";
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
    if (!content){
        alert("回复内容不能为空！")
    }
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
               var isAccepted = confirm(response.message);
               if (isAccepted){
                   window.open("http://localhost:8081");
                   window.localStorage.setItem("closable",true);
               }
            }
        },
        dataType: "json"
    });

}

/**
 * 预约功能
 */
function reserve() {
    var studentName = $("#modal_student_name").text();
    var price = $("#modal_price").text();
    var item = $("#modal_item").text();
    var teacherName = $("#modal_teacher_name").val();
    var teacherPhone = $("#modal_teacher_phone").val();
    var studentPhone = $("#modal_student_phone").val();
    var address = $("#modal_address").val();
    $.ajax({
        type: "post",
        url: "/student/reserve",
        contentType: "application/json",
        data: JSON.stringify({
            "studentName": studentName,
            "price": price,
            "item": item,
            "teacherName": teacherName,
            "teacherPhone": teacherPhone,
            "studentPhone": studentPhone,
            "address": address
        }),
        success: function (response) {
            console.log(response);
            if (response.code === 200) {
                window.location.reload();
                alert("预约成功！")

            } else if (response.code === 500) {
                alert(response.message);
            }
        },
        dataType: "json"
    });
}

function teaReserve() {
    var teacherName = $("#modal_teacher_name").text();
    var price = $("#modal_teacher_price").text();
    var item = $("#modal_item").val();
    var studentName = $("#modal_student_name").val();
    var teacherPhone = $("#modal_teacher_phone").val();
    var studentPhone = $("#modal_student_phone").val();
    var address = $("#modal_address").val();
    $.ajax({
        type: "post",
        url: "/teacher/teaReserve",
        contentType: "application/json",
        data: JSON.stringify({
            "teacherName": teacherName,
            "price": price,
            "item": item,
            "studentName": studentName,
            "teacherPhone": teacherPhone,
            "studentPhone": studentPhone,
            "address": address
        }),
        success: function (response) {
            console.log(response);
            if (response.code === 200) {
                window.location.reload();
                alert("预约成功！");
            } else if (response.code === 500) {
                alert(response.message);
            }
        },
        dataType: "json"
    });
}

function queryArticleById(title,content,id) {
    console.log(id);
    console.log(title);
    console.log(content);
    $("#article_id").val(id)
    $("#article_title").val(title);
    $("#article_content").val(content);
}

function updateArticle() {
    var id = $("#article_id").val();
    var title = $("#article_title").val();
    var content = $("#article_content").val();
    console.log(id);
    console.log(title);
    console.log(content);
    $.ajax({
        type: "post",
        url: "/admin/updateArticle",
        contentType: "application/json",
        data: JSON.stringify({
            "id": id,
            "title": title,
            "content": content
        }),
        success: function (response) {
            console.log(response);
            if (response.code === 200) {
                window.location.reload();
                alert("修改成功！");
            } else if (response.code === 501) {
                alert(response.message);
            }
        },
        dataType: "json"
    });

}

