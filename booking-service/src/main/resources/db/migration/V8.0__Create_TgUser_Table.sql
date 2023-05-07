create table tgusers (
    id bigint not null,
    role bigint,
    primary key (id),
    foreign key (role) references roles (id)
);