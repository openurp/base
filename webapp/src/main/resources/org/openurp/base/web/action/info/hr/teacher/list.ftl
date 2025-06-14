[#ftl]

  <div class="card card-info card-primary card-outline">
    <div class="card-header">
      <h3 class="card-title">教师信息
        <span class="badge badge-primary">${teachers.totalItems}</span>
      </h3>
      [@b.form name="teacherSearchForm" action="!search" class="form-inline ml-3 float-right" ]
        <div class="input-group input-group-sm ">
          <input class="form-control form-control-navbar" type="search" name="q" value="${Parameters['q']!}" aria-label="Search" placeholder="输入搜索关键词" autofocus="autofocus">
          [#list Parameters?keys as k]
           [#if k != 'q']
          <input type="hidden" name="${k}" value="${Parameters[k]?html}"/>
          [/#if]
          [/#list]
          <div class="input-group-append">
            <button class="btn btn-navbar" type="submit" onclick="bg.form.submit(document.teacherSearchForm);return false;">
              <i class="fas fa-search"></i>
            </button>
          </div>
        </div>
      [/@]
    </div>
    [#assign displayTutorType = tutorTypes?size >0/]
    <div class="card-body">
        <table class="table table-hover table-sm table-striped">
          <thead>
             <th>工号</th>
             <th>姓名</th>
             <th>部门</th>
             <th>职称</th>
             <th>教师类型</th>
             [#if displayTutorType]<th>导师类别</th>[/#if]
             <th>在职状态</th>
          </thead>
          <tbody>
          [#list teachers as teacher]
           <tr>
            <td>${teacher.staff.code}</td>
            <td>
              <a href="${b.url('!info?id='+teacher.id)}" data-toggle="modal" data-target="#staffInfo">${teacher.name}</a>
            </td>
            <td>${teacher.department.name}</td>
            <td>${(teacher.staff.title.name)!}</td>
            <td>${(teacher.staff.staffType.name)!}</td>
            [#if displayTutorType]<td>${(teacher.staff.tutorType.name)!}</td>[/#if]
            <td>${(teacher.staff.status.name)!}</td>
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
[@b.dialog id="staffInfo" title="教师信息" /]
