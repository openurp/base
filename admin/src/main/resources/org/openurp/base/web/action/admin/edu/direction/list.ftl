[#ftl]
[@b.head/]
[@b.grid items=directions var="direction"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="8%" property="code" title="代码"]${direction.code}[/@]
    [@b.col width="15%" property="name" title="名称"][@b.a href="!info?id=${direction.id}"]${direction.name}[/@][/@]
    [@b.col width="23%" property="enName" title="英文名"]${direction.engName!}[/@]
    [@b.col width="15%" property="major" title="专业"]${direction.major.name!}[/@]
    [@b.col width="20%" title="院系"]
    [#assign departs = [] /]
    [#list direction.journals as j][#if !(j.endOn??) && !departs?seq_contains(j.depart)][#assign departs = departs+[j.depart]][/#if][/#list]
    [#list departs as d]${d.name}[#if d_has_next] [/#if][/#list]
    [/@]
    [@b.col width="10%" title="培养层次"]
    [#assign levels = [] /]
    [#list direction.journals as j][#if !(j.endOn??) && !levels?seq_contains(j.level)][#assign levels = levels+[j.level]][/#if][/#list]
    [#list levels as d]${d.name}[#if d_has_next] [/#if][/#list]
    [/@]
  [/@]
[/@]
[@b.foot/]