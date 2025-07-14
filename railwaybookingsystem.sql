create database if not exists railwaybookingsystem;
use railwaybookingsystem;

create table customer (first_name varchar(20), last_name varchar (20)  , email  varchar (30) ,birthdate date, disabled boolean,  username varchar (20) primary key, password varchar(20)  );



create table station ( id  varchar(20) primary key , name varchar(30) , state varchar(20) );
create table transitline ( fare  float , name varchar (30) primary key , departure_time  time , arrival_time time , travel_time  float , num_stops  int , origin  varchar (30) not null , destination  varchar (30) not null ,
foreign key (origin) references station (id)  ,
foreign key (destination) references station (id)  ) ;

create table train (id  varchar(20) primary key, transitline_name  varchar(30), foreign key (transitline_name) references transitline (name)) ;

create table reservation ( number  varchar (20) primary key , id_train varchar(20) not null, date  date , total_fare float , type  varchar(20) , username  varchar(20) not null ,transitline_name  varchar(30) not null,
foreign key (transitline_name) references transitline (name) ,
foreign key (username) references customer (username) ,
foreign key (id_train) references train (id) ) ;

create table has_stop ( transitline_name varchar (30) , id_station  varchar (20) , stop_fare float , departure_time  time, arrival_time  time,
primary key (transitline_name, id_station ),
foreign key (transitline_name) references transitline (name),
foreign key (id_station) references station (id)) ;

create table customer_service (ssn varchar (10) primary key, lastname  varchar(30), first_name varchar(30) , username varchar (20) , password  varchar (30) );
create table manager (ssn varchar (10) primary key, lastname  varchar(30), first_name  varchar(30) ,username varchar (20) , password  varchar (30) );

insert into customer values('Max' ,'Pierre','maxpierre@gmail.com', "1998-03-19", false, 'mp112','qaz12');
