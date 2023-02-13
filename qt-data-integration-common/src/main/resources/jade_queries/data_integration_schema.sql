drop table if exists `jade_cash_invoice`;

create table `jade_cash_invoice`
(
    `gkey` bigint auto_increment,
    `invoice_date` date         default null,
    `receipt_no` text,
    `amount`    double       default null,
    `column1`   double       default null,
    `column2`   double       default null,
    `column3`   double       default null,
    `column4`   double       default null,
    `column5`   double       default null,
    `column6`   double       default null,
    `column7`   double       default null,
    `column8`   double       default null,
    `column9`   double       default null,
    `column10`  double       default null,
    `column11`  double       default null,
    `column12`  double       default null,
    `column13`  double       default null,
    `column14`  double       default null,
    `column15`  double       default null,
    `column16`  double       default null,
    `column17`  double       default null,
    `column18`  double       default null,
    `column19`  double       default null,
    `column20`  double       default null,
    `column21`  double       default null,
    `column22`  double       default null,
    `column23`  double       default null,
    `column24`  double       default null,
    `column25`  double       default null,
    `column26`  double       default null,
    `column27`  double       default null,
    `column28`  double       default null,
    `column29`  double       default null,
    PRIMARY KEY (gkey)
);

drop table if exists `sequence`;

create table `sequence`
(
    `gkey` bigint auto_increment,
    `sequence_format` varchar(255) not null,
    `sequence_counter` bigint not null,
    `maximum_digits` bigint not null,
    `sequence_type` varchar(255) not null,
    primary key (gkey)
);

INSERT INTO data_import_integration.`sequence` (sequence_format,sequence_counter,maximum_digits,sequence_type) VALUES ('{MM}{YYYY}{SQ}',1,8,'JADE_CASH_INVOICE');

drop table if exists `generate_payment`;

create table `generate_payment`
(
    `gkey` bigint auto_increment,
    `last_run_date` datetime(3) null,
    primary key (gkey)
);

drop table if exists `data_integration_job`;

create table `data_integration_job`
(
    `gkey` bigint auto_increment,
    `job_run_date` datetime(3) not null,
    `module_name` varchar(255) not null ,
    primary key (gkey)
);