[#ftl attributes={"content_type":"application/javascript"}]
${Parameters["callback"]}([[#list codes as code]
{id:${code.id},[#t]
name:"${code.name}",[#t]
indexno:"${code.indexno}"}[#if code_has_next],[/#if][#t]
[/#list]
])