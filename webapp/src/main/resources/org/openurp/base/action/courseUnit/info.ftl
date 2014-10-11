[#ftl]
[@b.head/]
[@b.toolbar title="小节信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">小节名称</td>
    <td class="content">${courseUnit.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">序号</td>
    <td class="content">${courseUnit.indexno}</td>
  </tr>
  <tr>
    <td class="title" width="20%">开始时间</td>
    <td class="content" >${courseUnit.beginTime}</td>
  </tr>
  <tr>
    <td class="title" width="20%">失效时间</td>
    <td class="content" >${courseUnit.endTime}</td>
  </tr>
</table>

[@b.foot/]