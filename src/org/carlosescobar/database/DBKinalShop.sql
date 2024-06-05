set global time_zone = '-6:00';
drop database if exists DBKinalShop;
create database DBKinalShop;


ALTER USER '2019067_IN5BM'@'localhost' IDENTIFIED WITH mysql_native_password BY 'admi';
use DBKinalShop;

create table Clientes (
clienteID int,
nitClientes varchar(10),
nombreClientes varchar(50) ,
apellidoClientes varchar(50),
direccionClientes varchar(150),
telefonoClientes varchar(15),
CorreoClientes varchar(45),
primary key PK_clienteID(clienteID)
);

create table TipoProducto
(
    codigoTipoProducto int not null ,
    descripcion varchar(45),
    primary key PK_codigoTipoProducto (codigoTipoProducto)
);

create table Compras
(
    numeroDocumento int not null ,
    fechaDocumento date,
    descripcion varchar(60),
    totalDocumento decimal(10.2),
    primary key PK_numeroDocumento (numeroDocumento)
);

create table Proveedores
(
    codigoProveedor int not null ,
    nitProveedor varchar (10),
    nombresProveedor varchar(60),
    apellidosProveedor varchar(60),
    direccionProveedor varchar(150),
    razonSocial varchar(60),
    contactoPrincipal varchar(100),
    paginaWeb varchar(50),
    primary key PK_codigoProveedor (codigoProveedor)
);

create table CargoEmpleado
(
      codigoCargoEmpleado int not null ,
    nombreCargo varchar (45),
    descripcionCargo varchar(45),
    primary key PK_codigoCargoEmpleado (codigoCargoEmpleado)
);

create table Empleados
(
	codigoEmpleado int not null,
    nombresEmpleados varchar(50),
    apellidosEmpleados varchar(50),
    sueldo decimal(10,2),
    direccion varchar(150),
    turno varchar(15),
    codigoCargoEmpleado int,
    primary key PK_codigoEmpleado (codigoEmpleado),
    foreign key (codigoCargoEmpleado) references CargoEmpleado(codigoCargoEmpleado)
);
create table Factura
(
	numeroFactura int,
	estado varchar(50),
    totalFactura decimal(10,2),
    fechaFactura varchar(45),
    clienteID int not null,
    codigoEmpleado int not null,
    primary key PK_numeroFactura (numeroFactura),
	foreign key (clienteID) references Clientes(clienteID),
	foreign key (codigoEmpleado) references Empleados(codigoEmpleado) 
    

);
create table Productos
(
	codigoProducto varchar(15),
	descripcionProducto varchar(45),
    precioUnitario decimal(10,2),
    precioDocena decimal(10,2),
    precioMayor decimal(10,2),
    imagenProducto varchar(45),
    existencia int not null,
    codigoTipoProducto int ,
    codigoProveedor int ,
    primary key PK_codigoProducto (codigoProducto),
	foreign key (codigoTipoProducto) references TipoProducto(codigoTipoProducto),
	foreign key (codigoProveedor) references Proveedores(codigoProveedor) 
);

create table DetalleCompra
(
	codigoDetalleCompra int not null,
    costoUnitario decimal(10,2),
    cantidad int not null,
    codigoProducto varchar(15),
    numeroDocumento int ,
    primary key PK_codigoDetalleCompra (codigoDetalleCompra),
	foreign key (codigoProducto) references Productos(codigoProducto),
    foreign key (numeroDocumento) references Compras(numeroDocumento)
);

create table EmailProveedor
(
	codigoEmailProveedor int not null,
    emailProveedor varchar(50),
    descripcion varchar(100),
    codigoProveedor int ,
    primary key PK_codigoEmailProveedor (codigoEmailProveedor),
	foreign key (codigoProveedor) references Proveedores(codigoProveedor)

);

create table TelefonoProveedor
(
codigoTelefonoProveedor int not null,
numeroPrincipal varchar(8),
numeroSecundario varchar(8),
observaciones varchar(45),
codigoProveedor int ,
primary key PK_codigoTelefonoProveedor (codigoTelefonoProveedor),
foreign key (codigoProveedor) references Proveedores(codigoProveedor)
);
create table DetalleFactura
(
	codigoDetalleFactura int not null,
    precioUnitario decimal(10,2),
    cantidad int,
    numeroFactura int not null,
    codigoProducto varchar(15),
    primary key PK_codigoDetalleFactura (codigoDetalleFactura),
	foreign key (numeroFactura) references Factura(numeroFactura),
    foreign key (codigoProducto) references Productos(codigoProducto)

);







