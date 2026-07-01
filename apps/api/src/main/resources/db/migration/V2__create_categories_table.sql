CREATE TABLE categories (
        id UUID NOT NULL,
        name VARCHAR(255) NOT NULL,
        color_code VARCHAR(7) NOT NULL,
        icon_name VARCHAR(50) NOT NULL,
        created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
        updated_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,

        CONSTRAINT pk_categories PRIMARY KEY (id),
        CONSTRAINT uq_categories_name UNIQUE (name)
);