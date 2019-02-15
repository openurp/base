[#ftl]
[@b.head/]
<table class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="nationSearchForm" action="!search" target="nationlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="nation.code;代码"/]
      [@b.textfields names="nation.name;名称"/]
      [@b.textfields names="nation.enName;英文名称"/]
      [@b.textfields names="nation.alphaCode;字母代码"/]
      <input type="hidden" name="orderBy" value="nation.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="nationlist" href="!search?orderBy=nation.code"/]</td>
  </tr>
</table>
[@b.foot/]
