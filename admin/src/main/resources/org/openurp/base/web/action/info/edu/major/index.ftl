[@b.head/]

[#include "../info_macros.ftl"/]
[@info_header title="专业信息"/]

<div class="container-fluid">
<div class="row">
  <div class="col-3">
    <div class="card  card-info card-primary card-outline">
      <div class="card-header">
        专业列表
      </div>
      <div class="card-body" style="padding-top: 0px;">
         <table class="table table-hover table-sm">
          <tbody>
          [#list majors as major]
           <tr>
            <td>[@b.a href="!info?id="+major.id target="major_info"]${major.code} ${major.name}[/@]</td>
           </tr>
           [/#list]
          </tbody>
        </table>
      </div>
    </div>
  </div>
  [@b.div href="!info?id="+majors?first.id class="col-9" id="major_info"/]
[@b.foot/]
