package bo.com.spaps.service;

import javax.ejb.Stateless;

import bo.com.spaps.model.Sucursal;

//The @Stateless annotation eliminates the need for manual transaction demarcation

@Stateless
public class SucursalRegistration extends DataAccessService<Sucursal>{
	public SucursalRegistration(){
		super(Sucursal.class);
	}

}
