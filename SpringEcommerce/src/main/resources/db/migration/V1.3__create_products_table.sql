-- V1.3__create_products_table.sql

CREATE TABLE IF NOT EXISTS public.product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(1000),
    price NUMERIC(12, 2) NOT NULL,
    sku VARCHAR(50) NOT NULL,
    image_url VARCHAR(255),
    category_id BIGINT,
    quantity INT DEFAULT 0,
    manufacturer VARCHAR(100),
    featured BOOLEAN DEFAULT false,
    FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE SET NULL
);