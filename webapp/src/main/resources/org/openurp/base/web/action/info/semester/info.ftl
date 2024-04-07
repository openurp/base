<div class="card card-info card-primary card-outline">
  <div class="card-header">
   <h3 class="card-title">${semester.schoolYear}学年度 ${semester.name}学期(${semester.beginOn?string('yyyy-MM-dd')}~${semester.endOn?string('yyyy-MM-dd')})</h3>
  </div>
  <div class="card-body">
<table class="infoTable">
   <tr>
     <td  style="text-align:center">周次</td>
     [#list weekdays as weekday]
     <td  style="text-align:center" [#if weekday_index=0 || !weekday_has_next]class="title" [/#if]>${weekday}</td>
     [/#list]
   </tr>
   [#list dates as weekDates]
   <tr style="text-align:center">
     <td >${weekDates_index+1}</td>
     [#list weekDates as wd]
     [#assign day_str= wd?string("dd")/]
     <td [#if wd_index=0 || wd_index==6]class="title"[/#if] style="text-align:center;[#if day_str="01"]font-weight: bold;[/#if]">
     <span [#if wd<semester.beginOn]class="text-muted"[/#if]>
     [#if day_str=="01"]${wd?string("yy-MM-dd")}[#else]${day_str}[/#if]
     </span>
     </td>
     [/#list]
     [#if weekDates?size<7]
       [#list 1..(7 - weekDates?size) as i]
         <td></td>
       [/#list]
     [/#if]
   </tr>
   [/#list]
 </table>
 <p>[#list semester.stages as stage]${stage.stage.name}(${stage.beginOn?string("yyyy-MM-dd")}~${stage.endOn?string("yyyy-MM-dd")})[/#list]</p>
 </div>
</div>
