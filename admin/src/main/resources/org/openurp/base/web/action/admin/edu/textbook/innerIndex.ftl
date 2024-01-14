[#ftl]
<div class="search-container" id="textbook-search-container">
    <div class="search-panel">
    [@b.form name="textbookSearchForm" action="!search" target="textbooklist" title="ui.searchForm" theme="search"]
      [@b.textfields names="textbook.isbn;ISBN"/]
      [@b.textfields names="textbook.name;名称,textbook.author;作者,textbook.press.name;出版社,textbook.category.name;分类,textbook.series;丛书,textbook.awardType.name;获奖"/]
      [@b.select style="width:100px" name="textbook.madeInSchool" label="是否自编" items={"1":"是", "0":"否"} empty="..."/]
      [@b.select style="width:100px" name="active" label="是否有效" items={"1":"是", "0":"否"} empty="..." value="1"/]
      <input type="hidden" name="orderBy" value="textbook.code"/>
    [/@]
    </div>
    <div class="search-list">[@b.div id="textbooklist" href="!search?active=1&orderBy=textbook.name asc"/]
    </td>
  </tr>
</table>