insert into Clientes (clienteID, nombreClientes, apellidoClientes, nitClientes, direccionClientes,
telefonoClientes, CorreoClientes)
values(1, "Julio","Frame","12345-6","colonia 1ro de julio","12345678","sdfgjh@hhd.com");


delimiter $$
create procedure sp_agregarClientes (in _clienteID int, in _nombreClientes varchar(50), in _apellidoClientes varchar(50),
in _nitClientes varchar(10), in _direccionClientes varchar(150), in _telefonoClientes varchar(15), in _correoClientes varchar(45))
begin
	insert into Clientes (Clientes.clienteID,  Clientes.nombreClientes, Clientes.apellidoClientes, Clientes.nitClientes, 
    Clientes.direccionClientes, Clientes.telefonoClientes, Clientes.correoClientes)
    values (_clienteID, _nombreClientes, _apellidoClientes, nitClientes, _direccionClientes, _telefonoClientes, _correoClientes);
end$$
delimiter ;
 

 
delimiter $$
	create procedure sp_listarClientes ()
    begin
		select Clientes.clienteID,  Clientes.nombreClientes,
		Clientes.apellidoClientes, Clientes.direccionClientes, Clientes.telefonoClientes, Clientes.correoClientes  from Clientes;
    end$$
delimiter ;
 

 
delimiter $$
	create procedure sp_buscarClientes(in _clienteID int)
    begin
	select Clientes.nitClientes, Clientes.nombreClientes, Clientes.apellidoClientes, Clientes.direccionClientes, 
    Clientes.telefonoClientes, Clientes.correoClientes from Clientes where Clientes.clienteID = _clienteID;
	end$$
delimiter ;
 
delimiter $$
	create procedure sp_actualizarClientes(in _clienteID int, in _nombreClientes varchar(10), in _apellidoClientes varchar(50),
in _nitClientes varchar(50), in _direccionClientes varchar(150), in _telefonoClientes varchar(15), in _correoClientes varchar(45))
    
    begin
		update Clientes 
		set
        Clientes.nitClientes = _nitClientes,
        Clientes.nombreClientes = _nombreClientes,
        Clientes.apellidoClientes = _apellidoClientes,
        Clientes.direccionClientes =direccionClientes, 
        Clientes.correoClientes = _correoClientes
        where 
        Clientes.clienteID = _clienteID;
    end$$
delimiter ;
 
delimiter $$
	create procedure sp_eliminarCliente (in _clienteID int)
    begin
		delete from Clientes where Clientes.clienteID = _clienteID;
    end$$
delimiter ;

-- call sp_actualizarClientes(1, "Julio","Frame","12345-6","colonia 1ro de julio","12345678","sdfgjh@hhd.com");


-- Procedimientos de TipoProducto

delimiter $$
create procedure sp_agregarTipoProducto (in _codigoTipoProducto int, in _descripcion varchar(45))
begin
	insert into TipoProducto ( codigoTipoProducto ,TipoProducto.descripcion)
    values (_codigoTipoProducto,_descripcion);
end$$
delimiter ;

call sp_agregarTipoProducto(1,'Agua pura salvavidas 200 ml');


-- Listar 

delimiter $$
	create procedure sp_listarTipoProducto ()
    begin
		select TipoProducto.codigoTipoProducto, TipoProducto.descripcion from TipoProducto;
    end$$
delimiter ;

call sp_listarTipoProducto();

-- Buscar 

delimiter $$
	create procedure sp_buscarTipoProducto(in _descripcion int)
    begin
	select TipoProducto.descripcion from TipoProducto where TipoProducto.codigoTipoProducto = _descripcion;
	end$$
delimiter ;

call sp_buscarTipoProducto(1);


-- Actualizar

delimiter $$
	create procedure sp_actualizarTipoProducto(in _CodigoTipoProduto int, in _descripcion varchar(45))
    begin
		update TipoProducto 
		set
        TipoProducto.descripcion = _descripcion
        where 
        TipoProducto.codigoTipoProducto = _CodigoTipoProduto;
    end$$
delimiter ;

call sp_actualizarTipoProducto(1,'Tambo agua salvavidas');

-- Eliminar

delimiter $$
	create procedure sp_eliminarTipoProducto(in _CodigoTipoProduto int)
    begin
		delete from TipoProducto where TipoProducto.codigoTipoProducto  = _CodigoTipoProduto;
    end$$
