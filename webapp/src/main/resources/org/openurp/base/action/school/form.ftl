[#ftl]
[@b.head/]
[@b.toolbar title="修改学校类型"]bar.addBack();[/@]
[@b.tabs]
  [@b.form action="!update?id=${school.id}" theme="list"]
    [@b.textfield name="school.code" label="代码" value="${school.code!}" required="true" maxlength="10"/]
    [@b.textfield name="school.name" label="名称" value="${school.name!}" required="true" maxlength="50"/]
    [@b.select name="school.institution.id" label="科研机构" value="${(school.institution.id)!}" 
               style="width:200px;" href=urp.service("/base/code/institutions") option="id,name" empty="..."/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]