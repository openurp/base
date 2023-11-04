  <table class="table table-hover table-sm table-striped">
    <thead>
      <tr>
        <th style="width:40px">序号</th>
        <th style="width:40px">代码</th>
        <th>名称</th>
        <th>英文名</th>
      </tr>
    </thead>
    <tbody>
    [#list codes as code]
    <tr>
      <td>${code_index+1}</td>
      <td>${code.code}</td>
      <td>${code.name}</td>
      <td>${code.enName!}</td>
    </tr>
    [/#list]
  </table>
