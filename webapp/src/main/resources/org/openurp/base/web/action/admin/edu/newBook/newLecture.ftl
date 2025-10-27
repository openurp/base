[#ftl]
[@b.head/]
<style>
  @media (max-width:767.98px) {
    fieldset.listset li > label.title{
      min-width: 4.5rem;
    }
    fieldset.listset > ol > li{
      padding: 4px 0px 7px;
    }
  }
</style>
<div class="container">
  [@b.form action="!save" theme="list"]
    [@b.textfield name="textbook.name" label="名称" value="${textbook.name!}" required="true" maxlength="200" placeholder="最多200个字"/]
    [@b.textfield name="textbook.author" label="作者" value=textbook.author! maxlength="100" required="true"/]
    [@b.date name="textbook.publishedIn" label="编写年月" value=(textbook.publishedIn)! format="yyyy-MM" required="true"/]
    [@b.textfield name="textbook.edition" label="版次" value=textbook.edition! maxlength="20" style="width:100px" required="true"/]
    [@b.select name="textbook.category.id" label="图书分类" value=(textbook.category.id)!
               style="width:200px;" items=categories empty="..." required="false"/]

    [#if bookTypes?size>0]
    [@b.select name="textbook.bookType.id" label="教材类型" value=(textbook.bookType.id)! items=bookTypes empty="..." required="true"/]
    [/#if]

    [@b.radios label="是否自编"  name="textbook.madeInSchool" value=textbook.madeInSchool items="1:common.yes,0:common.no" required="true"/]
    [@b.radios label="境内/境外" name="textbook.domestic" onchange="displayForeign(this.value)" value=textbook.domestic items="1:境内教材,0:境外教材" required="true"/]
    [@b.select label="境外教材类型" name="textbook.foreignBookType.id" id="foreignBookType" value=textbook.foreignBookType! items=foreignBookTypes empty="..." required="false"/]
    [@b.select label="教材形态" name="textbook.bookForm.id" value=textbook.bookForm! items=textbookForms required="true"/]
    [@b.select label="学科门类" name="textbook.disciplineCategory.id" value=textbook.disciplineCategory! items=disciplineCategories empty="..." required="true"/]
    [@b.formfoot]
      <input type="hidden" name="project.id" value="${project.id}"/>
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
</div>
<script>
  function displayForeign(domestic){
    var hidden = domestic =='1';
    if(hidden){
      $("#foreignBookType").val("");
      $("#foreignBookType").parents("li").hide();
    }else{
      $("#foreignBookType").parents("li").show();
    }
  }
  jQuery(function() {
    displayForeign("1");//境内教材
  })
</script>
[@b.foot/]
