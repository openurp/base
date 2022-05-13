[#ftl]
[@b.head/]
[@b.grid items=majors var="major"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="6%" property="code" title="代码"]${major.code}[/@]
    [@b.col width="21%" property="name" title="名称"][@b.a href="!info?id=${major.id}"]${major.name}[/@][/@]
    [@b.col width="40%" title="院系"]
    [#assign departs = [] /]
    [#list major.journals as j][#if major.endOn?? || !(j.endOn??)] [#if !departs?seq_contains(j.depart)][#assign departs = departs+[j.depart]][/#if][/#if][/#list]
    [#list departs as d]${d.name}[#if d_has_next] [/#if][/#list]
    [/@]
    [@b.col width="9%" title="培养层次"]
    [#assign levels = [] /]
    [#list major.journals as j][#if major.endOn?? || !(j.endOn??)] [#if !levels?seq_contains(j.level)][#assign levels = levels+[j.level]][/#if][/#if][/#list]
    [#list levels as d]${d.name}[#if d_has_next] [/#if][/#list]
    [/@]
    [@b.col width="7%" title="学科"]
    [#assign categories = [] /]
    [#list major.disciplines as d][#if (d.category??) && !categories?seq_contains(d.category)][#assign categories = categories+[d.category]][/#if][/#list]
    [#list categories as d]${d.name}[#if d_has_next] [/#if][/#list]
    [@b.col width="8%" property="beginOn" title="开始于"]${(major.beginOn?string('yyyy-MM'))!}[/@]
    [/@]
  [/@]
[/@]
[@b.foot/]
