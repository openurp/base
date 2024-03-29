[#ftl]
[@b.head/]
[@b.toolbar title="修改领导干部信息"]bar.addBack();[/@]
  [@b.form action=b.rest.save(official) theme="list"]
    [@b.select name="official.staff.id" label="职工号" required="true" style="width:400px;" items=staffs option=r"${item.code} ${item.name} ${item.department.name}" chosenMin="1"/]
    [@b.select name="official.department.id" label="部门" required="false" items=departments empty="..." comment="空为本人所在部门，兼任另外填写"/]
    [@b.textfield name="official.duty" label="行政职务" value=official.duty! required="true" maxlength="20"/]
    [@b.radios label="是否兼任"  name="official.parttime" value=official.parttime items="1:common.yes,0:common.no"/]
    [@b.startend label="在职时间" name="official.beginOn,official.endOn" required="true,false" start=official.beginOn
       end=official.endOn format="date"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[@b.foot/]
