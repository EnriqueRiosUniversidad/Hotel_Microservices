-- Insert roles
INSERT INTO Rol (nombre, descripcion) VALUES
('Admin', 'Administrator with all access'),
('Guest', 'Standard guest role'),
('Manager', 'Manager with room management privileges');

-- Insert permissions
INSERT INTO Permiso (nombre, descripcion) VALUES
('Create_Reservation', 'Permission to create a reservation'),
('Manage_Rooms', 'Permission to manaSELECT * FROM Permiso;ge rooms'),
('Send_Notifications', 'Permission to send notifications');

-- Link roles and permissions in Rol_Permiso
INSERT INTO Rol_Permiso (rol_id, permiso_id) VALUES
(1, 1), -- Admin can create reservations
(1, 2), -- Admin can manage rooms
(1, 3), -- Admin can send notifications
(2, 1), -- Guest can create reservations
(3, 2); -- Manager can manage rooms


-- Insert test users
-- Insert 50 random users
INSERT INTO Usuario (nombre, email, password, rol_id) VALUES
('Edward Brown', 'edward.brown0@example.com', 'hashed_password_4', 1),
('Alice Rodriguez', 'alice.rodriguez1@example.com', 'hashed_password_5', 3),
('Helen Smith', 'helen.smith2@example.com', 'hashed_password_6', 1),
('George Williams', 'george.williams3@example.com', 'hashed_password_7', 2),
('Bob Williams', 'bob.williams4@example.com', 'hashed_password_8', 2),
('George Jones', 'george.jones5@example.com', 'hashed_password_9', 2),
('Alice Johnson', 'alice.johnson6@example.com', 'hashed_password_10', 1),
('Charlie Rodriguez', 'charlie.rodriguez7@example.com', 'hashed_password_11', 1),
('Helen Jones', 'helen.jones8@example.com', 'hashed_password_12', 2),
('Jenna Brown', 'jenna.brown9@example.com', 'hashed_password_13', 1),
('Helen Brown', 'helen.brown10@example.com', 'hashed_password_14', 3),
('Charlie Garcia', 'charlie.garcia11@example.com', 'hashed_password_15', 3),
('Fiona Johnson', 'fiona.johnson12@example.com', 'hashed_password_16', 1),
('George Davis', 'george.davis13@example.com', 'hashed_password_17', 3),
('George Davis', 'george.davis14@example.com', 'hashed_password_18', 2),
('George Brown', 'george.brown15@example.com', 'hashed_password_19', 2),
('Alice Johnson', 'alice.johnson16@example.com', 'hashed_password_20', 1),
('Helen Brown', 'helen.brown17@example.com', 'hashed_password_21', 3),
('Fiona Davis', 'fiona.davis18@example.com', 'hashed_password_22', 1),
('Diana Williams', 'diana.williams19@example.com', 'hashed_password_23', 2),
('George Brown', 'george.brown20@example.com', 'hashed_password_24', 2),
('George Jones', 'george.jones21@example.com', 'hashed_password_25', 3),
('Helen Garcia', 'helen.garcia22@example.com', 'hashed_password_26', 3),
('Alice Williams', 'alice.williams23@example.com', 'hashed_password_27', 3),
('Charlie Davis', 'charlie.davis24@example.com', 'hashed_password_28', 2),
('Charlie Johnson', 'charlie.johnson25@example.com', 'hashed_password_29', 2),
('Alice Jones', 'alice.jones26@example.com', 'hashed_password_30', 2),
('Jenna Brown', 'jenna.brown27@example.com', 'hashed_password_31', 1),
('Edward Garcia', 'edward.garcia28@example.com', 'hashed_password_32', 1),
('Edward Williams', 'edward.williams29@example.com', 'hashed_password_33', 1),
('Bob Miller', 'bob.miller30@example.com', 'hashed_password_34', 3),
('Fiona Garcia', 'fiona.garcia31@example.com', 'hashed_password_35', 1),
('Alice Johnson', 'alice.johnson32@example.com', 'hashed_password_36', 1),
('Fiona Garcia', 'fiona.garcia33@example.com', 'hashed_password_37', 1),
('Charlie Garcia', 'charlie.garcia34@example.com', 'hashed_password_38', 3),
('Alice Garcia', 'alice.garcia35@example.com', 'hashed_password_39', 2),
('Helen Williams', 'helen.williams36@example.com', 'hashed_password_40', 1),
('George Garcia', 'george.garcia37@example.com', 'hashed_password_41', 1),
('Helen Williams', 'helen.williams38@example.com', 'hashed_password_42', 1),
('George Jones', 'george.jones39@example.com', 'hashed_password_43', 1),
('Alice Jones', 'alice.jones40@example.com', 'hashed_password_44', 1),
('Fiona Johnson', 'fiona.johnson41@example.com', 'hashed_password_45', 2),
('Fiona Rodriguez', 'fiona.rodriguez42@example.com', 'hashed_password_46', 2),
('Diana Jones', 'diana.jones43@example.com', 'hashed_password_47', 3),
('George Garcia', 'george.garcia44@example.com', 'hashed_password_48', 3),
('Bob Johnson', 'bob.johnson45@example.com', 'hashed_password_49', 1),
('Ivan Davis', 'ivan.davis46@example.com', 'hashed_password_50', 3),
('Bob Rodriguez', 'bob.rodriguez47@example.com', 'hashed_password_51', 2),
('Helen Garcia', 'helen.garcia48@example.com', 'hashed_password_52', 1),
('Jenna Williams', 'jenna.williams49@example.com', 'hashed_password_53', 3);



-- Insert test rooms
INSERT INTO Habitacion (id, numero, tipo, disponibilidad, precio) VALUES
(101,101, 'Single', true, 100.00),
(102,102, 'Double', true, 150.00),
(103,103, 'Suite', false, 300.00); -- Suite is not available


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
