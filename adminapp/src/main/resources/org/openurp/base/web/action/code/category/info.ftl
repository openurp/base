[#ftl]
[@b.head/]
[@b.toolbar title="代码类型信息"]
bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">代码类名</td>
    <td class="content" width="80%">${codeCategory.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">序号</td>
    <td class="content" width="80%">${codeCategory.indexno}</td>
  </tr>
  <tr>
    <td class="title" width="20%">父类菜单</td>
    <td class="content" width="80%">${(codeCategory.parent.name)!}</td>
  </tr>
</table>
[@b.foot/]
