UPDATE users
SET password_hash = '$2a$10$G8amQZ765Ii1OLFFHqZa2eXR7L8Wpiz4qKiHV/qTB0.7MqOmXlkva'
WHERE email IN ('cantor@example.com', 'mason@example.com', 'sam@example.com');