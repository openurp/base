[#ftl]
[@b.head/]
[@b.toolbar title="项目"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="projectSearchForm" action="!search" target="projectlist" title="ui.searchForm" theme="search"]

      [@b.textfields names="project.name;名称"/]
      <input type="hidden" name="orderBy" value="project.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="projectlist" href="!search?orderBy=project.code"/]
  </div>
</div>
[@b.foot/]
