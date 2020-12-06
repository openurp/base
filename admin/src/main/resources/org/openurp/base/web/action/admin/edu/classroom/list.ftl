[#ftl]
[@b.head/]
[@b.grid items=classrooms var="classroom"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="代码"]${classroom.code}[/@]
    [@b.col width="20%" property="name" title="名称"]
       [@b.a href="!info?id=${classroom.id}"]${classroom.name}
         [#if !classroom.roomNo??]<sup>虚拟</sup>[/#if]
       [/@]
    [/@]

    [@b.col width="18%" property="roomType.name" title="教室类型"/]
    [@b.col width="17%" property="building.name" title="教学楼"/]
    [@b.col width="10%" property="campus.name" title="校区"]
      ${(classroom.campus.shortName)!((classroom.campus.name)!'--')}
    [/@]
    [@b.col width="6%" property="capacity" title="容量"]${classroom.capacity!}[/@]
    [@b.col width="7%" property="courseCapacity" title="上课容量"]${classroom.courseCapacity!}[/@]
    [@b.col width="7%" property="examCapacity" title="考试容量"]${classroom.examCapacity!}[/@]
  [/@]
  [/@]
[@b.foot/]
