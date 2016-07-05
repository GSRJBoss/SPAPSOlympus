--  @author mauriciobejaranorivera

-- Insertar datos de prueba database usando SQL


-- ----------------------------
--  Records of modulo
INSERT INTO "public"."modulo" (id,descripcion,estado,fecha_modificacion,fecha_registro,nombre,tipo,usuario_registro) VALUES ('1', 'MODULO DE SEGURIDAD', 'AC', null, '2016-01-01 00:00:01', 'SEGURIDAD', '1', '1');
ALTER SEQUENCE "public"."modulo_id_seq" RESTART WITH 2;

-- ----------------------------
--  Records of rol
INSERT INTO "public"."rol" (id,descripcion,estado,fecha_modificacion,fecha_registro,nombre,usuario_registro,id_rol_parent) VALUES ('1', 'GRUPO ADMINISTRADOR', 'AC', null, '2016-01-01 00:00:01', 'ADMINISTRADOR', '0', null);
ALTER SEQUENCE "public"."rol_id_seq" RESTART WITH 2;
 
 -- ----------------------------
--  Records of usuario
INSERT INTO "public"."usuario" (id,email,estado,fecha_modificacion,fecha_registro,foto_perfil,login,nombre,pagina_inicio,password,peso_foto,tipo,usuario_registro,id_rol) VALUES ('1', 'mbr.bejarano@gmail.com', 'AC', null, '2016-01-01 00:00:01', null, 'mbr', 'MAURICIO BEJARANO RIVERA', '/pages/dashboard.xhtml', 'mbr', '0', '1', '1', '1');
ALTER SEQUENCE "public"."usuario_id_seq" RESTART WITH 2;