delimiter ;

call sp_eliminarTipoProducto(4);


-- procedimientos de compras

delimiter $$
create procedure sp_agregarCompras (in _numeroDocumento int, in _fechaDocumento date, in _descripcion varchar(60), in _totalDocumento decimal(10,0))
begin
	insert into Compras (Compras.numeroDocumento, Compras.fechaDocumento, Compras.descripcion,
    Compras.totalDocumento)
    values (_numeroDocumento, _fechaDocumento, _descripcion,  _totalDocumento );
end$$
delimiter ;	

call sp_agregarCompras(1,'2023-05-21','Hoy 21 de Mayo se realizo una compra de', 100.00);


-- Listar 

delimiter $$
	create procedure sp_listarCompras ()
    begin
		select Compras.numeroDocumento, Compras.fechaDocumento, Compras.descripcion,
    Compras.totalDocumento from Compras;
    end$$
delimiter ;

call sp_listarCompras();

-- Buscar 

delimiter $$
	create procedure sp_buscarCompras(in _numeroDocumento int)
    begin
	select Compras.fechaDocumento, Compras.descripcion, Compras.totalDocumento 
    from Compras where Compras.numeroDocumento = _numeroDocumento;
	end$$
delimiter ;

call sp_buscarCompras(1);
call sp_buscarCompras(2);


-- Actualizar

delimiter $$
	create procedure sp_actualizarCompras(in _numeroDocumento int, in _fechaDocumento date, in _descripcion varchar(60), in _totalDocumento decimal(10,0))
    begin
		update Compras
		set
        Compras.fechaDocumento =_fechaDocumento,
        Compras.descripcion = _descripcion,
        Compras.totalDocumento = totalDocumento
        where 
        Compras.numeroDocumento = _numeroDocumento;
    end$$
delimiter ;

call sp_actualizarCompras(1,'2023-05-21','Hoy 21 de Mayo se realizo una compra de', 90.00);

-- Eliminar

delimiter $$
	create procedure sp_eliminarCompras(in _numeroDocumento int)
    begin
		delete from Compras where Compras.numeroDocumento = _numeroDocumento;
    end$$
delimiter ;

-- call sp_eliminarCompras();

-- procedimientos de CargoEmpleado




delimiter $$
create procedure sp_agregarCargo (in _codigoCargoEmpleado  int, in _nombreCargo varchar(45), in _descripcionCargo varchar(50))
begin
	insert into CargoEmpleado (CargoEmpleado.codigoCargoEmpleado ,CargoEmpleado.nombreCargo, CargoEmpleado.descripcionCargo )
    values (_codigoCargoEmpleado , _nombreCargo,  _descripcionCargo);
end$$
delimiter ;
 
call sp_agregarCargo(1,'Encargado Visual','Revisar el trabajo de todos');
 
delimiter $$
	create procedure sp_listarCargos ()
    begin
		select CargoEmpleado.codigoCargoEmpleado ,CargoEmpleado.nombreCargo, CargoEmpleado.descripcionCargo from CargoEmpleado;
    end$$
delimiter ;
 
 call sp_listarCargos();
 
 
delimiter $$
	create procedure sp_actualizarCargo(in _codigoCargoEmpleado  int, in _nombreCargo varchar(10), in _descripcionCargo varchar(50))
    
    begin
		update CargoEmpleado 
		set
        CargoEmpleado.nombreCargo = _nombreCargo,
        CargoEmpleado.descripcionCargo =_descripcionCargo
        where 
        CargoEmpleado.codigoCargoEmpleado = _codigoCargoEmpleado ;
    end$$
delimiter ;
 
delimiter $$
	create procedure sp_eliminarCargo (in _codigoCargoEmpleado  int)
    begin
		delete from CargoEmpleado where CargoEmpleado.codigoCargoEmpleado = _codigoCargoEmpleado;
    end$$
delimiter ;




-- procedimientos de Proveedores

-- agregar

delimiter $$
create procedure sp_agregarProveedores (in _codigoProveedor int, in _nitProveedor  varchar(10), in _nombresProveedor  varchar(60), in _apellidosProveedor  varchar(60), 
in _direccionProveedor  varchar(150), in _razonSocial  varchar(60), in _contactoPrincipal  varchar(100), in _paginaWeb  varchar(50))
begin
	insert into Proveedores (Proveedores.codigoProveedor, Proveedores.nitProveedor, Proveedores.nombresProveedor,
    Proveedores.apellidosProveedor, Proveedores.direccionProveedor, Proveedores.razonSocial, Proveedores.contactoPrincipal,
    Proveedores.paginaWeb)
    values (_codigoProveedor, _nitProveedor, _nombresProveedor, _apellidosProveedor, _direccionProveedor, _razonSocial, _contactoPrincipal, _paginaWeb);
