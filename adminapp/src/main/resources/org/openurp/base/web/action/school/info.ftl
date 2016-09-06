[#ftl]
[@b.head/]
[@b.toolbar title="学校信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${school.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">名称</td>
    <td class="content" >${school.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">科研机构</td>
    <td class="content" >${(school.institution.name)!}</td>
  </tr>
</table>

[@b.foot/]