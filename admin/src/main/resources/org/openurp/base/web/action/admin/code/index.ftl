[#ftl]
[@b.head/]
[#assign codes={}]
[#assign codes=codes+{'国家地区':'/admin/code/country'}]
[#assign codes=codes+{'民族':'/admin/code/nation'}]
[#assign codes=codes+{'证件类型':'/admin/code/id-type'}]
[#assign codes=codes+{'性别':'/admin/code/gender'}]
[#assign codes=codes+{'语种':'/admin/code/language'}]
[#assign codes=codes+{'政治面貌':'/admin/code/political-status'}]
[#assign codes=codes+{'行政区划':'/admin/code/division'}]
[#assign codes=codes+{'社会关系':'/admin/code/family-relationship'}]

[#assign codes=codes+{'学科门类':'/admin/code/discipline-category'}]
[#assign codes=codes+{'科研机构':'/admin/code/institution'}]
[#assign codes=codes+{'教育类型':'/admin/code/edu-category'}]
[#assign codes=codes+{'用户类型':'/admin/code/user-category'}]

<ul class="nav nav-tabs nav-tabs-compact" id="code_nav">
  [#list codes?keys as code]
    [#assign link_class]${(code_index==0)?string("nav-link active","nav-link")}[/#assign]
  <li role="presentation" class="nav-item">[@b.a href=codes[code] class=link_class target="codelist"]${code}[/@]</li>
  [/#list]
</ul>
[@b.div id="codelist" href="/admin/code/country"/]
<script>
  jQuery(document).ready(function(){
    jQuery('#code_nav>li>a').bind("click",function(e){
      jQuery("#code_nav>li>a").removeClass("active");
      jQuery(this).addClass("active");
    });
  });
</script>
[@b.foot/]
