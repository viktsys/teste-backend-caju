--- Insert the accounts used
INSERT INTO account (cash_balance, food_balance, meal_balance, id) VALUES(100.0, 50.0, 50.0, 123);
INSERT INTO account (cash_balance, food_balance, meal_balance, id) VALUES(0.0, 50.0, 50.0, 456);
INSERT INTO account (cash_balance, food_balance, meal_balance, id) VALUES(0.0, 0.0, 50.0, 789);

-- Insert the MerchantCodeChargeability list used by the system
INSERT INTO merchant_code_chargeability (id, charge_type, code) VALUES(1, 'FOOD', '5411');
INSERT INTO merchant_code_chargeability (id, charge_type, code) VALUES(2, 'FOOD', '5412');
INSERT INTO merchant_code_chargeability (id, charge_type, code) VALUES(3, 'MEAL', '5811');
INSERT INTO merchant_code_chargeability (id, charge_type, code) VALUES(4, 'MEAL', '5812');

-- Insert the MerchanteNameChargeability list used by the system
INSERT INTO merchant_name_chargeability (id, charge_type, "name") VALUES(1, 'FOOD', 'SUPERLAGOA FORTALEZA BR');
INSERT INTO merchant_name_chargeability (id, charge_type, "name") VALUES(2, 'MEAL', 'Pag*LanLanches FORTALEZA BR');
INSERT INTO merchant_name_chargeability (id, charge_type, "name") VALUES(3, 'FOOD', 'NalticoPl FORTALEZA BR');
INSERT INTO merchant_name_chargeability (id, charge_type, "name") VALUES(4, 'MEAL', 'CafePlants FORTALEZA BR');
INSERT INTO merchant_name_chargeability (id, charge_type, "name") VALUES(5, 'MEAL', 'Sao Gerardo LTDA FORTALEZA BR');