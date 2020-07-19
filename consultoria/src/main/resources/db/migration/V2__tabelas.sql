insert into usuario values (NULL, 1, '$2a$10$MAajnv2bVF5BTIg1J/9L6e3uMA7Hd32aOOn9nMDSDuUmvENkykJwO', 'SIGO', 1);

insert into perfis values (NULL, 'ROLE_ADMIN', (select id from usuario where codigo = 1));

insert into perfis values (NULL, 'ROLE_USUARIO', (select id from usuario where codigo = 1));