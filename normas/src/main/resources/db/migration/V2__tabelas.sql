alter table documentos drop versao;
alter table documentos add numero_referencia varchar(45) not null ;
alter table documentos add data DATE not null ;


insert into tipo_norma values (NULL, 'Portaria');
insert into tipo_norma values (NULL, 'Lei Municipal');
insert into tipo_norma values (NULL, 'Lei Estadual');
insert into tipo_norma values (NULL, 'Lei Federal');
insert into tipo_norma values (NULL, 'Instrução Normativa');

insert into categoria_norma values (NULL , 'Meio Ambiente');
insert into categoria_norma values (NULL , 'Indústria');
insert into categoria_norma values (NULL , 'Segurança do trabalho');

insert into norma values (null , '', '', 1, 1);