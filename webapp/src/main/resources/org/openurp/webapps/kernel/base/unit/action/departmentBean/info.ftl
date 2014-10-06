[#ftl]
[@b.head/]
[@b.toolbar title="部门信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">部门名称</td>
    <td class="content">${departmentBean.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${departmentBean.code}</td>
  </tr>
  <tr>
   <td class="title" width="20%">是否教学部门</td>
   <td class="content">${(departmentBean.teaching?string("是","否"))!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">序号</td>
    <td class="content">${departmentBean.indexno}</td>
  </tr>
  <tr>
    <td class="title" width="20%">起始时间</td>
    <td class="content" >${departmentBean.beginOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">终止时间</td>
    <td class="content" >${departmentBean.endOn!}</td>
  </tr>
  <tr>
</table>

[@b.foot/]