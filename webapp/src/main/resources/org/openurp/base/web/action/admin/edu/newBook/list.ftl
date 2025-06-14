[#ftl]
  <div class="card card-primary card-outline">
    <div class="card-header">
      <h3 class="card-title">教材列表
        <span class="badge badge-primary">${textbooks.totalItems}</span>
      </h3>
      [@b.form name="searchForm" action="!search" class="form-inline ml-3 float-right" ]
        <div class="input-group input-group-sm ">
          <input class="form-control form-control-navbar" type="search" name="q" value="${Parameters['q']!}" aria-label="Search" placeholder="书名、作者、ISBN" autofocus="autofocus">
          [#list Parameters?keys as k]
           [#if k != 'q']
          <input type="hidden" name="${k}" value="${Parameters[k]?html}"/>
          [/#if]
          [/#list]
          <div class="input-group-append">
            <button class="btn btn-navbar" type="submit" onclick="bg.form.submit(document.searchForm);return false;">
              <i class="fas fa-search"></i>
            </button>
          </div>
        </div>
      [/@]
    </div>
    <div class="card-body">
      [@b.grid items=textbooks var="textbook" theme="mini" class="table-hover table-sm table-striped"]
        [@b.row]
          [@b.col width="12%" property="isbn" title="ISBN"]
            <span style="font-size:0.8em"> ${(textbook.isbn)!}</span>
          [/@]
          [@b.col property="name" title="名称"]
            <span [#if textbook.name?length > 25]style="font-size:0.8em"[/#if]>
            ${textbook.name}
            [#if textbook.madeInSchool]<sup>自编</sup>[/#if]
            [#if textbook.awardType??]<sup>${textbook.awardType.name}</sup>[/#if]
            </span>
          [/@]
          [@b.col width="13%" property="press.name" title="出版社"]
           <span style="font-size:0.8em">${(textbook.press.name)!}</span>
          [/@]
          [@b.col width="16%" property="author" title="作者"/]
          [@b.col width="8%" property="publishedIn" title="出版年月"]${(textbook.publishedIn?string('yyyy-MM'))!}[/@]
          [@b.col width="8%" property="bookType.name" title="教材类型"/]
          [@b.col width="8%" property="category.name" title="图书分类"]
            [#if ((textbook.category.name)!'-')?length > 5 ]
            <span style="font-size:0.6em">${(textbook.category.name)!}</span>
            [#else]
            ${(textbook.category.name)!}
            [/#if]
          [/@]
        [/@]
       [/@]
         <nav aria-label="Page navigation example">
           <ul class="pagination float-right">
             [#if textbooks.pageIndex > 1]
             <li class="page-item"><a class="page-link" href="#" onclick="listCourse(1)">首页</a></li>
             <li class="page-item"><a class="page-link" href="#"  onclick="listCourse(${textbooks.pageIndex-1})">${textbooks.pageIndex-1}</a></li>
             [/#if]
             <li class="page-item active"><a class="page-link" href="#" >${textbooks.pageIndex}</a></li>
             [#if textbooks.pageIndex < textbooks.totalPages]
             <li class="page-item"><a class="page-link" href="#" onclick="listCourse(${textbooks.pageIndex+1})">${textbooks.pageIndex+1}</a></li>
             <li class="page-item"><a class="page-link" href="#" onclick="listCourse(${textbooks.totalPages})">末页</a></li>
             [/#if]
           </ul>
         </nav>
    </div>
  </div>
  <script>
     var qElem = document.searchForm['q'];
     qElem.focus();
     if(qElem.setSelectionRange && qElem.value.length>0){
       qElem.setSelectionRange(qElem.value.length,qElem.value.length)
     }
     function listCourse(pageIndex){
        bg.form.addInput(document.searchForm,"pageIndex",pageIndex);
        bg.form.submit(document.searchForm);
     }
  </script>
