insert into user values (90001,sysdate(), 'User1', 'test1111','707070-1111111');
insert into user values (90002,sysdate(), 'User2', 'test2222','807070-2222222');
insert into user values (90003,sysdate(), 'User3', 'test3333','909999-1111111');

insert into post values (10001, 'My first post', 90001);
insert into post values (10002, 'My Second post', 90001);
insert into post values (10003, 'My first post90002', 90002);