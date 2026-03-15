[#ftl]
[@b.head/]
[@b.toolbar title="修改课程"]bar.addBack();[/@]
[@b.tabs]
  [@b.tab label="基本信息"]
    [@b.form action=b.rest.save(course) theme="list" onsubmit="validCourse" name="courseForm"]
      [@b.textfield name="course.code" label="代码" value="${course.code!}" required="true" maxlength="20"/]
      [@b.textfield name="course.name" id="name" label="名称" value="${course.name!}" required="true" maxlength="100"/]
      [@b.textfield name="course.enName" id="enName" label="英文名" value=course.enName! maxlength="200" style="width:500px"]
        <a href="javascript:void(0)" style="margin-left: 10px;" onclick="return translateName()">自动翻译</a>
        <a href="javascript:void(0)" style="margin-left: 10px;" onclick="return checkEnName()">语法检查</a>
        <span id="enNameCheckMsg" style="display: block;"></span>
      [/@]
      [@b.field label="培养层次"]
        [#list levels?sort_by("code") as level]
          [#assign findMatched=false/]
          [#list course.levels as cl]
            [#if cl.level=level][#assign findMatched=true /][#assign matchedLevel=cl /][#break/][/#if]
          [/#list]
          <input type="checkbox" id="level${level.id}" value="${level.id}" name="levelId" [#if findMatched]checked="checked"[/#if]/>
          <label for="level${level.id}">${level.name}</label>
          [#if levelCreditSupported]<input type="text" name="level${level.id}.credits" value="[#if findMatched]${matchedLevel.credits!}[/#if]" style="width:50px" placeholder="学分"/>分[/#if]
          [#if level_has_next]&nbsp;[/#if]
        [/#list]
      [/@]
      [@b.radios name="course.module.id" label="课程模块" value=course.module! items=courseModules/]
      [@b.radios name="course.rank.id" label="课程属性" value=course.rank! items=courseRanks/]
      [@b.radios name="course.nature.id" label="课程性质" value=course.nature! items=courseNatures required="true"/]
      [@b.select name="course.courseType.id" label="课程类别" value=course.courseType! items=courseTypes empty="..."/]
      [#list categories?keys as dimension]
      [@b.radios name="category.id" label=dimension.name items=categories.get(dimension) value=courseCategories.get(dimension)! required="false"/]
      [/#list]
      [@b.select name="course.department.id" label="院系" value=course.department! required="true"
                 style="width:200px;" items=departments option="id,name" empty="..."/]
      [@b.textfield name="course.defaultCredits" label="学分" onchange="autoCalcHours(this)" value=course.defaultCredits! required="true" maxlength="20"/]
      [@b.textfield name="course.creditHours" label="学时" value=course.creditHours! required="true"  maxlength="100"/]
      [@b.textfield name="course.weekHours" label="周课时" value=course.weekHours! required="true" maxlength="20"/]
      [@b.textfield name="course.weeks" label="实践课周数" value=course.weeks! maxlength="3" comment="非实践课不要填写"/]
      [#if teachingNatures?size>0]
      [@b.field label="分类课时"]
         [#assign hours={}/]
         [#list course.hours as h]
            [#assign hours=hours+{'${h.nature.id}':h} /]
         [/#list]
         [#list teachingNatures as ht]
          <label for="teachingNature${ht.id}_p">${ht_index+1}.${ht.name}</label>
          <input name="creditHour${ht.id}" style="width:30px" id="teachingNature${ht.id}_p" value="${(hours[ht.id?string].creditHours)!}">课时
         [/#list]
      [/@]
      [/#if]
      [@b.radios name="course.examMode.id" label="考核方式" value=course.examMode! required="true" items=examModes /]
      [@b.radios name="course.gradingMode.id" label="成绩记录方式" items=gradingModes value=course.gradingMode! required="true" /]

      [#if multiTermSupported]
      [#assign hasSubCourse=false/][#if course.subCourse??][#assign hasSubCourse=true/][/#if]
      [@b.radios name="hasSubCourse" label="多学期开课" value=hasSubCourse items={"1":"是", "0":"否"} onclick="displaySubcourse(this)"/]
      [@base.course name='course.subCourse.id' label="每学期子课程" value=course.subCourse! required="false" /]
      [@b.number name='course.terms' label="开课学期数" value=course.terms! required="false" min="0" max="8"/]
      [/#if]

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
    function validCourse(form){
      [#if teachingNatures?size>0]
      var sumCreditHours=0;
      [#list teachingNatures as ht]
      sumCreditHours += Number.parseFloat(form['creditHour${ht.id}'].value||'0');
      [/#list]
      if(sumCreditHours != Number.parseFloat(form['course.creditHours'].value||'0')){
         alert("分类课时总和"+sumCreditHours+",不等于课程学时"+form['course.creditHours'].value);
         return false;
      }
      [/#if]
      [#if multiTermSupported]
      if(form['hasSubCourse'].value=='1'){
        if(form['course.terms'].value==""){
          alert("需要填写开课学期数");
          return false;
        }
        if(parseInt(form['course.terms'].value)<2){
          alert("开课学期数需要大于1");
          return false;
        }
        if(form['course.subCourse.id'].value==""){
          alert("需要填写每学期开课课程，该课程一般为0分的课程");
          return false;
        }
      }
      [/#if]
      return true;
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

    function translateName(){
      var name = document.getElementById('name').value;
      if(name) {
        name = encodeURIComponent(name);
        $.get("${api}/base/edu/${project.id}/courses/en?q="+name,function(data,status){
            jQuery('#enName').val(data);
        });
      }
      return false;
    }

    function checkEnName(){
      var name = document.getElementById('enName').value;
      if(name) {
        name = encodeURIComponent(name);
        $.get("${api}/tools/lang/en/check.json?name="+name,function(data,status){
            if(data.success){
              jQuery('#enNameCheckMsg').html("🎉没有检查出错误");
            }else{
              jQuery('#enNameCheckMsg').html("&#128296; " +data.data.join(","));
            }
        });
      }
      return false;
    }

    [#if multiTermSupported]
    function displaySubcourse(ele){
      var hidden=jQuery(ele).val()=='0';
      [#--从完成子课程组到开课学期数字--]
      var i=0;
      var start =0;
      jQuery(ele).parents("ol").children("li").each(function(){
        if($(this).children("label").text().indexOf("多学期开课")>0){
         start =i;
        }
        i++;
      });
      if(start >0){
        start += 1;
        for(var i=start;i<start+2;i++){
          if(hidden){
            jQuery(ele).parents("ol").children("li:nth("+i+")").hide();
          }else{
            jQuery(ele).parents("ol").children("li:nth("+i+")").show();
          }
        }
      }
    }
    jQuery(document).ready(function(){
      displaySubcourse(jQuery('#courseForm input:radio[value="${hasSubCourse?string('1','0')}"]').get(0));
    });
    [/#if]
</script>
[/@]
  [#if course.persisted]
    [@b.tab label="变化记录"]
      [@b.div href="course-journal!search?journal.course.id=${course.id}"/]
    [/@]
  [/#if]
[/@]
[@b.foot/]
