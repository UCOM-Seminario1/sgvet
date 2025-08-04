package com.sgvet.mascota.control;

import com.sgvet.mascota.boundary.MascotaRepository;
import com.sgvet.mascota.entity.Mascota;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class MascotaController {

    private static final Logger logger = Logger.getLogger(MascotaController.class.getName());
    private MascotaRepository mascotaRepository = new MascotaRepository();
    // Aquí puedes agregar métodos para manejar las solicitudes relacionadas con los Mascotas
    // Por ejemplo, crear, actualizar, eliminar y listar Mascotas

    public Boolean crearMascota(Mascota mascota) {
        // Lógica para crear un Mascota
        try{
            mascotaRepository.insertar(mascota);
            return true;
        } 
        catch (Exception e) {
            logger.log(Level.SEVERE, "Error al insertar mascota " + mascota.getNombre(), e);
            return false;
        }
    }

    public List<Mascota> listarMascotas() {
        // Lógica para listar todos los Mascotas
        return mascotaRepository.listarTodos();
    }

    public boolean eliminarMascota(int id) {
        return mascotaRepository.eliminarPorId(id);
    }
    
    public Mascota buscarMascotaPorId(int id) {
        return mascotaRepository.buscarPorId(id);
    }

    public List<Mascota> buscarMascotasPorNombre(String nombre) {
        return mascotaRepository.buscarPorNombre(nombre);
    }

    public List<Mascota> buscarMascotasPorCliente(int idCliente) {
        return mascotaRepository.buscarPorCliente(idCliente);
    }

    public boolean actualizarMascota(Mascota mascota) {
        return mascotaRepository.actualizar(mascota);
    }
}
