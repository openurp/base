[@b.head/]
[#include "../tutor/nav.ftl"/]
[#assign stdLimit=15/]
  [#macro displayCounter counter,params]
    [#if counter?? && counter?size>0]
      [#if params?size >0]
        [#assign paramStr][#list params as k,v]${k}=${v}[#sep]&[/#list][/#assign]
        <a href="${b.url('!stdList?orderBy=std.code&'+paramStr)}" data-toggle="modal" data-target="#tutorStdList">${counter?first}</a>
      [#else]
        ${counter?first}
      [/#if]
    [/#if]
  [/#macro]

  [#macro displayMatrix depart dy dxs dyLabel dxLabels,paramNames]
    <h2 id="depart_${depart.id}" style="text-align: center;caption-side: top;padding-bottom: 0.25rem;font-size: 0.9rem;margin: 0px;" class="text-muted" >${depart.name}导师带学生人数分布</h2>
    <table class="table table-bordered table-striped table-sm" style="text-align: center;">
      <thead>
        <tr>
          <th width="5%" rowspan="2" style="vertical-align: middle;">序号</th>
          <th width="5%" rowspan="2" style="vertical-align: middle;">工号</th>
          <th width="10%" rowspan="2" style="vertical-align: middle;">${dyLabel}</th>
          <th width="10%" rowspan="2" style="vertical-align: middle;">职称</th>
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
            <th>${d.get(dk)!}</th>
            [/#list]
          [/#list]
        </tr>
      </thead>
      <tbody>
      [#assign lmatrix = matrix.groupBy(dy)/]
      [#assign rows=0/]
      [#assign yDimensionValues=matrix.getColumn(dy).values/]
      [#assign teacherIdx=1/]
      [#list teachers as teacher]
      [#if lmatrix.getCounter(teacher.id)??]

      [#assign v = teacher.id/]
      <tr>
        <td>${teacherIdx}[#assign teacherIdx = teacherIdx+1/]</td>
        <td>${teacher.code}</td>
        <td>${teacher.name}</td>
        <td>${(teacher.staff.title.name)!}</td>
        [#list dxs as dx]
          [#assign d = matrix.getColumn(dx)/]
          [#assign lgmatrix = matrix.groupBy(dy+","+dx)/]
          [#list d.keys?sort as dk]
          [#assign params={"std.tutor.id":teacher.id,paramNames[dx_index]:(dk!'null')?string} /]
          <td>[@displayCounter lgmatrix.getCounter(v,dk)!,params/]</td>
          [/#list]
        [/#list]
        <td>[@displayCounter lmatrix.getCounter(v)!,{} /] </td>
      </tr>
      [/#if]
      [#assign rows = rows + 1/]
      [/#list]

     [#if rows>1]
      <tr>
        <td colspan="4">合计</td>
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

<div class="container-fluid">
  <div class="row">
    <div class="col-3">
      <div class="card sticky-top">
        <div class="card-header">
          院系列表 <span class="badge badge-primary">${departs?size}</span>
        </div>
        <div class="card-body" style="padding-top: 0px;overflow:scroll;max-height:700px;">
          <ul class="nav nav-pills flex-column">
            [#list departs as depart]
            <li class="nav-item">
              <a href="${b.url('!std')}#depart_${depart.id}" class="nav-link">${depart.name}
                <span class="badge badge-primary float-right">${departTeachers.get(depart)?size}</span>
              </a>
            </li>
            [/#list]
          </ul>
        </div>
      </div>
    </div>
    <div class="col-9" style="padding:0px 20px">
      [#list departs as depart]
      [#assign matrix = matrixes.get(depart.id)/]
        [@displayMatrix depart,"tutor",["level","graduationDeferred"],
          "导师",["培养层次","是否延期"],["std.level.id","std.graduationDeferred"]  /]
      [/#list]
    </div>
  </div>
</div>

  [@b.dialog id="tutorStdList" title="导师学生信息" /]
  [@b.foot/]
