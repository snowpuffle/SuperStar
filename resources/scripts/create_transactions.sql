CREATE TABLE Transactions (
    ID INT PRIMARY KEY,
    TotalAmount DECIMAL NOT NULL,
    TaxAmount DECIMAL DEFAULT 0.00, 
    Type ENUM('CASH', 'CREDIT') NOT NULL DEFAULT 'CASH',
    CashierName VARCHAR(250) NOT NULL,
    PaymentStatus ENUM('PENDING', 'COMPLETED', 'REFUNDED') DEFAULT 'COMPLETED',
    Date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);