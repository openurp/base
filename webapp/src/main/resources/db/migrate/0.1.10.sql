update se.func_resources r
set name='/admin/edu'||name where exists(select * from cfg.apps a where a.id=r.app_id
and a.name='edu-base-adminapp');

update se.func_resources r
set name='/info/edu'||name where exists(select * from cfg.apps a where a.id=r.app_id
and a.name='edu-base-infoapp');

update se.func_resources r
set name='/admin'||name where exists(select * from cfg.apps a where a.id=r.app_id
and a.name='base-adminapp');

update se.func_resources  r set app_id=(select id from cfg.apps a where a.name='edu-base-adminapp')
where exists(select * from cfg.apps a where a.id=r.app_id and a.name in('base-adminapp','edu-base-infoapp'));

update se.menus  r set app_id=(select id from cfg.apps a where a.name='edu-base-adminapp')
where exists(select * from cfg.apps a where a.id=r.app_id and a.name in('base-adminapp','edu-base-infoapp'));

update cfg.apps set name='base-webapp',title='基础信息',secret='base-webapp' where name='edu-base-adminapp';

