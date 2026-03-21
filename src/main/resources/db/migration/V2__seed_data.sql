INSERT INTO users (name, email) VALUES
                                    ('Cantor', 'cantor@example.com'),
                                    ('Mason', 'mason@example.com'),
                                    ('Sam', 'sam@example.com');

INSERT INTO categories (name) VALUES
                                  ('Concert'),
                                  ('Conference'),
                                  ('Workshop');

INSERT INTO events (title, description, ticket_price, event_date, active, category_id) VALUES
                                                                                           ('Spring Music Fest', 'Live music downtown', 49.99, '2026-04-10', TRUE, 1),
                                                                                           ('Java Summit', 'Developer conference for Java and Spring', 99.00, '2026-05-15', TRUE, 2),
                                                                                           ('Docker Workshop', 'Hands-on container workshop', 0.00, '2026-04-20', TRUE, 3),
                                                                                           ('Archived Winter Gala', 'Past inactive event', 35.00, '2026-01-10', FALSE, 1);

INSERT INTO registrations (registration_date, status, total_amount, user_id) VALUES
                                                                                 ('2026-03-18 10:00:00', 'CONFIRMED', 149.98, 1),
                                                                                 ('2026-03-19 14:30:00', 'PENDING', 99.00, 2);

INSERT INTO registration_items (registration_id, event_id, quantity, ticket_price) VALUES
                                                                                       (1, 1, 1, 49.99),
                                                                                       (1, 2, 1, 99.99),
                                                                                       (2, 2, 1, 99.00);