end$$
delimiter ;	
 
call sp_agregarProveedores(1,'12347894-9','Carlos José','Méndez Oliva','Almacén 18 carretera al Salvador', 'risitos', 'risitos12@gmail.com', 'www.risitos.com');


-- Listar
 
delimiter $$
	create procedure sp_listarProveedores()
    begin
		select Proveedores.codigoProveedor, Proveedores.nitProveedor, Proveedores.nombresProveedor,Proveedores.apellidosProveedor, 
	Proveedores.direccionProveedor, Proveedores.razonSocial, Proveedores.contactoPrincipal, Proveedores.paginaWeb 
    from Proveedores;
    end$$
delimiter ;
 
call sp_listarProveedores();
 
-- Buscar
 
delimiter $$
	create procedure sp_buscarProveedores(in _codigoProveedor int)
    begin
	select Proveedores.nitProveedor, Proveedores.nombresProveedor,Proveedores.apellidosProveedor, 
	Proveedores.direccionProveedor, Proveedores.razonSocial, Proveedores.contactoPrincipal, Proveedores.paginaWeb  
    from Proveedores where Proveedores.codigoProveedor = _codigoProveedor;
	end$$
delimiter ;
 


 
-- Actualizar
 
delimiter $$
	create procedure sp_actualizarProveedores(in _codigoProveedor int, in _nitProveedor  varchar(10), in _nombresProveedor  varchar(60), in _apellidosProveedor  varchar(60), 
in _direccionProveedor  varchar(150), in _razonSocial  varchar(60), in _contactoPrincipal  varchar(100), in _paginaWeb  varchar(50))
    begin
		update Proveedores
		set
		Proveedores.nitProveedor = _nitProveedor,
        Proveedores.nombresProveedor = _nombresProveedor,
        Proveedores.apellidosProveedor = _apellidosProveedor,
		Proveedores.direccionProveedor = _direccionProveedor,
        Proveedores.razonSocial = _razonSocial,
        Proveedores.contactoPrincipal = _contactoPrincipal,
        Proveedores.paginaWeb = _paginaWeb
        where 
        Proveedores.codigoProveedor = _codigoProveedor;
    end$$
delimiter ;
 

 
-- Eliminar
 
delimiter $$
	create procedure sp_eliminarProveedores(in _codigoProveedor int)
    begin
		delete from Proveedores where Proveedores.codigoProveedor = _codigoProveedor;
    end$$
delimiter ;
 




-- procedimientos de empleados

-- agregar

delimiter $$
create procedure sp_agregarEmpleado (in _codigoEmpleado int , in _nombresEmpleados  varchar(50), in _apellidosEmpleados varchar(50)  , in _sueldo decimal(10,2), 
in _direccion varchar(150), in _turno  varchar(15), in _codigoCargoEmpleado int)
begin
	insert into Empleados (Empleados.codigoEmpleado, Empleados.nombresEmpleados, Empleados.apellidosEmpleados,
    Empleados.sueldo, Empleados.direccion, Empleados.turno, Empleados.codigoCargoEmpleado
    )
    values (_codigoEmpleado, _nombresEmpleados, _apellidosEmpleados, _sueldo, _direccion, _turno, _codigoCargoEmpleado );
end$$
delimiter ;	
 
call sp_agregarEmpleado(1,'Jonathan','bolaños',4000.00,'Casa de campo', 'Matutino', 1);

-- Listar
 
delimiter $$
	create procedure sp_listarEmpleados()
    begin
		select Empleados.codigoEmpleado, Empleados.nombresEmpleados, Empleados.apellidosEmpleados,
    Empleados.sueldo, Empleados.direccion, Empleados.turno, Empleados.codigoCargoEmpleado 
    from Empleados;
    end$$
delimiter ;
 
call sp_listarEmpleados();
 
-- Buscar
 
delimiter $$
	create procedure sp_buscarEmpleado(in _codigoEmpleado int)
    begin
	select Empleados.codigoEmpleado, Empleados.nombresEmpleados, Empleados.apellidosEmpleados,
    Empleados.sueldo, Empleados.direccion, Empleados.turno, Empleados.codigoCargoEmpleado 
    from Empleados where Empleados.codigoEmpleado = _codigoEmpleado;
	end$$
