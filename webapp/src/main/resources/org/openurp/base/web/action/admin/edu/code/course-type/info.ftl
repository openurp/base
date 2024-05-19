[#ftl]
[@b.head/]
[@b.toolbar title="课程类别信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${code.code}</td>
  </tr>
  <tr>
    <td class="title">名称</td>
    <td class="content">${code.name}</td>
  </tr>
  <tr>
    <td class="title">英文名</td>
    <td class="content">${code.enName!}</td>
  </tr>
  <tr>
    <td class="title">是否实践课</td>
    <td class="content">${(code.practical?string("是","否"))!}</td>
  </tr>
  <tr>
    <td class="title">生效日期</td>
    <td class="content" >${code.beginOn!}</td>
  </tr>
  <tr>
    <td class="title">失效日期</td>
    <td class="content" >${code.endOn!}</td>
  </tr>
  <tr>
    <td class="title">备注</td>
    <td class="content">${code.remark!}</td>
  </tr>
</table>

[@b.foot/]
