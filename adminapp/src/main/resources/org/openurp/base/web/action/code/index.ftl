[#ftl]
[@b.head/]
[#assign codes={}]
[#assign codes=codes+{'国家地区':'/code/country'}]
[#assign codes=codes+{'民族':'/code/nation'}]
[#assign codes=codes+{'证件类型':'/code/id-type'}]
[#assign codes=codes+{'性别':'/code/gender'}]
[#assign codes=codes+{'语种':'/code/language'}]
[#assign codes=codes+{'政治面貌':'/code/political-status'}]
[#assign codes=codes+{'行政区划':'/code/division'}]
[#assign codes=codes+{'社会关系':'/code/family-relationship'}]

[#assign codes=codes+{'学科门类':'/code/discipline-category'}]
[#assign codes=codes+{'科研机构':'/code/institution'}]
[#assign codes=codes+{'教育类型':'/code/edu-category'}]
[#assign codes=codes+{'用户类型':'/code/user-category'}]

<ul class="nav nav-tabs" id="code_nav">
  [#list codes?keys as code]
  <li role="presentation" [#if code_index=0]class="active"[/#if]>[@b.a href=codes[code] target="codelist"]${code}[/@]</li>
  [/#list]
</ul>
[@b.div id="codelist" href="/code/country"/]
<script>
  jQuery(document).ready(function(){
    jQuery('#code_nav>li').bind("click",function(e){
      jQuery("#code_nav>li").removeClass("active");
      jQuery(this).addClass("active");
    });
  });
</script>
[@b.foot/]
