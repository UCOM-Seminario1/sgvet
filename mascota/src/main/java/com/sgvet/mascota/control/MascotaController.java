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

    public Boolean crearMascota(Mascota Mascota) {
        // Lógica para crear un Mascota
        try{
            mascotaRepository.insertar(Mascota);
            return true;
        } 
        catch (Exception e) {
            if (logger.isLoggable(Level.SEVERE)) {
                logger.log(Level.SEVERE, "Error al crear mascota: " + Mascota.getNombre(), e);
            }
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
    
    public Mascota buscarMascotaPorId(Integer id) {
        if (id == null) {
            if (logger.isLoggable(Level.WARNING)) {
                logger.log(Level.WARNING, "El valor de id es requerido");
            }
            throw new IllegalArgumentException("El valor de id es requerido");
        }
        return mascotaRepository.buscarPorId(id);
    }

    public List<Mascota> buscarMascotasPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            if (logger.isLoggable(Level.WARNING)) {
                logger.log(Level.WARNING, "El valor de nombre es requerido");
            }
            throw new IllegalArgumentException("El valor de nombre es requerido");
        }
        return mascotaRepository.buscarPorNombre(nombre);
    }

    public List<Mascota> buscarMascotasPorCliente(Integer idCliente) {
        if (idCliente == null) {
            if (logger.isLoggable(Level.WARNING)) {
                logger.log(Level.WARNING, "El valor de idCliente es requerido");
            }
            throw new IllegalArgumentException("El valor de idCliente es requerido");
        }
        return mascotaRepository.buscarPorCliente(idCliente);
    }

    public boolean actualizarMascota(Mascota mascota) {
        return mascotaRepository.actualizar(mascota);
    }
}
