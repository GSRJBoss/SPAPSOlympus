package bo.com.spaps.service;

import javax.ejb.Stateless;
import bo.com.spaps.model.Empresa;

//The @Stateless annotation eliminates the need for manual transaction demarcation
@Stateless
public class EmpresaRegistration extends DataAccessService<Empresa>{
	
	public EmpresaRegistration(){
		super(Empresa.class);
	}

}