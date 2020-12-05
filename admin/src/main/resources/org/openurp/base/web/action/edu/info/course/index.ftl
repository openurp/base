[#ftl]
[@b.head/]
[#include "../info_macros.ftl"/]
[@info_header title="课程信息"/]
<div class="container">
  <div class="row">
     <div class="col-3">
       <div class="card card-info card-primary card-outline">
         <div class="card-header">
          课程 分类统计
         </div>
         <div class="card-body">
             <table class="table table-hover table-sm">
              <thead>
                 <th width="80%">分类</th>
                 <th width="20%">门数</th>
              </thead>
              <tbody>
              [#list categoryStat as stat]
               <tr>
                <td>[@b.a href="!search?course.category.id="+stat[0] target="course_list"]${stat[1]}[/@]</td>
                <td>${stat[2]}</td>
               </tr>
               [/#list]
              </tbody>
            </table>
             <table class="table table-hover table-sm">
               <thead>
                  <th>院系</th>
                  <th>门数</th>
               </thead>
               <tbody>
               [#list departStat as stat]
                <tr>
                 <td width="80%">[@b.a href="!search?course.department.id="+stat[0] target="course_list"]${stat[1]}[/@]</td>
                 <td width="20%">${stat[2]}</td>
                </tr>
                [/#list]
               </tbody>
             </table>

             <table class="table table-hover table-sm">
              <thead>
                 <th width="80%">课程类别</th>
                 <th width="20%">门数</th>
              </thead>
              <tbody>
              [#list typeStat as stat]
               <tr>
                <td>[@b.a href="!search?course.courseType.id="+stat[0] target="course_list"]${stat[1]}[/@]</td>
                <td>${stat[2]}</td>
               </tr>
               [/#list]
              </tbody>
            </table>
         </div>
       </div>
     </div><!--end col-3-->
     [@b.div class="col-9" id="course_list" href="!search"]
     [/@]
  </div>
</div>
[@b.foot/]
