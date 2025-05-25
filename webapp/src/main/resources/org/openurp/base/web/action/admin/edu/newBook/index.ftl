[#ftl]
[@b.head/]
<div class="container">
  <div class="row">
    <div class="col-md-2">
      <div class="card card-primary card-outline">
           <div class="card-header">
              <h3 class="card-title">教材信息</h3>
            </div>
            <div class="card-body">
              <ul class="nav nav-pills flex-column" id="msgboxList">
                <li class="nav-item">
                  [@b.a href="!search?textbook.project.id=${project.id}" class="nav-link active" target="bookList" ]教材查询[/@]
                </li>
                <li class="nav-item">
                  [@b.a href="!search?textbook.project.id=${project.id}&textbook.creator.code=${me}" class="nav-link" target="bookList" ]我增加的教材[/@]
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="${b.url('!editNew?project.id='+project.id)}" data-toggle="modal" data-target="#newBookDiv" title="添加出版物教材">添加教材</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="${b.url('!editNew?lecture=1&project.id='+project.id)}"  data-toggle="modal" data-target="#newBookDiv" title="添加讲义">添加讲义</a>
                </li>
              </ul>
            </div>
          </div>
    </div>
    [@b.div class="col-md-10" id="bookList" href="!search?textbook.project.id=${project.id}"/]
  </div>

  [@b.dialog title="新增教材" id="newBookDiv"/]
  <script>
      jQuery("#msgboxList > li > a").click(function(){
         jQuery(this).parent().siblings().each(function(i,li){jQuery(li).children("a").removeClass("active")});
         jQuery(this).addClass("active")
      });

  </script>
</div>
[@b.foot/]
