CREATE TABLE benefits (
        id UUID NOT NULL,
        institution_id UUID NOT NULL,
        name VARCHAR(255) NOT NULL,
        description VARCHAR(700) NOT NULL,
        icon_name VARCHAR(50) NOT NULL,
        amount DECIMAL(10, 2) NOT NULL,
        active BOOLEAN NOT NULL,
        expected_payment_day INT,
        expected_payment_time TIME,
        frequency VARCHAR(50) NOT NULL,
        created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
        updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,

        CONSTRAINT pk_benefits PRIMARY KEY (id),
        CONSTRAINT uq_benefits_name_institution UNIQUE (name, institution_id)
);
