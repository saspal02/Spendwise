INSERT INTO payment_mode (name, type)
VALUES
    ('Credit Card', 'LIABILITY'),
    ('Debit Card', 'ASSET'),
    ('Net Banking', 'ASSET'),
    ('UPI', 'ASSET'),
    ('Cash', 'ASSET'),
    ('Digital Wallet', 'ASSET');

INSERT INTO bank (name)
VALUES
    ('State Bank of India'),
    ('HDFC Bank'),
    ('ICICI Bank'),
    ('Axis Bank'),
    ('Punjab National Bank'),
    ('Bank of Baroda'),
    ('Canara Bank'),
    ('Central Bank of India'),
    ('Union Bank of India'),
    ('IndusInd Bank'),
    ('Kotak Mahindra Bank');

INSERT INTO category (name)
VALUES
    ('Groceries'),
    ('Dining Out'),
    ('Rent/EMI'),
    ('Utilities (Electricity/Water)'),
    ('Fuel/Transportation'),
    ('Health & Medical'),
    ('Insurance'),
    ('Shopping (Clothing/Electronics)'),
    ('Entertainment & OTT'),
    ('Education'),
    ('Investments (SIP/Stocks)'),
    ('Gifts & Donations'),
    ('Travel & Vacation'),
    ('Maintenance & Repairs'),
    ('Miscellaneous');