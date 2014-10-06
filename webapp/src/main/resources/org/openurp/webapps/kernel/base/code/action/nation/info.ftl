[#ftl]
[@b.head/]
[@b.toolbar title="民族信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content" >${nation.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">字母代码</td>
    <td class="content" >${nation.alphaCode}</td>
  </tr>
  <tr>
    <td class="title" width="20%">民族名称</td>
    <td class="content" >${nation.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">英文名称</td>
    <td class="content" >${nation.enName!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">起始时间</td>
    <td class="content" >${nation.beginOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">终止时间</td>
    <td class="content" >${nation.endOn!}</td>
  </tr>
  <tr>
    <td class="title">备注</td>
    <td class="content">${nation.remark!}</td>
  </tr>
  <tr>
</table>

[@b.foot/]