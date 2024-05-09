package br.org.serratec.exec01;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/veiculo")
public class VeiculoController implements CrudController<Veiculo> {
	List<Veiculo> veiculos = new ArrayList<>();
	private int id = 0;

	
	
	@Override
	// Criação de um novo veículo
    @PostMapping
	public ResponseEntity<Veiculo> adicionar(@RequestBody Veiculo entity) {
		id += 1;
		entity.setId(id);
        veiculos.add(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(entity);
	}

	@Override
	// Recuperar todos os veículos
    @GetMapping
	public List<Veiculo> listar() {
		return veiculos;
	}

	@Override
	// Recuperar um veículo específico pelo ID
    @GetMapping("/{id}")
	public ResponseEntity<Veiculo> obterPorId(@PathVariable int id) {
		Optional<Veiculo> veiculoEncontrado = encontrarVeiculoPeloId(id);
        return veiculoEncontrado
                .map(ResponseEntity::ok) // Se o veículo for encontrado, retorna o veículo com status 200 OK.
                .orElseGet(() -> ResponseEntity.notFound().build());
	}

	@Override
	// Atualizar um veiculo específico pelo ID
    @PutMapping("/{id}")
	public ResponseEntity<Veiculo> atualizar(
			@PathVariable int id,
			@RequestBody Veiculo entityAtualizada
			) {
		Optional<Veiculo> veiculoEncontrado = encontrarVeiculoPeloId(id);
   	 
   	 if (veiculoEncontrado != null) {
   		 int index =  veiculos.indexOf(veiculoEncontrado.get());
   		entityAtualizada.setId(id);    		 
   		 veiculos.set(index, entityAtualizada);
 			return ResponseEntity.ok(entityAtualizada);
   	 }
   	 return ResponseEntity.notFound().build();
	}

	@Override
	// Deletar um veículo pelo ID
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(int id) {
		Optional<Veiculo> veiculoEncontrado = encontrarVeiculoPeloId(id);

	    if (veiculoEncontrado.isPresent()) {
	        veiculos.remove(veiculoEncontrado.get());
	        return ResponseEntity.ok().build();
	    }
	    return ResponseEntity.notFound().build();
	}
     
  // Método privado para encontrar um veículo pelo ID
     private Optional<Veiculo> encontrarVeiculoPeloId(int id) {
         return veiculos.stream()
                        .filter(veiculo -> veiculo.getId() == id)
                        .findFirst();
     }
}
