alter table REGISTER alter column USERNAME rename to NAME;

alter table REGISTER
	add gmt_create bigint;

alter table REGISTER
	add gmt_modified bigint;

alter table REGISTER
	add avatar_url varchar(200);