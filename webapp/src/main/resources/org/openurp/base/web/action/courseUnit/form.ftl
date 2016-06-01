[#ftl]
[@b.head/]
[@b.toolbar title="修改小节类型"]bar.addBack();[/@]
[@b.tabs]
  [#assign sa][#if courseUnit.persisted]!update?id=${courseUnit.id!}[#else]!save[/#if][/#assign]
  [@b.form action=sa theme="list"]
    [@b.textfield name="courseUnit.name" label="名称" value="${courseUnit.name!}" required="true" maxlength="20"/]
    [@b.textfield name="courseUnit.indexno" label="序号" value="${courseUnit.indexno!}" required="true"/]
    [@b.select name="courseUnit.part.id" label="时段" value="${(courseUnit.part.id)!}" 
               style="width:200px;" items=parts option="id,name" empty="..." required="true"/]
    [@b.select name="courseUnit.setting.id" label="时间设置" value="${(courseUnit.setting.id)!}" 
               style="width:200px;" items=settings option="id,name" empty="..."/]
    [@b.startend label="生效失效时间" 
      name="courseUnit.beginAt,courseUnit.endAt" required="true,true" 
      start=courseUnit.beginAt end=courseUnit.endAt format="HH:mm"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]