package com.dio.projectspring.projetoAPI.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dio.projectspring.projetoAPI.model.Cliente;
import com.dio.projectspring.projetoAPI.model.Endereco;
import com.dio.projectspring.projetoAPI.repository.ClienteRepository;
import com.dio.projectspring.projetoAPI.repository.EnderecoRepository;
import com.dio.projectspring.projetoAPI.service.ClienteService;
import com.dio.projectspring.projetoAPI.service.ViaCepService;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private ViaCepService viaCepService;

    @Override
    public Iterable<Cliente> buscarTodos() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        if(cliente.get() instanceof Cliente) return cliente.get();
        return null;
    }

    @Override
    public void inserir(Cliente cliente) {
        cliente = verificaCepESalva(cliente);
        clienteRepository.save(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        Optional<Cliente> clienteBd = clienteRepository.findById(id);
        if(clienteBd.isPresent()){
            Cliente update = clienteBd.get();
            update = verificaCepESalva(cliente);
            clienteRepository.save(update);
        }
        
    }

    @Override
    public void deletar(Long id) {
        clienteRepository.deleteById(id);
    }

    private Cliente verificaCepESalva(Cliente cliente){
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            enderecoRepository.save(viaCepService.consultarCep(cep));
            return viaCepService.consultarCep(cep);
        });
        cliente.setEndereco(endereco);
        return cliente;
    }
    
}
