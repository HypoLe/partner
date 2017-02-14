-- drop the existing database
drop database EPMSDB;

-- create the test user
create user test password 'test';

-- create the database
create database EPMSDB owner test;
