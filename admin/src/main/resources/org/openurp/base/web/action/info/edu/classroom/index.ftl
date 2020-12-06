[@b.head/]

[#include "../info_macros.ftl"/]
[@info_header title="教室信息"/]

<div class="container">
<div class="row">
  <div class="col-3">
    <div class="card card-info card-primary card-outline">
      <div class="card-header">
        教学楼列表
      </div>
        <ul class="list-group list-group-flush">
        [#assign displayCampuses=[]/]
        [#list buildings as building]
          <li class="list-group-item">
          [@b.a href="!building?id="+building[1] target="classroom_info"]
             ${building[2]}
             [#if !displayCampuses?seq_contains(building[0])]
             [#assign displayCampuses=displayCampuses+[building[0]]/] <span style="font-size:0.8rem;color: #999;">${building[0]}</span>[/#if]
          [/@]
          <span class="badge badge-primary badge-pill float-right">${building[3]}</span>
          </li>
        [/#list]
       </ul>

       <ul class="list-group list-group-flush">
         <li class="list-group-item">
          [@b.a href="!building?id=0" target="classroom_info"]无[/@]
          </li>
        </ul>
    </div>
  </div>
  [#if buildings?size>0]
  [@b.div href="!building?id="+buildings?first[1] class="col-9" id="classroom_info"/]
  [#else]
  [@b.div href="!building?id=0" class="col-9" id="classroom_info"/]
  [/#if]
[@b.foot/]
