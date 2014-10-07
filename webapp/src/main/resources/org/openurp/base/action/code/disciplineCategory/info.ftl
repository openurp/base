[#ftl]
[@b.head/]
[@b.toolbar title="学科门类信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">学科门类名称</td>
    <td class="content">${disciplineCategory.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${disciplineCategory.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">英文名称</td>
    <td class="content" >${disciplineCategory.enName!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">起始时间</td>
    <td class="content" >${disciplineCategory.beginOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">终止时间</td>
    <td class="content" >${disciplineCategory.endOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">备注</td>
    <td class="content">${disciplineCategory.remark!}</td>
  </tr>
  <tr>
</table>

[@b.foot/]