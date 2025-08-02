CREATE TABLE FACTURA (
    ID INT PRIMARY KEY,
    NOMBRE VARCHAR(50),
    RAZONSOCIAL VARCHAR(100),
    CANTIDAD INT,
    IMPORTE VARCHAR(20),
    IVA INT,
    TOTAL VARCHAR(20),
    DESCRIPCION VARCHAR(100)
);

INSERT INTO FACTURA (ID, NOMBRE, RAZONSOCIAL, CANTIDAD, IMPORTE, IVA, TOTAL, DESCRIPCION) VALUES
(1, 'Veterinaria San Roque', 'San Roque S.A.', 2, '150000', 10, '165000', 'Consulta general y vacunas'),
(2, 'Mascotas Felices', 'Mascotas Felices SRL', 1, '80000', 5, '84000', 'Desparasitación'),
(3, 'PetCare', 'PetCare Ltda.', 3, '50000', 15, '57500', 'Baño y corte de pelo');
