DROP TABLE IF EXISTS PRODUCT;
CREATE TABLE product (
                         product_id INT AUTO_INCREMENT PRIMARY KEY,   -- Unique identifier for each product
                         name VARCHAR(255) NOT NULL,                 -- Product name
                         description TEXT,                           -- Product description
                         category TEXT,                           	-- Categroría description
                         price DECIMAL(10, 2) NOT NULL,              -- Product price (e.g., 12345.67)
                         discount DECIMAL(10, 2) NOT NULL,           -- Product discount (e.g., 12345.67)
                         taxes DECIMAL(10, 2) NOT NULL,              -- Product taxes (e.g., 12345.67)
                         visible INT DEFAULT 1,						-- Producti visibility (0,1)
                         image TEXT,									-- Product image
                         stock_quantity INT DEFAULT 0,               -- Quantity available in stock
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Timestamp of creation
                         updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

