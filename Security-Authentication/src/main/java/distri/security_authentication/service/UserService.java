package distri.security_authentication.service;


import distri.beans.domain.Rol;
import distri.beans.domain.Usuario;
import distri.beans.dto.UsuarioDTO;
import distri.security_authentication.dto.RegisterRequestDTO;
import distri.security_authentication.repository.RolRepository;
import distri.security_authentication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public void registerUser(RegisterRequestDTO registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("El email ya está en uso");
        }

        Usuario usuario = modelMapper.map(registerRequest, Usuario.class);
        usuario.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        usuario.setDeleted(false);
        Rol rolUsuario = rolRepository.findByNombre("USER")
                .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));

        usuario.setRol(rolUsuario);

        userRepository.save(usuario);
    }


    public UsuarioDTO findByEmail(String email) {
        Usuario usuario = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario con email no encontrado"));

        return modelMapper.map(usuario, UsuarioDTO.class);
    }


    public List<UsuarioDTO> getAllUsers() {
       List<Usuario> usuarios = userRepository.findAll();
        return usuarios.stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());

    }
}

