[#ftl]
[@b.head/]
[#include "../info_macros.ftl"/]
[@info_header title="教师信息"/]
<div class="container">
  <div class="row">
     <div class="col-3">
       <div class="card card-info card-primary card-outline">
         <div class="card-header">
          教师统计
          [#if categoryStats[0]>0]
          <span class="badge badge-primary">兼职${categoryStats[0]}</span>
          [/#if]
          [#if categoryStats[1]>0]
          <span class="badge badge-danger">返聘${categoryStats[1]}</span>
          [/#if]
          [#if categoryStats[2]>0]
          <span class="badge badge-primary">在编${categoryStats[2]}</span>
          [/#if]
          [#if categoryStats[3]>0]
          <span class="badge badge-success">总计${categoryStats[3]}</span>
          [/#if]
         </div>
         <div class="card-body">
             <table class="table table-hover table-sm">
               <thead>
                  <th>院系</th>
                  <th>人数</th>
               </thead>
               <tbody>
               [#list departStat as stat]
                <tr>
                 <td width="80%">[@b.a href="!search?teacher.user.department.id="+stat[0] target="course_list"]${stat[1]}[/@]</td>
                 <td width="20%">${stat[2]}</td>
                </tr>
                [/#list]
               </tbody>
             </table>

             <table class="table table-hover table-sm">
              <thead>
                 <th width="80%">教师类型</th>
                 <th width="20%">人数</th>
              </thead>
              <tbody>
              [#list typeStat as stat]
               <tr>
                <td>[@b.a href="!search?teacher.teacherType.id="+stat[0] target="course_list"]${stat[1]}[/@]</td>
                <td>${stat[2]}</td>
               </tr>
               [/#list]
              </tbody>
            </table>

             <table class="table table-hover table-sm">
              <thead>
                 <th width="80%">职称</th>
                 <th width="20%">人数</th>
              </thead>
              <tbody>
              [#list titleStat as stat]
               <tr>
                <td>[@b.a href="!search?teacher.title.id="+stat[0] target="course_list"]${stat[1]}[/@]</td>
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
