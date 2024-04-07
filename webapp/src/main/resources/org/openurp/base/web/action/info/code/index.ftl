[@b.head/]
[#include "../info_macros.ftl"/]
[@info_header title="基础代码"/]

  <div class="container-fluid">
    <div class="row">
    [#list packages as packageName]
      [@b.card style="width:300px" class="ml-3 mr-3" ]
        [@b.card_header]
          <h5 class="card-title">${packageName}</h5>
          <span class="badge badge-primary" style="float: right;">${entities[packageName]?size}</span>
        [/@]
        [@b.card_body style="padding-top: 0px;"]
          <div style="max-height:350px;overflow: scroll;">
          <table class="table table-hover table-sm">
            <tbody>
            [#list entities[packageName] as codeType]
            <tr>
              <td>${codeType_index+1}</td>
              <td><a href="javascript:loadData('${codeType.entityName}')">${comments[codeType.entityName]}</a></td>
            </tr>
            [/#list]
          </table>
         </div>
        [/@]
      [/@]
    [/#list]
    </div>
  </div>

<button type="button" id="modal-btn" class="btn btn-primary" data-toggle="modal" data-target="#dataModal" style="display:none">
  Loading data
</button>

  <div class="modal fade" id="dataModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">代码列表</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body" id="code-list" style="padding-top: 0px;">
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
        </div>
      </div>
    </div>
  </div>
  <script>
    function loadData(clazz){
       jQuery("#code-list").html("加载中....");
       bg.Go("${b.url('!list')}?clazz="+clazz,"code-list");
       jQuery("#modal-btn").click();
    }
  </script>
[@b.foot/]
