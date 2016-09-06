[#ftl]
[@b.head/]
[@b.toolbar title="政治面貌信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${politicalStatus.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">名称</td>
    <td class="content" >${politicalStatus.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">英文名称</td>
    <td class="content" >${politicalStatus.enName!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">生效时间</td>
    <td class="content" >${politicalStatus.beginOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">失效时间</td>
    <td class="content" >${politicalStatus.endOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">备注</td>
    <td class="content">${politicalStatus.remark!}</td>
  </tr>
</table>

[@b.foot/]