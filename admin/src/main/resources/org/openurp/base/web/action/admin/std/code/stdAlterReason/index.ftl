[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="stdAlterReasonSearchForm" action="!search" target="stdAlterReasonlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="stdAlterReason.code;代码"/]
      [@b.textfields names="stdAlterReason.name;名称"/]
      <input type="hidden" name="orderBy" value="stdAlterReason.code"/>
    [/@]
    </div>
    <div class="search-list">
     [@b.div id="stdAlterReasonlist" href="!search?orderBy=stdAlterReason.code"/]
    </div>
</div>
[@b.foot/]
