CREATE TABLE book (
    id                      BIGSERIAL PRIMARY KEY NOT NULL,
    author                  varchar(255) NOT NULL,
    isbn                    varchar(255) UNIQUE NOT NULL,
    price                   numeric(9, 2) NOT NULL,
    title                   varchar(255) NOT NULL,
    created_date            timestamp NOT NULL,
    last_modified_date      timestamp NOT NULL,
    version                 integer NOT NULL
);