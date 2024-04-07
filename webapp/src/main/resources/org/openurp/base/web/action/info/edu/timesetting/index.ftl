[@b.head/]

[#include "../../info_macros.ftl"/]
[@info_header title="作息时间"/]

<div class="container-fluid" style="display:flex;justify-content:space-around;flex-wrap:wrap">
[#list timesettings as ts]
[@b.card style="width:360px"]
  [@b.card_header]
    <h3 class="card-title">${ts.name} (每节${ts.minutesPerUnit}分)
    [#if ts.campus??]<span class="badge badge-primary">${ts.campus.name}</span>[/#if]
    </h3>
    [@b.card_tools]
        <button type="button" class="btn btn-tool" data-card-widget="collapse">
          <i class="fas fa-minus"></i>
        </button>
    [/@]
    <span class="float-right text-muted" style="font-size:0.8rem;" >有效期:${ts.beginOn?string("yyyy-MM-dd")}~${(ts.endOn?string("yyyy-MM-dd"))!}</span>
  [/@]
  [@b.card_body]
    <table class="table table-hover table-sm">
      <thead>
         <th>节次</th>
         <th>名称</th>
         <th>时段</th>
         <th>时间</th>
      </thead>
      <tbody>
      [#list ts.units?sort_by("indexno") as courseUnit]
      <tr>
        <td>${courseUnit.indexno}</td>
        <td>${courseUnit.name}</td>
        <td>${courseUnit.part.name}</td>
        <td>${courseUnit.beginAt}~${courseUnit.endAt}</td>
      </tr>
      [/#list]
    </table>
  [/@]
[/@]
[/#list]

</div>
[@b.foot/]
