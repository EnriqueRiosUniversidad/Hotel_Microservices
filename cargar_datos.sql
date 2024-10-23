-- Insert roles
INSERT INTO Rol (nombre, descripcion) VALUES
('ADMIN', 'Administrator with all access'),
('USER', 'Standard user role with limited access');

-- Insert test users
-- Insert 50 random users
-- Cambiamos rol_id 3 (Manager) y 2 (Guest) por 1 (Admin) o 2 (User)

-- Generar el hash de la contraseña "password123" usando BCrypt
-- Hash generado: $2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm

INSERT INTO Usuario (nombre, email, password, rol_id) VALUES
('Edward Brown', 'edward.brown0@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 1),
('Alice Rodriguez', 'alice.rodriguez1@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Helen Smith', 'helen.smith2@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 1),
('George Williams', 'george.williams3@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Bob Williams', 'bob.williams4@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('George Jones', 'george.jones5@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Alice Johnson', 'alice.johnson6@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 1),
('Charlie Rodriguez', 'charlie.rodriguez7@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 1),
('Helen Jones', 'helen.jones8@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Jenna Brown', 'jenna.brown9@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 1),
('Helen Brown', 'helen.brown10@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Charlie Garcia', 'charlie.garcia11@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Fiona Johnson', 'fiona.johnson12@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 1),
('George Davis', 'george.davis13@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('George Davis', 'george.davis14@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('George Brown', 'george.brown15@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Alice Johnson', 'alice.johnson16@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 1),
('Helen Brown', 'helen.brown17@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Fiona Davis', 'fiona.davis18@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 1),
('Diana Williams', 'diana.williams19@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('George Brown', 'george.brown20@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('George Jones', 'george.jones21@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Helen Garcia', 'helen.garcia22@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Alice Williams', 'alice.williams23@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Charlie Davis', 'charlie.davis24@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Charlie Johnson', 'charlie.johnson25@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Alice Jones', 'alice.jones26@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Jenna Brown', 'jenna.brown27@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 1),
('Edward Garcia', 'edward.garcia28@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 1),
('Edward Williams', 'edward.williams29@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 1),
('Bob Miller', 'bob.miller30@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Fiona Garcia', 'fiona.garcia31@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 1),
('Alice Johnson', 'alice.johnson32@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 1),
('Fiona Garcia', 'fiona.garcia33@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 1),
('Charlie Garcia', 'charlie.garcia34@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Alice Garcia', 'alice.garcia35@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Helen Williams', 'helen.williams36@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 1),
('George Garcia', 'george.garcia37@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 1),
('Helen Williams', 'helen.williams38@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 1),
('George Jones', 'george.jones39@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 1),
('Alice Jones', 'alice.jones40@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 1),
('Fiona Johnson', 'fiona.johnson41@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Fiona Rodriguez', 'fiona.rodriguez42@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Diana Jones', 'diana.jones43@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('George Garcia', 'george.garcia44@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Bob Johnson', 'bob.johnson45@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 1),
('Ivan Davis', 'ivan.davis46@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Bob Rodriguez', 'bob.rodriguez47@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2),
('Helen Garcia', 'helen.garcia48@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 1),
('Jenna Williams', 'jenna.williams49@example.com', '$2a$10$Dow1xF5jl4u9J3zK4fWQfuysZ0I5sH6E9M5vYH0k4eFHk1WzEJHjEm', 2);





-- Insert test rooms
INSERT INTO Habitacion (id, numero, tipo, disponibilidad, precio) VALUES
(101, 101, 'Single', true, 100.00),
(102, 102, 'Double', true, 150.00),
(103, 103, 'Suite', false, 300.00), -- Suite is not available
(104, 104, 'Single', true, 120.00),
(105, 105, 'Double', false, 180.00), -- Double room is not available
(106, 106, 'Suite', true, 350.00),
(107, 107, 'Single', true, 110.00),
(108, 108, 'Double', true, 170.00),
(109, 109, 'Suite', false, 400.00), -- Suite is not available
(110, 110, 'Single', true, 130.00);


-- Insert reservation made by user (Jane Smith)
INSERT INTO Reserva (usuario_id, fecha_creacion, total) VALUES
(2, NOW(), 150.00);

-- Insert reservation details (Jane reserved room 102)
INSERT INTO Detalle_Reserva (reserva_id, habitacion_id, precio) VALUES
(1, 102, 150.00);


-- Insert notifications for users
INSERT INTO Notificacion (usuario_id, mensaje, fecha_envio) VALUES
(1, 'Welcome, Admin!', NOW()),  -- Notification for Admin
(2, 'Your reservation is confirmed!', NOW()),  -- Notification for Guest
(3, 'Manager access granted.', NOW());  -- Notification for Manager


SELECT * FROM Usuario;
SELECT * FROM Habitacion;
SELECT * FROM Reserva;

SELECT * FROM Permiso;


SELECT * FROM Rol_Permiso
