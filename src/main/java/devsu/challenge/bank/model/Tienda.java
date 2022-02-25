package devsu.challenge.bank.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Entity
@Table(name = "tienda")
@Getter
@Setter
@NoArgsConstructor
public class Tienda {
	
	public static final int NAME_MAX_SIZE = 100;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "idTienda", nullable = false)
    private Long idTienda;
	
	@Column(name = "nombre", nullable = false, length = NAME_MAX_SIZE)
    private String nombre;
	
	@Singular
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true, mappedBy = "tienda")
    @Setter(AccessLevel.PRIVATE)
    private List<ProductoPorTienda> productosPorTienda = new ArrayList<>();

	public Tienda(String nombre) {
		this.setNombre(nombre);
	}
	
	public List<ProductoPorTienda> getProductosPorTienda(){
		return List.copyOf(this.productosPorTienda);
	}
	
	public void addProductosPorTienda(ProductoPorTienda productoPorTienda) {
		productosPorTienda.add(productoPorTienda);
	}
	
}
