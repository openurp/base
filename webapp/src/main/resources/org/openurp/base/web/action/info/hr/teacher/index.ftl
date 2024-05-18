[#ftl]
[@b.head/]
[#include "../../info_macros.ftl"/]
[@info_header title="教师信息"/]
<div class="container-fluid">
  <div class="row">
     <div class="col-3" id="accordion">

       <div class="card card-info card-primary card-outline">
         <div class="card-header" id="stat_header_1">
              <button class="btn btn-link" data-toggle="collapse" data-target="#stat_body_1" aria-expanded="true" aria-controls="stat_body_1" style="padding: 0;">
                所属院系统计
              </button>
              [#if categoryStats[0]>0]
              <span class="badge badge-primary">兼职${categoryStats[0]}</span>
              [/#if]
              [#if categoryStats[1]>0]
              <span class="badge badge-danger">外聘${categoryStats[1]}</span>
              [/#if]
              [#if categoryStats[2]>0]
              <span class="badge badge-primary">在编${categoryStats[2]}</span>
              [/#if]
              [#if categoryStats[3]>0]
              <span class="badge badge-success">总计${categoryStats[3]}</span>
              [/#if]
         </div>
         <div id="stat_body_1" class="collapse show" aria-labelledby="stat_header_1" data-parent="#accordion">
           <div class="card-body" style="padding-top: 0px;">
             <table class="table table-hover table-sm">
               <tbody>
               [#list departStat as stat]
               <tr>
                <td width="80%">[@b.a href="!search?teacher.department.id="+stat[0] target="course_list"]${stat[1]}[/@]</td>
                <td width="20%">${stat[2]}</td>
               </tr>
               [/#list]
               [#if otherDepartStat?size >1]
               <tr>
                <td width="80%">[@b.a href="!search?teacher.department.teaching=0" target="course_list"]其他部门[/@]</td>
                <td width="20%">[#assign otherDepartTeacherCnt=0/][#list otherDepartStat as s][#assign otherDepartTeacherCnt=otherDepartTeacherCnt+s[2] /][/#list]
                  ${otherDepartTeacherCnt}
                </td>
               </tr>
               [/#if]
               </tbody>
             </table>
           </div>
         </div>
       </div>

       <div class="card card-info card-primary card-outline">
         <div class="card-header" id="stat_header_2">
           <button class="btn btn-link" data-toggle="collapse" data-target="#stat_body_2" aria-expanded="true" aria-controls="stat_body_2" style="padding: 0;">
             教师类型统计
           </button>
         </div>
         <div id="stat_body_2" class="collapse" aria-labelledby="stat_header_2" data-parent="#accordion">
           <div class="card-body" style="padding-top: 0px;">
             <table class="table table-hover table-sm">
              <tbody>
              [#list typeStat as stat]
               <tr>
                <td>[@b.a href="!search?teacher.staff.staffType.id="+stat[0] target="course_list"]${stat[1]}[/@]</td>
                <td>${stat[2]}</td>
               </tr>
               [/#list]
              </tbody>
            </table>
           </div>
         </div>
       </div>

       <div class="card card-info card-primary card-outline">
         <div class="card-header" id="stat_header_3">
           <button class="btn btn-link" data-toggle="collapse" data-target="#stat_body_3" aria-expanded="true" aria-controls="stat_body_3" style="padding: 0;">
             职称统计
           </button>
         </div>
         <div id="stat_body_3" class="collapse" aria-labelledby="stat_header_3" data-parent="#accordion">
           <div class="card-body" style="padding-top: 0px;">
             <table class="table table-hover table-sm">
              <tbody>
              [#list titleStat as stat]
               <tr>
                <td>[@b.a href="!search?teacher.staff.title.id="+stat[0] target="course_list"]${stat[1]}[/@]</td>
                <td>${stat[2]}</td>
               </tr>
               [/#list]
              </tbody>
            </table>
           </div>
         </div>
       </div>

     </div><!--end col-3-->
     [@b.div class="col-9" id="course_list" href="!search"]
     [/@]
  </div>
</div>
[@b.foot/]
