[#ftl]

  <div class="card card-info card-primary card-outline">
    <div class="card-header">
      <h3 class="card-title">课程信息
        <span class="badge badge-primary">${courses.totalItems}</span>
      </h3>
      [@b.form name="courseSearchForm" action="!search" class="form-inline ml-3 float-right" ]
        <div class="input-group input-group-sm ">
          <input class="form-control form-control-navbar" type="search" name="q" value="${Parameters['q']!}" aria-label="Search" placeholder="输入搜索关键词" autofocus="autofocus">
          [#list Parameters?keys as k]
           [#if k != 'q']
          <input type="hidden" name="${k}" value="${Parameters[k]?html}"/>
          [/#if]
          [/#list]
          <div class="input-group-append">
            <button class="btn btn-navbar" type="submit" onclick="bg.form.submit(document.courseSearchForm);return false;">
              <i class="fas fa-search"></i>
            </button>
          </div>
        </div>
      [/@]
    </div>
    <div class="card-body">
        <table class="table table-hover table-sm table-striped">
          <thead>
             <th>代码</th>
             <th>名称</th>
             <th>学分</th>
             <th>学时</th>
             <th>周课时</th>
             <th>院系</th>
             <th>课程类型</th>
             <th>考核方式</th>
          </thead>
          <tbody>
          [#list courses as course]
           <tr>
            <td>${course.code}</td>
            <td>${course.name}</td>
            <td>${course.defaultCredits}</td>
            <td>
             ${course.creditHours}
              [#if course.hours?size>1]
                ([#list course.hours?sort_by(['teachingNature','code']) as ch]${ch.creditHours}[#if ch_has_next]+[/#if][/#list])
              [/#if]
            </td>
            <td>${course.weekHours}</td>
            <td>${course.department.shortName!course.department.name}</td>
            <td>${(course.courseType.name)!}</td>
            <td>${(course.examMode.name)!}</td>
           </tr>
           [/#list]
          </tbody>
         </table>
         <nav aria-label="Page navigation example">
           <ul class="pagination float-right">
             [#if courses.pageIndex > 1]
             <li class="page-item"><a class="page-link" href="#" onclick="listCourse(1)">首页</a></li>
             <li class="page-item"><a class="page-link" href="#"  onclick="listCourse(${courses.pageIndex-1})">${courses.pageIndex-1}</a></li>
             [/#if]
             <li class="page-item active"><a class="page-link" href="#" >${courses.pageIndex}</a></li>
             [#if courses.pageIndex < courses.totalPages]
             <li class="page-item"><a class="page-link" href="#" onclick="listCourse(${courses.pageIndex+1})">${courses.pageIndex+1}</a></li>
             <li class="page-item"><a class="page-link" href="#" onclick="listCourse(${courses.totalPages})">末页</a></li>
             [/#if]
           </ul>
         </nav>
    </div>
  </div>
  <script>
     var qElem = document.courseSearchForm['q'];
     qElem.focus();
     if(qElem.setSelectionRange && qElem.value.length>0){
       qElem.setSelectionRange(qElem.value.length,qElem.value.length)
     }
     function listCourse(pageIndex){
        bg.form.addInput(document.courseSearchForm,"pageIndex",pageIndex);
        bg.form.submit(document.courseSearchForm);
     }
  </script>
