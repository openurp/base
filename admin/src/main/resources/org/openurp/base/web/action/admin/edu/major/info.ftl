[#ftl]
[@b.head/]
[@b.toolbar title="专业信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<div style="margin:0px 20px">

  <div class="card card-info card-primary card-outline">
    <div class="card-header">
      <h3 class="card-title">基本信息</h3>
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
      <tr>
        <td class="title">最后修改</td>
        <td class="content" colspan="3">${major.updatedAt?string('yyyy-MM-dd HH:mm:ss')}</td>
      </tr>
    </table>
 </div>

   <div class="card card-info card-primary card-outline">
      <div class="card-header">
        <h3 class="card-title">专业所属院系<span class="badge badge-primary">${major.journals?size}</span></h3>
      </div>
      <table class="infoTable" style="text-align:center">
        <tr>
         <td style="background-color:#F5EDDB" width="8%">序号</td>
         <td style="background-color:#F5EDDB">院系</td>
         <td style="background-color:#F5EDDB">学历层次</td>
         <td style="background-color:#F5EDDB">有效期</td>
        </tr>
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

   <div class="card card-info card-primary card-outline">
      <div class="card-header">
        <h3 class="card-title">专业方向<span class="badge badge-primary">${major.directions?size}</span></h3>
      </div>
        <table class="infoTable" style="text-align:center">
          <tr>
           <td style="background-color:#F5EDDB" width="8%">代码</td>
           <td style="background-color:#F5EDDB" width="20%">名称</td>
           <td style="background-color:#F5EDDB" width="34%">英文名</td>
           <td style="background-color:#F5EDDB" width="15%">院系</td>
           <td style="background-color:#F5EDDB" width="8%">层次</td>
           <td style="background-color:#F5EDDB" width="15%">有效期</td>
          </tr>
          [#list major.directions?sort_by("code") as d]
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
</div>


[@b.foot/]
