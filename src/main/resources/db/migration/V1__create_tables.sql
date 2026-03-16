CREATE TABLE categories (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE events (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        title VARCHAR(100) NOT NULL,
                        description VARCHAR(1000),
                        ticket_price DECIMAL(10,2) NOT NULL,
                        event_date DATE NOT NULL,
                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        category_id BIGINT NOT NULL,
                        CONSTRAINT fk_events_category FOREIGN KEY (category_id) REFERENCES categories(id)
);