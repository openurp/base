[@b.head/]
[@b.toolbar title="学生基础代码维护"/]
  <div class="container-fluid">
    <div class="row">
      <div class="col-2">
      [@b.card]
        [@b.card_header]
          <div class="input-group input-group-sm" style="width: 100%;margin: auto;">
            <input class="form-control form-control-navbar" type="search" name="q" id="meta_q" value=""
                   aria-label="Search" placeholder="输入关键词，在${metas?size}个中查找" autofocus="autofocus"
                   onchange="return search(this.value);">
            <div class="input-group-append">
              <button class="input-group-text" type="submit" onclick="return search(document.getElementById('meta_q').value);">
                <i class="fas fa-search"></i>
              </button>
            </div>
          </div>
          <script>
             function search(q){
              jQuery("#code_meta_list li").each(function(i,e){
                var anchors = jQuery(e).children("a");
                var matched = (q=="") || anchors.length<1;
                if(!matched){
                  for(var idx=0;idx < anchors.length;idx++){
                    if(q=='' || anchors[0].innerHTML.indexOf(q)>-1){
                      matched=true;
                      break;
                    }
                  }
                }
                if(matched){
                  jQuery(e).show();
                }else{
                  jQuery(e).hide();
                }
              });
              return false;
             }
             function toggleCode(elem){
               jQuery(elem).parent().parent().find("li a").removeClass("active");
               jQuery(elem).addClass("active");
             }
          </script>

        [/@]
        [@b.card_body style="padding-top: 0px;"]
          <div style="max-height:700px;overflow: scroll;">
          <ul class="nav nav-pills flex-column" id="code_meta_list">
            [#list metas as m]
              <li class="nav-item">
               [#assign item_link_class][#if m_index=0]nav-link active[#else]nav-link[/#if][/#assign]
               [@b.a href="code/{category}!search?category="+m.code target="code_list" class=item_link_class onclick="toggleCode(this);return bg.Go(this,'code_list')"]
                ${(m_index+1)?string("00")}&nbsp;&nbsp;${m.name}
               [/@]
              </li>
            [/#list]
          </ul>
         </div>
        [/@]
      [/@]
      </div>
      <div  class="col-10">
        [@b.div id="code_list" href="code/{category}!search?category="+metas?first.code style="border: 0.5px solid var(--grid-border-color);"/]
      </div>
    </div>
  </div>
[@b.foot/]
