CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    lastname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    last_modified_by VARCHAR(255),
    last_modified_at TIMESTAMP
);

CREATE TABLE tokens (
   id BIGSERIAL PRIMARY KEY NOT NULL,
   token VARCHAR(255) NOT NULL,
   token_type VARCHAR(255) NOT NULL,
   expired BOOLEAN NOT NULL,
   revoked BOOLEAN NOT NULL,
   user_id BIGINT NOT NULL
);