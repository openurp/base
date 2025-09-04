[#ftl]
[@b.head/]
[@b.toolbar title="校区信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="table table-sm table-detail">
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${campus.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">名称</td>
    <td class="content">${campus.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">英文名称</td>
    <td class="content">${campus.enName!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">简称</td>
    <td class="content">${campus.shortName!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">生效时间</td>
    <td class="content" >${campus.beginOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">失效时间</td>
    <td class="content" >${campus.endOn!}</td>
  </tr>
</table>

[@b.foot/]
