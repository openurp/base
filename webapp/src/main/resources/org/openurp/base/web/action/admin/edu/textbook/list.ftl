[#ftl]
[@b.head/]
[@b.grid items=textbooks var="textbook"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
    bar.addItem("导入",action.method('importForm'));
    bar.addItem("${b.text("action.export")}",action.exportData("isbn:ISBN,name:名称,press.name:出版社,author:作者,translator:译者,publishedOn:出版年月,edition:版次,category.name:图书分类,bookType.name:教材类型,awardType.name:获奖类型,awardOrg:奖项授予单位,series:丛书,madeInSchool:是否自编,beginOn:生效日期,endOn:停用日期",null,'fileName=教材信息'));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="12%" property="isbn" title="ISBN"]
      <span style="font-size:0.8em"> ${(textbook.isbn)!}</span>
    [/@]
    [@b.col property="name" title="名称"]
      <span [#if textbook.name?length > 25]style="font-size:0.8em"[/#if]>
      [@b.a href="!info?id=${textbook.id}"]${textbook.name}[/@]
      [#if textbook.madeInSchool]<sup>自编</sup>[/#if]
      [#if textbook.awardType??]<sup>${textbook.awardType.name}</sup>[/#if]
      </span>
    [/@]
    [@b.col width="12%" property="press.name" title="出版社"]
     <span style="font-size:0.8em">${(textbook.press.name)!}</span>
    [/@]
    [@b.col width="13%" property="author" title="作者"/]
    [@b.col width="8%" property="publishedOn" title="出版年月"]${(textbook.publishedOn?string('yyyy-MM'))!}[/@]
    [@b.col width="8%" property="bookType.name" title="教材类型"/]
    [@b.col width="8%" property="category.name" title="图书分类"]
      [#if ((textbook.category.name)!'-')?length > 5 ]
      <span style="font-size:0.6em">${(textbook.category.name)!}</span>
      [#else]
      ${(textbook.category.name)!}
      [/#if]
    [/@]
  [/@]
 [/@]
[@b.foot/]
