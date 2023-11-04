[#ftl]
[@b.head/]
[#assign codes={}]
[#assign codes=codes+{'教职工类型':'/admin/hr/code/staff-type'}]
[#assign codes=codes+{'教职工来源':'/admin/hr/code/staff-source-type'}]
[#assign codes=codes+{'职称':'/admin/hr/code/professional-title'}]
[#assign codes=codes+{'职称等级':'/admin/hr/code/professional-grade'}]
[#assign codes=codes+{'工作状态':'/admin/hr/code/work-status'}]

[@b.nav class="nav nav-tabs nav-tabs-compact"  id="code_nav"]
  [#list codes?keys as code]
  [#if code_index<9]
  [#assign link_class]${(code_index==0)?string("nav-link active","nav-link")}[/#assign]
  <li role="presentation" class="nav-item">[@b.a href=codes[code] class=link_class target="codelist"]${code}[/@]</li>
  [/#if]
  [/#list]
[/@]
[@b.div id="codelist" href="/admin/hr/code/staff-type"/]
<script>
  jQuery(document).ready(function(){
    jQuery('#code_nav>li>a').bind("click",function(e){
      jQuery("#code_nav>li>a").removeClass("active");
      jQuery(this).addClass("active");
    });
  });
</script>

[@b.foot/]
