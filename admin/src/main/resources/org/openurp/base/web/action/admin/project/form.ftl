[#ftl]
[@b.head/]
[@b.toolbar title="修改项目"]bar.addBack();[/@]
[@b.tabs]
  [@b.tab label="项目维护"]
  [@b.form action=b.rest.save(project) theme="list"]
    [@b.textfield name="project.name" label="名称" value="${project.name!}" required="true" maxlength="20"/]
    [@b.textfield name="project.code" label="代码" value="${project.code!}" required="true" maxlength="20"/]
    [@b.select name="project.school.id" label="适用学校" value="${(project.school.id)!}" required="true" items=schools/]
    [@b.select2 label="校区列表" name1st="campusesId1st" name2nd="campusesId2nd"
      items1st=campuses items2nd= project.campuses
      option="id,name"/]
    [@b.select2 label="院系列表" name1st="departmentsId1st" name2nd="departmentsId2nd"
      items1st=departments items2nd= project.departments
      option="id,name"/]
    [@b.select2 label="培养层次列表" name1st="levelsId1st" name2nd="levelId2nd"
      items1st=levels items2nd= project.levels
      option="id,name"/]
    [@b.select2 label="学生分类列表" name1st="labelsId1st" name2nd="labelsId2nd"
      items1st=labels items2nd= project.stdLabels
      option="id,name"/]
    [@b.select2 label="学生类别列表" name1st="typesId1st" name2nd="typesId2nd"
      items1st=types items2nd= project.stdTypes
      option="id,name"/]
    [@b.select name="project.calendar.id" label="使用校历" value= project.calendar! required="true" items=calendars  /]
    [@b.radios label="是否辅修"  name="project.minor" value=project.minor items="1:common.yes,0:common.no"/]
    [@b.select name="project.category.id" label="类型" value="${(project.category.id)!}" required="true"
               style="width:200px;" items=eduCategories empty="..."/]
    [@b.textarea name="project.description" label="描述" value="${project.description!}" required="true" maxlength="2000" /]
    [@b.startend label="有效期"
      name="project.beginOn,project.endOn" required="true,false"
      start=project.beginOn end=project.endOn format="date"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
  [/@]
  [#if project.persisted]
  [@b.tab label="基础代码配置"]
    [@b.div href="project-code!search?projectCode.project.id=${project.id}"/]
  [/@]
  [@b.tab label="项目配置"]
    [@b.div href="project-property!search?projectProperty.project.id=${project.id}"/]
  [/@]
  [/#if]
[/@]
[@b.foot/]
