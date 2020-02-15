create table Register
(
	id int auto_increment,
	username varchar(20) not null,
	password varchar(20) not null,
	constraint Register_pk
		primary key (id)
);

create unique index Register_username_uindex
	on Register (username);

