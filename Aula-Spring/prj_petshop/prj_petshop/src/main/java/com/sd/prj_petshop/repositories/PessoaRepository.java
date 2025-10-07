package com.sd.prj_petshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sd.prj_petshop.models.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

}
