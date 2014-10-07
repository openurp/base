[#ftl]
[@b.head/]
[@b.toolbar title="科研机构信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">科研机构名称</td>
    <td class="content">${institution.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${institution.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">英文名称</td>
    <td class="content" >${institution.enName!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">起始时间</td>
    <td class="content" >${institution.beginOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">终止时间</td>
    <td class="content" >${institution.endOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">备注</td>
    <td class="content">${institution.remark!}</td>
  </tr>
  <tr>
</table>

[@b.foot/]