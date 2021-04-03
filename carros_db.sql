drop database expensetrackerdb;
drop user expensetracker;
create user expensetracker with password 'password';
create database expensetrackerdb with template=template0 owner=expensetracker;
\connect expensetrackerdb;
alter default privileges grant all on tables to expensetracker;
alter default privileges grant all on sequences to expensetracker;

create table carros(
                         carro_id integer primary key not null,
                         marca varchar(20) not null,
                         modelo varchar(20) not null,
                         ano varchar(30) not null,
                         placa varchar(30) not null,
                         cor text not null
);

create sequence et_carros_seq increment 1 start 1;
