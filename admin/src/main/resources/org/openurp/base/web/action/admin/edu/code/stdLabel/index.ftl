[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="stdLabelSearchForm" action="!search" target="stdLabellist" title="ui.searchForm" theme="search"]
      [@b.textfields names="stdLabel.code;代码"/]
      [@b.textfields names="stdLabel.name;名称"/]
      [@b.textfields names="stdLabel.labelType;标签类型"/]
      <input type="hidden" name="orderBy" value="stdLabel.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="stdLabellist" href="!search?orderBy=stdLabel.code"/]
  </div>
</div>
[@b.foot/]
