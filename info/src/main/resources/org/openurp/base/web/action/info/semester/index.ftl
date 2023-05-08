[@b.head/]

[#include "../info_macros.ftl"/]
[@info_header title="学期信息"/]

<div class="container-fluid" style="display:flex;justify-content:space-around;flex-wrap:wrap">
[@b.card style="width:100%" class="card-info card-primary card-outline"]
  [@b.card_header]
    [#assign c=calendar/]
    <h3 class="card-title">${c.name} (${c.firstWeekday}开始)
    <span class="badge badge-primary">${semesters?size}个学期</span>
    </h3>
    [@b.card_tools]
        <button type="button" class="btn btn-tool" data-card-widget="collapse">
          <i class="fas fa-minus"></i>
        </button>
    [/@]
    <span class="float-right" style="font-size:0.8rem;color: #999;" >有效期:${c.beginOn?string("yyyy-MM-dd")}~${(c.endOn?string("yyyy-MM-dd"))!}</span>
  [/@]
  [@b.card_body]
    <div class="row">
      <div class="col-4">
    [#assign semesters=semesters?sort_by("beginOn")?reverse/]
    <table class="table table-hover table-sm">
      <thead>
         <th>学年度</th>
         <th>学期</th>
         <th>起始结束</th>
      </thead>
      <tbody>
      [#list semesters as semester]
        [#if !semester.archived]
      <tr>
        <td>${semester.schoolYear}[#if semester.archived]<sup>归档</sup>[/#if]</td>
        <td>${semester.name}</td>
        <td>[@b.a href="!info?id="+semester.id target="semesterOf"+c.id]${semester.beginOn}~${semester.endOn}[/@]</td>
      </tr>
      [/#if]
      [/#list]
    </table>
    </div>
    [#if semesters?size>0]
    [@b.div class="col-8" href="!info?id="+semesters?first.id id="semesterOf"+c.id/]
    [/#if]
  [/@]
[/@]
</div>
[@b.foot/]
