[#ftl]
[@b.head/]
[@b.toolbar title="修改项目配置"]bar.addBack();[/@]
  [#assign typeNames={'boolean':'布尔变量','integer':'整数','float':'浮点数','string':'字符串','json':'JSON'}/]
  [@b.form action=b.rest.save(projectProperty) theme="list"]
    [@b.textfield name="projectProperty.name" label="名称" value=projectProperty.name! required="true" maxlength="100"/]
    [@b.select name="projectProperty.typeName" label="类型" value=projectProperty.typeName! items=typeNames required="true"/]
    [@b.textfield name="projectProperty.description" label="描述" value=projectProperty.description! required="true" maxlength="100"/]
    [@b.textarea name="projectProperty.value" label="值" value=projectProperty.value! required="true" maxlength="1000" rows="10" cols="80"/]
    [@b.formfoot]
      <input type="hidden" name="projectProperty.project.id" value="${projectProperty.project.id}"/>
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
