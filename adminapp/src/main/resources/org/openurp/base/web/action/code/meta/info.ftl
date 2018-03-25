[#ftl]
[@b.head/]
[@b.toolbar title="代码信息"]
bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">名称</td>
    <td class="content" width="80%">${codeMeta.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">中文名称</td>
    <td class="content" width="80%">${codeMeta.title}</td>
  </tr>
  <tr>
    <td class="title" width="20%">代码类名</td>
    <td class="content" width="80%">${codeMeta.className!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">所在分类</td>
    <td class="content" width="80%">${(codeMeta.category.name)!}</td>
  </tr>
</table>
[@b.foot/]
