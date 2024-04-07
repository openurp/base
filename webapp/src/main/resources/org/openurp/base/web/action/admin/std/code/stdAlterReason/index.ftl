[#ftl]
[@b.head/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="stdAlterReasonSearchForm" action="!search" target="stdAlterReasonlist" title="ui.searchForm" theme="search"]
      [@b.textfields names="stdAlterReason.code;代码"/]
      [@b.textfields names="stdAlterReason.name;名称"/]
      [@b.select label="是否有效"  name="active" items={"1":"是","0":"否"} value="1" empty="..."/]
      <input type="hidden" name="orderBy" value="stdAlterReason.code"/>
    [/@]
    </div>
    <div class="search-list">
     [@b.div id="stdAlterReasonlist" href="!search?orderBy=stdAlterReason.code&active=1"/]
    </div>
</div>
[@b.foot/]
