[#ftl]
[@b.head/]
[@b.toolbar title="部门变迁信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="table table-sm table-detail">
  <tr>
    <td class="title" width="20%">原部门代码</td>
    <td class="content">${transition.from.code!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">原部门名称</td>
    <td class="content">${transition.from.name!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">目标部门代码</td>
    <td class="content">${transition.to.code!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">目标部门名称</td>
    <td class="content">${transition.to.name!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">生效日期</td>
    <td class="content">${transition.effectiveOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">备注</td>
    <td class="content">${transition.remark!}</td>
  </tr>
</table>

[@b.foot/]
