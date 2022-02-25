package devsu.challenge.bank;

import java.util.List;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import devsu.challenge.bank.model.Producto;
import devsu.challenge.bank.repository.ProductoRepository;

@SpringBootApplication
public class BankApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner crearProductos(ProductoRepository productoRepository) {
        return args -> {
        	RestTemplate restTemplate = new RestTemplate();
    		ResponseEntity<Producto[]> response = restTemplate.getForEntity("https://mocki.io/v1/dda4fd1a-0219-4bdb-b95e-8112c60d1c8b",Producto[].class);
    		List<Producto> productos = Arrays.asList(response.getBody());
    		for(Producto producto : productos) {
    			productoRepository.save(producto);
    		}
        };
	}
	
}
