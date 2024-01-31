[#ftl]
[@b.head/]
[@b.toolbar title="教室设备信息"/]
<div class="search-container">
    <div class="search-panel">
    [@b.form name="deviceSearchForm" action="!search" target="devicelist" title="ui.searchForm" theme="search"]
      [@b.textfield name="device.code" label="代码" maxlength="5000"/]
      [@b.textfields names="device.name;名称"/]
      [@b.select name="device.deviceType.id" label="设备类型" items=deviceTypes empty="..." /]
      [@b.select name="active" label="是否有效" items={"1":"是", "0":"否"} value="1" empty="..." /]
      <input type="hidden" name="orderBy" value="device.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="devicelist" href="!search?orderBy=device.code&active=1"/]
  </div>
</div>
[@b.foot/]
