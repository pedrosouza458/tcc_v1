CREATE TABLE users (
        id UUID NOT NULL,
        name VARCHAR(255) NOT NULL,
        email VARCHAR(255) NOT NULL,
        password VARCHAR(255) NOT NULL,
        cpf VARCHAR(11) NOT NULL,
        phone VARCHAR(15),
        profile_picture VARCHAR(255),
        institution_id UUID,
        role VARCHAR(50) NOT NULL,
        created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
        updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,

        CONSTRAINT pk_users PRIMARY KEY (id),
        CONSTRAINT uq_users_email UNIQUE (email),
        CONSTRAINT uq_users_cpf UNIQUE (cpf)
    );