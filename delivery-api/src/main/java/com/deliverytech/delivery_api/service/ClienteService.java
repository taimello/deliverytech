package com.deliverytech.delivery_api.service;

import java.util.List;

import com.deliverytech.delivery_api.dto.response.ClienteResponseDTO;
import com.deliverytech.delivery_api.dto.request.ClienteDTO;

public interface ClienteService {
    ClienteResponseDTO cadastrarCliente(ClienteDTO dto);
    ClienteResponseDTO buscarClientePorId(Long id);
    ClienteResponseDTO buscarClientePorEmail(String email);
    ClienteResponseDTO atualizarCliente(Long id, ClienteDTO dto);
    ClienteResponseDTO ativarDesativarCliente(Long id);
    List<ClienteResponseDTO> listarClientesAtivos();    
}
