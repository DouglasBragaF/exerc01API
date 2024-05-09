package br.org.serratec.exec01;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface CrudController<T> {
	ResponseEntity<T> adicionar(@RequestBody T entity);
    List<T> listar();
    ResponseEntity<T> obterPorId(@PathVariable int id);
    ResponseEntity<T> atualizar(@PathVariable int id, @RequestBody T entityAtualizada);
    ResponseEntity<Void> deletar(@PathVariable int id);
}
