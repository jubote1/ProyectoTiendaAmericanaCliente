/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE TABLE IF NOT EXISTS `agrupador_menu` (
  `idmenuagrupador` int(11) NOT NULL AUTO_INCREMENT,
  `menu_agrupador` varchar(50) DEFAULT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idmenuagrupador`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='Tabla que se encargara de definir los agrupadores de menú principales para la aplicación en su módulo de seguridad';

/*!40000 ALTER TABLE `agrupador_menu` DISABLE KEYS */;
INSERT INTO `agrupador_menu` (`idmenuagrupador`, `menu_agrupador`, `descripcion`) VALUES
	(1, 'Ventas finales', 'Menú que agrupa las funciones de ventas'),
	(5, 'fsdfds', 'fdsfdsf'),
	(6, 'FDSSDF', 'FDFSF');
/*!40000 ALTER TABLE `agrupador_menu` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `cambios_estado_pedido` (
  `idcambioestado` int(11) NOT NULL AUTO_INCREMENT,
  `idpedidotienda` int(11) NOT NULL,
  `idestadoanterior` int(11) NOT NULL,
  `idestadoposterior` int(11) NOT NULL,
  `fechacambio` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idcambioestado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla que almacena los cambios de estado de cada uno de los pedido del sistema';

/*!40000 ALTER TABLE `cambios_estado_pedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `cambios_estado_pedido` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `cliente` (
  `idcliente` int(11) NOT NULL AUTO_INCREMENT,
  `idtienda` int(11) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `apellido` varchar(100) DEFAULT NULL,
  `nombrecompania` varchar(100) DEFAULT NULL,
  `direccion` varchar(200) DEFAULT NULL,
  `idnomenclatura` int(11) DEFAULT NULL,
  `num_nomencla1` varchar(10) DEFAULT NULL,
  `num_nomencla2` varchar(10) DEFAULT NULL,
  `num3` varchar(10) DEFAULT NULL,
  `idmunicipio` int(11) DEFAULT NULL,
  `latitud` float DEFAULT NULL,
  `longitud` float DEFAULT NULL,
  `zona` varchar(100) DEFAULT NULL,
  `telefono` varchar(50) NOT NULL,
  `observacion` varchar(200) DEFAULT NULL,
  `memcode` int(11) DEFAULT '0',
  PRIMARY KEY (`idcliente`),
  UNIQUE KEY `idcliente` (`idcliente`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='Tabla de clientes';

/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` (`idcliente`, `idtienda`, `nombre`, `apellido`, `nombrecompania`, `direccion`, `idnomenclatura`, `num_nomencla1`, `num_nomencla2`, `num3`, `idmunicipio`, `latitud`, `longitud`, `zona`, `telefono`, `observacion`, `memcode`) VALUES
	(0, 1, 'CLIENTE TIENDA', NULL, NULL, 'Punto Venta', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '0', NULL, 0),
	(43, 1, 'JUAN', 'BOTERO', 'BANCOLO', 'CALLE 56 # 45 - 54', 2, NULL, NULL, NULL, 2, NULL, NULL, 'CENTRO', '2548831', 'BUENA ZONA', 0);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `configuracion_menu` (
  `idconfiguracionmenu` int(11) NOT NULL AUTO_INCREMENT,
  `multimenu` int(11) NOT NULL DEFAULT '0',
  `menu` int(11) NOT NULL DEFAULT '0',
  `fila` int(11) NOT NULL DEFAULT '0',
  `columna` int(11) NOT NULL DEFAULT '0',
  `idproducto` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idconfiguracionmenu`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='Tabla que tendrá almacenada como está la configuración de  Menú para la toma de productos';

/*!40000 ALTER TABLE `configuracion_menu` DISABLE KEYS */;
INSERT INTO `configuracion_menu` (`idconfiguracionmenu`, `multimenu`, `menu`, `fila`, `columna`, `idproducto`) VALUES
	(1, 1, 1, 0, 0, 1),
	(5, 1, 6, 0, 5, 3),
	(6, 1, 8, 1, 1, 4),
	(7, 1, 15, 2, 2, 1),
	(8, 2, 18, 2, 5, 1),
	(9, 1, 13, 2, 0, 2);
/*!40000 ALTER TABLE `configuracion_menu` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `detalle_pedido` (
  `iddetalle_pedido` int(11) NOT NULL AUTO_INCREMENT,
  `idpedidotienda` int(11) NOT NULL DEFAULT '0',
  `idproducto` int(11) NOT NULL DEFAULT '0',
  `cantidad` double NOT NULL DEFAULT '0',
  `valorunitario` double NOT NULL DEFAULT '0',
  `valortotal` double NOT NULL DEFAULT '0',
  `observacion` varchar(50) NOT NULL DEFAULT '0',
  `iddetalle_pedido_master` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`iddetalle_pedido`)
) ENGINE=InnoDB AUTO_INCREMENT=258 DEFAULT CHARSET=utf8 COMMENT='Tabla donde se almacenará el detalle de los pedidos, con los productos a incluir y la cantidad.\r\n';

/*!40000 ALTER TABLE `detalle_pedido` DISABLE KEYS */;
INSERT INTO `detalle_pedido` (`iddetalle_pedido`, `idpedidotienda`, `idproducto`, `cantidad`, `valorunitario`, `valortotal`, `observacion`, `iddetalle_pedido_master`) VALUES
	(182, 70, 1, 1, 12000, 12000, '', 0),
	(183, 70, 1, 1, 12000, 12000, '', 0),
	(184, 70, 2, 1, 0, 0, '', 0),
	(185, 71, 1, 1, 12000, 12000, '', 0),
	(186, 71, 1, 1, 12000, 12000, '', 0),
	(187, 71, 2, 1, 0, 0, '', 0),
	(188, 71, 5, 0.5, 31000, 15500, '', 187),
	(189, 71, 14, 0.5, 2000, 1000, '', 187),
	(190, 71, 3, 0.5, 31000, 15500, '', 187),
	(191, 71, 13, 0.5, 2000, 1000, '', 187),
	(192, 72, 1, 1, 12000, 12000, '', 0),
	(199, 73, 1, 1, 12000, 12000, '', 0),
	(200, 73, 1, 1, 12000, 12000, '', 0),
	(201, 73, 2, 1, 0, 0, '', 0),
	(202, 73, 8, 0.5, 31000, 15500, '', 201),
	(203, 73, 10, 0.5, 31000, 15500, '', 201),
	(204, 74, 1, 1, 12000, 12000, '', 0),
	(205, 74, 1, 1, 12000, 12000, '', 0),
	(206, 75, 1, 1, 12000, 12000, '', 0),
	(207, 75, 1, 1, 12000, 12000, '', 0),
	(208, 76, 1, 1, 12000, 12000, '', 0),
	(209, 76, 1, 1, 12000, 12000, '', 0),
	(210, 76, 2, 1, 0, 0, '', 0),
	(211, 76, 8, 0.5, 31000, 15500, '', 210),
	(212, 76, 14, 0.5, 2000, 1000, '', 210),
	(213, 76, 5, 0.5, 31000, 15500, '', 210),
	(214, 76, 13, 0.5, 2000, 1000, '', 210),
	(215, 77, 1, 1, 12000, 12000, '', 0),
	(216, 77, 1, 1, 12000, 12000, '', 0),
	(217, 77, 2, 1, 0, 0, '', 0),
	(218, 77, 4, 0.5, 31000, 15500, '', 217),
	(219, 77, 8, 0.5, 31000, 15500, '', 217),
	(220, 78, 1, 1, 12000, 12000, '', 0),
	(221, 78, 2, 1, 0, 0, '', 0),
	(222, 79, 1, 1, 12000, 12000, '', 0),
	(223, 80, 2, 1, 0, 0, '', 0),
	(224, 81, 1, 1, 12000, 12000, '', 0),
	(225, 81, 1, 1, 12000, 12000, '', 0),
	(226, 81, 2, 1, 0, 0, '', 0),
	(227, 81, 8, 0.5, 31000, 15500, '', 226),
	(228, 81, 14, 0.5, 2000, 1000, '', 226),
	(229, 81, 9, 0.5, 31000, 15500, '', 226),
	(230, 81, 13, 0.5, 2000, 1000, '', 226),
	(231, 82, 1, 1, 12000, 12000, '', 0),
	(232, 82, 2, 1, 0, 0, '', 0),
	(233, 82, 8, 0.5, 31000, 15500, '', 232),
	(234, 82, 14, 0.5, 2000, 1000, '', 232),
	(235, 82, 9, 0.5, 31000, 15500, '', 232),
	(236, 82, 13, 0.5, 2000, 1000, '', 232),
	(237, 83, 1, 1, 12000, 12000, '', 0),
	(238, 83, 1, 1, 12000, 12000, '', 0),
	(239, 83, 2, 1, 0, 0, '', 0),
	(240, 83, 3, 0.5, 31000, 15500, '', 239),
	(241, 83, 6, 0.5, 31000, 15500, '', 239),
	(242, 84, 1, 1, 12000, 12000, '', 0),
	(243, 84, 4, 1, 31000, 31000, '', 0),
	(244, 85, 1, 1, 12000, 12000, '', 0),
	(245, 85, 2, 1, 0, 0, '', 0),
	(246, 85, 9, 0.5, 31000, 15500, '', 245),
	(247, 85, 4, 0.5, 31000, 15500, '', 245),
	(248, 86, 1, 1, 12000, 12000, '', 0),
	(249, 86, 1, 1, 12000, 12000, '', 0),
	(250, 86, 2, 1, 0, 0, '', 0),
	(251, 86, 4, 0.5, 31000, 15500, '', 250),
	(252, 86, 12, 0.5, 31000, 15500, '', 250),
	(253, 87, 1, 1, 12000, 12000, '', 0),
	(255, 87, 2, 1, 0, 0, '', 0),
	(256, 87, 4, 0.5, 31000, 15500, '', 255),
	(257, 87, 5, 0.5, 31000, 15500, '', 255);
/*!40000 ALTER TABLE `detalle_pedido` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `eleccion_forzada` (
  `ideleccion_forzada` int(11) NOT NULL AUTO_INCREMENT,
  `idpregunta` int(11) NOT NULL,
  `idproducto` int(11) NOT NULL,
  `precio` varchar(50) NOT NULL,
  `estado` int(1) NOT NULL,
  PRIMARY KEY (`ideleccion_forzada`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='Tabla que tiene el detalle de las posibles opciones de las preguntas., en donde se detalla con base en los productos y el precio.\r\n';

/*!40000 ALTER TABLE `eleccion_forzada` DISABLE KEYS */;
INSERT INTO `eleccion_forzada` (`ideleccion_forzada`, `idpregunta`, `idproducto`, `precio`, `estado`) VALUES
	(5, 2, 3, 'precio1', 1),
	(6, 2, 4, 'precio1', 1),
	(7, 2, 5, 'precio1', 1),
	(8, 2, 6, 'precio1', 1),
	(9, 2, 7, 'precio1', 1),
	(10, 2, 8, 'precio1', 1),
	(11, 2, 9, 'precio1', 1),
	(12, 2, 10, 'precio1', 1),
	(13, 2, 11, 'precio1', 1),
	(14, 2, 12, 'precio1', 1),
	(15, 3, 13, 'precio1', 1),
	(16, 3, 14, 'precio1', 1);
/*!40000 ALTER TABLE `eleccion_forzada` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `estado` (
  `idestado` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) NOT NULL DEFAULT '',
  `descripcion_corta` varchar(50) NOT NULL DEFAULT '',
  `idtipopedido` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idestado`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='Tabla  relacionada con los estados parametrizables para los pedidos';

/*!40000 ALTER TABLE `estado` DISABLE KEYS */;
INSERT INTO `estado` (`idestado`, `descripcion`, `descripcion_corta`, `idtipopedido`) VALUES
	(1, 'Tomando Pedido', 'Tomando', 1),
	(2, 'En Elaboración de los productos', 'En Elaboración', 1),
	(3, 'En el Horno', 'En el Horno', 1),
	(5, 'Producto Empacado', 'Producto Empacado', 1),
	(7, 'En Ruta', 'En Ruta', 1),
	(8, 'Entregado', 'Entregado', 1);
/*!40000 ALTER TABLE `estado` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `estado_anterior` (
  `idestado` int(11) NOT NULL,
  `idestado_anterior` int(11) NOT NULL,
  PRIMARY KEY (`idestado`,`idestado_anterior`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `estado_anterior` DISABLE KEYS */;
INSERT INTO `estado_anterior` (`idestado`, `idestado_anterior`) VALUES
	(2, 1),
	(3, 2),
	(5, 3),
	(7, 5);
/*!40000 ALTER TABLE `estado_anterior` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `estado_posterior` (
  `idestado` int(11) NOT NULL,
  `idestado_posterior` int(11) NOT NULL,
  PRIMARY KEY (`idestado`,`idestado_posterior`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `estado_posterior` DISABLE KEYS */;
INSERT INTO `estado_posterior` (`idestado`, `idestado_posterior`) VALUES
	(1, 2),
	(2, 3),
	(3, 5),
	(5, 7),
	(7, 8);
/*!40000 ALTER TABLE `estado_posterior` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `forma_pago` (
  `idforma_pago` int(11) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `tipoformapago` varchar(100) DEFAULT 'EFECTIVO',
  PRIMARY KEY (`idforma_pago`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla que define las formas de pago de un pedido en el sistema\r\n';

/*!40000 ALTER TABLE `forma_pago` DISABLE KEYS */;
INSERT INTO `forma_pago` (`idforma_pago`, `nombre`, `tipoformapago`) VALUES
	(1, 'EFECTIVO', 'EFECTIVO'),
	(2, 'TARJETA', 'TARJETA');
/*!40000 ALTER TABLE `forma_pago` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `impuesto` (
  `idimpuesto` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(50) NOT NULL DEFAULT '0',
  `valorporcentaje` double NOT NULL DEFAULT '0',
  PRIMARY KEY (`idimpuesto`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='Tabla donde se almacenarán los impuesto que se podrán aplicar en el sistema.';

/*!40000 ALTER TABLE `impuesto` DISABLE KEYS */;
INSERT INTO `impuesto` (`idimpuesto`, `descripcion`, `valorporcentaje`) VALUES
	(1, 'Impuesto al consumo', 10);
/*!40000 ALTER TABLE `impuesto` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `impuesto_x_producto` (
  `idimpuesto_producto` int(11) NOT NULL AUTO_INCREMENT,
  `idproducto` int(11) NOT NULL DEFAULT '0',
  `idimpuesto` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idimpuesto_producto`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='Tabla donde es almacenada la relación de impuestos por producto';

/*!40000 ALTER TABLE `impuesto_x_producto` DISABLE KEYS */;
INSERT INTO `impuesto_x_producto` (`idimpuesto_producto`, `idproducto`, `idimpuesto`) VALUES
	(16, 1, 1);
/*!40000 ALTER TABLE `impuesto_x_producto` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `item_inventario` (
  `iditem` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_item` varchar(50) NOT NULL DEFAULT '0',
  `unidad_medida` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`iditem`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='Tabla que almacenara los items de inventario que podrán ser descontados del inventario cuando se presenta la venta de un producto en el sistema';

/*!40000 ALTER TABLE `item_inventario` DISABLE KEYS */;
INSERT INTO `item_inventario` (`iditem`, `nombre_item`, `unidad_medida`) VALUES
	(1, 'Piña Dulce', 'Gramos');
/*!40000 ALTER TABLE `item_inventario` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `item_inventario_x_producto` (
  `iditem_producto` int(11) NOT NULL AUTO_INCREMENT,
  `iditem` int(11) DEFAULT '0',
  `idproducto` int(11) DEFAULT '0',
  `cantidad` double DEFAULT '0',
  PRIMARY KEY (`iditem_producto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla que almacena la relación de items inventario que deben ser descontados con la venta de un producto';

/*!40000 ALTER TABLE `item_inventario_x_producto` DISABLE KEYS */;
/*!40000 ALTER TABLE `item_inventario_x_producto` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `log_tiempo_tienda` (
  `idlogtienda` int(11) NOT NULL AUTO_INCREMENT,
  `idtienda` int(11) NOT NULL DEFAULT '0',
  `usuario` varchar(20) DEFAULT '0',
  `nuevotiempo` int(11) NOT NULL DEFAULT '0',
  `fechainsercion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`idlogtienda`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='Logs de los cambios de tiempos en la tienda';

/*!40000 ALTER TABLE `log_tiempo_tienda` DISABLE KEYS */;
INSERT INTO `log_tiempo_tienda` (`idlogtienda`, `idtienda`, `usuario`, `nuevotiempo`, `fechainsercion`) VALUES
	(1, 1, '', 50, '2017-11-19 09:11:08'),
	(2, 1, 'admin', 60, '2017-11-19 09:12:55'),
	(3, 1, 'admin', 100, '2017-12-08 16:28:13'),
	(4, 1, 'admin', 80, '2018-01-09 22:10:30'),
	(5, 1, '', 100, '2018-01-09 22:12:10'),
	(6, 1, '', 110, '2018-01-09 22:16:02'),
	(7, 1, '', 100, '2018-01-09 22:18:14'),
	(8, 1, '', 110, '2018-01-09 22:20:37'),
	(9, 1, 'admin', 120, '2018-01-09 22:27:45');
/*!40000 ALTER TABLE `log_tiempo_tienda` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `municipio` (
  `idmunicipio` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) DEFAULT '0',
  PRIMARY KEY (`idmunicipio`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `municipio` DISABLE KEYS */;
INSERT INTO `municipio` (`idmunicipio`, `nombre`) VALUES
	(1, 'Medellin'),
	(2, 'Itagui Prueba');
/*!40000 ALTER TABLE `municipio` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `nomenclatura_direccion` (
  `idnomenclatura` int(11) NOT NULL AUTO_INCREMENT,
  `nomenclatura` varchar(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idnomenclatura`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `nomenclatura_direccion` DISABLE KEYS */;
INSERT INTO `nomenclatura_direccion` (`idnomenclatura`, `nomenclatura`) VALUES
	(1, 'Calle'),
	(2, 'Carrera'),
	(3, 'Avenida'),
	(4, 'Avenida Carrera'),
	(5, 'Avenida Calle'),
	(6, 'Circular'),
	(7, 'Circunvalar'),
	(8, 'Diagonal'),
	(9, 'Manzana'),
	(10, 'Transversal'),
	(11, 'Via');
/*!40000 ALTER TABLE `nomenclatura_direccion` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `opcion_menu` (
  `idmenu` int(11) NOT NULL,
  `menu` varchar(50) NOT NULL,
  `idmenuagrupador` int(11) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `ordenmenu` int(11) DEFAULT NULL,
  `habilitado` int(1) DEFAULT '1',
  `pantalla` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla para definir las opciones de menú de los menus agrupadores';

/*!40000 ALTER TABLE `opcion_menu` DISABLE KEYS */;
/*!40000 ALTER TABLE `opcion_menu` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `parametros` (
  `valorparametro` varchar(50) NOT NULL,
  `valornumerico` int(20) DEFAULT NULL,
  `valortexto` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`valorparametro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Tabla que almacena el valor de parámetros generales para el funcionamiento del sistema tienda';

/*!40000 ALTER TABLE `parametros` DISABLE KEYS */;
INSERT INTO `parametros` (`valorparametro`, `valornumerico`, `valortexto`) VALUES
	('TIEMPOPEDIDO', 120, NULL);
/*!40000 ALTER TABLE `parametros` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `pedido` (
  `idpedidotienda` int(11) NOT NULL AUTO_INCREMENT,
  `idtienda` int(11) NOT NULL DEFAULT '0',
  `total_bruto` double NOT NULL DEFAULT '0',
  `impuesto` double NOT NULL DEFAULT '0',
  `total_neto` double NOT NULL DEFAULT '0',
  `idcliente` int(11) NOT NULL DEFAULT '0',
  `fechapedido` date DEFAULT NULL,
  `idpedidocontact` int(11) NOT NULL DEFAULT '0',
  `fechainsercion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `usuariopedido` varchar(20) NOT NULL,
  `tiempopedido` int(3) NOT NULL DEFAULT '0',
  `idtipopedido` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idpedidotienda`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8 COMMENT='Tabla que define el encabezado de un pedido con los datos agrupadores de este.';

/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
INSERT INTO `pedido` (`idpedidotienda`, `idtienda`, `total_bruto`, `impuesto`, `total_neto`, `idcliente`, `fechapedido`, `idpedidocontact`, `fechainsercion`, `usuariopedido`, `tiempopedido`, `idtipopedido`) VALUES
	(15, 0, 0, 0, 0, 0, '2018-03-02', 0, '2018-04-02 21:59:32', '', 0, 0),
	(16, 0, 0, 0, 0, 0, '2018-03-02', 0, '2018-04-02 22:16:40', '', 0, 0),
	(17, 0, 0, 0, 0, 0, '2018-03-02', 0, '2018-04-02 22:21:19', '', 0, 0),
	(18, 0, 0, 0, 0, 0, '2018-03-03', 0, '2018-04-03 14:18:53', '', 0, 0),
	(19, 0, 0, 0, 0, 0, '2018-03-03', 0, '2018-04-03 18:02:52', '', 0, 0),
	(20, 0, 0, 0, 0, 0, '2018-03-03', 0, '2018-04-03 18:47:21', '', 0, 0),
	(21, 0, 0, 0, 0, 0, '2018-03-03', 0, '2018-04-03 18:52:18', '', 0, 0),
	(22, 0, 0, 0, 0, 0, '2018-03-04', 0, '2018-04-04 22:21:55', '', 0, 0),
	(23, 0, 0, 0, 0, 0, '2018-03-06', 0, '2018-04-06 05:52:05', '', 0, 0),
	(24, 0, 0, 0, 0, 43, '2018-03-06', 0, '2018-04-06 12:14:17', '', 0, 0),
	(25, 0, 0, 0, 0, 0, '2018-03-06', 0, '2018-04-06 13:18:43', '', 0, 0),
	(26, 0, 0, 0, 0, 0, '2018-03-06', 0, '2018-04-06 18:03:06', '', 0, 0),
	(27, 0, 0, 0, 0, 0, '2018-03-06', 0, '2018-04-06 21:35:53', '', 0, 0),
	(28, 0, 0, 0, 0, 0, '2018-03-06', 0, '2018-04-06 21:38:38', '', 0, 0),
	(29, 0, 0, 0, 0, 0, '2018-03-06', 0, '2018-04-06 21:45:48', '', 0, 0),
	(30, 0, 0, 0, 0, 0, '2018-03-07', 0, '2018-04-07 06:20:34', '', 0, 0),
	(31, 0, 0, 0, 0, 0, '2018-03-07', 0, '2018-04-07 06:26:23', '', 0, 0),
	(32, 0, 0, 0, 0, 0, '2018-03-07', 0, '2018-04-07 06:30:24', '', 0, 0),
	(33, 0, 0, 0, 0, 0, '2018-03-07', 0, '2018-04-07 06:31:53', '', 0, 0),
	(34, 0, 0, 0, 0, 0, '2018-03-07', 0, '2018-04-07 06:38:32', '', 0, 0),
	(35, 0, 0, 0, 0, 0, '2018-03-07', 0, '2018-04-07 06:39:20', '', 0, 0),
	(36, 0, 0, 0, 0, 0, '2018-03-07', 0, '2018-04-07 06:42:06', '', 0, 0),
	(37, 0, 0, 0, 0, 0, '2018-03-07', 0, '2018-04-07 06:46:46', '', 0, 0),
	(38, 0, 0, 0, 0, 0, '2018-03-07', 0, '2018-04-07 06:50:27', '', 0, 0),
	(39, 0, 0, 0, 0, 0, '2018-03-07', 0, '2018-04-07 06:56:31', '', 0, 0),
	(40, 0, 0, 0, 0, 0, '2018-03-07', 0, '2018-04-07 06:58:40', '', 0, 0),
	(41, 0, 0, 0, 0, 0, '2018-03-07', 0, '2018-04-07 07:01:48', '', 0, 0),
	(42, 0, 0, 0, 0, 0, '2018-03-07', 0, '2018-04-07 07:05:18', '', 0, 0),
	(43, 0, 0, 0, 0, 0, '2018-03-07', 0, '2018-04-07 07:06:45', '', 0, 0),
	(44, 0, 0, 0, 0, 0, '2018-03-07', 0, '2018-04-07 07:09:21', '', 0, 0),
	(45, 0, 0, 0, 0, 0, '2018-03-07', 0, '2018-04-07 14:16:36', '', 0, 0),
	(46, 0, 0, 0, 0, 0, '2018-03-07', 0, '2018-04-07 14:30:03', '', 0, 0),
	(47, 0, 0, 0, 0, 0, '2018-03-07', 0, '2018-04-07 14:33:51', '', 0, 0),
	(48, 0, 0, 0, 0, 0, '2018-03-07', 0, '2018-04-07 14:34:21', '', 0, 0),
	(49, 0, 0, 0, 0, 0, '2018-03-07', 0, '2018-04-07 14:45:35', '', 0, 0),
	(50, 0, 0, 0, 0, 0, '2018-03-07', 0, '2018-04-07 15:18:40', '', 0, 0),
	(51, 0, 0, 0, 0, 0, '2018-03-08', 0, '2018-04-08 01:06:24', '', 0, 0),
	(52, 0, 0, 0, 0, 0, '2018-03-08', 0, '2018-04-08 01:09:50', '', 0, 0),
	(53, 0, 0, 0, 0, 0, '2018-03-08', 0, '2018-04-08 01:11:58', '', 0, 0),
	(54, 0, 0, 0, 0, 0, '2018-03-08', 0, '2018-04-08 01:16:04', '', 0, 0),
	(55, 0, 0, 0, 0, 0, '2018-03-08', 0, '2018-04-08 01:24:45', '', 0, 0),
	(56, 0, 0, 0, 0, 0, '2018-03-08', 0, '2018-04-08 01:34:19', '', 0, 0),
	(57, 0, 0, 0, 0, 0, '2018-03-08', 0, '2018-04-08 01:40:48', '', 0, 0),
	(58, 0, 0, 0, 0, 0, '2018-03-08', 0, '2018-04-08 01:42:57', '', 0, 0),
	(59, 0, 0, 0, 0, 0, '2018-03-08', 0, '2018-04-08 01:49:16', '', 0, 0),
	(60, 0, 0, 0, 0, 0, '2018-03-08', 0, '2018-04-08 02:07:30', '', 0, 0),
	(61, 0, 0, 0, 0, 0, '2018-03-08', 0, '2018-04-08 02:16:53', '', 0, 0),
	(62, 0, 0, 0, 0, 0, '2018-03-08', 0, '2018-04-08 10:42:05', '', 0, 0),
	(63, 0, 0, 0, 0, 0, '2018-03-08', 0, '2018-04-08 12:56:41', '', 0, 0),
	(64, 0, 0, 0, 0, 0, '2018-03-08', 0, '2018-04-08 12:58:26', '', 0, 0),
	(65, 0, 0, 0, 0, 0, '2018-03-08', 0, '2018-04-08 13:17:01', '', 0, 0),
	(66, 0, 0, 0, 0, 0, '2018-03-08', 0, '2018-04-08 13:20:37', '', 0, 0),
	(67, 0, 0, 0, 0, 0, '2018-03-08', 0, '2018-04-08 13:26:18', '', 0, 0),
	(68, 0, 0, 0, 0, 0, '2018-03-08', 0, '2018-04-08 14:05:18', '', 0, 0),
	(69, 0, 0, 0, 0, 0, '2018-03-08', 0, '2018-04-08 14:12:39', '', 0, 0),
	(70, 0, 0, 0, 0, 0, '2018-03-08', 0, '2018-04-08 14:24:48', '', 0, 0),
	(71, 0, 0, 0, 0, 0, '2018-03-08', 0, '2018-04-08 14:28:44', '', 0, 0),
	(72, 0, 0, 0, 0, 0, '2018-03-10', 0, '2018-04-10 18:16:18', '', 0, 0),
	(73, 0, 0, 0, 0, 0, '2018-03-11', 0, '2018-04-11 06:57:10', '', 0, 0),
	(74, 0, 0, 0, 0, 0, '2018-03-11', 0, '2018-04-11 12:57:48', '', 0, 0),
	(75, 0, 0, 0, 0, 0, '2018-03-11', 0, '2018-04-11 13:05:15', '', 0, 0),
	(76, 0, 0, 0, 0, 0, '2018-03-12', 0, '2018-04-12 05:26:42', '', 0, 0),
	(77, 0, 0, 0, 0, 0, '2018-03-12', 0, '2018-04-12 05:33:01', '', 0, 0),
	(78, 0, 0, 0, 0, 0, '2018-03-12', 0, '2018-04-12 05:35:51', '', 0, 0),
	(79, 0, 11040, 960, 12000, 0, '2018-03-12', 0, '2018-04-12 05:39:18', '', 0, 0),
	(80, 0, 0, 0, 0, 0, '2018-03-13', 0, '2018-04-13 17:56:26', '', 0, 0),
	(81, 0, 52440, 4560, 57000, 0, '2018-03-13', 0, '2018-04-13 19:01:19', '', 0, 0),
	(82, 0, 41400, 3600, 45000, 0, '2018-03-23', 0, '2018-04-23 06:25:30', '', 0, 2),
	(83, 0, 50600, 4400, 55000, 0, '2018-03-24', 0, '2018-04-24 12:56:17', '', 0, 2),
	(84, 0, 39560, 3440, 43000, 0, '2018-04-25', 0, '2018-04-25 20:16:47', '', 0, 2),
	(85, 0, 39560, 3440, 43000, 0, '2018-04-25', 0, '2018-04-25 20:19:18', '', 0, 2),
	(86, 0, 50600, 4400, 55000, 43, '2018-04-25', 0, '2018-04-25 22:26:16', '', 0, 1),
	(87, 0, 39560, 3440, 43000, 43, '2018-03-26', 0, '2018-04-26 12:35:54', '', 0, 1);
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `pedido_forma_pago` (
  `idpedido_forma_pago` int(11) NOT NULL AUTO_INCREMENT,
  `idpedidotienda` int(11) NOT NULL,
  `idforma_pago` int(11) NOT NULL,
  `valortotal` double NOT NULL,
  `valorformapago` double NOT NULL,
  PRIMARY KEY (`idpedido_forma_pago`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='Tabla que permite definir las forma o formas de pago para un pedido.';

/*!40000 ALTER TABLE `pedido_forma_pago` DISABLE KEYS */;
INSERT INTO `pedido_forma_pago` (`idpedido_forma_pago`, `idpedidotienda`, `idforma_pago`, `valortotal`, `valorformapago`) VALUES
	(1, 79, 1, 12000, 12000),
	(2, 81, 1, 57000, 57000),
	(3, 82, 1, 45000, 45000),
	(4, 83, 1, 55000, 55000),
	(5, 84, 1, 43000, 43000),
	(6, 85, 1, 43000, 43000),
	(7, 86, 1, 55000, 55000),
	(8, 87, 1, 43000, 43000);
/*!40000 ALTER TABLE `pedido_forma_pago` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `pregunta` (
  `idpregunta` int(11) NOT NULL AUTO_INCREMENT,
  `titulopregunta` varchar(100) NOT NULL DEFAULT '0',
  `obliga_eleccion` int(1) NOT NULL DEFAULT '0',
  `numero_maximo_eleccion` int(3) NOT NULL DEFAULT '0',
  `estado` int(1) NOT NULL DEFAULT '0',
  `permite_dividir` int(1) NOT NULL DEFAULT '0',
  `descripcion` varchar(100) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idpregunta`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='Tabla donde es almacenada la información del encabezado de lasp reguntas forzadas.';

/*!40000 ALTER TABLE `pregunta` DISABLE KEYS */;
INSERT INTO `pregunta` (`idpregunta`, `titulopregunta`, `obliga_eleccion`, `numero_maximo_eleccion`, `estado`, `permite_dividir`, `descripcion`) VALUES
	(2, 'PIzza Grande', 1, 1, 1, 2, 'Que especialidad Desea?'),
	(3, 'Desea Adiciones?', 0, 10, 1, 2, 'Desea Adiciones?');
/*!40000 ALTER TABLE `pregunta` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `producto` (
  `idproducto` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) NOT NULL DEFAULT '0',
  `impresion` varchar(100) DEFAULT '',
  `textoboton` varchar(100) DEFAULT '',
  `colorboton` varchar(50) DEFAULT '',
  `idpreguntaforzada1` int(11) DEFAULT '0',
  `idpreguntaforzada2` int(11) DEFAULT '0',
  `idpreguntaforzada3` int(11) DEFAULT '0',
  `idpreguntaforzada4` int(11) DEFAULT '0',
  `idpreguntaforzada5` int(11) DEFAULT '0',
  `precio1` double DEFAULT '0',
  `precio2` double DEFAULT '0',
  `precio3` double DEFAULT '0',
  `precio4` double DEFAULT '0',
  `precio5` double DEFAULT '0',
  `precio6` double DEFAULT '0',
  `precio7` double DEFAULT '0',
  `precio8` double DEFAULT '0',
  `precio9` double DEFAULT '0',
  `precio10` double DEFAULT '0',
  `impresion_comanda` varchar(10) DEFAULT '0',
  PRIMARY KEY (`idproducto`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COMMENT='Tabla que se encargará de definir los parámetros del los productos creados en el sistema';

/*!40000 ALTER TABLE `producto` DISABLE KEYS */;
INSERT INTO `producto` (`idproducto`, `descripcion`, `impresion`, `textoboton`, `colorboton`, `idpreguntaforzada1`, `idpreguntaforzada2`, `idpreguntaforzada3`, `idpreguntaforzada4`, `idpreguntaforzada5`, `precio1`, `precio2`, `precio3`, `precio4`, `precio5`, `precio6`, `precio7`, `precio8`, `precio9`, `precio10`, `impresion_comanda`) VALUES
	(1, 'Lasagna Pollo', 'Lasagna Pollo', 'Lasagna Pollo', '', 0, 0, 0, 0, 0, 12000, 12000, 0, 0, 0, 0, 0, 0, 0, 0, 'S'),
	(2, 'Pizza GD', 'Pizza GD', 'Pizza GD', '', 2, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, '0'),
	(3, 'Pizza GD Hawaina', 'Pizza GD Hawaina', 'Pizza GD Hawaina', '', 0, 0, 0, 0, 0, 31000, 31000, 0, 0, 0, 0, 0, 0, 0, 0, '0'),
	(4, 'Pizza GD Americana', 'Pizza GD Americana', 'Pizza GD Americana', '', 0, 0, 0, 0, 0, 31000, 31000, 0, 0, 0, 0, 0, 0, 0, 0, '0'),
	(5, 'Pizza GD Pollo Champiñon', 'Pizza GD Pollo Champiñon', 'Pizza GD Pollo Champiñon', '', 0, 0, 0, 0, 0, 31000, 31000, 0, 0, 0, 0, 0, 0, 0, 0, '0'),
	(6, 'Pizza GD Americana Especial', 'Pizza GD Americana Especial', 'Pizza GD Americana Especial', '', 0, 0, 0, 0, 0, 31000, 31000, 0, 0, 0, 0, 0, 0, 0, 0, '0'),
	(7, 'Pizza GD Jamón Champiñon', 'Pizza GD Jamón Champiñon', 'Pizza GD Jamón Champiñon', '', 0, 0, 0, 0, 0, 31000, 31000, 0, 0, 0, 0, 0, 0, 0, 0, '0'),
	(8, 'Pizza GD Pollo Especial', 'Pizza GD Pollo Especial', 'Pizza GD Pollo Especial', '', 0, 0, 0, 0, 0, 31000, 31000, 0, 0, 0, 0, 0, 0, 0, 0, '0'),
	(9, 'Pizza GD Pollo Champiñon', 'Pizza GD Pollo Champiñon', 'Pizza GD Pollo Champiñon', '', 0, 0, 0, 0, 0, 31000, 31000, 0, 0, 0, 0, 0, 0, 0, 0, '0'),
	(10, 'Pizza GD Mexicana', 'Pizza GD Mexicana', 'Pizza GD Mexicana', '', 0, 0, 0, 0, 0, 31000, 31000, 0, 0, 0, 0, 0, 0, 0, 0, '0'),
	(11, 'Pizza GD Peperoni Queso', 'Pizza GD Peperoni Queso', 'Pizza GD Peperoni Queso', '', 0, 0, 0, 0, 0, 31000, 31000, 0, 0, 0, 0, 0, 0, 0, 0, '0'),
	(12, 'Pizza GD Peperoni Champinon', 'Pizza GD Peperoni Champinon', 'Pizza GD Peperoni Champinon', '', 0, 0, 0, 0, 0, 31000, 31000, 0, 0, 0, 0, 0, 0, 0, 0, '0'),
	(13, 'Adicion Piña GD', 'Adicion Piña GD', 'Adicion Piña GD', '', 0, 0, 0, 0, 0, 2000, 2000, 0, 0, 0, 0, 0, 0, 0, 0, '0'),
	(14, 'Adicion Jamón GD', 'Adicion Jamón GD', 'Adicion Jamón GD', '', 0, 0, 0, 0, 0, 2000, 2000, 0, 0, 0, 0, 0, 0, 0, 0, '0');
/*!40000 ALTER TABLE `producto` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `producto_incluido` (
  `idproducto_incluido` int(11) NOT NULL AUTO_INCREMENT,
  `idproductoincluido` int(11) DEFAULT NULL,
  `idproductoincluye` int(11) DEFAULT NULL,
  `cantidad` double DEFAULT NULL,
  `precio` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`idproducto_incluido`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='Tabla que almacena la información de aquellos producto que por su elección incluyen automáticamente otros.';

/*!40000 ALTER TABLE `producto_incluido` DISABLE KEYS */;
INSERT INTO `producto_incluido` (`idproducto_incluido`, `idproductoincluido`, `idproductoincluye`, `cantidad`, `precio`) VALUES
	(2, 1, 1, 2, 'precio1');
/*!40000 ALTER TABLE `producto_incluido` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `tienda` (
  `idtienda` int(11) NOT NULL,
  `nombretienda` varchar(50) NOT NULL,
  `urlcontact` varchar(100) NOT NULL,
  `fecha_apertura` date DEFAULT NULL,
  `fecha_ultimo_cierre` date DEFAULT NULL,
  PRIMARY KEY (`idtienda`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40000 ALTER TABLE `tienda` DISABLE KEYS */;
INSERT INTO `tienda` (`idtienda`, `nombretienda`, `urlcontact`, `fecha_apertura`, `fecha_ultimo_cierre`) VALUES
	(1, 'Manrique', 'http://localhost:8080/ProyectoPizzaAmericana/', '2018-04-25', '2018-04-24');
/*!40000 ALTER TABLE `tienda` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `tipo_pedido` (
  `idtipopedido` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(100) NOT NULL DEFAULT '',
  `valordefecto` binary(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idtipopedido`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='Tabla que contiene los tipos de pedidos disponibles en el sistema';

/*!40000 ALTER TABLE `tipo_pedido` DISABLE KEYS */;
INSERT INTO `tipo_pedido` (`idtipopedido`, `descripcion`, `valordefecto`) VALUES
	(1, 'Domicilio', '0'),
	(2, 'Punto de Venta', '1'),
	(3, 'Para Llevar', '0');
/*!40000 ALTER TABLE `tipo_pedido` ENABLE KEYS */;

CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) NOT NULL,
  `password` varchar(20) NOT NULL,
  `nombre_largo` varchar(100) NOT NULL,
  `administrador` varchar(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='usuarios del aplicativo';

/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` (`id`, `nombre`, `password`, `nombre_largo`, `administrador`) VALUES
	(1, 'admin', 'admin', 'Administrador', 'S');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
