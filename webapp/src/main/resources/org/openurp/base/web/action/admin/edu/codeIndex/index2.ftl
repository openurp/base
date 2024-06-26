[#ftl]
[@b.head/]
[#assign codes={}]
[#assign codes=codes+{'成绩类型':'/admin/edu/code/grade-type'}]
[#assign codes=codes+{'成绩记录方式':'/admin/edu/code/grading-mode'}]
[#assign codes=codes+{'课程分类':'/admin/edu/code/course-category'}]
[#assign codes=codes+{'课程能力等级':'/admin/edu/code/course-ability-rate'}]
[#assign codes=codes+{'课程类别':'/admin/edu/code/course-type'}]
[#assign codes=codes+{'课程性质':'/admin/edu/code/course-nature'}]
[#assign codes=codes+{'课程模块':'/admin/edu/code/course-module'}]
[#assign codes=codes+{'授课性质':'/admin/edu/code/teaching-nature'}]
[#assign codes=codes+{'授课方式':'/admin/edu/code/teaching-method'}]
[#assign codes=codes+{'考核方式':'/admin/edu/code/exam-mode'}]
[#assign codes=codes+{'考试类型':'/admin/edu/code/exam-type'}]
[#assign codes=codes+{'考试情况':'/admin/edu/code/exam-status'}]
[#assign codes=codes+{'培养层次':'/admin/edu/code/education-level'}]
[#assign codes=codes+{'培养类型':'/admin/edu/code/education-type'}]
[#assign codes=codes+{'修读类别':'/admin/edu/code/course-take-type'}]
[#assign gbcodes={}]
[#assign gbcodes=gbcodes+{'学位层次':'/admin/edu/code/degree-level'}]
[#assign gbcodes=gbcodes+{'学位':'/admin/edu/code/degree'}]
[#assign gbcodes=gbcodes+{'学习形式':'/admin/edu/code/study-type'}]
[#assign gbcodes=gbcodes+{'学科门类':'/admin/edu/code/discipline-category'}]
[#assign gbcodes=gbcodes+{'毕结业结论':'/admin/edu/code/education-result'}]

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
[@b.div id="codelist" href="/admin/edu/code/grade-type"/]
<script>
  jQuery(document).ready(function(){
    jQuery('#code_nav>li>a').bind("click",function(e){
      jQuery("#code_nav>li>a").removeClass("active");
      jQuery(this).addClass("active");
    });
  });
</script>

[@b.foot/]
