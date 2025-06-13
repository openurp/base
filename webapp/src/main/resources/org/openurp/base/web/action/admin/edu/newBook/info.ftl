[#ftl]
[@b.head/]
[@b.messages slash="3"/]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">ISBN:</td>
    <td class="content">${textbook.isbn!}</td>
    <td class="title" width="20%">名称:</td>
    <td class="content">${textbook.name}</td>
  </tr>
  <tr>
    <td class="title">作者:</td>
    <td class="content">${textbook.author!}</td>
    <td class="title">译者:</td>
    <td class="content">${textbook.translator!}</td>
  </tr>
  <tr>
    <td class="title">出版社:</td>
    <td class="content">${(textbook.press.name)!}</td>
    <td class="title">出版年月:</td>
    <td class="content">${(textbook.publishedIn?string('yyyy-MM'))!}</td>
  </tr>
  <tr>
    <td class="title">版次:</td>
    <td class="content">${textbook.edition!}</td>
    <td class="title">丛书:</td>
    <td class="content">${textbook.series!}</td>
  </tr>
   <tr>
    <td class="title">教材类型:</td>
    <td class="content">${(textbook.bookType.name)!}</td>
    <td class="title">获奖类型:</td>
    <td class="content">${(textbook.awardType.name)!}</td>
  </tr>
  <tr>
    <td class="title">奖项授予单位:</td>
    <td class="content">${(textbook.awardOrg)!}</td>
   <td class="title">是否自编:</td>
   <td class="content">${(textbook.madeInSchool?string("是","否"))!}</td>
  </tr>
</table>

[@b.foot/]
