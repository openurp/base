[#ftl]
[@b.head/]
<style>
  .title{
    margin-left:2rem;
    font-weight:bolder;
  }
  .page {
    margin: 1em auto;
    padding: 1em 0 2em;
    box-shadow: 0 2px 10px 1px rgba(0, 0, 0, 0.2);
    text-shadow: 0 1px 0 #F6EF97;
  }
  .page p {
    padding: 0 2em;
  }
</style>

[#macro section name contents]
[#if contents?? && contents?length>0]
<p>
  [#assign contents = contents?trim/]
  [#local hasNewLine = contents?contains("\n")/]
  <span class="title">${name}：</span>
  [#if hasNewLine]
  <br><span class="title"></span>
  ${contents?html?replace("\n",'<br>')}
  [#else]
  ${contents?html?replace("\n",'<br>')}
  [/#if]
</p>
[/#if]
[/#macro]

[#macro intro name contents]
<p> <span class="title">${name}，</span>
  <span>${contents}</span>
</p>
[/#macro]
[@b.toolbar title="教师简介"]
[/@]
<div class="page container">
  [#if profile??]
  [@intro profile.staff.name profile.intro!/]
  [@section "研究领域" profile.research!/]
  [@section "教授课程" profile.courses!/]
  [@section "学术、工作经历" profile.career!/]
  [@section "获奖情况" profile.awards!/]
  [@section "科研成果" profile.harvest!/]
  [@section "科研项目" profile.projects!/]
  [@section "学术兼职" profile.titles!/]
  [@section "联系信息" profile.contact!/]
  [#else]
  暂无简介
  [/#if]
</div>
[@b.foot/]
