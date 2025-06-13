[#ftl]
[@b.head/]
[@b.toolbar title="新建、修改教材"]bar.addBack();[/@]
  [@b.form action=b.rest.save(textbook) theme="list" name="textbookForm"]
    [@b.textfield name="textbook.isbn" label="ISBN" value="${textbook.isbn!}" required="true" maxlength="30"]
      <a href="${b.url('new-book!queryByIsbn')}" onclick="return fetchByIsbn(this,event);">按照isbn查询，并自动填充</a>
      <span class="alert alert-warning" style="display:none;" id="queryResult"><span>
    [/@]
    [@b.textfield name="textbook.name" label="名称" value="${textbook.name!}" required="true" maxlength="50" style="width:400px"/]
    [@b.textfield name="textbook.author" label="作者" value=textbook.author! maxlength="100" required="true"/]
    [@b.textfield name="textbook.translator" label="译者" value=textbook.translator! maxlength="100" /]
    [@b.date name="textbook.publishedIn" label="出版年月" value=(textbook.publishedIn)!  format="yyyy-MM" required="true"/]
    [@b.textfield name="textbook.edition" label="版次" value=textbook.edition! maxlength="20" style="width:100px" required="true"/]
    [@b.textfield name="textbook.series" label="丛书" value=textbook.series! maxlength="50" style="width:200px" required="false"/]
    [@b.textfield name="press.name" label="出版社" maxlength="100" value=(textbook.press.name)! required="true" placeholder="输入完整名称或从已有种选择"]
      [@b.select name="press.id" label="" theme="html" items=presses?sort_by('name') empty="..." required="false" onchange="fillPress(this.form,this)"/]
    [/@]
    [@b.select name="textbook.category.id" label="图书分类" value=(textbook.category.id)!
               style="width:200px;" items=categories empty="..." required="false"/]
    [@b.textarea name="textbook.description" label="简介" value=textbook.description! maxlength="600" rows="5" cols="80" required="false"/]
    [@b.select name="textbook.bookType.id" label="教材类型" value=(textbook.bookType.id)!
               style="width:200px;" items=bookTypes empty="..." required="false"/]

    [@b.select name="textbook.awardType.id" label="获奖类型" value=(textbook.awardType.id)!
               style="width:200px;" items=awardTypes empty="..." required="false"/]
    [@b.textfield name="textbook.awardOrg" label="奖项授予单位" value="${textbook.awardOrg!}" required="false" maxlength="200" style="width:200px"/]
    [@b.radios label="是否自编"  name="textbook.madeInSchool" value=textbook.madeInSchool items="1:common.yes,0:common.no" required="true"/]
    [@b.radios label="境内/境外"  name="textbook.domestic" value=textbook.domestic items="1:境内教材,0:境外教材" required="true" onchange="displayForeign(this.value)"/]
    [@b.select label="境外教材类型" id="foreignBookType" name="textbook.foreignBookType.id" value=textbook.foreignBookType! items=foreignBookTypes empty="..." required="false"/]
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
  function fetchByIsbn(anchor,event){
    var form = document.textbookForm;
    event.preventDefault();
    if(form['textbook.isbn'].value){
      var isbn = form['textbook.isbn'].value;
      jQuery.ajax({
        url:anchor.href+"?isbn="+isbn,
        headers:{"Accept":"application/json"},
        success:function (obj){
          var data = JSON.parse(obj);
          if(data.name){
            $("#queryResult").hide();
            form['textbook.name'].value=data.name;
            form['textbook.author'].value=data.author;
            form['press.name'].value=data.press;
            form['textbook.publishedIn'].value=data.publishedIn.replace(".","-");
            form['textbook.edition'].value=data.edition;
          }else{
            $("#queryResult").show();
            $("#queryResult").text("查无此书");
          }
        }
      });
    }
    return false;
  }
  function fillPress(form,select){
    var pressName = jQuery(select).find("option:selected").text()
    if(pressName!='...' && pressName!=''){
      form['press.name'].value=pressName;
    }else{
      form['press.name'].value="";
    }
  }
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
