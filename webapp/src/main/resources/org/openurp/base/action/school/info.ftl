[#ftl]
[@b.head/]
[@b.toolbar title="学校信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">学校名称</td>
    <td class="content" >${schoolBean.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${schoolBean.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">科研机构</td>
    <td class="content" >${schoolBean.institution!}</td>
  </tr>
</table>

[@b.foot/]