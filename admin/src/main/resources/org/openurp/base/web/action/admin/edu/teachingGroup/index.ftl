[#ftl]
[@b.head/]
[@b.toolbar title="教研室信息"]
  bar.addItem("导入","importForm()")
  function importForm(){
    bg.form.submit(document.indexForm,"${b.url('!importForm')}","_blank")
  }
[/@]

<div class="search-container">
    <div class="search-panel">
    [@b.form name="searchForm" action="!search" target="teachingGrouplist" title="ui.searchForm" theme="search"]
      [@b.textfields names="teachingGroup.code;代码"/]
      [@b.textfields names="teachingGroup.name;名称"/]
      [@b.select style="width:100px" name="teachingGroup.department.id" label="所属院系" items=departments option="id,name" empty="..." /]
      <input type="hidden" name="orderBy" value="teachingGroup.code desc"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="teachingGrouplist" href="!search?active=1&orderBy=teachingGroup.code desc"/]
    </td>
  </tr>
</table>
<br><br>
[@b.form name="indexForm" action="!importForm"/]
[@b.foot/]
