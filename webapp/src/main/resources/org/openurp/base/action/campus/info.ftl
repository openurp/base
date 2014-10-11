[#ftl]
[@b.head/]
[@b.toolbar title="校区信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">校区名称</td>
    <td class="content">${campus.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${campus.code}</td>
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