[#ftl]
[@b.head/]
[@b.toolbar title="修改时间设置类型"]bar.addBack();[/@]
[@b.tabs]
  [@b.form action="!update?id=${timeSetting.id}" theme="list"]
    [@b.textfield name="timeSetting.name" label="时间设置名称" value="${timeSetting.name!}" required="true" maxlength="20" /]
    [@b.select2 label="关联课程单元" name1st="unitId1st" name2nd="unitId2nd" 
      items1st=units items2nd= timeSetting.units 
      option="id,name"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
[@b.foot/]