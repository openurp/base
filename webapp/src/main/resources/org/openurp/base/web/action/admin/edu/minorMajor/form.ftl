[#ftl]
[@b.head/]
[@b.toolbar title="修改辅修专业"]bar.addBack();[/@]
[@b.form action=b.rest.save(major) theme="list"]
  [@b.textfield name="major.code" label="代码" value=major.code! required="true" maxlength="80"/]
  [@b.textfield name="major.name" label="名称" value=major.name! required="true" maxlength="80"/]
  [@b.textfield name="major.enName" label="英文名" value=major.enName! maxlength="180"/]
  [@base.code type="discipline-categories"  name="major.category.id" label="学科门类" value=major.category! required="true"/]
  [@base.code type="institutions"  name="major.institution.id" label="所属院校" value=major.institution!  required="true"/]
  [@b.startend label="有效期限"
    name="major.beginOn,major.endOn" required="true,false"
    start=major.beginOn end=major.endOn format="date"/]
  [@b.formfoot]
    [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
  [/@]
[/@]
<div style="height:300px"></div>
[@b.foot/]
