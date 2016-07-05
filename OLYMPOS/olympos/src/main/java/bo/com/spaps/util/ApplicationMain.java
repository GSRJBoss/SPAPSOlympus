package bo.com.spaps.util;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import bo.com.spaps.model.Usuario;

@Named
@Startup
@ApplicationScoped
public class ApplicationMain {
	
	//log
	private Logger log = Logger.getLogger(this.getClass());

	//list Object
/*	private List<PlanCuenta> listPlanCuenta;
	private List<CentroCosto> listCentroCosto;*/
	private List<Usuario> listUsuario;
	
	//Repository
	/*@Inject*/
/*	private PlanCuentaRepository planCuentaRepository;*/
	
	private int contadorTest = 0;

	@PostConstruct
	public void initAplicationMain(){
		contadorTest = contadorTest + 1;
		log.info(" -- initAplicationMain() ----");
		/*listPlanCuenta = planCuentaRepository.findAll();*/
	}

	/**
	 * obtiene lista cuentas auxiliare por empresa
	 * @return List<PlanCuenta>
	 * @param Empresa empresa
	 */
/*	public List<PlanCuenta> findPlanCuentaAuxiliarByEmpresa(Empresa empresa) {
		List<PlanCuenta> results = new ArrayList<PlanCuenta>();
		for(PlanCuenta i : listPlanCuenta) {
			if(i.getEmpresa().equals(empresa) && i.getClase().equals("AUXILIAR") ){
				results.add(i);
			}
		}
		log.info(" -- findPlanCuentaByEmpresa() results="+results.size()+" ----");
		return results;
	}

	*//**
	 * obtiene lista cuentas activas por empresa
	 * @return List<PlanCuenta>
	 * @param Empresa empresa
	 *//*
	public List<PlanCuenta> findAllActivoByEmpresa(Empresa empresa) {
		List<PlanCuenta> results = new ArrayList<PlanCuenta>();
		for(PlanCuenta i : listPlanCuenta) {
			if(i.getEmpresa().equals(empresa) && i.getEstado().equals("AC")){
				results.add(i);
			}
		}
		log.info(" -- findPlanCuentaByEmpresa() results="+results.size()+" ----");
		return results;
	}

	public void actualizarCuenta(PlanCuenta planCuenta){
		for(PlanCuenta pc : this.getListPlanCuenta()){
			if(pc.equals(planCuenta)){
				pc = planCuenta;
			}
		}
	}
	public void eliminarCuenta(PlanCuenta planCuenta){
		this.getListPlanCuenta().remove(planCuenta);
	}
	// -------------------------------------------

	public List<PlanCuenta> getListPlanCuenta() {
		return listPlanCuenta;
	}

	public void setListPlanCuenta(List<PlanCuenta> listPlanCuenta) {
		this.listPlanCuenta = listPlanCuenta;
	}

	public List<CentroCosto> getListCentroCosto() {
		return listCentroCosto;
	}

	public void setListCentroCosto(List<CentroCosto> listCentroCosto) {
		this.listCentroCosto = listCentroCosto;
	}*/

	public List<Usuario> getListUsuario() {
		return listUsuario;
	}

	public void setListUsuario(List<Usuario> listUsuario) {
		this.listUsuario = listUsuario;
	}

	public int getContadorTest() {
		return contadorTest;
	}

	public void setContadorTest(int contadorTest) {
		this.contadorTest = contadorTest;
	}

}
