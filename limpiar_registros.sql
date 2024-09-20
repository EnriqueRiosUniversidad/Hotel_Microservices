
-- Reset the sequence for the 'Permiso' table to start from 1
ALTER SEQUENCE permiso_id_seq RESTART WITH 1;

-- Optionally do the same for other tables
ALTER SEQUENCE rol_id_seq RESTART WITH 1;
ALTER SEQUENCE usuario_id_seq RESTART WITH 1;
ALTER SEQUENCE habitacion_id_seq RESTART WITH 1;
ALTER SEQUENCE reserva_id_seq RESTART WITH 1;
ALTER SEQUENCE detalle_reserva_id_seq RESTART WITH 1;
ALTER SEQUENCE notificacion_id_seq RESTART WITH 1;


-- Delete from dependent tables first
DELETE FROM Rol_Permiso;
DELETE FROM Detalle_Reserva;
DELETE FROM Notificacion;
DELETE FROM Reserva;
DELETE FROM Habitacion;
DELETE FROM Usuario;
DELETE FROM Rol;
DELETE FROM Permiso;
