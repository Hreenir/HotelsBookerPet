create table tgusers (
    id bigint UNIQUE not null ,
    role bigint,
    primary key (id),
    foreign key (role) references roles (id)
);