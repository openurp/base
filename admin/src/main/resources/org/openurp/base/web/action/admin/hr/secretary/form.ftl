[#ftl]
[@b.head/]

[@b.toolbar title="修改秘书信息"]bar.addBack();[/@]
  [@b.form action=b.rest.save(secretary) theme="list"]
    [@b.field label="工号姓名"]
      ${secretary.staff.code} ${secretary.staff.name}
    [/@]
    [@b.textfield name="secretary.officePhone" label="办公电话" value=secretary.officePhone! maxlength="13"/]
    [@b.textfield name="secretary.officeAddr" label="办公室地址" value=secretary.officeAddr! maxlength="30"/]
    [@b.email name="secretary.officeEmail" label="办公邮件" value=secretary.officeEmail! maxlength="100"/]

    [@b.startend label="有效期" name="secretary.beginOn,secretary.endOn" required="true,false" start=secretary.beginOn end=secretary.endOn format="date"/]
    [@b.textfield name="secretary.remark" label="备注" value=secretary.remark! maxlength="30"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
