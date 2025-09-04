[#ftl]
[@b.head/]
[@b.toolbar title="学期阶段信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="table table-sm table-detail">
  <tr>
    <td class="title" width="20%">名称</td>
    <td class="content">${calendarStage.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">生效日期</td>
    <td class="content" >${calendarStage.beginOn}</td>
  </tr>
  <tr>
    <td class="title" width="20%">失效日期</td>
    <td class="content" >${calendarStage.endOn}</td>
  </tr>
</table>

[@b.foot/]
