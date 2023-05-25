[#ftl]
[@b.head/]
[#include "../../info_macros.ftl"/]
[@info_header title="班级信息"/]
<div class="container-fluid">
  <div class="row">
     <div class="col-3">
       <div class="card card-info card-primary card-outline">
         <div class="card-header">
          班级统计
         </div>
         <div class="card-body" style="padding-top: 0px;">
             <table class="table table-hover table-sm">
               <thead>
                  <th>院系</th>
                  <th>班数</th>
               </thead>
               <tbody>
               [#list departStat as stat]
                <tr>
                 <td width="80%">[@b.a href="!search?squad.department.id="+stat[0] target="squad_list"]${stat[1]}[/@]</td>
                 <td width="20%">${stat[2]}</td>
                </tr>
                [/#list]
               </tbody>
             </table>

             <table class="table table-hover table-sm">
              <thead>
                 <th width="80%">年级</th>
                 <th width="20%">班数</th>
              </thead>
              <tbody>
              [#list gradeStat as stat]
               <tr>
                <td>[@b.a href="!search?squad.grade.code="+stat[0] target="squad_list"]${stat[0]}[/@]</td>
                <td>${stat[1]}</td>
               </tr>
               [/#list]
              </tbody>
            </table>
         </div>
       </div>
     </div><!--end col-3-->
     [@b.div class="col-9" id="squad_list" href="!search"]
     [/@]
  </div>
</div>
[@b.foot/]
