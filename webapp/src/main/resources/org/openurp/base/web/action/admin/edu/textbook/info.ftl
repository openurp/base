[#ftl]
[@b.head/]
[@b.toolbar title="教材信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="table table-sm table-detail">
  <tr>
    <td class="title" width="20%">ISBN:</td>
    <td >${textbook.isbn}</td>
    <td class="title" width="20%">名称:</td>
    <td >${textbook.name}</td>
  </tr>
  <tr>
    <td class="title">作者:</td>
    <td >${textbook.author!}</td>
    <td class="title">译者:</td>
    <td >${textbook.translator!}</td>
  </tr>
  <tr>
    <td class="title">出版年月:</td>
    <td >${(textbook.publishedIn?string('yyyy-MM'))!}</td>
    <td class="title">版次:</td>
    <td >${textbook.edition!}</td>
  </tr>
  <tr>
    <td class="title">丛书:</td>
    <td >${textbook.series!}</td>
    <td class="title">出版社:</td>
    <td >${(textbook.press.name)!}</td>
  </tr>
  <tr>
    <td class="title">简介:</td>
    <td  colspan="3">${(textbook.description)!}</td>
  </tr>
   <tr>
    <td class="title">教材类型:</td>
    <td >${(textbook.bookType.name)!}</td>
    <td class="title">获奖类型:</td>
    <td >${(textbook.awardType.name)!}</td>
  </tr>
  <tr>
    <td class="title">奖项授予单位:</td>
    <td >${(textbook.awardOrg)!}</td>
   <td class="title">是否自编:</td>
   <td >${(textbook.madeInSchool?string("是","否"))!}</td>
  </tr>
  <tr>
    <td class="title">生效日期:</td>
    <td >${textbook.beginOn!}~${textbook.endOn!}</td>
    <td class="title">定价:</td>
    <td  >${textbook.price!}</td>
  </tr>
  <tr>
    <td class="title">采用课程:</td>
    <td colspan="3">
      <ul style="padding-left: 1rem;list-style: decimal;margin:0px;">
      [#list courses as p]
        <li>[@b.a href="course!info?id="+p.id]${p.name}[/@] ${p.code!} ${p.defaultCredits}学分</li>
      [/#list]
      </ul>
    </td>
  </tr>
  <tr>
    <td class="title">添加人:</td>
    <td>[#if textbook.creator??]${textbook.creator.code} ${textbook.creator.name}[/#if]</td>
    <td class="title">备注:</td>
    <td>${textbook.remark!}</td>
  </tr>
</table>

[@b.foot/]
