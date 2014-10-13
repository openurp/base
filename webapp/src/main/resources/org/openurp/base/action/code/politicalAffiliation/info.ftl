[#ftl]
[@b.head/]
[@b.toolbar title="政治面貌信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${politicalAffiliation.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">名称</td>
    <td class="content" >${politicalAffiliation.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">英文名称</td>
    <td class="content" >${politicalAffiliation.enName!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">生效时间</td>
    <td class="content" >${politicalAffiliation.beginOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">失效时间</td>
    <td class="content" >${politicalAffiliation.endOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">备注</td>
    <td class="content">${politicalAffiliation.remark!}</td>
  </tr>
</table>

[@b.foot/]