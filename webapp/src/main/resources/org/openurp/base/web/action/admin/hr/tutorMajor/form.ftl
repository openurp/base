[#ftl]
[@b.head/]
<style>
 .title{
   max-width:100px;
 }
</style>
[@b.toolbar title="修改研究领域"]bar.addBack();[/@]
  [@b.form action=b.rest.save(tutorMajor) theme="list"]
    [@base.teacher label="导师" name="tutorMajor.staff.id" value=tutorMajor.staff! required="true"/]
    [@base.code type="education-types" label="培养类型" name="tutorMajor.eduType.id" value=tutorMajor.eduType! empty="..." required="true" /]
    [@base.code type="education-levels" label="培养层次" name="tutorMajor.level.id" value=tutorMajor.level! empty="..." required="true" /]
    [@b.select label="专业" name="tutorMajor.major.id" value=tutorMajor.major! items=majors empty="..." required="true" /]
    [@b.select label="专业方向" name="direction.id" values=tutorMajor.directions! items=directions option=r"${item.code} ${item.name}" multiple="true" /]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
  [#list 1..10 as i]<br>[/#list]
[@b.foot/]
