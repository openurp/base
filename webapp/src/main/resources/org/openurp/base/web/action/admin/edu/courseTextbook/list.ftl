[#ftl]
[@b.head/]
[@b.grid items=courseTextbooks var="courseTextbook"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("导入",action.method('importForm'));
    bar.addItem("${b.text("action.export")}",action.exportData("course.code,course.name,course.defaultCredits:学分,"+
                "course.creditHours,course.department.name:所属院系,textbook.isbn:ISBN,textbook.name:教材名称,textbook.author:作者,"+
                "textbook.press.name:出版社,textbook.edition:版次,textbook.publishedOn:出版年月,begin:生效日期,endOn:失效日期",
                null,'fileName=课程默认教材信息'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="course.code" title="课程代码"/]
    [@b.col property="course.name" title="课程名称"]${courseTextbook.course.name}[/@]
    [@b.col width="5%" property="course.defaultCredits" title="学分"/]
    [@b.col width="5%" property="course.creditHours" title="学时"/]
    [@b.col width="10%" title="培养层次"]
      <span style="font-size:0.8em">[#list courseTextbook.course.levels as l]${l.level.name}[#if l.credits??]<sup>${l.credits}</sup>[/#if][#sep]&nbsp;[/#list]</span>
    [/@]
    [@b.col width="10%" property="department.name" title="课程所属院系"]
      ${courseTextbook.course.department.shortName!courseTextbook.course.department.name}
    [/@]
    [@b.col  property="textbook.name" title="教材"]
    ${courseTextbook.textbook.name} ${courseTextbook.textbook.isbn!}
    [/@]
    [@b.col width="10%" property="beginOn" title="有效期"]
      ${courseTextbook.beginOn?string("yy-MM")}~${(courseTextbook.endOn?string("yy-MM"))!}
    [/@]
  [/@]
[/@]
[@b.foot/]
