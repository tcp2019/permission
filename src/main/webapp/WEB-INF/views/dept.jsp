<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>部门管理</title>
    <jsp:include page="/common/backend_common.jsp"/>
    <jsp:include page="/common/page.jsp"/>
</head>
<body class="no-skin" youdao="bind" style="background: white">
<input id="gritter-light" checked="" type="checkbox" class="ace ace-switch ace-switch-5"/>

<div class="page-header">
    <h1>
        用户管理
        <small>
            <i class="ace-icon fa fa-angle-double-right"></i>
            维护部门与用户关系
        </small>
    </h1>
</div>
<div class="main-content-inner">
    <div class="col-sm-3">
        <div class="table-header">
            部门列表&nbsp;&nbsp;
            <a class="green" href="#">
                <i class="ace-icon fa fa-plus-circle orange bigger-130 dept-add"></i>
            </a>
        </div>
        <div id="deptList">
        </div>
    </div>
    <div class="col-sm-9">
        <div class="col-xs-12">
            <div class="table-header">
                用户列表&nbsp;&nbsp;
                <a class="green" href="#">
                    <i class="ace-icon fa fa-plus-circle orange bigger-130 user-add"></i>
                </a>
            </div>
            <div>
                <div id="dynamic-table_wrapper" class="dataTables_wrapper form-inline no-footer">
                    <div class="row">
                        <div class="col-xs-6">
                            <div class="dataTables_length" id="dynamic-table_length"><label>
                                展示
                                <select id="pageSize" name="dynamic-table_length" aria-controls="dynamic-table"
                                        class="form-control input-sm">
                                    <option value="10">10</option>
                                    <option value="25">25</option>
                                    <option value="50">50</option>
                                    <option value="100">100</option>
                                </select> 条记录 </label>
                            </div>
                        </div>
                    </div>
                    <table id="dynamic-table" class="table table-striped table-bordered table-hover dataTable no-footer"
                           role="grid"
                           aria-describedby="dynamic-table_info" style="font-size:14px">
                        <thead>
                        <tr role="row">
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                姓名
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                所属部门
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                邮箱
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                电话
                            </th>
                            <th tabindex="0" aria-controls="dynamic-table" rowspan="1" colspan="1">
                                状态
                            </th>
                            <th class="sorting_disabled" rowspan="1" colspan="1" aria-label=""></th>
                        </tr>
                        </thead>
                        <tbody id="userList"></tbody>
                    </table>
                    <div class="row" id="userPage">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="dialog-dept-form" style="display: none;">
    <form id="deptForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">上级部门</label></td>
                <td>
                    <select id="parentId" name="parentId" data-placeholder="选择部门" style="width: 200px;"></select>
                    <input type="hidden" name="id" id="deptId"/>
                </td>
            </tr>
            <tr>
                <td><label for="deptName">名称</label></td>
                <td><input type="text" name="name" id="deptName" value="" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td><label for="deptSeq">顺序</label></td>
                <td><input type="text" name="seq" id="deptSeq" value="1" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td><label for="deptRemark">备注</label></td>
                <td><textarea name="remark" id="deptRemark" class="text ui-widget-content ui-corner-all" rows="3"
                              cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>
<div id="dialog-user-form" style="display: none;">
    <form id="userForm">
        <table class="table table-striped table-bordered table-hover dataTable no-footer" role="grid">
            <tr>
                <td style="width: 80px;"><label for="parentId">所在部门</label></td>
                <td>
                    <select id="deptSelectId" name="deptId" data-placeholder="选择部门" style="width: 200px;"></select>
                </td>
            </tr>
            <tr>
                <td><label for="userName">名称</label></td>
                <input type="hidden" name="id" id="userId"/>
                <td><input type="text" name="username" id="userName" value=""
                           class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userMail">邮箱</label></td>
                <td><input type="text" name="mail" id="userMail" value="" class="text ui-widget-content ui-corner-all">
                </td>
            </tr>
            <tr>
                <td><label for="userTelephone">电话</label></td>
                <td><input type="text" name="telephone" id="userTelephone" value=""
                           class="text ui-widget-content ui-corner-all"></td>
            </tr>
            <tr>
                <td><label for="userStatus">状态</label></td>
                <td>
                    <select id="userStatus" name="status" data-placeholder="选择状态" style="width: 150px;">
                        <option value="1">有效</option>
                        <option value="0">无效</option>
                        <option value="2">删除</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><label for="userRemark">备注</label></td>
                <td><textarea name="remark" id="userRemark" class="text ui-widget-content ui-corner-all" rows="3"
                              cols="25"></textarea></td>
            </tr>
        </table>
    </form>
