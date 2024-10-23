package distri.gestion_reservas.service;



import distri.beans.domain.Reserva;
import distri.beans.dto.ReservaDTO;

import distri.gestion_reservas.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaService {
    @Autowired
    private final ReservaRepository reservaRepository;

    private ModelMapper modelMapper;

    public Page<ReservaDTO> findAll(Pageable pageable) {
        return reservaRepository.findAll(pageable)
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class));
    }

    public Optional<ReservaDTO> findById(Long id) {
        return reservaRepository.findById(id)
                .map(reserva -> modelMapper.map(reserva, ReservaDTO.class));
    }

    public ReservaDTO create(ReservaDTO reservaDTO) {
        Reserva reserva = modelMapper.map(reservaDTO, Reserva.class);
        Reserva savedReserva = reservaRepository.save(reserva);
        return modelMapper.map(savedReserva, ReservaDTO.class);
    }

    public Optional<ReservaDTO> update(Long id, ReservaDTO reservaDTO) {
        return reservaRepository.findById(id)
                .map(existingReserva -> {
                    Reserva updatedReserva = modelMapper.map(reservaDTO, Reserva.class);
                    updatedReserva.setId(existingReserva.getId());
                    return modelMapper.map(reservaRepository.save(updatedReserva), ReservaDTO.class);
                });
    }

    public void delete(Long id) {
        reservaRepository.deleteById(id);
    }
}
