CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       email VARCHAR(150) NOT NULL UNIQUE,
                       created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

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
                        active BOOLEAN NOT NULL DEFAULT TRUE,
                        created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        category_id BIGINT NOT NULL,
                        CONSTRAINT fk_events_category
                            FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE registrations (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                               registration_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               status VARCHAR(50) NOT NULL,
                               total_amount DECIMAL(10,2) NOT NULL DEFAULT 0.00,
                               user_id BIGINT NOT NULL,
                               CONSTRAINT fk_registrations_user
                                   FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE registration_items (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    registration_id BIGINT NOT NULL,
                                    event_id BIGINT NOT NULL,
                                    quantity INT NOT NULL,
                                    ticket_price DECIMAL(10,2) NOT NULL,
                                    CONSTRAINT fk_registration_items_registration
                                        FOREIGN KEY (registration_id) REFERENCES registrations(id) ON DELETE CASCADE,
                                    CONSTRAINT fk_registration_items_event
                                        FOREIGN KEY (event_id) REFERENCES events(id)
);