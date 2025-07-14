create database if not exists railwaybookingsystem;
use railwaybookingsystem;

create table customer (first_name varchar(20), last_name varchar (20)  , email  varchar (30) ,birthdate date, disabled boolean,  username varchar (20) primary key, password varchar(20)  );
create table station (  id  varchar(20) primary key , name varchar(30) , state varchar(20) );
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

create table customer_service (ssn varchar (10) primary key, last_name  varchar(30), first_name varchar(30) , username varchar (20) , password  varchar (30) );
create table manager (ssn varchar (10) primary key, lastname  varchar(30), first_name  varchar(30) ,username varchar (20) , password  varchar (30) );
create table questions (id int primary key auto_increment, question varchar (500), customer varchar (20),customer_service varchar (10) , foreign key (customer) references customer (username) , foreign key (customer_service) references customer_service (ssn));

insert into customer values('Max' ,'Pierre','maxpierre@gmail.com', "1998-03-19", false, 'mp112','qaz12');
insert into customer_service (ssn, lastname, first_name, username, password) VALUES ('111223333', 'Smith', 'John', 'jsmith', 'service123');
insert into customer_service values ('2345678901', 'Johnson', 'Michael', 'mjohnson_cs', 'helpdesk456'), ('3456789012', 'Williams', 'Sarah', 'swilliams_cs', 'support789'), ('4567890123', 'Brown', 'David', 'dbrown_cs', 'assist2023'),
('5678901234', 'Davis', 'Emily', 'edavis_cs', 'csrep!2024');
insert into manager (ssn, lastname, first_name, username, password) VALUES ('999887777', 'Johnson', 'Lisa', 'ljohnson', 'manage123');
INSERT INTO station VALUES
('ST001', 'New York Penn', 'NY'),
('ST002', 'Newark', 'NJ'),
('ST003', 'Philadelphia', 'PA'),
('ST004', 'Baltimore', 'MD'),
('ST005', 'Washington DC', 'DC'),
('ST006', 'Boston', 'MA'),
('ST007', 'Providence', 'RI'),
('ST008', 'Hartford', 'CT');

INSERT INTO transitline VALUES
(50.0, 'NortheastExpress', '08:00', '13:30', 5.5, 5, 'ST001', 'ST005'),
(60.0, 'CapitalConnector', '09:30', '13:30', 4.0, 4, 'ST002', 'ST005'),
(70.0, 'BostonLine', '14:00', '17:00', 3.0, 3, 'ST006', 'ST008'),
(40.0, 'GardenStateRoute', '09:30', '10:30', 1.0, 2, 'ST002', 'ST003'),
(45.0, 'LibertyLine', '08:00', '10:30', 2.5, 3, 'ST001', 'ST003');

INSERT INTO train VALUES
('T001', 'NortheastExpress'),
('T002', 'CapitalConnector'),
('T003', 'BostonLine'),
('T004', 'GardenStateRoute'),
('T005', 'LibertyLine');

-- NortheastExpress
INSERT INTO has_stop VALUES
('NortheastExpress', 'ST001', 0.0, '08:00', '08:00'),
('NortheastExpress', 'ST002', 10.0, '08:30', '08:45'),
('NortheastExpress', 'ST003', 20.0, '09:30', '09:45'),
('NortheastExpress', 'ST004', 30.0, '11:00', '11:10'),
('NortheastExpress', 'ST005', 50.0, '13:15', '13:30');

-- CapitalConnector
INSERT INTO has_stop VALUES
('CapitalConnector', 'ST002', 0.0, '09:30', '09:30'),
('CapitalConnector', 'ST003', 15.0, '10:30', '10:45'),
('CapitalConnector', 'ST004', 25.0, '11:45', '12:00'),
('CapitalConnector', 'ST005', 60.0, '13:15', '13:30');

-- BostonLine
INSERT INTO has_stop VALUES
('BostonLine', 'ST006', 0.0, '14:00', '14:00'),
('BostonLine', 'ST007', 30.0, '15:00', '15:15'),
('BostonLine', 'ST008', 70.0, '16:45', '17:00');

-- GardenStateRoute
INSERT INTO has_stop VALUES
('GardenStateRoute', 'ST002', 0.0, '09:30', '09:30'),
('GardenStateRoute', 'ST003', 40.0, '10:15', '10:30');

-- LibertyLine
INSERT INTO has_stop VALUES
('LibertyLine', 'ST001', 0.0, '08:00', '08:00'),
('LibertyLine', 'ST002', 15.0, '09:00', '09:15'),
('LibertyLine', 'ST003', 45.0, '10:15', '10:30');

alter table reservation add column origin varchar (20),add column destination varchar(20), add foreign key (origin) references station(id), add foreign key (destination) references station(id);