</div>

<script id="deptListTemplate" type="x-tmpl-mustache">
<ol class="dd-list">
    {{#deptList}}
        <li class="dd-item dd2-item dept-name" id="dept_{{id}}" href="javascript:void(0)" data-id="{{id}}">
            <div class="dd2-content" style="cursor:pointer;">
            {{name}}
            <span style="float:right;">
                <a class="green dept-edit" href="#" data-id="{{id}}" >
                    <i class="ace-icon fa fa-pencil bigger-100"></i>
                </a>
                &nbsp;
                <a class="red dept-delete" href="#" data-id="{{id}}" data-name="{{name}}">
                    <i class="ace-icon fa fa-trash-o bigger-100"></i>
                </a>
            </span>
            </div>
        </li>
    {{/deptList}}
</ol>


















</script>
<script id="userListTemplate" type="x-tmpl-mustache">
{{#userList}}
<tr role="row" class="user-name odd" data-id="{{id}}" data-dept-id="{{deptId}}"><!--even -->
    <td><a href="#" class="user-edit" data-id="{{id}}" data-dept-id="{{deptId}}">{{username}}</a></td>
    <td>{{showDeptName}}</td>
    <td>{{mail}}</td>
    <td>{{telephone}}</td>
    <td>{{#bold}}{{showStatus}}{{/bold}}</td> <!-- 此处套用函数对status做特殊处理 -->
    <td>
        <div class="hidden-sm hidden-xs action-buttons">
            <a class="green user-edit" href="#" data-id="{{id}}">
                <i class="ace-icon fa fa-pencil bigger-100"></i>
            </a>
            <a class="red user-acl" href="#" data-id="{{id}}">
                <i class="ace-icon fa fa-flag bigger-100"></i>
            </a>
        </div>
    </td>
</tr>
{{/userList}}

















</script>

<script type="application/javascript">
    $(function () {
        var deptList;
        //缓存dept部门信息
        var deptMap = {};
        //缓存用户信息
        var userMap = {};
        var optionStr;
        //父部门id
        var lastClickDeptId = -1;
        // MUstache 引擎解析模板
        var deptTemplate = $('#deptListTemplate').html();
        Mustache.parse(deptTemplate);
        var userListTemplate = $("#userListTemplate").html();
        Mustache.parse(userListTemplate);
        loadDeptTree();

        //加载部门树
        function loadDeptTree() {
            $.ajax({
                url: "/sys/dept/tree.json",
                success: function (result) {
                    console.log(result);
                    if (result.status == 200) {
                        deptList = result.data;
                        var render = Mustache.render(deptTemplate, {deptList: deptList});
                        $("#deptList").html(render);
                        recursiveRenderDept(deptList);
                        bindDeptClick();
                    } else {
                        showMessage("加载部门列表", result.msg, false);
                    }
                }
            })
        }

        // 递归渲染部门树
        function recursiveRenderDept(deptList) {
            console.log(deptList)
            if (deptList != null && deptList.length > 0) {
                $(deptList).each(function (i, dept) {
                    deptMap[dept.id] = dept;
                    if (dept.deptLevelDtoList.length > 0) {
                        var rendered = Mustache.render(deptTemplate, {deptList: dept.deptLevelDtoList});
                        $("#dept_" + dept.id).append(rendered);
                        recursiveRenderDept(dept.deptLevelDtoList);
                    }
                })
            }
        }

        // 绑定部门操作
        function bindDeptClick() {
            //删除部门点击事件
            $(".dept-delete").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var deptName = $(this).attr("data-name");
                var deptId = $(this).attr("data-id");
                if (confirm("确定要删除部门为【" + deptName + "】吗")) {
                    $.ajax({
                        url: "/sys/dept/delete.json?deptId=" + deptId,
                        success: function (result) {
                            if (result.status == 200) {
                                console.log("dept-delete" + deptName);
                                loadDeptTree();
                            } else {
                                showMessage("删除部门", result.msg, false);
                            }

                        },
                        type: "delete"
                    })
                }
            })
            // 点击部门名称,执行选中效果
            $(".dept-name").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                var currentDeptId = $(this).attr("data-id");
                //处理选中效果
                handlerDeptSelected(currentDeptId)
            })
            //编辑部门点击事件
            $(".dept-edit").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                //获取对应父部门的id
                var deptId = $(this).attr("data-id");
                $("#dialog-dept-form").dialog({
                    model: true,
                    title: "编辑部门",
                    open: function (event, ui) {
                        $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                        optionStr = "<option value='0'>-</option>"
                        recursiveRenderDeptSelect(deptList, 1);
                        $("#deptForm")[0].reset();
                        $("#parentId").append(optionStr);
                        var dept = deptMap[deptId];
                        if (dept) {
                            $("#parentId").val(dept.parentId);
                            $("#deptId").val(dept.id);
                            $("#deptName").val(dept.name);
                            $("#deptSeq").val(dept.seq);
                            $("#deptRemark").val(dept.remark);
                        }
                    },
                    buttons: {
                        "修改": function (e) {
                            // 阻止默认事件
                            e.preventDefault();
                            updateDept(false, function (data) {
                                //关闭dialog
                                $("#dialog-dept-form").dialog("close");
                            }, function (data) {
                                showMessage("编辑部门", data.msg, false);
                            })

                        },
                        "取消": function (e) {
                            $("#dialog-dept-form").dialog("close");
                        }
                    }
                });
            })
        }

        // 新增部门操作
        $(".dept-add").click(function (e) {
            e.preventDefault()
            // 弹出添加部门窗口
            $("#dialog-dept-form").dialog({
                model: true,
                title: "新增部门",
                open: function (event, ui) {
                    $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                    optionStr = "<option value='0'>-</option>"
                    recursiveRenderDeptSelect(deptList, 1);
                    $("#deptForm")[0].reset();
                    $("#parentId").append(optionStr);
                },
                buttons: {
                    "添加": function (e) {
                        // 阻止默认事件
                        e.preventDefault();
                        updateDept(true, function (data) {
                            //关闭dialog
                            $("#dialog-dept-form").dialog("close");
                        }, function (data) {
                            showMessage("新增部门", data.msg, false);
                        })

                    },
                    "取消": function (e) {
                        $("#dialog-dept-form").dialog("close");
                    }
                }
            });
        })

        function recursiveRenderDeptSelect(deptList, level) {
            level = level | 0;
            if (deptList && deptList.length > 0) {
                $(deptList).each(function (i, dept) {
                    deptMap[dept.id] = dept;
                    var blank = "";
                    if (level > 1) {
                        for (var j = 3; j <= level; j++) {
                            blank += "..";
                        }
                        blank += "*";
                    }
                    optionStr += Mustache.render("<option value='{{id}}'>{{name}}</option>", {
                        id: dept.id,
                        name: blank += dept.name
                    });
                    if (dept.deptLevelDtoList && dept.deptLevelDtoList.length > 0) {
                        recursiveRenderDeptSelect(dept.deptLevelDtoList, level + 1);
                    }
                })
            }
        }

        // 添加或者编辑部门
        function updateDept(isCreate, successCallBack, failCallBack) {
            $.ajax({
                url: isCreate ? "/sys/dept/save.json" : "/sys/dept/update.json",
                data: $("#deptForm").serializeArray(),
                success: function (result) {
                    if (result.status == 200) {
                        loadDeptTree();
                        if (successCallBack) {
                            successCallBack(result);
                        }
                    } else {
                        if (failCallBack) {
                            failCallBack(result);
                        }
                    }

                },
                type: "post"
            })
        }

        function handlerDeptSelected(deptId) {
            debugger;
            if (lastClickDeptId != -1) {
                var lastDept = $("#dept_" + lastClickDeptId + " .dd2-content:first");
                lastDept.removeClass("btn-yellow");
                lastDept.removeClass("no-hover");
            }
            var currentDept = $("#dept_" + deptId + " .dd2-content:first");
            currentDept.addClass("btn-yellow");
            currentDept.addClass("no-hover");
            lastClickDeptId = deptId;
            loadUserList(deptId);
        }

        //根据部门id加载用户信息
        function loadUserList(deptId) {
            //TODO
            var pageSize = $("#pageSize").val();
            var url = "/sys/user/list.json?deptId=" + deptId;
            var pageNo = $("#userPage .pageNo").val() || 1;
            $.ajax({
                url: url,
                data: {
                    pageSize: pageSize,
                    pageNo: pageNo
                },
                success: function (result) {
                    renderUserListAndPage(result, url);
                }
            })
            console.log("load userList,deptId:" + deptId);

        }

        //用户分页列表
        function renderUserListAndPage(result, url) {
            console.log(result)
            if (result.status === 200) {
                if (result.data.total > 0) {
                    var rendered = Mustache.render(userListTemplate, {
                        userList: result.data.data,
                        "showDeptName": function () {
                            return deptMap[this.deptId].name;
                        },
                        "showStatus": function () {
                            return this.status == 1 ? '有效' : (this.status == 0 ? '无效' : '删除')
                        },
                        "bold": function () {
                            return function (text, render) {
                                var status = render(text);
                                if (status == '有效') {
                                    return "<span class='label label-sm label-success'>有效</span>"
                                } else if (status == '无效') {
                                    return "<span class='label label-sm label-warning'>无效</span>"
                                } else {
                                    return "<span class='label'>删除</span>"
                                }
                            }
                        }
                    });
                    $("#userList").html(rendered);
                    bindUserClick()
                    $.each(result.data.data, function (i, user) {
                        userMap[user.id] = user;
                    })
                } else {
                    $("#userList").html('');
                }
                //渲染分页信息
                var pageSize = $("#pageSize").val();
                var pageNo = $("#userPage .pageNo").val() || 1;
                renderPage(url, result.data.total, pageNo, pageSize, (result.data.total > 0 ? result.data.data.length : 0), "userPage", renderUserListAndPage);
            } else {
                showMessage("获取部门下用户列表", result.msg, false);
            }
        }

        // 用户添加、修改的点击操作
        function bindUserClick() {
            //添加用户
            $(".user-add").click(function (e) {
                e.preventDefault();
                // 弹出添加用户窗口
                $("#dialog-user-form").dialog({
                    model: true,
                    title: "新增用户",
                    open: function (event, ui) {
                        $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                        optionStr = ""
                        recursiveRenderDeptSelect(deptList, 1);
                        $("#userForm")[0].reset();
                        $("#deptSelectId").append(optionStr);
                    },
                    buttons: {
                        "添加": function (e) {
                            // 阻止默认事件
                            e.preventDefault();
                            updateUser(true, function (data) {
                                //关闭dialog
                                $("#dialog-user-form").dialog("close");
                                //刷新 user 列表
                                loadUserList(lastClickDeptId)
                            }, function (data) {
                                showMessage("新增用户", data.msg, false);
                            })

                        },
                        "取消": function (e) {
                            $("#dialog-user-form").dialog("close");
                        }
                    }
                });
            })
            //编辑用户
            $(".user-edit").click(function (e) {
                e.preventDefault();
                e.stopPropagation();
                // 获取当前用户的id
                var userId = $(this).attr("data-id");
                //定义当前用户的部门id
                var deptId = $(this).attr("data-dept-id");
                // 弹出编辑用户窗口
                $("#dialog-user-form").dialog({
                    model: true,
                    title: "编辑用户",
                    open: function (event, ui) {
                        $(".ui-dialog-titlebar-close", $(this).parent()).hide();
                        optionStr = ""
                        recursiveRenderDeptSelect(deptList, 1);
                        $("#userForm")[0].reset();
                        $("#deptSelectId").append(optionStr);
                        // 从 userMap 中取出user信息
                        var user = userMap[userId];
                        console.log(user)
                        if (user) {
                            //修改信息回显
                            $("#userId").val(userId);
                            $("#userName").val(user.username);
                            $("#userMail").val(user.mail);
                            $("#userTelephone").val(user.telephone);
                            $("#userRemark").val(user.remark);
                            $("#deptSelectId").val(user.deptId);
                        }
                    },
                    buttons: {
                        "修改": function (e) {
                            // 阻止默认事件
                            e.preventDefault();
                            updateUser(false, function (data) {
                                //关闭dialog
                                $("#dialog-user-form").dialog("close");
                                // 刷新 user 列表
                                loadUserList(lastClickDeptId);
                            }, function (data) {
                                showMessage("修改用户", data.msg, false);
                            })

                        },
                        "取消": function (e) {
                            $("#dialog-user-form").dialog("close");
                        }
                    }
                });
            })
            $(".user-acl").click(function (e) {
                var userId = $(this).attr("data-id");
                $.ajax({
                    url: "/sys/user/acls.json?userId="+userId,
                    type: "get",
                    success: function (result) {
                        if (result.status == 200) {
                            console.log(result);
                        } else {
                            showMessage("获取用户角色和权限列表", result.msg, false);
                        }
                    }

                })
            })
        }

        //添加或者修改用户信息
        function updateUser(isCreate, successCallBack, failCallBack) {
            $.ajax({
                url: isCreate ? "/sys/user/save.json" : "/sys/user/update.json",
                data: $("#userForm").serializeArray(),
                type: "post",
                success: function (result) {
                    if (result.status == 200) {
                        if (successCallBack) {
                            successCallBack(result);
                        }
                    } else {
                        failCallBack(result);
                    }
                }

            })
        }
    })
</script>
</body>
</html>
