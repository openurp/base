[#ftl]
[@b.head/]
[@b.toolbar title="语种信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">语种名称</td>
    <td class="content">${language.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${language.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">英文名称</td>
    <td class="content">${language.enName!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">起始时间</td>
    <td class="content" >${language.beginOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">终止时间</td>
    <td class="content" >${language.endOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">备注</td>
    <td class="content">${language.remark!}</td>
  </tr>
  <tr>
</table>

[@b.foot/]