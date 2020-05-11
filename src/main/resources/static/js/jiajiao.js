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
    $("#article_id").val(id);
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
function updateTeaArticle() {
    var id = $("#article_id").val();
    var title = $("#article_title").val();
    var content = $("#article_content").val();
    $.ajax({
        type: "post",
        url: "/teacher/updateArticle",
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

function findTeacherById(e) {
    var id = e.getAttribute("data-id");
    console.log(id);
    $.getJSON("/admin/findTeacherById/"+id, function (data) {
        console.log(data);
        $("#modal_tea_id").val(id);
        $("#modal_tea_name").val(data.username);
        $("#modal_tea_phone").val(data.phone);
        $("#modal_tea_age").val(data.age);
        $("#modal_tea_price").val(data.price);
        $("#modal_tea_year").val(data.teachYear);
        $("#modal_tea_add").val(data.address);
        $("#modal_tea_title").val(data.title);
        $("#modal_tea_area").val(data.area);
        $("#modal_tea_item").val(data.item);
        $("#modal_tea_desc").val(data.description);
    });
}

function updateTeacher() {
        var id = $("#modal_tea_id").val();
        var username = $("#modal_tea_name").val();
        var phone= $("#modal_tea_phone").val();
        var age=$("#modal_tea_age").val();
        var price= $("#modal_tea_price").val();
        var teachYear= $("#modal_tea_year").val();
        var address= $("#modal_tea_add").val();
        var title=$("#modal_tea_title").val();
        var area= $("#modal_tea_area").val();
        var item= $("#modal_tea_item").val();
        var description=$("#modal_tea_desc").val();
    $.ajax({
        type: "post",
        url: "/admin/updateTeacher",
        contentType: "application/json",
        data: JSON.stringify({
            "id":id,
            "username" :username,
            "phone":phone,
            "age":age,
            "price":price,
            "teachYear":teachYear,
            "address":address,
            "title":title,
            "area":area,
            "item":item,
            "description":description,
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

function findStudentById(e) {
    var id = e.getAttribute("data-id");
    console.log(id);
    $.getJSON("/admin/findStudentById/"+id, function (data) {
        console.log(data);
        $("#modal_stu_id").val(id);
        $("#modal_stu_name").val(data.username);
        $("#modal_stu_phone").val(data.phone);
        $("#modal_stu_sex").val(data.sex);
        $("#modal_stu_price").val(data.price);
        $("#modal_stu_grade").val(data.grade);
        $("#modal_stu_area").val(data.area);
        $("#modal_stu_add").val(data.address);
        $("#modal_stu_school").val(data.school);
        $("#modal_stu_score").val(data.score);
        $("#modal_stu_idRequirement").val(data.idRequirement);
        $("#modal_stu_item").val(data.item);
        $("#modal_stu_desc").val(data.detailRequirement);
    });
}

function updateStudent() {

    var id = $("#modal_stu_id").val();
    var username = $("#modal_stu_name").val();
    var phone = $("#modal_stu_phone").val();
    var sex = $("#modal_stu_sex").val();
    var price = $("#modal_stu_price").val();
    var grade = $("#modal_stu_grade").val();
    var area = $("#modal_stu_area").val();
    var address = $("#modal_stu_add").val();
    var school = $("#modal_stu_school").val();
    var score = $("#modal_stu_score").val();
    var idRequirement = $("#modal_stu_idRequirement").val();
    var item = $("#modal_stu_item").val();
    var detailRequirement= $("#modal_stu_desc").val();
    $.ajax({
        type: "post",
        url: "/admin/updateStudent",
        contentType: "application/json",
        data: JSON.stringify({
            "id":id,
            "username" :username,
            "phone":phone,
            "sex":sex,
            "price":price,
            "grade":grade,
            "address":address,
            "school":school,
            "area":area,
            "score":score,
            "idRequirement":idRequirement,
            "item":item,
            "detailRequirement":detailRequirement
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

