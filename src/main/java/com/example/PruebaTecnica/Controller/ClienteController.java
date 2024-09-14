package com.example.PruebaTecnica.Controller;
import com.example.PruebaTecnica.Repository.ClienteRepository;
import com.example.PruebaTecnica.model.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.PruebaTecnica.model.Cliente;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/registro")
    public Cliente registrarCliente(@RequestBody Cliente cliente) {
        cliente.setPassword(passwordEncoder.encode(cliente.getPassword()));

        if (cliente.getRol() == null) {
            cliente.setRol(Rol.EXTERNO);
        }

        return clienteRepository.save(cliente);
    }


    @PostMapping("/login")
    public Cliente login(@RequestBody Cliente cliente) {
        Cliente usuario = clienteRepository.findByCorreo(cliente.getCorreo());

        if (usuario != null && passwordEncoder.matches(cliente.getPassword(), usuario.getPassword())) {
            // Puedes agregar lógica según el rol si es necesario
            if (usuario.getRol() == Rol.ADMIN) {
                System.out.println("Administrador ha iniciado sesión");
            }
            return usuario;
        }
        throw new RuntimeException("Usuario o contraseña incorrectos");
    }

    @GetMapping("/admin")
    public String adminAction(@RequestParam Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        if (cliente.getRol() == Rol.ADMIN) {
            return "Acción permitida para el administrador";
        }
        throw new RuntimeException("Acceso denegado. No tienes permiso para realizar esta acción.");
    }
}

