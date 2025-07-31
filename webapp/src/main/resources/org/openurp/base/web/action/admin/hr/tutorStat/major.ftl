[@b.head/]
[#include "../tutor/nav.ftl"/]
  [#macro displayCounter counter,params]
    [#if counter?? && counter?size>0]
      [#if params?size >0]
        [#assign paramStr][#list params as k,v]${k}=${v}[#sep]&[/#list][/#assign]
        <a href="${b.url('tutor!list?orderBy=staff.code &'+paramStr)}" data-toggle="modal" data-target="#tutorList">${counter?first}</a>
      [#else]
        ${counter?first}
      [/#if]
    [/#if]
  [/#macro]

  [#macro displayMatrix majorId dy dxs dyLabel dxLabels,paramNames]
    <table class="table table-bordered table-striped table-sm" style="text-align: center;">
      <caption style="text-align: center;caption-side: top;padding-bottom: 0.25rem;">${majors.get(majorId).name}学科导师分布</caption>
      <thead>
        <tr>
          <th width="10%" rowspan="2" style="vertical-align: middle;">${dyLabel}</th>
          [#list dxLabels as dxLabel]
          [#assign dx = dxs[dxLabel_index]/]
          <th colspan="${matrix.getColumn(dx).values?size}" style="vertical-align: middle;">${dxLabel}</th>
          [/#list]
          <th width="5%">合计</th>
        </tr>
        <tr>
          [#list dxs as dx]
            [#assign d = matrix.getColumn(dx)/]
            [#list d.keys?sort as dk]
            <th>[#assign dkv = d.get(dk)/] [#if dkv?is_hash]${dkv.name}[#else]${dkv}[/#if]</th>
            [/#list]
          [/#list]
        </tr>
      </thead>
      <tbody>
      [#assign lmatrix = matrix.groupBy(dy)/]
      [#assign rows=0/]
      [#assign yDimensionValues=matrix.getColumn(dy).values/]
      [#list yDimensionValues?keys as v]
      <tr>
        <td>${yDimensionValues.get(v).name}</td>
        [#list dxs as dx]
          [#assign d = matrix.getColumn(dx)/]
          [#assign lgmatrix = matrix.groupBy(dy+","+dx)/]
          [#list d.keys?sort as dk]
          [#assign params={"major.id":majorId,"staff.title.grade.id":v!'null',paramNames[dx_index]:(dk!'null')?string} /]
          <td>[@displayCounter lgmatrix.getCounter(v,dk)!,params/]</td>
          [/#list]
        [/#list]
        <td>[@displayCounter lmatrix.getCounter(v)!,{} /] </td>
      </tr>
      [#assign rows = rows + 1/]
      [/#list]

     [#if rows>1]
      <tr>
        <td>合计</td>
        [#list dxs as dx]
          [#assign gmatrix = matrix.groupBy(dx)/]
          [#assign dvalues = matrix.getColumn(dx).values/]
          [#list dvalues?keys?sort as g]
          <td>[@displayCounter gmatrix.getCounter(g)!,{} /]</td>
          [/#list]
        [/#list]
        <td>[@displayCounter gmatrix.sum ,{}/]</td>
      </tr>
      [/#if]
      </tbody>
    </table>
  [/#macro]
  <div class="container">
    [#list majors?keys as majorId]
    [#assign matrix = matrixes.get(majorId)/]
      [@displayMatrix majorId,"grade",["age","degreeLevel","eduLevel","degreeAwardOutside","parttime"],
        "职称级别",["年龄分布","学位层次","导师类型","学位授予单位","是否兼职"],["age","staff.degreeLevel.id","majorEduLevelId","degreeAwardOutside","staff.parttime"]  /]
    [/#list]
  </div>
  [@b.dialog id="tutorList" title="导师信息" /]
  [@b.foot/]
