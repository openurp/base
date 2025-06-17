[#ftl]
[@b.head/]
[@b.grid items=laboratories var="laboratory"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("导入",action.method('importForm'));
    bar.addItem("${b.text("action.export")}",action.exportData("code:代码,name:名称,roomNo:房间号,roomType.name:教室类型,building.name:教学楼,campus.name:校区,capacity:容量,courseCapacity:上课容量,examCapacity:考试容量,beginOn:生效日期,endOn:失效日期",null,'fileName=教室信息'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="代码"]${laboratory.code}[/@]
    [@b.col property="name" title="名称"]
       [@b.a href="!info?id=${laboratory.id}"]${laboratory.name}
         [#if !laboratory.roomNo??]<sup>虚拟</sup>[/#if]
       [/@]
    [/@]

    [@b.col width="17%" property="building.name" title="教学楼"/]
    [@b.col width="10%" property="campus.name" title="校区"]
      ${(laboratory.campus.shortName)!((laboratory.campus.name)!'--')}
    [/@]
  [/@]
  [/@]
[@b.foot/]
