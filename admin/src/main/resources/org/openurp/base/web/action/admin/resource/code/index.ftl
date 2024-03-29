[#ftl]
[@b.head/]
[#assign codes={}]
[#assign codes=codes+{'教室类型':'/admin/resource/code/classroom-type'}]
[#assign codes=codes+{'建筑物用途':'/admin/resource/code/building-type'}]
[#assign codes=codes+{'房间类型':'/admin/resource/code/room-type'}]
[#assign codes=codes+{'设备类型':'/admin/resource/code/device-type'}]
<ul class="nav nav-tabs nav-tabs-compact" id="code_nav">
  [#list codes?keys as code]
    [#assign link_class]${(code_index==0)?string("nav-link active","nav-link")}[/#assign]
  <li role="presentation" class="nav-item">[@b.a href=codes[code] class=link_class target="codelist"]${code}[/@]</li>
  [/#list]
</ul>
[@b.div id="codelist" href="/admin/resource/code/classroom-type"/]
<script>
  jQuery(document).ready(function(){
    jQuery('#code_nav>li>a').bind("click",function(e){
      jQuery("#code_nav>li>a").removeClass("active");
      jQuery(this).addClass("active");
    });
  });
</script>
[@b.foot/]
