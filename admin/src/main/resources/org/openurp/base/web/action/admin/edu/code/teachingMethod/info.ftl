[#ftl]
[@b.head/]
[@b.toolbar title="授课方式信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${teachingMethod.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">名称</td>
    <td class="content">${teachingMethod.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">英文名</td>
    <td class="content">${teachingMethod.enName!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">生效日期</td>
    <td class="content" >${teachingMethod.beginOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">失效日期</td>
    <td class="content" >${teachingMethod.endOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">备注</td>
    <td class="content">${teachingMethod.remark!}</td>
  </tr>
</table>

[@b.foot/]
