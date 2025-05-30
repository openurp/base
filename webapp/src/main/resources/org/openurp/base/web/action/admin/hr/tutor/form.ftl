[#ftl]
[@b.head/]
<style>
 fieldset.listset li > label.title{
   min-width:200px;
 }
</style>
[@b.toolbar title="修改导师信息"]bar.addBack();[/@]
  [@b.form action=b.rest.save(staff) theme="list"]
    [@b.field label="工号姓名"]
      ${staff.code} ${staff.name}
    [/@]
    [@b.select name="staff.tutorType.id" label="导师类别" items=tutorTypes value=staff.tutorType! empty="..." required="true"/]
    [#list tutorTypes as tutorType]
    [@b.date name="tutorType"+tutorType.id+".appointOn" label="聘任"+tutorType.name+"日期" value=appointDates.get(tutorType)! empty="..." required="false"/]
    [/#list]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
