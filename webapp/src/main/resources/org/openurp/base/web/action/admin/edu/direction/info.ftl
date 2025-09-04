[#ftl]
[@b.head/]
[@b.toolbar title="专业方向信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<div style="margin:0px 20px">
  <div class="card card-primary card-outline">
    <div class="card-header">
      <h5 class="card-title">基本信息</h5>
    </div>
    <div class="card-body">
      <table class="table table-sm table-mini">
        <tr>
          <td class="title" width="15%">代码</td>
          <td class="content">${direction.code}</td>
          <td class="title" width="15%">名称</td>
          <td class="content">${direction.name}</td>
        </tr>
        <tr>
          <td class="title">英文名</td>
          <td class="content">${direction.enName!}</td>
          <td class="title">专业</td>
          <td class="content">${direction.major.name!}</td>
        </tr>
        <tr>
          <td class="title">有效期</td>
          <td class="content" >${direction.beginOn}~${direction.endOn!"现在"}</td>
          <td class="title" width="20%">备注</td>
          <td class="content">${direction.remark!}</td>
        </tr>
        <tr>
          <td class="title">最后修改</td>
          <td class="content" colspan="3">${direction.updatedAt?string('yyyy-MM-dd HH:mm:ss')}</td>
        </tr>
      </table>
    </div>
  </div>
  <div class="card card-primary card-outline">
      <div class="card-header">
        <h5 class="card-title">所属院系</h5>
      </div>
      <div class="card-body">
        <table class="table table-sm table-mini">
          <tr>
           <td width="8%">序号</td>
           <td>院系</td>
           <td>培养层次</td>
           <td>有效期</td>
          </tr>
          [#list direction.journals?sort_by("beginOn")?reverse as dj]
          <tr>
            <td>${dj_index+1}</td>
            <td>${dj.depart.name}</td>
            <td>${dj.level.name}</td>
            <td>${dj.beginOn?string("yyyy-MM-dd")}~${(dj.endOn?string("yyyy-MM-dd"))!"现在"}</td>
          </tr>
          [/#list]
        </table>
      </div>
  </div>
</div>
[@b.foot/]
