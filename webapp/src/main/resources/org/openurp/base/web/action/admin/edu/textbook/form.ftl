[#ftl]
[@b.head/]
[@b.toolbar title="新建、修改教材"]bar.addBack();[/@]
  [@b.form action=b.rest.save(textbook) theme="list"]
    [@b.textfield name="textbook.isbn" label="ISBN" value="${textbook.isbn!}" required="true" maxlength="30"/]
    [@b.textfield name="textbook.name" label="名称" value="${textbook.name!}" required="true" maxlength="50" style="width:400px"/]
    [@b.textfield name="textbook.author" label="作者" value=textbook.author! maxlength="100" required="true"/]
    [@b.textfield name="textbook.translator" label="译者" value=textbook.translator! maxlength="100" /]
    [@b.date name="textbook.publishedOn" label="出版年月" value=(textbook.publishedOn)!  format="yyyy-MM" required="true"/]
    [@b.textfield name="textbook.edition" label="版次" value=textbook.edition! maxlength="20" style="width:100px" required="true"/]
    [@b.textfield name="textbook.series" label="丛书" value=textbook.series! maxlength="50" style="width:200px" required="false"/]
    [@b.select name="textbook.press.id" id="textbook_press_id" label="出版社" value=(textbook.press.id)!
               style="width:200px;" items=presses?sort_by('name') empty="..." required="true"/]
    [@b.select name="textbook.category.id" label="图书分类" value=(textbook.category.id)!
               style="width:200px;" items=categories empty="..." required="false"/]
    [@b.textarea name="textbook.description" label="简介" value=textbook.description! maxlength="600" rows="5" cols="80" required="false"/]
    [@b.select name="textbook.bookType.id" label="教材类型" value=(textbook.bookType.id)!
               style="width:200px;" items=bookTypes empty="..." required="false"/]

    [@b.select name="textbook.awardType.id" label="获奖类型" value=(textbook.awardType.id)!
               style="width:200px;" items=awardTypes empty="..." required="false"/]
    [@b.textfield name="textbook.awardOrg" label="奖项授予单位" value="${textbook.awardOrg!}" required="false" maxlength="200" style="width:200px"/]
    [@b.radios label="是否自编"  name="textbook.madeInSchool" value=textbook.madeInSchool items="1:common.yes,0:common.no" required="true"/]
    [@b.radios label="境内/境外"  name="textbook.domestic" value=textbook.domestic items="1:境内教材,0:境外教材" required="true"/]
    [@b.select label="境外教材类型" name="textbook.foreignBookType.id" value=textbook.foreignBookType! items=foreignBookTypes empty="..." required="false"/]
    [@b.select label="教材形态" name="textbook.bookForm.id" value=textbook.bookForm! items=textbookForms empty="..." required="false"/]
    [@b.select label="学科门类" name="textbook.disciplineCategory.id" value=textbook.disciplineCategory! items=disciplineCategories empty="..." required="false"/]

    [@b.textfield name="textbook.price" label="定价" value=textbook.price! maxlength="100" /]
    [@b.textarea name="textbook.remark" label="备注" value="${textbook.remark!}"  maxlength="100"/]
    [@b.startend label="有效期"  name="textbook.beginOn,textbook.endOn" required="true,false"
      start=textbook.beginOn end=textbook.endOn format="date"/]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
  <script>
  $("#textbook_press_id").chosen({no_results_text: "没有找到结果！",search_contains:true,allow_single_deselect:true});
  </script>
[@b.foot/]
