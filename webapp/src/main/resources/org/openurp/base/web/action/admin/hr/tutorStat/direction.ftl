[@b.head/]
[#include "../tutor/nav.ftl"/]
<div class="container">
  [#list levels as level]
  [#assign directionTutors = levelDirectionTutors.get(level)/]
  <table class="table table-bordered table-striped table-sm" style="text-align: center;">
    <caption style="text-align: center;caption-side: top;padding-bottom: 0.25rem;">${level.name} 研究方向导师名单</caption>
    <thead>
      <tr>
        <th width="6%">序号</th>
        <th width="7%">专业代码</th>
        <th width="12%">专业名称</th>
        <th width="7%">方向代码</th>
        <th>专业方向</th>
        <th width="6%">导师人数</th>
        <th width="40%">导师</th>
      </tr>
    </thead>
    <tbody>
      [#list directionTutors?keys?sort_by(["code"]) as direction]
      <tr>
        <td>${direction_index+1}</td>
        <td>${direction.major.code}</td>
        <td>${direction.major.name}</td>
        <td>${direction.code}</td>
        <td>${direction.name}</td>
        <td>${directionTutors.get(direction)?size}</td>
        <td>
        [#list directionTutors.get(direction)?sort_by("code") as t]
        <a href="${b.url('/info/hr/staff/'+t.id)}" data-toggle="modal" data-target="#staffInfo">${t.name}</a>
        [#sep],
        [/#list]
        </td>
      </tr>
      [/#list]
    </tbody>
  </table>
  [/#list]
  [@b.dialog id="staffInfo" title="教师信息" /]
</div>

[@b.foot/]
