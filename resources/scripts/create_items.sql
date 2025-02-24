CREATE TABLE Items (
    ID INT PRIMARY KEY,
    Price DOUBLE NOT NULL,
    Type VARCHAR(10) CHECK (Type IN ('FRUITS', 'VEGETABLES')),
    Name VARCHAR(250) NOT NULL,
    Quantity INT NOT NULL,
    BrandName VARCHAR(250) NOT NULL,
    isOrganic BOOLEAN,  
    ExpirationDate DATE,
    Status VARCHAR(15) CHECK (Status IN ('AVAILABLE', 'NOT AVAILABLE')),
    ImageURL VARCHAR(250)
);