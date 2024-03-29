[#ftl]
[@b.head/]
[@b.toolbar title="行政班级信息"]
  bar.addItem("导入","importForm()")
  function importForm(){
    bg.form.submit(document.indexForm,"${b.url('!importForm')}","_blank")
  }
[/@]

<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="squadlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="squad.grade.name;年级"/]
      [@b.textfield name="squad.code" label="代码" maxlength="5000"/]
      [@b.textfields names="squad.name;名称"/]
      [@b.select style="width:100px" name="squad.department.id" label="所属院系" items=departments option="id,name" empty="..." /]
      [@base.code type="education-levels" name="squad.level.id" label="培养层次" empty="..." /]
      [@base.code type="std-types" name="squad.stdType.id" label="学生类别" empty="..." /]
      [@b.textfield name="staff.name" label="管理人员" placeholder="姓名"/]
      [@b.select style="width:100px" name="squad.campus.id" label="校区" items=campuses option="id,name" empty="..." /]
      <input type="hidden" name="orderBy" value="squad.code desc"/>
      [@b.select style="width:100px" name="active" value="1" label="是否在校" items={"1":"是", "0":"否"} empty="..." /]
    [/@]
    </div>
    <div class="search-list">[@b.div id="squadlist" href="!search?active=1&orderBy=squad.code desc"/]
    </td>
  </tr>
</table>
<br><br>
[@b.form name="indexForm" action="!importForm"/]
[@b.foot/]
