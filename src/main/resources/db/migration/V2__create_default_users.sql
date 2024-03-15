INSERT INTO users (email, firstname, lastname, password, role, created_by, created_at)
VALUES
('admin@mail.com', 'Admin', 'Admin', '$2a$10$cCjkPtmvpgOJ0pnmMQ2b5eRwwWK.o7CdaNLFAGFpUbcFWr5gYxDX2', 'ADMIN', 'System', current_timestamp), -- password
('user@mail.com', 'User', 'User', '$2a$10$cCjkPtmvpgOJ0pnmMQ2b5eRwwWK.o7CdaNLFAGFpUbcFWr5gYxDX2', 'USER', 'System', current_timestamp); -- password