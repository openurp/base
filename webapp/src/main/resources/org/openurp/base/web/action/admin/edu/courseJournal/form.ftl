[#ftl]
[@b.head/]
[@b.toolbar title="修改课程"]bar.addBack();[/@]
  [@b.form action=b.rest.save(journal) theme="list" onsubmit="validCreditHour"]
    [@b.field  label="课程"]
       ${journal.course.code!} ${journal.course.name!} ${journal.course.defaultCredits!}学分 ${journal.creditHours}学时
    [/@]
    [@b.textfield name="journal.name" label="名称" value=journal.name! required="true" maxlength="100"/]
    [@b.textfield name="journal.enName" label="英文名" value=journal.enName! maxlength="200" style="width:500px"/]
    [@b.select name="journal.department.id" label="院系" value=journal.department! required="true"
                   style="width:200px;" items=departments option="id,name" empty="..."/]
    [@b.textfield name="journal.creditHours" label="学时" value=journal.creditHours! required="true"  maxlength="100"/]
    [@b.textfield name="journal.weekHours" label="周课时" value=journal.weekHours! required="true" maxlength="20"/]
    [@b.textfield name="journal.weeks" label="周数" value=journal.weeks! maxlength="3"/]
    [#if teachingNatures?size>0]
    [@b.field label="分类学时"]
       [#assign hours={}/]
       [#list journal.hours as h]
          [#assign hours=hours+{'${h.nature.id}':h} /]
       [/#list]
       [#list teachingNatures as ht]
        <label for="teachingNature${ht.id}_p">${ht_index+1}.${ht.name}</label>
        <input name="creditHour${ht.id}" style="width:30px" id="teachingNature${ht.id}_p" value="${(hours[ht.id?string].creditHours)!}">学时
        [#if ht.category.id==9]
        <input name="week${ht.id}" style="width:30px" id="teachingNature${ht.id}_w" value="${(hours[ht.id?string].weeks)!}">周
        [/#if]
       [/#list]
    [/@]
    [/#if]
    [@b.radios name="journal.examMode.id" label="考核方式" value=journal.examMode! required="true" items=examModes /]
    [@b.select name="journal.beginOn" label="生效日期" value=journal.beginOn?string required="true"  items=semesterDates empty="..."/]
    [@b.checkboxes name="tag.id" label="课程标签" values=journal.tags! items=tags  required="false" /]

    [@b.formfoot]
      <input type="hidden" name="journal.course.id" value="${journal.course.id}"/>
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
  [#list 1..10 as i]<br/>[/#list]
<script>
   function validCreditHour(form){
      var creditHours ="${journal.creditHours}";
      if(form['journal.creditHours']) creditHours = form['journal.creditHours'].value;
      [#if teachingNatures?size>0]
      var sumCreditHours=0;
      [#list teachingNatures as ht]
      sumCreditHours += Number.parseFloat(form['creditHour${ht.id}'].value||'0');
      [/#list]
      if(sumCreditHours != Number.parseFloat(creditHours)){
         alert("分类学时总和"+sumCreditHours+",不等于课程学时"+creditHours);
         return false;
      }else{
         return true;
      }
      [#else]
      return true;
      [/#if]
   }
</script>
[@b.foot/]
