[#ftl]
[@b.head/]
[@b.toolbar title="教材信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">ISBN:</td>
    <td class="content">${textbook.isbn}</td>
    <td class="title" width="20%">名称:</td>
    <td class="content">${textbook.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">作者:</td>
    <td class="content">${textbook.author!}</td>
    <td class="title" width="20%">译者:</td>
    <td class="content">${textbook.translator!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">出版年月:</td>
    <td class="content">${(textbook.publishedOn?string('YYYY-MM'))!}</td>
    <td class="title" width="20%">版次:</td>
    <td class="content">${textbook.edition!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">丛书:</td>
    <td class="content">${textbook.series!}</td>
    <td class="title" width="20%">出版社:</td>
    <td class="content">${(textbook.press.name)!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">简介:</td>
    <td class="content" colspan="3">${(textbook.description)!}</td>
  </tr>
   <tr>
    <td class="title" width="20%">教材类型:</td>
    <td class="content">${(textbook.bookType.name)!}</td>
    <td class="title" width="20%">获奖类型:</td>
    <td class="content">${(textbook.awardType.name)!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">奖项授予单位:</td>
    <td class="content">${(textbook.awardOrg)!}</td>
   <td class="title" width="20%">是否自编:</td>
   <td class="content">${(textbook.madeInSchool?string("是","否"))!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">生效日期:</td>
    <td class="content" >${textbook.beginOn!}~${textbook.endOn!}</td>
    <td class="title" width="20%">定价:</td>
    <td class="content" >${textbook.price!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">备注:</td>
    <td class="content" colspan="3">${textbook.remark!}</td>
  </tr>
</table>

[@b.foot/]
