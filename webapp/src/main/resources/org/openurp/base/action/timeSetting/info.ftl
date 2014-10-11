[#ftl]
[@b.head/]
[@b.toolbar title="时间设置信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">时间设置名称</td>
    <td class="content">${timeSetting.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">关联课程单元</td>
    <td class="content">
      [#list timeSetting.units as unit]${unit.name!}&nbsp;[/#list]
    </td>
  </tr>
</table>

[@b.foot/]