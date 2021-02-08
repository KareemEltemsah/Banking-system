Create DATABASE banksystem;
use   banksystem;

-- Drop Tables
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS bankAccount;
DROP TABLE IF EXISTS bankTransaction;


-- Create Tables
CREATE TABLE customer (
  CustomerID    	int AUTO_INCREMENT,
  CustomerPassword	varchar(16) not null,
  CustomerName   	varchar(20) not null,
  CustomerAddress  	varchar(50) not null,
  CustomerMobile 	char(11) not null,
  primary key (CustomerID)
);

CREATE TABLE bankaccount (
  BankAccountID 	int AUTO_INCREMENT,
  BACreationDate 	datetime not null,
  BACurrentBalance 	float not null,
  CustomerID 		int not null,
  primary key (BankAccountID),
  foreign key (CustomerID) references customer(CustomerID)
);

CREATE TABLE banktransaction (
  BankTransactionID 	int AUTO_INCREMENT,
  BTCreationDate 		datetime not null,
  BTAmount 				float not null,
  BTFromAccount 		int not null,
  BTToAccount			int not null,
  primary key (BankTransactionID),
  foreign key (BTFromAccount) references bankaccount(BankAccountID),
  foreign key (BTToAccount) references bankaccount(BankAccountID)
);

-- load data
insert into customer(CustomerPassword,CustomerName,CustomerAddress,CustomerMobile) 
	values ('kareem123','Kareem','address1','01011231234');
insert into customer(CustomerPassword,CustomerName,CustomerAddress,CustomerMobile) 
	values ('ahmed123','Ahmed','address2','01021231234');
insert into customer(CustomerPassword,CustomerName,CustomerAddress,CustomerMobile) 
	values ('osama123','Osama','address3','01031231234');
insert into customer(CustomerPassword,CustomerName,CustomerAddress,CustomerMobile) 
	values ('mahmoud123','mahmoud','address4','01041231234');

insert into bankaccount (BACreationDate,BACurrentBalance,CustomerID) values ('2020-12-25 19:10:00',1500,1);
insert into bankaccount (BACreationDate,BACurrentBalance,CustomerID) values ('2020-12-25 20:15:20',1300,2);
insert into bankaccount (BACreationDate,BACurrentBalance,CustomerID) values ('2020-12-25 23:30:10',1200,3);

insert into banktransaction(BTCreationDate,BTAmount,BTFromAccount,BTToAccount)
	values ('2020-12-26 19:10:00',400,1,3);
insert into banktransaction(BTCreationDate,BTAmount,BTFromAccount,BTToAccount)
	values ('2020-12-26 20:15:00',200,1,2);
insert into banktransaction(BTCreationDate,BTAmount,BTFromAccount,BTToAccount)
	values ('2020-12-26 21:30:00',100,2,1);
