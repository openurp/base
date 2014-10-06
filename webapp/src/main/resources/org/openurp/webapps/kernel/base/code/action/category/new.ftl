[#ftl]
[@b.head/]
[@b.toolbar title="新建代码类别"]bar.addBack();[/@]
[@b.tabs]
    [@b.form action="!save" theme="list"]
      [@b.textfield name="codeCategory.name" label="代码类名" value="${codeCategory.name!}" required="true" maxlength="50"/]
      [@b.textfield name="codeCategory.indexno" label="序号" value="${codeCategory.indexno!}" required="true" maxlength="50"/]
      [#--
      [@b.select name="codeCategory.parent.id" label="父级菜单" value="${codeCategory.parent!}" 
                 style="width:200px;" items=categorys option="id,name" empty="..."/]
      [@urp.codeSelect name="codeCategory.parent.id" label="父级菜单" value="${codeCategory.parent!}"
        style="width:200px;" empty="..." className="org.openurp.kernel.base.code.domain.CodeCategory"/]
      --]
      [@urp.codeSelect name="codeCategory.parent.id" label="父级菜单" value="${codeCategory.parent!}"
        style="width:200px;" empty="..." code="codeCategory"/]
      [@urp.codeSelect name="codeCategory.parent.id" label="父级菜单" value="${codeCategory.parent!}"
        style="width:200px;" empty="..." code="codeCategory"/]
      [@b.formfoot]
        [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
      [/@]
    [/@]
  [/@]
[@b.foot/]