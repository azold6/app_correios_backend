package com.alex.projeto_correios.services;

import com.alex.projeto_correios.domain.Cliente;
import com.alex.projeto_correios.domain.enums.Perfil;
import com.alex.projeto_correios.exceptions.AccessDeniedException;
import com.alex.projeto_correios.exceptions.ObjectNotFoundException;
import com.alex.projeto_correios.repositories.ClienteRepository;
import com.alex.projeto_correios.security.UserSpringSecurity;
import com.alex.projeto_correios.utils.LoggedUserUtils;
import com.alex.projeto_correios.utils.PasswordUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private PasswordUtils passwordUtils;

    @Autowired
    private ClienteRepository repo;

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private EmailService emailService;

    public Cliente findById(Integer id){

        UserSpringSecurity loggedUser = LoggedUserUtils.getAuthenticatedUser();

        if(loggedUser == null || !loggedUser.hasRole(Perfil.ADMIN) && !id.equals(loggedUser.getId())){
            throw new AccessDeniedException("Acesso negado");
        }

        Optional<Cliente> obj = repo.findById(id);

        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public void delete(Cliente cli){
         repo.delete(cli);
    }

    public Cliente update(Integer id, Cliente new_cliente){
        Cliente old = this.findById(id);

        if (old == null)
            throw new ObjectNotFoundException("Objeto não encontrado");

        new_cliente.setId(old.getId());
        return repo.save(new_cliente);

    }

    public List<Cliente> findAll(){
        List<Cliente> obj = repo.findAll();
        return obj;
    }

    public Cliente findByEmail(String email){
        Cliente obj = repo.findByEmail(email);
        return obj;
    }


    public Cliente newPassword(Cliente obj){
        obj.setSenha(passwordUtils.newPassword());
        emailService.sendNewPasswordEmail(obj);
        obj.setSenha(pe.encode(obj.getSenha()));
        return repo.save(obj);
    }

    public Cliente insert(Cliente cliente){
        System.out.println(cliente.getNome());
        System.out.println(cliente.getEmail());
        System.out.println(cliente.getSenha());
        cliente.setSenha(pe.encode(cliente.getSenha()));
        return this.repo.save(cliente);
    }
}