delimiter ;
 
-- call sp_buscarProveedores();

 
-- Actualizar
 
delimiter $$
	create procedure sp_actualizarEmpleado(in _codigoEmpleado int , in _nombresEmpleados  varchar(50), in _apellidosEmpleados varchar(50)  , in _sueldo decimal(10,2), 
in _direccion varchar(150), in _turno  varchar(15), in _codigoCargoEmpleado int)
    begin
		update Empleados
		set
		
        Empleados.nombresEmpleados = _nombresEmpleados,
        Empleados.apellidosEmpleados = _apellidosEmpleados,
		Empleados.sueldo = _sueldo,
        Empleados.direccion = _direccion,
        Empleados.turno = _turno,
        Empleados.codigoCargoEmpleado = _codigoCargoEmpleado
        where 
		Empleados.codigoEmpleado = _codigoEmpleado;

    end$$
delimiter ;
 
-- call sp_actualizarEmpleado();
 
-- Eliminar
 
delimiter $$
	create procedure sp_eliminarEmpleado(in _codigoEmpleado int)
    begin
		delete from Empleados where Empleados.codigoEmpleado = _codigoEmpleado;
    end$$
delimiter ;
 
-- call sp_eliminarEmpleado();


-- procedimientos de factura

-- agregar

delimiter $$
create procedure sp_agregarFactura (in _numeroFactura int, in _estado  varchar(50), in _totalFactura  decimal(10.2), in _fechaFactura  varchar(45), 
in _clienteID  int, in _codigoEmpleado int)
begin
	insert into Factura (Factura.numeroFactura, Factura.estado, Factura.totalFactura,
    Factura.fechaFactura, Factura.clienteID, Factura.codigoEmpleado )
    values (_numeroFactura, _estado, _totalFactura, _fechaFactura, _clienteID, _codigoEmpleado);
end$$
delimiter ;	
 
call sp_agregarFactura(1,'',12.00,'15/09/2023',1,1);

-- Listar
 
delimiter $$
	create procedure sp_listarFacturas()
    begin
		select Factura.numeroFactura, Factura.estado, Factura.totalFactura,
    Factura.fechaFactura, Factura.clienteID, Factura.codigoEmpleado 
    from Factura;
    end$$
delimiter ;
 
call sp_listarFacturas();
 
-- Buscar
 
delimiter $$
	create procedure sp_buscarFactura(in _numeroFactura int)
    begin
	select Factura.numeroFactura, Factura.estado, Factura.totalFactura,
    Factura.fechaFactura, Factura.clienteID, Factura.codigoEmpleado  
    from Factura where Factura.numeroFactura = _numeroFactura;
	end$$
delimiter ;
 


 
-- Actualizar
 
delimiter $$
	create procedure sp_actualizarFactura(in _numeroFactura int, in _estado  varchar(50), in _totalFactura  decimal(10.2), in _fechaFactura  varchar(45), 
in _clienteID  int, in _codigoEmpleado int)
    begin
		update Factura
		set
		Factura.estado = _estado,
        Factura.totalFactura = _totalFactura,
        Factura.fechaFactura = _fechaFactura,
		Factura.clienteID = _clienteID,
        Factura.codigoEmpleado = _codigoEmpleado
        where 
        Factura.numeroFactura = _numeroFactura;
    end$$
delimiter ;
 
 
-- Eliminar
 
delimiter $$
	create procedure sp_eliminarFactura(in _numeroFactura int)
    begin
		delete from Factura where Factura.numeroFactura = _numeroFactura;
    end$$
delimiter ;
 



-- Procedimientos de Productos
-- agregar

delimiter $$
create procedure sp_agregarProducto (in _codigoProducto varchar(15), in _descripcionProducto  varchar(45), in _precioUnitario  decimal(10.2), in _precioDocena  decimal(10.2), 
in _precioMayor  decimal(10.2), in _imagenProducto  varchar(45), in _existencia  int, in _codigoTipoProducto  int,in _codigoProveedor int)
begin
	insert into Productos (Productos.codigoProducto, Productos.descripcionProducto, Productos.precioUnitario,
    Productos.precioDocena, Productos.precioMayor, Productos.imagenProducto, Productos.existencia,
    Productos.codigoTipoProducto, Productos.codigoProveedor)
    values (_codigoProducto, _descripcionProducto, _precioUnitario, _precioDocena, _precioMayor, _imagenProducto, _existencia,  _codigoTipoProducto,_codigoProveedor);
