create table if not exists author
(
    id         bigint generated always as identity,
    last_name        varchar              not null,
    first_name       varchar             not null,
    patronymic       varchar,
    birthday         date,

    primary key (id)
);

create table if not exists book
(
    id      bigint generated always as identity,
    title                varchar       not null,
    isbn                 varchar,
    year_of_publishing   int,
    number_of_pages      int,

    primary key (id),
    unique(isbn)
);

create table if not exists author_book
(
    author_id bigint not null,
    book_id   bigint not null,
    primary key (author_id, book_id),
    foreign key (author_id) references author (id),
    foreign key (book_id) references book (id)
);