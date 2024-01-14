[#ftl]
[@b.head/]
[#assign codes={}]
[#assign codes=codes+{'学籍异动类型':'/admin/std/code/std-alter-type'}]
[#assign codes=codes+{'学籍异动原因':'/admin/std/code/std-alter-reason'}]
[#assign codes=codes+{'学生标签':'/admin/std/code/std-label'}]
[#assign codes=codes+{'学生标签分类':'/admin/std/code/std-label-type'}]
[#assign codes=codes+{'学生类别':'/admin/std/code/std-type'}]
[#assign codes=codes+{'学籍状态':'/admin/std/code/student-status'}]

[@b.nav class="nav nav-tabs nav-tabs-compact"  id="code_nav"]
  [#list codes?keys as code]
  [#if code_index<9]
  [#assign link_class]${(code_index==0)?string("nav-link active","nav-link")}[/#assign]
  <li role="presentation" class="nav-item">[@b.a href=codes[code] class=link_class target="codelist"]${code}[/@]</li>
  [/#if]
  [/#list]
  [#if codes?keys?size>8]
  <li class="nav-item dropdown">
    <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">更多.. <span class="caret"></span></a>
    <div class="dropdown-menu">
      [#list codes?keys as code]
      [#if code_index >8]
      [@b.a href=codes[code] class="dropdown-item" target="codelist"]${code}[/@]
      [/#if]
      [/#list]
    </div>
  </li>
  [/#if]

[/@]
[@b.div id="codelist" href="/admin/std/code/std-alter-type"/]
<script>
  jQuery(document).ready(function(){
    jQuery('#code_nav>li>a').bind("click",function(e){
      jQuery("#code_nav>li>a").removeClass("active");
      jQuery(this).addClass("active");
    });
  });
</script>

[@b.foot/]
