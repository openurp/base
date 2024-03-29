[#ftl]
[@b.head/]
[@b.toolbar title="修改建筑物用途"]bar.addBack();[/@]
  [@b.form action=b.rest.save(buildingType) theme="list"]
    [@b.textfield name="buildingType.code" label="代码" value="${buildingType.code!}" required="true" maxlength="20"/]
    [@b.textfield name="buildingType.name" label="名称" value="${buildingType.name!}" required="true" maxlength="20"/]
    [@b.textfield name="buildingType.enName" label="英文名称" value="${buildingType.enName!}" maxlength="100"/]
    [@b.startend label="有效期限"
      name="buildingType.beginOn,buildingType.endOn" required="true,false"
      start=buildingType.beginOn end=buildingType.endOn format="date"/]
    [@b.textfield name="buildingType.remark" label="备注" value="${buildingType.remark!}" maxlength="3"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
