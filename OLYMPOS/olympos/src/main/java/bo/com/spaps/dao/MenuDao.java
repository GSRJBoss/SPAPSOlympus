/**
 * 
 */
package bo.com.spaps.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import bo.com.spaps.model.Menu;
import bo.com.spaps.model.MenuAccion;
import bo.com.spaps.util.FacesUtil;
import bo.com.spaps.util.O;
import bo.com.spaps.util.P;
import bo.com.spaps.util.Q;
import bo.com.spaps.util.R;
import bo.com.spaps.util.S;
import bo.com.spaps.util.U;
import bo.com.spaps.util.V;
import bo.com.spaps.util.W;

/**
 * @author ANITA
 *
 */

@Stateless
public class MenuDao extends
		DataAccessObjectJpa<Menu, MenuAccion, R, S, O, P, Q, U, V, W> {

	public MenuDao() {
		super(Menu.class, MenuAccion.class);
	}

	public Menu registrar(Menu menu, List<MenuAccion> menuAccion) {
		try {
			beginTransaction();
			menu = create(menu);
			for (MenuAccion menuAccion2 : menuAccion) {
				menuAccion2.setMenu(menu);
				menuAccion2.setFechaRegistro(new Date());
				menuAccion2 = createE(menuAccion2);
			}
			commitTransaction();
			FacesUtil.infoMessage("Registro Correcto",
					"Menu " + menu.toString());
			return menu;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al registrar");
			}
			rollbackTransaction();
			return null;
		}
	}

	public Menu modificar(Menu menu) {
		try {
			beginTransaction();
			menu = update(menu);
			commitTransaction();
			FacesUtil.infoMessage("Modificación Correcta",
					"Menu " + menu.toString());
			return menu;
		} catch (Exception e) {
			String cause = e.getMessage();
			if (cause
					.contains("org.hibernate.exception.ConstraintViolationException: could not execute statement")) {
				FacesUtil.errorMessage("Ya existe un registro igual.");
			} else {
				FacesUtil.errorMessage("Error al modificar");
			}
			rollbackTransaction();
			return null;
		}
	}

	public boolean eliminar(Menu menu) {
		try {
			beginTransaction();
			menu.setEstado("RM");
			Menu bar = modificar(menu);
			commitTransaction();
			FacesUtil.infoMessage("Eliminación Correcta",
					"Menu " + menu.toString());
			return bar != null ? true : false;
		} catch (Exception e) {
			FacesUtil.errorMessage("Error al eliminar");
			rollbackTransaction();
			return false;
		}
	}

	public Menu obtenerMenu(Integer id) {
		return findById(id);
	}

	public Menu obtenerMenuPorNombre(String nombre) {
		return findByParameter("nombre", nombre);
	}

	public List<Menu> obtenerMenusPorNombre(String nombre) {
		String query = "select em.* from Menu em where nombre like '" + nombre
				+ "'";
		return executeQueryResulList(query);
	}

	public List<Menu> obtenerMenusPorModulo(Integer id) {
		return findAllActivosByParameter("modulo", id);
	}

	public List<Menu> obtenerMenusPorModulo(String nombre) {
		return findAllActivosByParameter("modulo", nombre);
	}

	public List<Menu> obtenerMenuOrdenAscPorId() {
		return findAscAllOrderedByParameter("id");
	}

	public List<Menu> obtenerMenuOrdenDescPorId() {
		return findDescAllOrderedByParameter("id");
	}

}
