[#ftl]

  <div class="card card-info card-primary card-outline">
    <div class="card-header">
      <h3 class="card-title">教师信息
        <span class="badge badge-primary">${teachers.totalItems}</span>
      </h3>
      [@b.form name="teacherSearchForm" action="!search" class="form-inline ml-3 float-right" ]
        <div class="input-group input-group-sm ">
          <input class="form-control form-control-navbar" type="search" name="q" value="${Parameters['q']!}" aria-label="Search" placeholder="输入搜索关键词" autofocus="autofocus">
          <div class="input-group-append">
            <button class="btn btn-navbar" type="submit" onclick="bg.form.submit(document.teacherSearchForm);return false;">
              <i class="fas fa-search"></i>
            </button>
          </div>
        </div>
      [/@]
    </div>
    <div class="card-body">
        <table class="table table-hover table-sm">
          <thead>
             <th>工号</th>
             <th>姓名</th>
             <th>部门</th>
             <th>职称</th>
             <th>是否兼职</th>
             <th>教师类型</th>
             <th>在职状态</th>
          </thead>
          <tbody>
          [#list teachers as teacher]
           <tr>
            <td>${teacher.user.code}</td>
            <td>${teacher.user.name}</td>
            <td>${teacher.user.department.name}</td>
            <td>${(teacher.title.name)!}</td>
            <td>${teacher.parttime?string("是","否")}</td>
            <td>${(teacher.teacherType.name)!}</td>
            <td>${(teacher.status.name)!}</td>
           </tr>
           [/#list]
          </tbody>
         </table>
         <nav aria-label="Page navigation example">
           <ul class="pagination float-right">
             [#if teachers.pageIndex > 1]
             <li class="page-item"><a class="page-link" href="#" onclick="listCourse(1)">首页</a></li>
             <li class="page-item"><a class="page-link" href="#"  onclick="listCourse(${teachers.pageIndex-1})">${teachers.pageIndex-1}</a></li>
             [/#if]
             <li class="page-item active"><a class="page-link" href="#" >${teachers.pageIndex}</a></li>
             [#if teachers.pageIndex < teachers.totalPages]
             <li class="page-item"><a class="page-link" href="#" onclick="listCourse(${teachers.pageIndex+1})">${teachers.pageIndex+1}</a></li>
             <li class="page-item"><a class="page-link" href="#" onclick="listCourse(${teachers.totalPages})">末页</a></li>
             [/#if]
           </ul>
         </nav>
    </div>
  </div>
  <script>
     var qElem = document.teacherSearchForm['q'];
     qElem.focus();
     if(qElem.setSelectionRange && qElem.value.length>0){
       qElem.setSelectionRange(qElem.value.length,qElem.value.length)
     }
     function listCourse(pageIndex){
        bg.form.addInput(document.teacherSearchForm,"pageIndex",pageIndex);
        bg.form.submit(document.teacherSearchForm);
     }
  </script>
