[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="stdLabelTypeSearchForm" action="!search" target="stdLabelTypelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="stdLabelType.code;代码"/]
      [@b.textfields names="stdLabelType.name;名称"/]
      <input type="hidden" name="orderBy" value="stdLabelType.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="stdLabelTypelist" href="!search?orderBy=stdLabelType.code"/]
  </div>
</div>
[@b.foot/]
