[@b.head/]

[#include "../info_macros.ftl"/]
[@info_header title="专业信息"/]

<div class="container">
<div class="row">
  <div class="col-3">
    <div class="card  card-info card-primary card-outline">
      <div class="card-header">
        专业列表
      </div>
        <ul class="list-group list-group-flush">
        [#list majors as major]
          <li class="list-group-item">
          [@b.a href="!info?id="+major.id target="major_info"]${major.code} ${major.name}[/@]
          </li>
        [/#list]
        </ul>
    </div>
  </div>
  [@b.div href="!info?id="+majors?first.id class="col-9" id="major_info"/]
[@b.foot/]
