[#ftl]
[@b.head/]
[#assign codes={}]
[#assign codes=codes+{'成绩记录方式':'/edu/admin/code/grading-mode'}]
[#assign codes=codes+{'课程类别':'/edu/admin/code/course-type'}]
[#assign codes=codes+{'课程能力等级':'/edu/admin/code/course-ability-rate'}]
[#assign codes=codes+{'课程种类':'/edu/admin/code/course-category'}]
[#assign codes=codes+{'课时类别':'/edu/admin/code/course-hour-type'}]
[#assign codes=codes+{'考核方式':'/edu/admin/code/exam-mode'}]
[#assign codes=codes+{'考试情况':'/edu/admin/code/exam-status'}]
[#assign codes=codes+{'培养层次':'/edu/admin/code/education-level'}]
[#assign codes=codes+{'学籍异动类型':'/edu/admin/code/std-alter-type'}]
[#assign codes=codes+{'学籍异动原因':'/edu/admin/code/std-alter-reason'}]
[#assign codes=codes+{'学生标签':'/edu/admin/code/std-label'}]
[#assign codes=codes+{'学生标签分类':'/edu/admin/code/std-label-type'}]
[#assign codes=codes+{'学生类别':'/edu/admin/code/std-type'}]

[#assign gbcodes={}]
[#assign gbcodes=gbcodes+{'学位层次':'/edu/admin/code/degree-level'}]
[#assign gbcodes=gbcodes+{'学位':'/edu/admin/code/degree'}]
[#assign gbcodes=gbcodes+{'学习形式':'/edu/admin/code/study-type'}]
[#assign gbcodes=gbcodes+{'学科门类':'/edu/admin/code/discipline-category'}]
[#assign gbcodes=gbcodes+{'毕结业结论':'/edu/admin/code/education-result'}]

[@b.nav class="nav nav-tabs nav-tabs-compact"  id="code_nav"]
  [#list codes?keys as code]
  [#if code_index<9]
  [#assign link_class]${(code_index==0)?string("nav-link active","nav-link")}[/#assign]
  <li role="presentation" class="nav-item">[@b.a href=codes[code] class=link_class target="codelist"]${code}[/@]</li>
  [/#if]
  [/#list]
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

  <li class="nav-item dropdown">
          <a href="#" class="nav-link dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">国标.. <span class="caret"></span></a>
          <div class="dropdown-menu">
              [#list gbcodes?keys as code]
              [@b.a href=gbcodes[code] class="dropdown-item" target="codelist"]${code}[/@]
              [/#list]
          </div>
  </li>
[/@]
[@b.div id="codelist" href="/edu/admin/code/grading-mode"/]
<script>
  jQuery(document).ready(function(){
    jQuery('#code_nav>li>a').bind("click",function(e){
      jQuery("#code_nav>li>a").removeClass("active");
      jQuery(this).addClass("active");
    });
  });
</script>

[@b.foot/]
