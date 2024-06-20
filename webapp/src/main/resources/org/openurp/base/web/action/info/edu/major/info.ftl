[#ftl]
<div style="margin:0px 20px">

  <div class="card card-info card-primary card-outline">
    <div class="card-header">
      <h3 class="card-title">${major.name} 基本信息</h3>
    </div>
    <table class="infoTable">
      <tr>
        <td class="title" width="15%">代码</td>
        <td class="content">${major.code}</td>
        <td class="title" width="15%">名称</td>
        <td class="content">${major.name}</td>
      </tr>
      <tr>
        <td class="title">英文名</td>
        <td class="content">${major.enName!}</td>
        <td class="title">简称</td>
        <td class="content">${major.shortName!}</td>
      </tr>
      <tr>
        <td class="title">学科信息</td>
        <td class="content">[#list major.disciplines as md]${md.category.name} ${md.disciplineCode!"--"} [#if md_has_next]<br>[/#if][/#list]</td>
        <td class="title">学制</td>
        <td class="content">[#list major.schoolLengths as sl]${sl.level.name}(${sl.fromGrade}~${sl.toGrade!}) ${sl.normal}年(${sl.minimum}~${sl.maximum}) [#if sl_has_next]<br>[/#if][/#list]</td>
      </tr>
      <tr>
        <td class="title">有效期</td>
        <td class="content" >${major.beginOn}~${major.endOn!"现在"}</td>
        <td class="title">备注</td>
        <td class="content">${major.remark!}</td>
      </tr>
    </table>
 </div>

   <div class="card card-info card-primary card-outline">
      <div class="card-header">
        <h3 class="card-title">开设院系<span class="badge badge-primary">${major.journals?size}</span></h3>
      </div>
      <table  class="table table-hover table-sm table-striped" style="text-align:center">
        <thead>
          <tr>
           <th style="background-color:#F5EDDB" width="8%">序号</th>
           <th style="background-color:#F5EDDB">院系</th>
           <th style="background-color:#F5EDDB">培养层次</th>
           <th style="background-color:#F5EDDB">有效期</th>
          </tr>
        </thead>
        [#list major.journals?sort_by(["depart","code"]) as mj]
        <tr>
          <td>${mj_index+1}</td>
          <td>${mj.depart.name}</td>
          <td>${mj.level.name}</td>
          <td>${mj.beginOn?string("yyyy-MM-dd")}~${(mj.endOn?string("yyyy-MM-dd"))!"现在"}</td>
        </tr>
        [/#list]
      </table>
   </div>
   [#assign actives=[]]
   [#list major.directions as d]
     [#if d.active][#assign actives=actives+[d]][/#if]
   [/#list]
[#if actives?size>0]
   <div class="card card-info card-primary card-outline">
      <div class="card-header">
        <h3 class="card-title">专业方向<span class="badge badge-primary">${actives?size}</span></h3>
      </div>
        <table class="table table-hover table-sm table-striped" style="text-align:center">
          <thead>
            <tr>
             <th style="background-color:#F5EDDB" width="8%">代码</th>
             <th style="background-color:#F5EDDB" width="20%">名称</th>
             <th style="background-color:#F5EDDB" width="34%">英文名</th>
             <th style="background-color:#F5EDDB" width="15%">院系</th>
             <th style="background-color:#F5EDDB" width="8%">层次</th>
             <th style="background-color:#F5EDDB" width="15%">有效期</th>
            </tr>
          </thead>
          [#list actives?sort_by("code") as d]
          <tr>
            <td>${d.code}</td>
            <td>${d.name}</td>
            <td><span style="font-size:0.8em">${d.enName!}</span></td>
            [#assign departs = [] /]
            [#assign levels = [] /]
            [#list d.journals as j][#if !(j.endOn??) && !levels?seq_contains(j.level)][#assign levels = levels+[j.level]][/#if][/#list]
            [#list d.journals as j][#if !(j.endOn??) && !departs?seq_contains(j.depart)][#assign departs = departs+[j.depart]][/#if][/#list]
            <td>[#list departs as dp]${dp.name}[#if dp_has_next],[/#if][/#list]</td>
            <td>[#list levels as dp]${dp.name}[#if dp_has_next],[/#if][/#list]</td>
            <td>${d.beginOn?string("yyyy-MM-dd")}~${(d.endOn?string("yyyy-MM-dd"))!"现在"}</td>
          </tr>
          [/#list]
        </table>
   </div>
[/#if]
</div>
