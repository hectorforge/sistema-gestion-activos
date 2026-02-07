-- =========================================================
-- CATEGORIAS
-- =========================================================
INSERT INTO tbl_categoria (
    id_category,
    nombre_categoria,
    abreviatura_categoria,
    descripcion_categoria,
    url_img_categoria,
    es_activo,
    esta_eliminado
) VALUES
      ('11111111-1111-1111-1111-111111111111', 'Laptops', 'LAP', 'Equipos portátiles de trabajo', '', TRUE, FALSE),
      ('22222222-2222-2222-2222-222222222222', 'Monitores', 'MON', 'Pantallas y monitores', '', TRUE, FALSE),
      ('33333333-3333-3333-3333-333333333333', 'Teclados', 'TEC', 'Teclados físicos', '', TRUE, FALSE),
      ('44444444-4444-4444-4444-444444444444', 'Impresoras', 'IMP', 'Equipos de impresión', '', TRUE, FALSE),
      ('55555555-5555-5555-5555-555555555555', 'Celulares', 'CEL', 'Dispositivos móviles corporativos', '', TRUE, FALSE);


-- =========================================================
-- ACTIVOS (2 POR CATEGORIA)
-- estado_actual y ubicacion_fisica son SMALLINT (valores ejemplo)
-- =========================================================

INSERT INTO tbl_activo (
    id_activo,
    codigo_inventario,
    nombre_activo,
    descripcion,
    url_img_activo,
    categoria_id,
    estado_actual,
    ubicacion_fisica,
    fecha_ingreso,
    valor_referencial,
    proveedor,
    vida_util_meses,
    observaciones,
    es_activo,
    esta_eliminado,
    fecha_creacion,
    fecha_actualizacion
) VALUES

-- ===================== LAPTOS (LAP)
('aaaaaaaa-0000-0000-0000-000000000001','LAP-060226-00001','Laptop Dell Latitude 5420','Equipo asignado a oficina','',
 '11111111-1111-1111-1111-111111111111','OPERATIVO','OFICINA_ADMINISTRATIVA','2024-01-10',3500,'Dell',48,'',TRUE,FALSE,NOW(),NOW()),

('aaaaaaaa-0000-0000-0000-000000000002','LAP-060226-00002','Laptop HP ProBook 440','Equipo para analistas','',
 '11111111-1111-1111-1111-111111111111','OCUPADO','OFICINA_ADMINISTRATIVA','2024-02-15',3200,'HP',48,'',TRUE,FALSE,NOW(),NOW()),

-- ===================== MONITORES (MON)
('bbbbbbbb-0000-0000-0000-000000000001','MON-060226-00001','Monitor LG 24 pulgadas','Monitor principal','',
 '22222222-2222-2222-2222-222222222222','OPERATIVO','SALA_COMPUTO','2024-01-20',800,'LG',60,'',TRUE,FALSE,NOW(),NOW()),

('bbbbbbbb-0000-0000-0000-000000000002','MON-060226-00002','Monitor Samsung 27 pulgadas','Monitor secundario','',
 '22222222-2222-2222-2222-222222222222','OCUPADO','SALA_COMPUTO','2024-03-05',950,'Samsung',60,'',TRUE,FALSE,NOW(),NOW()),

-- ===================== TECLADOS (TEC)
('cccccccc-0000-0000-0000-000000000001','TEC-060226-00001','Teclado Logitech K120','Teclado estándar USB','',
 '33333333-3333-3333-3333-333333333333','OPERATIVO','OFICINA_ADMINISTRATIVA','2024-01-12',80,'Logitech',36,'',TRUE,FALSE,NOW(),NOW()),

('cccccccc-0000-0000-0000-000000000002','TEC-060226-00002','Teclado Microsoft Wired 600','Teclado oficina','',
 '33333333-3333-3333-3333-333333333333','OCUPADO','OFICINA_ADMINISTRATIVA','2024-01-18',90,'Microsoft',36,'',TRUE,FALSE,NOW(),NOW()),

-- ===================== IMPRESORAS (IMP)
('dddddddd-0000-0000-0000-000000000001','IMP-060226-00001','Impresora HP LaserJet Pro','Impresora área administrativa','',
 '44444444-4444-4444-4444-444444444444','EN_MANTENIMIENTO','OFICINA_ADMINISTRATIVA','2023-11-10',1200,'HP',72,'',TRUE,FALSE,NOW(),NOW()),

('dddddddd-0000-0000-0000-000000000002','IMP-060226-00002','Impresora Epson L3250','Impresora multifuncional','',
 '44444444-4444-4444-4444-444444444444','OPERATIVO','OFICINA_ADMINISTRATIVA','2024-02-01',900,'Epson',72,'',TRUE,FALSE,NOW(),NOW()),

-- ===================== CELULARES (CEL)
('eeeeeeee-0000-0000-0000-000000000001','CEL-060226-00001','iPhone 13','Celular gerencial','',
 '55555555-5555-5555-5555-555555555555','OPERATIVO','DIRECCION','2024-01-05',4200,'Apple',36,'',TRUE,FALSE,NOW(),NOW()),

('eeeeeeee-0000-0000-0000-000000000002','CEL-060226-00002','Samsung Galaxy S23','Celular corporativo','',
 '55555555-5555-5555-5555-555555555555','OCUPADO','OFICINA_ADMINISTRATIVA','2024-03-12',3800,'Samsung',36,'',TRUE,FALSE,NOW(),NOW());