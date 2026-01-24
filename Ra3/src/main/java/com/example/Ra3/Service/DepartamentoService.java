package com.example.Ra3.Service;

import com.example.Ra3.Modelo.Departamento;
import com.example.Ra3.Repository.DepartamentoRepository;
import com.example.Ra3.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoService {
    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Departamento> listar() {
        return departamentoRepository.findAll();
    }

    public Optional<Departamento> buscarPorId(Long id) {
        return departamentoRepository.findById(id);
    }

    public Departamento crear(String nombre) {
        if (!StringUtils.hasText(nombre)) throw new IllegalArgumentException("Nombre vacío");
        if (departamentoRepository.existsByNombre(nombre.trim()))
            throw new IllegalStateException("Nombre ya existe");
        Departamento d = new Departamento();
        d.setNombre(nombre.trim());
        return departamentoRepository.save(d);
    }

    public Departamento actualizar(Long id, String nombre) {
        Departamento d = departamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("No existe"));
        if (!StringUtils.hasText(nombre)) throw new IllegalArgumentException("Nombre vacío");
        if (!d.getNombre().equalsIgnoreCase(nombre.trim()) &&
                departamentoRepository.existsByNombre(nombre.trim())) {
            throw new IllegalStateException("Nombre ya existe");
        }
        d.setNombre(nombre.trim());
        return departamentoRepository.save(d);
    }

    public void eliminar(Long id) {
        long usados = usuarioRepository.countByDepartamento_Id(id);
        if (usados > 0) throw new DataIntegrityViolationException("Departamento en uso");
        departamentoRepository.deleteById(id);
    }
}

