create table account (id bigint generated by default as identity, balance numeric(19,2), primary key (id));
create table usr (id bigint generated by default as identity, username varchar(255), account_id bigint, primary key (id));
alter table usr add constraint FKbj6hlgnhu2s1xsuslt6cjrrvp foreign key (account_id) references account;