end$$
delimiter ;	
 
call sp_agregarProducto('123','la mejor bebida',50.00,45.00,40.00,'imagen',50,1,1);

-- Listar
 
delimiter $$
	create procedure sp_listarProductos()
    begin
		select Productos.codigoProducto, Productos.descripcionProducto, Productos.precioUnitario,
    Productos.precioDocena, Productos.precioMayor, Productos.imagenProducto, Productos.existencia,
    Productos.codigoTipoProducto, Productos.codigoProveedor
    from Productos;
    end$$
delimiter ;
 
call sp_listarProductos();
 
-- Buscar
 
delimiter $$
	create procedure sp_buscarProducto(in _codigoProducto int)
    begin
	select Productos.codigoProducto, Productos.descripcionProducto, Productos.precioUnitario,
    Productos.precioDocena, Productos.precioMayor, Productos.imagenProducto, Productos.existencia,
    Productos.codigoTipoProducto, Productos.codigoProveedor 
    from Productos where Productos.codigoProducto = _codigoProducto;
	end$$
delimiter ;
 
-- call sp_buscarProducto();

 
-- Actualizar
 
delimiter $$
	create procedure sp_actualizarProducto(in _codigoProducto varchar(15), in _descripcionProducto  varchar(45), in _precioUnitario  decimal(10.2), in _precioDocena  decimal(10.2), 
in _precioMayor  decimal(10.2), in _imagenProducto  varchar(45), in _existencia  int, in _codigoTipoProducto  int,in _codigoProveedor int)
    begin
		update Productos
		set
		Productos.descripcionProducto = _descripcionProducto,
        Productos.precioUnitario = _precioUnitario,
        Productos.precioDocena = _precioDocena,
		Productos.precioMayor = _precioMayor,
        Productos.imagenProducto = _imagenProducto,
        Productos.existencia = _existencia,
        Productos.codigoTipoProducto = _codigoTipoProducto,
        Productos.codigoProveedor =_codigoProveedor
        where 
        Productos.codigoProducto = _codigoProducto;
    end$$
delimiter ;
 

 
-- Eliminar
 
delimiter $$
	create procedure sp_eliminarProducto(in _codigoProducto int)
    begin
		delete from Productos where Productos.codigoProducto = _codigoProducto;
    end$$
delimiter ;
 


-- Procedimientos de detalleCompra



-- agregar

delimiter $$
create procedure sp_agregarDetalleCompra (in _codigoDetalleCompra int, in _costoUnitario  decimal(10.2), in _cantidad  int, in _codigoProducto varchar(15), 
in _numeroDocumento  int)
begin
	insert into DetalleCompra (DetalleCompra.codigoDetalleCompra, DetalleCompra.costoUnitario,
    DetalleCompra.cantidad, DetalleCompra.codigoProducto, DetalleCompra.numeroDocumento)
    values (_codigoDetalleCompra, _costoUnitario, _cantidad, _codigoProducto, _numeroDocumento);
end$$
delimiter ;	
 
 call sp_agregarDetalleCompra(1,50.00,45,'123',1);


-- Listar
 
delimiter $$
	create procedure sp_listarDetalleCompra()
    begin
		select DetalleCompra.codigoDetalleCompra, DetalleCompra.costoUnitario,
    DetalleCompra.codigoProducto, DetalleCompra.numeroDocumento
    from DetalleCompra;
    end$$
delimiter ;
 
call sp_listarDetalleCompra();
 
-- Buscar
 
delimiter $$
	create procedure sp_buscarDetalleCompra(in _codigoDetalleCompra int)
    begin
	select DetalleCompra.codigoDetalleCompra, DetalleCompra.costoUnitario, DetalleCompra.precioUnitario,
    DetalleCompra.codigoProducto, DetalleCompra.numeroDocumento
    from DetalleCompra where codigoDetalleCompra.codigoDetalleCompra = _codigoDetalleCompra;
	end$$
delimiter ;
 
-- call sp_buscarDetalleCompra();

 
-- Actualizar
 
