[#ftl]
[@b.head/]
[@b.toolbar title="假日安排信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">名称</td>
    <td class="content">${holiday.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">生效时间</td>
    <td class="content" >${holiday.beginOn}</td>
  </tr>
  <tr>
    <td class="title" width="20%">失效时间</td>
    <td class="content" >${holiday.endOn}</td>
  </tr>
</table>

[@b.foot/]
