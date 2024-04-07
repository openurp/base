[#ftl]
[@b.head/]
[@b.toolbar title="修改课程"]bar.addBack();[/@]
  [@b.form action=b.rest.save(course) theme="list" onsubmit="validCreditHour"]
    [@b.textfield name="course.code" label="代码" value="${course.code!}" required="true" maxlength="20"/]
    [@b.textfield name="course.name" label="名称" value="${course.name!}" required="true" maxlength="100"/]
    [@b.textfield name="course.enName" label="英文名" value="${course.enName!}" maxlength="200" style="width:500px"/]
    [@b.field label="培养层次"]
      [#list levels?sort_by("code") as level]
        [#assign findMatched=false/]
        [#list course.levels as cl]
          [#if cl.level=level][#assign findMatched=true /][#assign matchedLevel=cl /][#break/][/#if]
        [/#list]
        <input type="checkbox" id="level${level.id}" value="${level.id}" name="levelId" [#if findMatched]checked="checked"[/#if]/>
        <label for="level${level.id}">${level.name}</label>
        [#if levelCreditSupported]<input type="text" name="level${level.id}.credits" value="[#if findMatched]${matchedLevel.credits!}[/#if]" style="width:50px" placeholder="学分"/>[/#if]
        [#if level_has_next]&nbsp;[/#if]
      [/#list]
    [/@]
    [@b.radios name="course.nature.id" label="课程性质" value=course.nature! items=courseNatures required="true"/]
    [@b.select name="course.courseType.id" label="课程类别" value=course.courseType! items=courseTypes empty="..." required="true"/]
    [@b.select name="course.category.id" label="评教分类" value=course.category! items=courseCategories empty="..." required="false"/]
    [@b.select name="course.department.id" label="院系" value=course.department! required="true"
               style="width:200px;" items=departments option="id,name" empty="..."/]
    [@b.textfield name="course.defaultCredits" label="学分" onchange="autoCalcHours(this)" value=course.defaultCredits! required="true" maxlength="20"/]
    [@b.textfield name="course.creditHours" label="学时" value=course.creditHours! required="true"  maxlength="100"/]
    [@b.textfield name="course.weekHours" label="周课时" value=course.weekHours! required="true" maxlength="20"/]
    [@b.textfield name="course.weeks" label="周数" value=course.weeks! maxlength="3"/]
    [#if teachingNatures?size>0]
    [@b.field label="分类课时"]
       [#assign hours={}/]
       [#list course.hours as h]
          [#assign hours=hours+{'${h.nature.id}':h} /]
       [/#list]
       [#list teachingNatures as ht]
        <label for="teachingNature${ht.id}_p">${ht_index+1}.${ht.name}</label>
        <input name="creditHour${ht.id}" style="width:30px" id="teachingNature${ht.id}_p" value="${(hours[ht.id?string].creditHours)!}">课时
        <input name="week${ht.id}" style="width:30px" id="teachingNature${ht.id}_w" value="${(hours[ht.id?string].weeks)!}">周
       [/#list]
    [/@]
    [/#if]
    [@b.radios name="course.examMode.id" label="考核方式" value=course.examMode! required="true" items=examModes /]
    [@b.radios name="course.gradingMode.id" label="成绩记录方式" items=gradingModes value=course.gradingMode! required="true" /]
    [@b.radios label="是否计算绩点"  name="course.calgp" value=course.calgp items="1:common.yes,0:common.no" required="true"/]
    [@b.radios label="是否设置补考"  name="course.hasMakeup" value=course.hasMakeup items="1:common.yes,0:common.no" required="true"/]
    [@b.startend label="有效期"  name="course.beginOn,course.endOn" required="true,false"
      start=course.beginOn end=course.endOn format="date" style="width:100px"/]
    [@b.textarea name="course.remark" label="备注" value="${course.remark!}"  maxlength="100"/]
    [@b.formfoot]
      <input type="hidden" name="course.project.id" value="${course.project.id}"/>
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
<script>
   function validCreditHour(form){
      [#if teachingNatures?size>0]
      var sumCreditHours=0;
      [#list teachingNatures as ht]
      sumCreditHours += Number.parseFloat(form['creditHour${ht.id}'].value||'0');
      [/#list]
      if(sumCreditHours != Number.parseFloat(form['course.creditHours'].value||'0')){
         alert("分类课时总和"+sumCreditHours+",不等于课程学时"+form['course.creditHours'].value);
         return false;
      }else{
         return true;
      }
      [#else]
      return true;
      [/#if]
   }
   [#--根据输入的学分自动计算周课时、学时和理论学时--]
   function autoCalcHours(creditInput){
     var form = creditInput.form;
     if(creditInput.value){
       var credits = parseFloat(creditInput.value);
       form['course.creditHours'].value =  credits*${hoursPerCredit};
       form['course.weekHours'].value =  credits;
       [#if teachingNatures?size>0]
          if(!form['creditHour${teachingNatures?first.id}'].value){
             form['creditHour${teachingNatures?first.id}'].value=form['course.creditHours'].value;
          }
       [/#if]
     }
   }
</script>
[@b.foot/]
