[#ftl]
[@b.head/]
[@b.toolbar title="教学日历方案信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="table table-sm table-detail">
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${calendar.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">名称</td>
    <td class="content">${calendar.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">每周开始时间</td>
    <td class="content" >${calendar.firstWeekday}</td>
  </tr>
  <tr>
    <td class="title" width="20%">生效日期</td>
    <td class="content" >${calendar.beginOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">失效日期</td>
    <td class="content" >${calendar.endOn!}</td>
  </tr>
</table>

[@b.foot/]
