set global time_zone = '-6:00';
drop database if exists DBKinalShop;
create database DBKinalShop;

use DBKinalShop;

create table Clientes (
clienteID int not null,
nitClientes varchar(10)not null,
nombreClientes varchar(50) not null,
apellidoClientes varchar(50)not null,
direccionClientes varchar(150)not null,
telefonoClientes varchar(15)not null,
CorreoClientes varchar(45)not null,
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
    NITProveedor varchar (10),
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
    codigoProveedor int not null ,
    NITProveedor varchar (10),
    nombresProveedor varchar(60),
    apellidosProveedor varchar(60),
    direccionProveedor varchar(150),
    razonSocial varchar(60),
    contactoPrincipal varchar(100),
    paginaWeb varchar(50),
    primary key PK_codigoProveedor (codigoProveedor)
);

insert into Clientes (clienteID, nombreClientes, apellidoClientes, nitClientes, direccionClientes,
telefonoClientes, CorreoClientes)
values(1, "xd","dx","12345-6","colonia 1ro de julio","12345678","sdfgjh@hhd.com");


delimiter $$
create procedure sp_agregarClientes (in _clienteID int, in _nombreClientes varchar(10), in _apellidoClientes varchar(50),
in _nitClientes varchar(50), in _direccionClientes varchar(150), in _telefonoClientes varchar(15), in _correoClientes varchar(45))
begin
	insert into Clientes (Clientes.clienteID, Clientes.nitClientes, Clientes.nombreClientes,
    Clientes.apellidoClientes, Clientes.direccionClientes, Clientes.telefonoClientes, Clientes.correoClientes)
    values (_clienteID, _nombreClientes, _apellidoClientes, nitClientes, _direccionClientes, _telefonoClientes, _correoClientes);
end$$
delimiter ;
 

 
delimiter $$
	create procedure sp_listarClientes ()
    begin
		select Clientes.clienteID, Clientes.nitClientes, Clientes.nombreClientes,
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
call sp_buscarCompras(3);
call sp_buscarCompras(4);
call sp_buscarCompras(5);

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

call sp_eliminarCompras(1);
