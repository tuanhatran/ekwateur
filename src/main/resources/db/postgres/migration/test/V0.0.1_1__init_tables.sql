create table client (
    reference                   varchar(255) primary key,
    client_type                 varchar(255) not null,
    siret_number                varchar(255),
    social_reason               varchar(255),
    revenue                     float,
    civility                    varchar(255),
    first_name                  varchar(255),
    last_name                   varchar(255),
    gas_delivery_point          varchar(255) not null,
    electricity_delivery_point  varchar(255) not null,
    CONSTRAINT reference_unique UNIQUE (reference),
    CONSTRAINT gas_delivery_point_unique UNIQUE (gas_delivery_point),
    CONSTRAINT electricity_delivery_point_unique UNIQUE (electricity_delivery_point)
);

create table invoice
(
    id                          serial primary key,
    client_reference            varchar(255) not null,
    gas_begin_index             integer,
    electricity_begin_index     integer,
    gas_end_index             integer,
    electricity_end_index     integer,
    gas_total_amount            float,
    electricity_total_amount    float,
    invoice_date        DATE NOT NULL,
    CONSTRAINT fk_client FOREIGN KEY (client_reference) REFERENCES client(reference)
);

