[#ftl]
[@b.head/]
[@b.toolbar title="部门基本信息"]bar.addBack();[/@]
[@b.tabs]
  [#assign sa][#if department.persisted]!update?id=${department.id!}[#else]!save[/#if][/#assign]
  [@b.form action=sa theme="list"]
    [@b.textfield name="department.code" label="代码" value="${department.code!}" required="true" maxlength="10"/]
    [@b.textfield name="department.name" label="名称" value="${department.name!}" required="true" maxlength="80"/]
    [@b.textfield name="department.enName" label="英文名称" value="${department.enName!}" maxlength="100"/]
    [@b.textfield name="department.shortName" label="简称" value="${department.shortName!}" maxlength="100"/]
    [@b.radios label="是否教学部门"  name="department.teaching" value=department.teaching items="1:common.yes,0:common.no"/]
    [@b.startend label="有效期限"
      name="department.beginOn,department.endOn" required="false,false"
      start=department.beginOn end=department.endOn format="date"/]
    [@b.textfield name="department.indexno" label="序号" value="${department.indexno!}" required="true" maxlength="20"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]
