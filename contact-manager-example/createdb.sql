
drop table if exists contact;

create table contact
(
	id integer not null primary key autoincrement,
	name text not null,
	phone text,
	address text,
	email text,
	url text
);