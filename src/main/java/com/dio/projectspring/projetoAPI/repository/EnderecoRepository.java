package com.dio.projectspring.projetoAPI.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dio.projectspring.projetoAPI.model.Endereco;

@Repository
public interface EnderecoRepository extends CrudRepository<Endereco, String>{}