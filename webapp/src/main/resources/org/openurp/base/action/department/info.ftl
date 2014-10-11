[#ftl]
[@b.head/]
[@b.toolbar title="部门基本信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">部门名称</td>
    <td class="content">${department.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${department.code}</td>
  </tr>
  <tr>
   <td class="title" width="20%">是否教学部门</td>
   <td class="content">${(department.teaching?string("是","否"))!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">序号</td>
    <td class="content">${department.indexno}</td>
  </tr>
  <tr>
    <td class="title" width="20%">生效时间</td>
    <td class="content" >${department.beginOn!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">失效时间</td>
    <td class="content" >${department.endOn!}</td>
  </tr>
</table>

[@b.foot/]