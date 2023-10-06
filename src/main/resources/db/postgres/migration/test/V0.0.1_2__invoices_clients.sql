insert into client (reference, client_type, social_reason, siret_number, revenue, gas_delivery_point, electricity_delivery_point)
    values ('EKW00000001', 'professional', 'GeoPost', 'Siret_00000001', 1200000, 'GDP0001', 'EDP0001') on conflict do nothing;
insert into client (reference, client_type, social_reason, siret_number, revenue, gas_delivery_point, electricity_delivery_point)
    values ('EKW00000002', 'professional', 'Rakuten', 'Siret_00000001', 800000, 'GDP0002', 'EDP0002') on conflict do nothing;
insert into client (reference, client_type, social_reason, siret_number, revenue, gas_delivery_point, electricity_delivery_point)
    values ('EKW00000003', 'professional', 'BNP', 'Siret_00000001', 1500000, 'GDP0003', 'EDP0003') on conflict do nothing;
insert into client (reference, client_type, social_reason, siret_number, revenue, gas_delivery_point, electricity_delivery_point)
    values ('EKW00000004', 'professional', 'CA', 'Siret_00000001', 700000, 'GDP0004', 'EDP0004') on conflict do nothing;

insert into client (reference, client_type, civility, first_name, last_name, gas_delivery_point, electricity_delivery_point)
    values ('EKW00000011', 'individual', 'M.', 'Dupont', 'DUPONT', 'GDP0005', 'EDP0005') on conflict do nothing;
insert into client (reference, client_type, civility, first_name, last_name, gas_delivery_point, electricity_delivery_point)
    values ('EKW00000012', 'individual', 'Mme.', 'Nhu', 'TA', 'GDP0006', 'EDP0006') on conflict do nothing;
insert into client (reference, client_type, civility, first_name, last_name, gas_delivery_point, electricity_delivery_point)
    values ('EKW00000013', 'individual', 'Mlle', 'Charlene', 'BOURDON', 'GDP0007', 'EDP0007') on conflict do nothing;
insert into client (reference, client_type, civility, first_name, last_name, gas_delivery_point, electricity_delivery_point)
    values ('EKW00000014', 'individual', 'M.', 'Karim', 'KSIBI', 'GDP0008', 'EDP0008') on conflict do nothing;


insert into invoice (client_reference, invoice_date, gas_begin_index, electricity_begin_index, gas_end_index, electricity_end_index, gas_total_amount, electricity_total_amount)
    values ('EKW00000001', '2023-07-01', 5, 8, 1005, 1008, 500, 700) on conflict do nothing;
insert into invoice (client_reference, invoice_date, gas_begin_index, electricity_begin_index, gas_end_index, electricity_end_index, gas_total_amount, electricity_total_amount)
    values ('EKW00000001', '2023-08-01', 1005, 1008, 2005, 2008, 600, 800) on conflict do nothing;

insert into invoice (client_reference, invoice_date, gas_begin_index, electricity_begin_index, gas_end_index, electricity_end_index, gas_total_amount, electricity_total_amount)
    values ('EKW00000002', '2023-09-01', 9, 15, 1009, 1015, 550, 750) on conflict do nothing;

insert into invoice (client_reference, invoice_date, gas_begin_index, electricity_begin_index, gas_end_index, electricity_end_index, gas_total_amount, electricity_total_amount)
    values ('EKW00000003', '2023-07-01', 6, 15, 1006, 1015, 600, 800) on conflict do nothing;
insert into invoice (client_reference, invoice_date, gas_begin_index, electricity_begin_index, gas_end_index, electricity_end_index, gas_total_amount, electricity_total_amount)
    values ('EKW00000003', '2023-08-01', 1006, 1015, 2006, 2015, 700, 900) on conflict do nothing;
insert into invoice (client_reference, invoice_date, gas_begin_index, electricity_begin_index, gas_end_index, electricity_end_index, gas_total_amount, electricity_total_amount)
    values ('EKW00000003', '2023-09-01', 2006, 2015, 3006, 3015, 750, 850) on conflict do nothing;

insert into invoice (client_reference, invoice_date, gas_begin_index, electricity_begin_index, gas_end_index, electricity_end_index, gas_total_amount, electricity_total_amount)
    values ('EKW00000011', '2023-07-01', 6, 15, 106, 115, 111, 150) on conflict do nothing;
insert into invoice (client_reference, invoice_date, gas_begin_index, electricity_begin_index, gas_end_index, electricity_end_index, gas_total_amount, electricity_total_amount)
    values ('EKW00000011', '2023-08-01', 106, 115, 206, 215, 111, 150) on conflict do nothing;
insert into invoice (client_reference, invoice_date, gas_begin_index, electricity_begin_index, gas_end_index, electricity_end_index, gas_total_amount, electricity_total_amount)
    values ('EKW00000011', '2023-09-01', 206, 215, 306, 315, 111, 150) on conflict do nothing;
