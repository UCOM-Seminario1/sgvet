package com.sgvet.facturacion.control;

import com.sgvet.facturacion.boundary.FacturacionRepository;
import com.sgvet.facturacion.entity.Facturacion;

import java.util.List;

public class FacturacionController {

    private FacturacionRepository facturacionRepository = new FacturacionRepository();
    // Aquí puedes agregar métodos para manejar las solicitudes relacionadas con los Facturacions
    // Por ejemplo, crear, actualizar, eliminar y listar Facturacions

    public Boolean crearFacturacion(Facturacion Facturacion) {
        // Lógica para crear un Facturacion
        try{

            facturacionRepository.insertar(Facturacion);
            return true;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Facturacion> listarFacturacions() {
        // Lógica para listar todos los Facturacions
        return facturacionRepository.listarTodos();
    }

    public boolean eliminarFacturacion(int id) {
        return facturacionRepository.eliminarPorId(id);
    }
    
}
