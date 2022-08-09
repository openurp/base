[#ftl]
[@b.head/]
[@b.toolbar title="修改课程"]bar.addBack();[/@]
  [@b.form action=b.rest.save(course) theme="list" onsubmit="validCreditHour"]
    [@b.textfield name="course.code" label="代码" value="${course.code!}" required="true" maxlength="20"/]
    [@b.textfield name="course.name" label="名称" value="${course.name!}" required="true" maxlength="100"/]
    [@b.textfield name="course.enName" label="英文名" value="${course.enName!}" maxlength="200" style="width:400px"/]
    [@b.select2 label="培养层次" name1st="levelId1st" name2nd="levelId2nd" style = "height:80px;width:152px"
      items1st=levels items2nd= course.levels
      option="id,name"  required="true" /]
    [@b.select name="course.nature.id" label="课程性质" value=course.nature! items=courseNatures empty="..." required="true"/]
    [@b.select name="course.courseType.id" label="课程类别" value=course.courseType! items=courseTypes empty="..." required="true"/]
    [@b.select name="course.category.id" label="评教分类" value=course.category! items=courseCategories empty="..." required="false"/]
    [@b.select name="course.department.id" label="院系" value="${(course.department.id)!}" required="true"
               style="width:200px;" items=departments option="id,name" empty="..."/]
    [#if teachingOffices?size>0]
      [@b.select name="course.teachingOffice.id" label="教研室" items=teachingOffices option="id,name" empty="..." required="false" /]
    [/#if]
    [@b.textfield name="course.credits" label="学分" onchange="autoCalcHours(this)" value=course.credits! required="true" maxlength="20"/]
    [@b.textfield name="course.creditHours" label="学时" value=course.creditHours! required="true"  maxlength="100"/]
    [@b.textfield name="course.weekHours" label="周课时" value=course.weekHours! required="true" maxlength="20"/]
    [@b.textfield name="course.weeks" label="周数" value=course.weeks! maxlength="3"/]
    [#if teachingNatures?size>0]
    [@b.field label="分类课时"]
       [#assign hours={}/]
       [#list course.hours as h]
          [#assign hours=hours+{'${h.teachingNature.id}':h} /]
       [/#list]
       [#list teachingNatures as ht]
        <label for="teachingNature${ht.id}_p">${ht_index+1}.${ht.name}</label>
        <input name="creditHour${ht.id}" style="width:30px" id="teachingNature${ht.id}_p" value="${(hours[ht.id?string].creditHours)!}">课时
        <input name="week${ht.id}" style="width:30px" id="teachingNature${ht.id}_w" value="${(hours[ht.id?string].weeks)!}">周
       [/#list]
    [/@]
    [/#if]
    [@b.select name="course.examMode.id" label="考核方式" value="${(course.examMode.id)!}" required="true"
               style="width:200px;" items=examModes option="id,name" empty="..."/]
    [@b.select2 label="成绩记录方式" name1st="gradingModeId1st" name2nd="gradingModeId2nd" style = "height:80px;width:152px"
      items1st=gradingModes items2nd= course.gradingModes  option="id,name" /]
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