delimiter $$
	create procedure sp_actualizarDetalleCompra(in _codigoDetalleCompra int, in _costoUnitario  decimal(10.2), in _cantidad  int, in _codigoProducto  varchar(15), 
in _numeroDocumento  int)
    begin
		update DetalleCompra
		set
		DetalleCompra.costoUnitario = _costoUnitario,
        DetalleCompra.cantidad = _cantidad,
        DetalleCompra.codigoProducto = _codigoProducto,
		DetalleCompra.numeroDocumento = _numeroDocumento
        where 
        DetalleCompra.codigoDetalleCompra = _codigoDetalleCompra;
    end$$
delimiter ;
 

 
-- Eliminar
 
delimiter $$
	create procedure sp_eliminarDetalleCompra(in _codigoDetalleCompra int)
    begin
		delete from DetalleCompra where DetalleCompra.codigoDetalleCompra = _codigoDetalleCompra;
    end$$
delimiter ;

-- Procedimiento de EmailProveedor

    
  -- agregar

delimiter $$
create procedure sp_agregarEmailProveedor (in _codigoEmailProveedor int, in _emailProveedor  varchar(50), in _descripcion varchar(100), 
in _codigoProveedor  int)
begin
	insert into EmailProveedor (EmailProveedor.codigoEmailProveedor, EmailProveedor.emailProveedor, EmailProveedor.descripcion,
    EmailProveedor.codigoProveedor)
    values (_codigoEmailProveedor, _emailProveedor, _descripcion, _codigoProveedor);
end$$
delimiter ;	
 
call sp_agregarEmailProveedor(1,'JuanPerez34@email.com','venta de agua',1);

-- Listar
 
delimiter $$
	create procedure sp_listarEmailProveedor()
    begin
		select EmailProveedor.codigoEmailProveedor, EmailProveedor.emailProveedor, EmailProveedor.descripcion,
    EmailProveedor.codigoProveedor
    from EmailProveedor;
    end$$
delimiter ;
 
call sp_listarEmailProveedor();
 
-- Buscar
 
delimiter $$
	create procedure sp_buscarEmailProveedor(in _codigoEmailProveedor int)
    begin
	select EmailProveedor.codigoEmailProveedor, EmailProveedor.emailProveedor, EmailProveedor.descripcion,
    EmailProveedor.codigoProveedor
    from EmailProveedor where EmailProveedor.codigoEmailProveedor = _codigoEmailProveedor;
	end$$
delimiter ;
 
-- call sp_buscarEmailProveedor();

 
-- Actualizar
 
delimiter $$
	create procedure sp_actualizarEmailProveedor(in _codigoEmailProveedor int, in _emailProveedor  varchar(50), in _descripcion varchar(100), 
in _codigoProveedor  int)
    begin
		update EmailProveedor
		set
		EmailProveedor.emailProveedor = _emailProveedor,
        EmailProveedor.descripcion = _descripcion,
        EmailProveedor.codigoProveedor = _codigoProveedor
        where 
        EmailProveedor.codigoEmailProveedor = _codigoEmailProveedor;
    end$$
delimiter ;
 

 
-- Eliminar
 
delimiter $$
	create procedure sp_eliminarEmailProveedor(in _codigoEmailProveedor int)
    begin
		delete from EmailProveedor where EmailProveedor.codigoEmailProveedor = _codigoEmailProveedor;
    end$$
delimiter ;  

 -- Procedimientos de TelefonoProveedor
 


-- agregar

delimiter $$
create procedure sp_agregarTelefonoProveedor (in _codigoTelefonoProveedor int, in _numeroPrincipal  varchar(8), in _numeroSecundario  varchar(8), in _observaciones  varchar(45), 
in _codigoProveedor  int)
begin
	insert into TelefonoProveedor (TelefonoProveedor.codigoTelefonoProveedor, TelefonoProveedor.numeroPrincipal, TelefonoProveedor.numeroSecundario,
    TelefonoProveedor.observaciones, TelefonoProveedor.codigoProveedor)
    values (_codigoTelefonoProveedor, _numeroPrincipal, _numeroSecundario, _observaciones, _codigoProveedor);
end$$
delimiter ;	
 
call sp_agregarTelefonoProveedor(1,'01111111','12222222','123',1);

-- Listar
 
delimiter $$
	create procedure sp_listarTelefonoProveedor()
    begin
		select TelefonoProveedor.codigoTelefonoProveedor, TelefonoProveedor.numeroPrincipal, TelefonoProveedor.numeroSecundario,
    TelefonoProveedor.observaciones, TelefonoProveedor.codigoProveedor
    from TelefonoProveedor;
    end$$
delimiter ;
 
call sp_listarTelefonoProveedor();
 
-- Buscar
 
