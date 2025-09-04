[#ftl]
[@b.head/]
[@b.toolbar title="学年学期信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="table table-sm table-detail">
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${semester.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">名称</td>
    <td class="content">${semester.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">学年度</td>
    <td class="content" >${semester.schoolYear}</td>
  </tr>
  <tr>
    <td class="title" width="20%">日历方案</td>
    <td class="content" >${semester.calendar.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">每周开始时间</td>
    <td class="content" >${semester.calendar.firstWeekday}</td>
  </tr>
  <tr>
    <td class="title" width="20%">生效日期</td>
    <td class="content" >${semester.beginOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">失效日期</td>
    <td class="content" >${semester.endOn!}</td>
  </tr>
</table>

[@b.foot/]
