[#ftl]
[@b.head/]
[@b.toolbar title="小节信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="table table-sm table-detail">
  <tr>
    <td class="title" width="20%">名称</td>
    <td class="content">${courseUnit.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">序号</td>
    <td class="content">${courseUnit.indexno}</td>
  </tr>
  <tr>
    <td class="title" width="20%">时段</td>
    <td class="content">${courseUnit.part.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">开始时间</td>
    <td class="content" >${courseUnit.beginAt}</td>
  </tr>
  <tr>
    <td class="title" width="20%">失效日期</td>
    <td class="content" >${courseUnit.endAt}</td>
  </tr>
</table>

[@b.foot/]
