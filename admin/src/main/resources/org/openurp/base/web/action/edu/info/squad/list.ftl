[#ftl]

  <div class="card card-info card-primary card-outline">
    <div class="card-header">
      <h3 class="card-title">班级信息
        <span class="badge badge-primary">${squads.totalItems}</span>
      </h3>
      [@b.form name="squadSearchForm" action="!search" class="form-inline ml-3 float-right" ]
        <div class="input-group input-group-sm ">
          <input class="form-control form-control-navbar" type="search" name="q" value="${Parameters['q']!}" aria-label="Search" placeholder="输入搜索关键词" autofocus="autofocus">
          <div class="input-group-append">
            <button class="btn btn-navbar" type="submit" onclick="bg.form.submit(document.squadSearchForm);return false;">
              <i class="fas fa-search"></i>
            </button>
          </div>
        </div>
      [/@]
    </div>
    <div class="card-body">
        <table class="table table-hover table-sm">
          <thead>
             <th>代码</th>
             <th>名称</th>
             <th>年级</th>
             <th>培养层次</th>
             <th>院系</th>
             <th>专业(方向)</th>
             <th>人数</th>
             <th>辅导员</th>
          </thead>
          <tbody>
          [#list squads as squad]
           <tr>
            <td>${squad.code}</td>
            <td>${squad.name}</td>
            <td>${squad.grade}</td>
            <td>${(squad.level.name)!}</td>
            <td>${squad.department.name}</td>
            <td>${(squad.major.name)!} ${(squad.direction.name)!}</td>
            <td>${squad.stdCount}</td>
           </tr>
           [/#list]
          </tbody>
         </table>
         <nav aria-label="Page navigation example">
           <ul class="pagination float-right">
             [#if squads.pageIndex > 1]
             <li class="page-item"><a class="page-link" href="#" onclick="listSquad(1)">首页</a></li>
             <li class="page-item"><a class="page-link" href="#"  onclick="listSquad(${squads.pageIndex-1})">${squads.pageIndex-1}</a></li>
             [/#if]
             <li class="page-item active"><a class="page-link" href="#" >${squads.pageIndex}</a></li>
             [#if squads.pageIndex < squads.totalPages]
             <li class="page-item"><a class="page-link" href="#" onclick="listSquad(${squads.pageIndex+1})">${squads.pageIndex+1}</a></li>
             <li class="page-item"><a class="page-link" href="#" onclick="listSquad(${squads.totalPages})">末页</a></li>
             [/#if]
           </ul>
         </nav>
    </div>
  </div>
  <script>
     var qElem = document.squadSearchForm['q'];
     qElem.focus();
     if(qElem.setSelectionRange && qElem.value.length>0){
       qElem.setSelectionRange(qElem.value.length,qElem.value.length)
     }
     function listSquad(pageIndex){
        bg.form.addInput(document.squadSearchForm,"pageIndex",pageIndex);
        bg.form.submit(document.squadSearchForm);
     }
  </script>
