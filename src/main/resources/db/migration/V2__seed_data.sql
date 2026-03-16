INSERT INTO categories (name) VALUES
                                  ('Concert'),
                                  ('Conference'),
                                  ('Workshop');

INSERT INTO events (title, description, ticket_price, event_date, category_id) VALUES
                                                                                   ('Spring Music Fest', 'Live music downtown', 49.99, '2026-04-10', 1),
                                                                                   ('Java Summit', 'Developer conference for Java and Spring', 99.00, '2026-05-15', 2),
                                                                                   ('Docker Workshop', 'Hands-on container workshop', 0.00, '2026-04-20', 3);