delimiter $$
	create procedure sp_buscarTelefonoProveedor(in _codigoTelefonoProveedor int)
    begin
	select TelefonoProveedor.codigoTelefonoProveedor, TelefonoProveedor.numeroPrincipal, TelefonoProveedor.numeroSecundario,
    TelefonoProveedor.observaciones, TelefonoProveedor.codigoProveedor
    from TelefonoProveedor where TelefonoProveedor.codigoTelefonoProveedor = _codigoTelefonoProveedor;
	end$$
delimiter ;
 

 
-- Actualizar
 
delimiter $$
	create procedure sp_actualizarTelefonoProveedor(in _codigoTelefonoProveedor int, in _numeroPrincipal  varchar(8), in _numeroSecundario  varchar(8), in _observaciones  varchar(45), 
in _codigoProveedor  int)
begin
		update TelefonoProveedor
		set
		TelefonoProveedor.numeroPrincipal = _numeroPrincipal,
        TelefonoProveedor.numeroSecundario = _numeroSecundario,
        TelefonoProveedor.observaciones = _observaciones,
		TelefonoProveedor.codigoProveedor = _codigoProveedor
        where 
        TelefonoProveedor.codigoTelefonoProveedor = _codigoTelefonoProveedor;
    end$$
delimiter ;
 
-- call sp_actualizarTelefonoProveedor();
 
-- Eliminar
 
delimiter $$
	create procedure sp_eliminarTelefonoProveedor(in _codigoTelefonoProveedor int)
    begin
		delete from TelefonoProveedor where TelefonoProveedor.codigoTelefonoProveedor = _codigoTelefonoProveedor;
    end$$
delimiter ;



-- procedimientos de DetalleFactura




-- agregar

delimiter $$
create procedure sp_agregarDetalleFactura (in _codigoDetalleFactura int , in _precioUnitario decimal(10,2), in _cantidad int, in _numeroFactura  int, 
in _codigoProducto varchar(15))
begin
	insert into DetalleFactura (DetalleFactura.codigoDetalleFactura, DetalleFactura.precioUnitario, DetalleFactura.cantidad,
    DetalleFactura.numeroFactura, DetalleFactura.codigoProducto)
    values (_codigoDetalleFactura, _precioUnitario, _cantidad, _numeroFactura, _codigoProducto);
end$$
delimiter ;	
 
call sp_agregarDetalleFactura(1,12.00,1,1,'123');

-- Listar
 
delimiter $$
	create procedure sp_listarDetalleFactura()
    begin
		select DetalleFactura.codigoDetalleFactura, DetalleFactura.precioUnitario, DetalleFactura.cantidad,
    DetalleFactura.numeroFactura, DetalleFactura.codigoProducto
    from DetalleFactura;
    end$$
delimiter ;
 
call sp_listarTelefonoProveedor();
 
-- Buscar
 
delimiter $$
	create procedure sp_buscarDetalleFactura(in _codigoDetalleFactura int)
    begin
	select DetalleFactura.codigoDetalleFactura, DetalleFactura.precioUnitario, DetalleFactura.cantidad,
    DetalleFactura.numeroFactura, DetalleFactura.codigoProducto
    from DetalleFactura where DetalleFactura.codigoTelefonoProveedor = _codigoDetalleFactura;
	end$$
delimiter ;
 

 
-- Actualizar
 
delimiter $$
	create procedure sp_actualizarDetalleFactura(in _codigoDetalleFactura int , in _precioUnitario decimal(10,2), in _cantidad int, in _numeroFactura  int, 
in _codigoProducto varchar(15))
begin
		update DetalleFactura
		set
		DetalleFactura.precioUnitario = _precioUnitario,
        DetalleFactura.cantidad = _cantidad,
        DetalleFactura.numeroFactura = _numeroFactura,
		DetalleFactura.codigoProducto = _codigoProducto
        where 
        DetalleFactura.codigoDetalleFactura = _codigoDetalleFactura;
    end$$
delimiter ;
 
-- call sp_actualizarDetalleCompra();
 
-- Eliminar
 
delimiter $$
	create procedure sp_eliminarDetalleFactura(in _codigoTelefonoProveedor int)
    begin
		delete from DetalleFactura where TelefonoProveedor.codigoTelefonoProveedor = _codigoTelefonoProveedor;
    end$$
delimiter ;


ALTER USER 'Proyectos_IN5BM'@'localhost' IDENTIFIED WITH mysql_native_password BY 'abc123**';