[#ftl]
[@b.head/]
[@b.toolbar title="实验室基本信息"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="laboratorySearchForm" action="!search" target="laboratorylist" title="ui.searchForm" theme="search"]
      [@b.textfield name="laboratory.code" label="代码" maxlength="5000"/]
      [@b.textfields names="laboratory.name;名称"/]
      [@b.select name="laboratory.campus.id" label="校区" items=campuses option="id,name" empty="..." /]
      [@b.select name="virtual" label="虚拟教室" items={"1":"是", "0":"否"} empty="..." /]
      [@b.select name="active" label="是否有效" items={"1":"是", "0":"否"} value="1" empty="..." /]
      <input type="hidden" name="orderBy" value="laboratory.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="laboratorylist" href="!search?orderBy=laboratory.code&active=1"/]
  </div>
</div>
[@b.foot/]
