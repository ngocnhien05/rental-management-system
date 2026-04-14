-- Tạo database
DROP DATABASE IF EXISTS nhatro_db;
CREATE DATABASE nhatro_db;
USE nhatro_db;

-- ======================
-- TABLE: Room
-- ======================
CREATE TABLE Room (
    room_id INT AUTO_INCREMENT PRIMARY KEY,
    room_name VARCHAR(50) NOT NULL,
    price DOUBLE NOT NULL,
    status VARCHAR(20) DEFAULT 'Trống'
);

INSERT INTO Room (room_id, room_name, price, status) VALUES
(2,'Phòng 101',1500000,'Trống'),
(3,'Phòng 102',1700000,'Đã thuê'),
(4,'Phòng 103',1800000,'Trống'),
(5,'Phòng 201',2200000,'Đã thuê'),
(6,'Phòng 202',2500000,'Trống');

-- ======================
-- TABLE: Customer
-- ======================
CREATE TABLE Customer (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    phone VARCHAR(15),
    cccd VARCHAR(20)
);

INSERT INTO Customer (customer_id, name, phone, cccd) VALUES
(1,'Nguyễn Văn A','0901234567','012345678901'),
(2,'Trần Thị B','0938765432','098765432112'),
(3,'Phạm Văn C','0912222333','023456789123'),
(4,'Lê Thị D','0987111222','034567891234'),
(5,'Đặng Văn E','0977555666','045678912345'),
(6,'Nguyễn Thanh Đức','0900190990','012122422'),
(7,'Nguyễn Thanh Đức','0900190990','012122422');

-- ======================
-- TABLE: Rental
-- ======================
CREATE TABLE Rental (
    rental_id INT AUTO_INCREMENT PRIMARY KEY,
    room_id INT,
    customer_id INT,
    date_rent DATE,
    FOREIGN KEY (room_id) REFERENCES Room(room_id),
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

INSERT INTO Rental (rental_id, room_id, customer_id, date_rent) VALUES
(1,2,1,'2025-01-05'),
(2,4,2,'2025-02-01'),
(3,2,3,'2025-03-10');