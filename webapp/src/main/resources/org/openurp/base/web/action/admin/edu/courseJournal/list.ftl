[#ftl]
[@b.head/]
[@b.grid items=journals var="journal"]
  [@b.gridbar]
    bar.addItem("新增",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("删除",action.remove());
  [/@]
  [#assign creditHourTitle]学时([#list teachingNatures as n]${n.name}[#sep]+[/#list])[/#assign]
  [@b.row]
    [@b.boxcol /]
    [@b.col property="name" title="名称"]${journal.name}[/@]
    [@b.col property="enName" title="英文称"]${journal.enName!}[/@]
    [@b.col width="8%" property="department.name" title="开课院系"]
      ${journal.department.shortName!journal.department.name}
    [/@]
    [@b.col width="10%" property="creditHours" title=creditHourTitle]
      ${journal.creditHours}
      <span [#if !journal.creditHourIdentical]style="color:red"[#else]class="text-muted"[/#if]>([#list teachingNatures as n]${journal.getHour(n)!0}[#sep]+[/#list])</span>
    [/@]
    [@b.col width="8%" property="examMode.name" title="考核方式"/]
    [@b.col title="课程标签" width="8%"]
      <div class="text-ellipsis">[#list journal.tags as t]${t.name}[#sep]&nbsp;[/#list]</div>
    [/@]
    [@b.col width="10%" property="beginOn" title="有效期"]${journal.beginOn?string("yy-MM")}~${(journal.endOn?string("yy-MM"))!}[/@]
  [/@]
  [/@]
[@b.foot/]
