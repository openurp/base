[#ftl]
[@b.head/]
[@b.toolbar title="小节"/]
<table class="indexpanel">
  <tr>
    <td class="index_view" >
    [@b.form name="courseUnitSearchForm" action="!search" target="courseUnitlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="courseUnit.name;名称"/]
      [@b.textfields names="courseUnit.indexno;序号"/]
      <input type="hidden" name="orderBy" value="courseUnit.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="courseUnitlist" href="!search?orderBy=courseUnit.id"/]
    </td>
  </tr>
</table>
[@b.foot/]